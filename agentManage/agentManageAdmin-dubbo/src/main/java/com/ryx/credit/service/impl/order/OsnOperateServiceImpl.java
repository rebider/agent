package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OsnOperateService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作者：cx
 * 时间：2019/4/19
 * 描述：
 */
@Service("osnOperateService")
public class OsnOperateServiceImpl implements com.ryx.credit.service.order.OsnOperateService {

    private static Logger logger = LoggerFactory.getLogger(OsnOperateServiceImpl.class);

    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private OReturnOrderDetailMapper oReturnOrderDetailMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private ImsTermWarehouseDetailService imsTermWarehouseDetailService;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private IdService idService;
    @Resource(name = "osnOperateService")
    private OsnOperateService osnOperateService;

    /**
     * 根据物流联动状态查询物流id
     * 0：未联动 1：已联动 2：联动失败， 3：部分联动失败， 4：生成明细失败，5：生成明细中，6：生成明细成功7：联动业务系统处理中
     * @param sendStatus
     * @return
     */
    @Override
    public List<String> queryLogicInfoIdByStatus(LogType logType, LogisticsSendStatus sendStatus){
        return oLogisticsMapper.queryLogicInfoIdByStatus(FastMap.fastMap("logType", logType.code).putKeyV("sendStatus",sendStatus.code));
    }


    /**
     * 生成物流执行的任务
     */
    @Override
    public void genLogicDetailTask(){

        List<String>  list = queryLogicInfoIdByStatus(LogType.Deliver,LogisticsSendStatus.none_send);
        if(list.size()>0) {
            logger.info("开始执行sn明细生成任务");
            list.forEach(id -> {
                OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                logistics.setSendStatus(LogisticsSendStatus.gen_detail_ing.code);
                if (1 == oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                    try {
                        if (osnOperateService.genOlogicDetailInfo(id)) {
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        } else {
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        }
                    } catch (MessageException e) {
                        e.printStackTrace();
                        logger.error("生成物流明细任务异常：", e);
                        logistics = oLogisticsMapper.selectByPrimaryKey(id);
                        logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                        logistics.setSendMsg(e.getMsg());
                        oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("生成物流明细任务异常：", e);
                        logistics = oLogisticsMapper.selectByPrimaryKey(id);
                        logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                        logistics.setSendMsg(e.getLocalizedMessage().length() > 30 ? e.getLocalizedMessage().substring(0, 30) : e.getLocalizedMessage());
                        oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    }
                }

            });
            logger.info("开始执行sn明细生成结束");
        }
    }


