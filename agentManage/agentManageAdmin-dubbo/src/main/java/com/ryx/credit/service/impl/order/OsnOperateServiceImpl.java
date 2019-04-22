package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
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
        list.forEach(id -> {
            OLogistics logistics  = oLogisticsMapper.selectByPrimaryKey(id);
            logistics.setSendStatus(LogisticsSendStatus.gen_detail_ing.code);
            if(1==oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                try {
                    if(osnOperateService.genOlogicDetailInfo(id)){
                         logistics  = oLogisticsMapper.selectByPrimaryKey(id);
                         logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                         oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    } else{
                        logistics  = oLogisticsMapper.selectByPrimaryKey(id);
                        logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                        oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    }
                } catch (MessageException e) {
                    e.printStackTrace();
                    logger.error("生成物流明细任务异常：",e);
                    logistics  = oLogisticsMapper.selectByPrimaryKey(id);
                    logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                    logistics.setSendMsg(e.getMsg());
                    oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("生成物流明细任务异常：",e);
                    logistics  = oLogisticsMapper.selectByPrimaryKey(id);
                    logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                    logistics.setSendMsg(e.getLocalizedMessage().length()>30?e.getLocalizedMessage().substring(0,30):e.getLocalizedMessage());
                    oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                }
            }

        });
    }


    /**
     * 根据物流信息生成物流明细,物流明细生成后进行物流状态的更新，更新为 4：生成明细失败 5：生成明细中 6：生成明细成功 添加版本号控制
     * @param logcId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public boolean genOlogicDetailInfo(String logcId)throws Exception{
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
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public boolean sendInfoToBusinessSystem(String logcId, Integer pageSize,int batch)throws Exception {
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logcId);
        //查询要发送的sn，根据sn进行排序
        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
        oLogisticsDetailExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)//发货
                .andRecordStatusEqualTo(Status.STATUS_1.status)//有效
                .andSendStatusEqualTo(LogisticsDetailSendStatus.send_ing.code)//处理中的sn
                .andLogisticsIdEqualTo(logcId);
        oLogisticsDetailExample.setPage(new Page(0, pageSize));
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
                        detail.setSbusBatch(new BigDecimal(batch));
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
                        detail.setSbusBatch(new BigDecimal(batch));
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
                    detail.setSbusBatch(new BigDecimal(batch));
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
                    detail.setSbusBatch(new BigDecimal(batch));
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
                        detail.setSbusBatch(new BigDecimal(batch));
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
                        detail.setSbusBatch(new BigDecimal(batch));
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
                    detail.setSbusBatch(new BigDecimal(batch));
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
                    detail.setSbusBatch(new BigDecimal(batch));
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