    /**
     * 执行同步sn任务，抓取发货业务 待处理的物流信息id,进行分配处理
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public List<String> fetchFhData(int nodecount,int shardingItem)throws Exception{

        //查询待处理的物流列表，并更新成处理中
         List<String> data = oLogisticsMapper.queryLogicInfoIdByStatus(FastMap
        .fastMap("logType",LogType.Deliver.code)
        .putKeyV("sendStatus",LogisticsSendStatus.gen_detail_sucess.code)
        .putKeyV("pagesize",200));

         //更新物流为下发处理中，任务更新状态，下次不再查询
        data.forEach(ids ->{
            OLogistics oLogistics = oLogisticsMapper.selectByPrimaryKey(ids);
            oLogistics.setSendStatus(LogisticsSendStatus.send_ing.code);
            if(oLogisticsMapper.updateByPrimaryKeySelective(oLogistics)!=1){
                logger.info("查询待处理的物流列表，并更新成处理中失败:{}",ids);
            }
        });

        //查询处理中的数据进行业务处理
        data = oLogisticsMapper.queryLogicInfoIdByStatus(FastMap
                .fastMap("logType",LogType.Deliver.code)
                .putKeyV("sendStatus",LogisticsSendStatus.send_ing.code)
                .putKeyV("pagesize",200));
        return data;
    }


    /**
     * 处理要处理的物流明细信息
     * @param ids
     * @return
     */
    @Override
    public boolean processData(List<String> ids){

        if(ids!=null && ids.size()>0) {

            ids.forEach(id -> {

                //根据物流id查找sn明细，并更新物流明细发送状态为待发送状态，200单位数量为1批次，避免接口错误进行接口请求数量限制。
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or()
                        .andLogisticsIdEqualTo(id)
                        .andSendStatusEqualTo(LogisticsDetailSendStatus.none_send.code)
                        .andStatusEqualTo(Status.STATUS_1.status);
                oLogisticsDetailExample.setOrderByClause(" sn_num asc ");

                //每次处理两百条数据
                oLogisticsDetailExample.setPage(new Page(0,200));
                List<OLogisticsDetail> logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);

                //批次格式为YYYYMMddHHmmss + inerBatch
                String date  = DateUtil.format(Calendar.getInstance().getTime(),"yyyyMMddHHmmss");
                BigDecimal inerBatch = BigDecimal.ONE;
                String batch = date +inerBatch;
                //直到物流明细处理完成
                do{
                    try {
                        //在一个独立事物中进行数据更新批次信息及状态信息
                        List<OLogisticsDetail> list = osnOperateService.updateDetailBatch(logisticsDetails,new BigDecimal(batch));
                        //发送到业务系统，根据批次号
                        if(osnOperateService.sendInfoToBusinessSystem(list,id,new BigDecimal(batch))){
                            logger.info("物流明细发送业务系统处理成功,{},{}",id,batch);
                        }else{
                            logger.info("物流明细发送业务系统处理失败,{},{}",id,batch);
                            //更新物流为发送失败停止发送，人工介入
                            OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                            logistics.setSendMsg("处理失败请查看处理物流明细");
                            if(oLogisticsMapper.updateByPrimaryKeySelective(logistics)!=1){
                                logger.info("物流明细发送业务系统处理失败，更新数据库失败,{},{}",id,batch);
                            }
                            //处理失败就停止
                            break;
                        }
                    } catch (MessageException e) {
                        e.printStackTrace();
                        //更新物流为发送失败停止发送，人工介入
                        OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(id);
                        logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                        logistics.setSendMsg(e.getMsg());
                        if(oLogisticsMapper.updateByPrimaryKeySelective(logistics)!=1){
                            logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}",id,batch);
                        }
                        //处理失败就停止
                        break;

                    }catch (Exception e) {
                        e.printStackTrace();
                        //更新物流为发送失败停止发送，人工介入
                        OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(id);
                        logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                        logistics.setSendMsg(e.getLocalizedMessage());
                        if(oLogisticsMapper.updateByPrimaryKeySelective(logistics)!=1){
                            logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}",id,batch);
                        }
                        //处理失败就停止
                        break;
                    }
                    //调用接口发送到业务系统，如果接口返回异常，更新物流明细发送失败，不在进行发送
                    logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);

                    inerBatch = inerBatch.add(BigDecimal.ONE);
                    batch = date +inerBatch.intValue();

                //检查是否包含有未发送的sn，如果有继续循环发送，如果没有更新物流记录为发送成功
                }while (logisticsDetails.size()>0);

                //检查是否包含有未发送的sn,如果没有更新为处理成功
                OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(id);
                if(LogisticsSendStatus.send_fail.equals(logistics.getSendStatus()) && LogisticsSendStatus.send_part_fail.equals(logistics.getSendStatus())) {
                    if(oLogisticsDetailMapper.countByExample(oLogisticsDetailExample)==0) {
                        logistics.setSendStatus(LogisticsSendStatus.send_success.code);
                        logistics.setSendMsg("成功");
                        if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                            logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}", id, batch);
                        }
                    }
                }
            });

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public List<OLogisticsDetail> updateDetailBatch(List<OLogisticsDetail>  datas,BigDecimal batch)throws Exception{
        Calendar c = Calendar.getInstance();
        for (OLogisticsDetail oLogisticsDetail : datas) {
            oLogisticsDetail.setuTime(c.getTime());
            oLogisticsDetail.setSendStatus(LogisticsDetailSendStatus.send_ing.code);
            oLogisticsDetail.setSbusBatch(batch);
            if(1!=oLogisticsDetailMapper.updateByPrimaryKeySelective(oLogisticsDetail)){
                throw new MessageException("更新失败:"+oLogisticsDetail.getId());
            }
        }
        return datas;
    }



    /**
     * 根据物流信息生成物流明细,物流明细生成后进行物流状态的更新，更新为 4：生成明细失败 5：生成明细中 6：生成明细成功 添加版本号控制
     * @param logcId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public boolean genOlogicDetailInfo(String logcId)throws Exception{
        logger.info("开始生成物流明细：{}",logcId);
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logcId);
        //检查状态是否是为生成中，待处理
        if(!LogisticsSendStatus.gen_detail_ing.code.equals(logistics.getSendStatus())){
            logger.info("只处理未联动的物流：{}",logcId);
            throw new MessageException("只处理未联动的物流");
        }
        List<String>  ids = null;
        try {
            //生成sn
            ids =oLogisticsService.idList(logistics.getSnBeginNum(),logistics.getSnEndNum(),0,0,logistics.getProCom());
            if(ids.size()==0){
                throw new MessageException("物流明细列表生成为空列表");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            logger.info("sn生成异常{},{},{}",logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
            logger.error("sn生成异常",e);
            throw e;
        }
        //查询订单信息
        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
        String orderId = planVo.getOrderId();//订单ID
        String proId = planVo.getProId();//收货单商品id
        OReceiptPro oReceiptPro  = oReceiptProMapper.selectByPrimaryKey(proId);
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(example);
        if(oSubOrders.size()==0){
            throw new MessageException("商品价格未能锁定");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andProIdEqualTo(oSubOrder.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity>  OSubOrderActivitylist = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());



        //手刷生成物流方式 根据机具类型确定机具明细的生成方式，首刷更新明细记录
        if(logistics.getProType().equals(PlatformType.MPOS.msg) || logistics.getProType().equals(PlatformType.MPOS.code)){
            logger.info("首刷发货 更新库存记录:{}:{}-{}",logistics.getProType(),logistics.getSnBeginNum(),logistics.getSnEndNum());
            //遍历sn进行逐个更新
            for (String idSn : ids) {
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andStatusEqualTo(Status.STATUS_0.status).andRecordStatusEqualTo(Status.STATUS_1.status).andSnNumEqualTo(idSn).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                List<OLogisticsDetail>  listOLogisticsDetailSn = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if (listOLogisticsDetailSn==null){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不存在:"+idSn);
                }else if(listOLogisticsDetailSn.size()!=1){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不存在:"+idSn);
                }
                //获取物流明细并更新
                OLogisticsDetail detail = listOLogisticsDetailSn.get(0);
                //id，物流id，创建人，更新人，状态
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logcId);
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(OSubOrderActivitylist.size()>0){
                    OSubOrderActivity oSubOrderActivity = OSubOrderActivitylist.get(0);
                    detail.setActivityId(oSubOrderActivity.getActivityId());
                    detail.setActivityName(oSubOrderActivity.getActivityName());
                    detail.setgTime(oSubOrderActivity.getgTime());
                    detail.setBusProCode(oSubOrderActivity.getBusProCode());
                    detail.setBusProName(oSubOrderActivity.getBusProName());
                    detail.setTermBatchcode(oSubOrderActivity.getTermBatchcode());
                    detail.setTermBatchname(oSubOrderActivity.getTermBatchname());
                    detail.setTermtype(oSubOrderActivity.getTermtype());
                    detail.setTermtypename(oSubOrderActivity.getTermtypename());
                    detail.setSettlementPrice(oSubOrderActivity.getPrice());
                    detail.setPosType(oSubOrderActivity.getPosType());
                    detail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                    detail.setStandTime(oSubOrderActivity.getStandTime());
                }
                detail.setAgentId(order.getAgentId());
                detail.setcUser(logistics.getcUser());
                detail.setuUser(logistics.getcUser());
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = oReturnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                if (1 != oLogisticsDetailMapper.updateByPrimaryKeySelective(detail)) {
                    logger.info("修改失败");
                    throw new MessageException("更新物流明细失败："+idSn);
                }
            }
        }else{
        //POS生成物流方式 根据机具类型确定机具明细的生成方式,pos生成明细记录
            for (String idSn : ids) {
                OLogisticsDetail detail = new OLogisticsDetail();
                //id，物流id，创建人，更新人，状态
                detail.setId(idService.genId(TabId.o_logistics_detail));
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logistics.getId());
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(OSubOrderActivitylist.size()>0){
                    OSubOrderActivity oSubOrderActivity = OSubOrderActivitylist.get(0);
                    detail.setActivityId(oSubOrderActivity.getActivityId());
                    detail.setActivityName(oSubOrderActivity.getActivityName());
                    detail.setgTime(oSubOrderActivity.getgTime());
                    detail.setBusProCode(oSubOrderActivity.getBusProCode());
                    detail.setBusProName(oSubOrderActivity.getBusProName());
                    detail.setTermBatchcode(oSubOrderActivity.getTermBatchcode());
                    detail.setTermBatchname(oSubOrderActivity.getTermBatchname());
                    detail.setTermtype(oSubOrderActivity.getTermtype());
                    detail.setTermtypename(oSubOrderActivity.getTermtypename());
                    detail.setSettlementPrice(oSubOrderActivity.getPrice());
                    detail.setPosType(oSubOrderActivity.getPosType());
                    detail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                    detail.setStandTime(oSubOrderActivity.getStandTime());
                }
                detail.setSnNum(idSn);
                detail.setAgentId(order.getAgentId());
                detail.setcUser(logistics.getcUser());
                detail.setuUser(logistics.getcUser());
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = oReturnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("物流明细添加失败:{},{}",logistics.getId(),idSn);
                    throw new MessageException("物流明细添加失败:"+logistics.getId()+":"+idSn);
                }
            }
        }
        return true;
    }


    /**
     * 将已生成物流明细成功的物流，待联动的物流明细，分页发送到业务系统，并检查是否发送完成，完成后更新物流信息未发送完毕
     * @param logcId
     * @param batch
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public boolean sendInfoToBusinessSystem(List<OLogisticsDetail>  datas,String logcId,BigDecimal batch)throws Exception {
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logcId);
        //查询要发送的sn，根据sn进行排序
        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
        oLogisticsDetailExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)//发货
                .andRecordStatusEqualTo(Status.STATUS_1.status)//有效
                .andSendStatusEqualTo(LogisticsDetailSendStatus.send_ing.code)//处理中的sn
                .andLogisticsIdEqualTo(logcId)
                .andSbusBatchEqualTo(batch);
        oLogisticsDetailExample.setOrderByClause(" SN_NUM asc ");
        List<OLogisticsDetail> listOLogisticsDetailSn = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
        List<String> snList = new ArrayList<>();
        listOLogisticsDetailSn.forEach(detail -> {
            snList.add(detail.getSnNum());
        });

        //查询订单信息
        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
        String orderId = planVo.getOrderId();//订单ID
        String proId = planVo.getProId();//收货单商品id
        OReceiptPro oReceiptPro = oReceiptProMapper.selectByPrimaryKey(proId);
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(example);
        if (oSubOrders.size() == 0) {
            throw new MessageException("商品价格未能锁定");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andProIdEqualTo(oSubOrder.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        //订单商品活动
        List<OSubOrderActivity> OSubOrderActivitylist = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        OSubOrderActivity oSubOrderActivity = null;
        if (OSubOrderActivitylist.size() > 0) {
            oSubOrderActivity = OSubOrderActivitylist.get(0);
        }

        //订单信息
        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
        PlatForm platForm = platFormMapper.selectByPlatFormNum(order.getOrderPlatform());
        Date date = Calendar.getInstance().getTime();


        OActivity oActivity = oActivityMapper.selectByPrimaryKey(oSubOrderActivity.getActivityId());

        //流量卡不进行下发操作
        if(oActivity!=null && com.ryx.credit.commons.utils.StringUtils.isNotBlank(oActivity.getActCode()) && "2204".equals(oActivity.getActCode())){
            logger.info("导入物流数据,流量卡不进行下发操作，活动代码{}={}={}" ,oActivity.getActCode(),logcId, JSONObject.toJSON(logistics));
            listOLogisticsDetailSn.forEach(detail -> {
                detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                detail.setSbusMsg("流量卡不下发");
                detail.setuTime(date);
                oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
            });
            return true;
        }

        if (platForm.getPlatformType().equals(PlatformType.POS.code) || platForm.getPlatformType().equals(PlatformType.ZPOS.code)) {
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            if (null == order) {
                throw new MessageException("查询订单数据失败！");
            }
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            if (null == agentBusInfo) {
                throw new MessageException("查询业务数据失败！");
            }
            imsTermWarehouseDetail.setOrgId(agentBusInfo.getBusNum());
            imsTermWarehouseDetail.setMachineId(oSubOrderActivity.getBusProCode());
            imsTermWarehouseDetail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
            imsTermWarehouseDetail.setPosType(oSubOrderActivity.getPosType());
            imsTermWarehouseDetail.setStandTime(oSubOrderActivity.getStandTime());
            try {
                //机具下发接口
                AgentResult posSendRes = imsTermWarehouseDetailService.insertWarehouseAndTransfer(snList, imsTermWarehouseDetail);
                //机具下发成功，更新物流明细为下发成功
                if (posSendRes.isOK()) {
                    logger.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), posSendRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                        detail.setSbusMsg(posSendRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    return true;
                    //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败
                } else {
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), posSendRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(posSendRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    return false;
                }
                //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败，禁止继续发送 ,人工介入
            } catch (MessageException e) {
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                listOLogisticsDetailSn.forEach(detail -> {
                    detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                    detail.setSbusMsg(e.getMsg());
                    detail.setuTime(date);
                    oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                });
                return false;
                //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败 ，禁止继续发送,人工介入
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                listOLogisticsDetailSn.forEach(detail -> {
                    detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                    detail.setSbusMsg("异常");
                    detail.setuTime(date);
                    oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                });
                return false;
            }
            //首刷下发业务系统
        } else if (platForm.getPlatformType().equals(PlatformType.MPOS.code)) {
            //最大sn
            Optional<OLogisticsDetail> optional_max = listOLogisticsDetailSn.stream().collect(Collectors.maxBy((ar1, ar2) -> {
                return ar1.getSnNum().compareTo(ar2.getSnNum());
            }));
            //最小sn
            Optional<OLogisticsDetail> optional_min = listOLogisticsDetailSn.stream().collect(Collectors.minBy((ar1, ar2) -> {
                return ar1.getSnNum().compareTo(ar2.getSnNum());
            }));
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            //sn号码段
            LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
            lowerHairMachineVo.setBusNum(agentBusInfo.getBusNum());
            lowerHairMachineVo.setOptUser(logistics.getcUser());
            lowerHairMachineVo.setSnStart(optional_min.get().getSnNum() + optional_min.get().getTerminalidCheck());
            lowerHairMachineVo.setSnEnd(optional_min.get().getSnNum() + optional_min.get().getTerminalidCheck());
            lowerHairMachineVo.setoLogisticsId(logistics.getId());
            //sn明细
            List<MposSnVo> listSn = new ArrayList<MposSnVo>();
            String sBusProCode = "";
            for (OLogisticsDetail forsendSn : listOLogisticsDetailSn) {
                listSn.add(new MposSnVo(forsendSn.getTermBatchcode()
                        , forsendSn.getSnNum() + forsendSn.getTerminalidCheck()
                        , forsendSn.getTerminalidKey()
                        , forsendSn.getBusProCode()
                        , forsendSn.getTermtype()));
                if (org.apache.commons.lang.StringUtils.isEmpty(sBusProCode)) {
                    sBusProCode = forsendSn.getBusProCode();
                }
            }
            lowerHairMachineVo.setListSn(listSn);
            lowerHairMachineVo.setActCode(sBusProCode);
            lowerHairMachineVo.setPlatFormNum(agentBusInfo.getBusPlatform());
            //机具下发接口
            OLogistics logistics_send = oLogisticsMapper.selectByPrimaryKey(lowerHairMachineVo.getoLogisticsId());
            try {
                logger.info("下发物流接口调用：下发到首刷平台请求参数:{}", JSONObject.toJSONString(lowerHairMachineVo));
                AgentResult lowerHairMachineRes = termMachineService.lowerHairMachine(lowerHairMachineVo);
                logger.info("下发物流接口调用：下发到首刷平台结果:{}", lowerHairMachineRes.getMsg());
                //机具下发成功，更新物流明细为下发成功
                if (lowerHairMachineRes.isOK()) {
                    logger.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), lowerHairMachineRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                        detail.setSbusMsg(lowerHairMachineRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    return true;
                    //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                } else {
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), lowerHairMachineRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(lowerHairMachineRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    return false;
                }
                //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
            } catch (MessageException e) {
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                listOLogisticsDetailSn.forEach(detail -> {
                    detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                    detail.setSbusMsg(e.getMsg());
                    detail.setuTime(date);
                    oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                });
                return false;
                //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                listOLogisticsDetailSn.forEach(detail -> {
                    detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                    detail.setSbusMsg("异常");
                    detail.setuTime(date);
                    oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                });
                return false;
            }
        }else{
            return false;
        }
    }

}
