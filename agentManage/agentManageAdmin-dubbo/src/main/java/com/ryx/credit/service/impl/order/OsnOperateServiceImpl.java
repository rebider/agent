package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OsnOperateService;
import com.ryx.internet.service.InternetCardService;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作者：cx
 * 时间：2019/4/19
 * 描述：
 */
@Service("osnOperateService")
public class OsnOperateServiceImpl implements OsnOperateService {

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
    private CUserMapper cUserMapper;
    @Autowired
    private IdService idService;
    @Resource(name = "osnOperateService")
    private OsnOperateService osnOperateService;
    @Autowired
    private InternetCardService internetCardService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OrgPlatformMapper orgPlatformMapper;

    /**
     * 根据物流联动状态查询物流id
     * 0：未联动 1：已联动 2：联动失败， 3：部分联动失败， 4：生成明细失败，5：生成明细中，6：生成明细成功7：联动业务系统处理中
     * @param sendStatus
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> queryLogicInfoIdByStatus(LogType logType, LogisticsSendStatus sendStatus){
        // return oLogisticsMapper.queryLogicInfoIdByStatus(FastMap.fastMap("logType", logType.code).putKeyV("sendStatus",sendStatus.code).putKeyV("count",count_wall));
        return oLogisticsMapper.queryLogicInfoIdByStatus(FastMap.fastMap("logType", logType.code).putKeyV("sendStatus",sendStatus.code));
    }


    /**
     * 生成物流执行的任务
     * 查询发货数量大于wall_count 的物流id，
     * 遍历并排除掉退货发货物流（根据排单表里的退货子订单明细字段值是否存在判断），
     * 进行明细的生成
     */
    @Override
    public void genLogicDetailTask(){
        //查询所有的未联动的发货物流
        List<String>  list = queryLogicInfoIdByStatus(LogType.Deliver,LogisticsSendStatus.none_send);
        if(list.size()>0) {
            logger.info("非退货物流处理 开始执行sn明细生成任务");
            for (String id : list) {
                //根据id
                OLogisticsExample example = new OLogisticsExample();
                example.or().andSendStatusEqualTo(LogisticsSendStatus.none_send.code).andIdEqualTo(id);
                List<OLogistics> logistics_list = oLogisticsMapper.selectByExample(example);
                OLogistics logistics = null;
                if(logistics_list.size()>0){
                    logistics = logistics_list.get(0);
                }
                //info 此处禁止处理退货发货
                ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
                if(logistics!=null && StringUtils.isEmpty(receiptPlan.getReturnOrderDetailId())) {
                    logger.info("非退货物流处理 更新物流状态为生成明细中:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                    logistics.setSendStatus(LogisticsSendStatus.gen_detail_ing.code);
                    if (1 == oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                        try {
                            logger.info("非退货物流处理 处理物流生成明细:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                            if (osnOperateService.genOlogicDetailInfo(id)) {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    logger.info("非退货物流处理 物流生成明细成功，更新数据库失败:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }else{
                                    logger.info("非退货物流处理 物流生成明细成功，更新数据库成功:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }
                            } else {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    logger.info("非退货物流处理 物流生成明细失败，更新数据库失败:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }else{
                                    logger.info("非退货物流处理 物流生成明细失败，更新数据库成功:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }
                            }
                        } catch (MessageException e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getMsg());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("非退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }else{
                                logger.info("非退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
                            String[] emailArr = new String[dicts.size()];
                            for (int i = 0; i < dicts.size(); i++) {
                                emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                            }
                            AppConfig.sendEmail(emailArr, "瑞大宝，查询下发接口，发送请求失败：" + MailUtil.printStackTrace(e), "瑞大宝接口异常");
                            //AppConfig.sendEmails("logisticId:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                        } catch (Exception e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getLocalizedMessage().length() > 30 ? e.getLocalizedMessage().substring(0, 30) : e.getLocalizedMessage());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("非退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }else{
                                logger.info("非退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            //从字典中取出相应的邮件人
                            List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
                            String[] emailArr = new String[dicts.size()];
                            for (int i = 0; i < dicts.size(); i++) {
                                emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                            }
                            AppConfig.sendEmail(emailArr, "logisticId:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                        }
                    }
                }

            }
            logger.info("开始执行sn明细生成结束");
        }
    }


    /**
     * 执行同步sn任务，抓取发货业务 待处理的物流信息id,进行分配处理
     * 提取发货数量大于 count_wall 的生成明细成功的物流编号
     * 对查找出来的物流编号进行 状态更新，更新成 联动业务系统处理中
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public List<String> fetchFhData(int nodecount,int shardingItem)throws Exception{

        //查询待处理的物流列表，并更新成处理中
         List<String> data = oLogisticsMapper.queryLogicInfoIdByStatus(FastMap
        .fastMap("logType",LogType.Deliver.code)
            .putKeyV("sendStatus",LogisticsSendStatus.gen_detail_sucess.code)
            .putKeyV("pagesize",200)
            //.putKeyV("count",count_wall)
         );

         //更新物流为下发处理中，任务更新状态，下次不再查询
        data.forEach(ids ->{
            OLogistics oLogistics = oLogisticsMapper.selectByPrimaryKey(ids);
            oLogistics.setSendStatus(LogisticsSendStatus.send_ing.code);
            if(oLogisticsMapper.updateByPrimaryKeySelective(oLogistics)!=1){
                logger.info("查询待处理的物流列表，并更新成处理中失败:{}",ids);
            }
        });

        return data;
    }


    /**
     * 处理要处理的物流明细信息
     * 查询状态为处理中的物流id
     * @param ids
     * @return
     */
    @Override
    public boolean processData(List<String> ids){

        //查询物流失败接收人
        List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
        String[] emailArr = new String[dicts.size()];
        for (int i = 0; i < dicts.size(); i++) {
            emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
        }

        if(ids!=null && ids.size()>0) {

            ids.forEach(id -> {

                OLogisticsExample example = new OLogisticsExample();
                example.or().andSendStatusEqualTo(LogisticsSendStatus.send_ing.code).andIdEqualTo(id);
                List<OLogistics> logistics_list = oLogisticsMapper.selectByExample(example);
                OLogistics logistics_item = null;
                //物流赋值
                if(logistics_list.size()>0){
                    logistics_item = logistics_list.get(0);
                }
                //查询到物流才进行处理
                if(logistics_item!=null) {

                    //根据物流id查找sn明细，并更新物流明细发送状态为待发送状态，200单位数量为1批次，避免接口错误进行接口请求数量限制。
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    oLogisticsDetailExample.or()
                            .andLogisticsIdEqualTo(id)
                            .andSendStatusEqualTo(LogisticsDetailSendStatus.none_send.code)
                            .andStatusEqualTo(Status.STATUS_1.status);
                    oLogisticsDetailExample.setOrderByClause(" sn_num asc ");

                    //每次处理两百条数据
                    oLogisticsDetailExample.setPage(new Page(0, 200));
                    List<OLogisticsDetail> logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);

                    //批次格式为YYYYMMddHHmmss + inerBatch
                    String date = DateUtil.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
                    BigDecimal inerBatch = BigDecimal.ONE;
                    String batch = date + inerBatch;

                    //直到物流明细处理完成
                    while (logisticsDetails.size() > 0) {
                        try {
                            //在一个独立事物中进行数据更新批次信息及状态信息
                            List<OLogisticsDetail> list = osnOperateService.updateDetailBatch(logisticsDetails, new BigDecimal(batch));
                            //发送到业务系统，根据批次号
                            Map<String, Object> retMap = osnOperateService.sendInfoToBusinessSystem(list, id, new BigDecimal(batch));
                            if(null != retMap.get("code") && "0000".equals(retMap.get("code"))){
                                //处理中，更新物流状态，结束循环
                                OLogistics oLogistics = oLogisticsMapper.selectByPrimaryKey(id);
                                oLogistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                                if(oLogisticsMapper.updateByPrimaryKeySelective(oLogistics) != 1){
                                    logger.info("下发处理中更新物流失败,物流ID:{}", id);
                                    throw new MessageException("更新物流状态失败,物流ID:{}", id);
                                }
                                break;
                            }else if (list.size() > 0 && null != retMap.get("code") && "1111".equals(retMap.get("code"))) {
                                //处理成功,不更新物流状态,等处理完所有的物流明细,才更新状态
                                logger.info("物流明细发送业务系统处理成功,{},{}", id, batch);
                            } else if (null != retMap.get("code") && "2222".equals(retMap.get("code"))){
                                //处理失败，停止发送
                                logger.info("物流明细发送业务系统处理失败,{},{}", id, batch);
                                AppConfig.sendEmail(emailArr, "机具下发失败，SN码:" + logistics_item.getSnBeginNum() + "-" + logistics_item.getSnEndNum() + "。失败原因：" + (null == retMap.get("msg")? "无": retMap.get("msg")), "物流下发失败");
                                OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                                logistics.setSendMsg(null != retMap.get("msg")? (String) retMap.get("msg"):"未返回失败原因。");
                                if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                    logger.info("物流明细发送业务系统处理失败，更新数据库失败,{},{}", id, batch);
                                }
                            } else {
                                //其他情况
                                logger.info("物流明细发送业务系统处理失败,{},{}", id, batch);
                                OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                                logistics.setSendMsg("联动失败，联动业务平台异常！！！");
                                if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                    logger.info("物流明细发送业务系统处理失败，更新数据库失败,{},{}", id, batch);
                                }
                            }
                        } catch (MessageException | ProcessException e) {
                            AppConfig.sendEmails("logisticId:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                            e.printStackTrace();
                            logisticsDetails.forEach(det -> {
                                OLogisticsDetail detail = oLogisticsDetailMapper.selectByPrimaryKey(det.getId());
                                detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                                detail.setSbusMsg(e.getLocalizedMessage());
                                oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                            });
                            //更新物流为发送失败停止发送，人工介入
                            OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                            logistics.setSendMsg(e.getLocalizedMessage());
                            if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}", id, batch);
                            }
                        } catch (Exception e) {
                            AppConfig.sendEmails("logisticId:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                            e.printStackTrace();
                            logisticsDetails.forEach(det -> {
                                OLogisticsDetail detail = oLogisticsDetailMapper.selectByPrimaryKey(det.getId());
                                detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                                detail.setSbusMsg(e.getLocalizedMessage());
                                oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                            });
                            //更新物流为发送失败停止发送，人工介入
                            OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                            logistics.setSendMsg(e.getLocalizedMessage());
                            if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}", id, batch);
                            }
                        }
                        //调用接口发送到业务系统，如果接口返回异常，更新物流明细发送失败，不在进行发送
                        logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                        inerBatch = inerBatch.add(BigDecimal.ONE);
                        batch = date + inerBatch.intValue();
                    }

                    //检查是否包含有未发送的sn,如果没有更新为处理成功 ，如果处理中的物流没有
                    OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(id);
                    if (LogisticsSendStatus.send_ing.code.compareTo(logistics.getSendStatus()) == 0) {
                        if (oLogisticsDetailMapper.countByExample(oLogisticsDetailExample) == 0) {
                            logistics.setSendStatus(LogisticsSendStatus.send_success.code);
                            logistics.setSendMsg("联动业务系统成功");
                            if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}", id, batch);
                            }
                        }
                    }

                    //发送邮件
                    logistics = oLogisticsMapper.selectByPrimaryKey(id);
                    if (StringUtils.isNotEmpty(logistics.getcUser())) {
                        CUser cUser = cUserMapper.selectById(logistics.getcUser());
                        if (cUser != null && StringUtils.isNotEmpty(cUser.getUserEmail())) {
                            //从字典中取出相应的邮件人
                            if (LogisticsSendStatus.send_fail.code.equals(logistics.getSendStatus()) || LogisticsSendStatus.send_part_fail.code.equals(logistics.getSendStatus())) {
                                //发送异常邮件
                                AppConfig.sendEmail(cUser.getUserEmail().split(","), "物流发送失败，号码段:" + logistics.getSnBeginNum() + "-" + logistics.getSnBeginNum() + "。失败原因:" + logistics.getSendMsg(), "物流发送失败");
                            } else if (LogisticsSendStatus.send_success.code.equals(logistics.getSendStatus())) {
                                //发送成功邮件
                                AppConfig.sendEmail(cUser.getUserEmail().split(","), "物流发送成功，号码段:" + logistics.getSnBeginNum() + "-" + logistics.getSnEndNum() + "。成功信息" + logistics.getSendMsg(), "物流发送成功");
                            }
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
            List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
            String[] emailArr = new String[dicts.size()];
            for (int i = 0; i < dicts.size(); i++) {
                emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
            }
            AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+MailUtil.printStackTrace(e),"任务生成物流明细错误报警OsnOperateServiceImpl");
            //AppConfig.sendEmails("SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
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
        PlatForm platForm =  platFormMapper.selectByPlatFormNum(order.getOrderPlatform());


        //排单的活动 下发到业务系统使用此活动
        OActivity oActivity_plan = oActivityMapper.selectByPrimaryKey(planVo.getActivityId());

        //手刷生成物流方式 根据机具类型确定机具明细的生成方式，首刷更新明细记录
        if(PlatformType.MPOS.code.equals(platForm.getPlatformType())){
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
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
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
        }else if(PlatformType.whetherPOS(platForm.getPlatformType())){
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
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
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
        }else if(PlatformType.SSPOS.code.equals(platForm.getPlatformType())){
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
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
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
        } else if (PlatformType.RDBPOS.code.equals(platForm.getPlatformType())) {
            // RDB生成物流方式 根据机具类型确定机具明细的生成方式,pos生成明细记录
            logger.info("瑞大宝明细生成");
            for(String id:ids){
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
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
                }
                detail.setSnNum(id);
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
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("物流明细添加失败:{},{}",logistics.getId(),id);
                    throw new MessageException("物流明细添加失败:"+logistics.getId()+":"+id);
                }
            }
        } else if (PlatformType.RJPOS.code.equals(platForm.getPlatformType())) {
            // 瑞+生成物流方式 根据机具类型确定机具明细的生成方式,pos生成明细记录
            logger.info("瑞+明细生成");
            for (String id : ids) {
                OLogisticsDetail detail = new OLogisticsDetail();
                //id，物流id，创建人，更新人，状态
                detail.setId(idService.genId(TabId.o_logistics_detail));
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logistics.getId());
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if (OSubOrderActivitylist.size() > 0) {
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
                }
                detail.setSnNum(id);
                detail.setAgentId(order.getAgentId());
                detail.setcUser(logistics.getcUser());
                detail.setuUser(logistics.getcUser());
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if (StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = oReturnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                }
                detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("物流明细添加失败:{},{}", logistics.getId(), id);
                    throw new MessageException("物流明细添加失败:" + logistics.getId() + ":" + id);
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
    public Map<String, Object> sendInfoToBusinessSystem(List<OLogisticsDetail>  datas,String logcId,BigDecimal batch)throws Exception {

        //查询发送邮件接收人
        List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
        String[] emailArr = new String[dicts.size()];
        for (int i = 0; i < dicts.size(); i++) {
            emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
        }

        Map<String, Object> retMap = new Hashtable<String, Object>();
        if(datas==null && datas.size()==0){
            retMap.put("code", "1111");
            return retMap;
        }
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
        logger.info("物流明细查出数量：{},{},{},{}",logcId,batch,listOLogisticsDetailSn.size(),datas.size());
        List<String> snList = new ArrayList<>();
        listOLogisticsDetailSn.forEach(detail -> {
            snList.add(detail.getSnNum());
        });
        logger.info("物流明细snID数量：{},{},{}",logcId,batch,snList.size());
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
        //排单的活动 下发到业务系统使用此活动
        OActivity oActivity_plan = oActivityMapper.selectByPrimaryKey(planVo.getActivityId());
        //订单信息
        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
        PlatForm platForm = platFormMapper.selectByPlatFormNum(order.getOrderPlatform());
        Date date = Calendar.getInstance().getTime();
        OActivity oActivity = oActivityMapper.selectByPrimaryKey(oSubOrderActivity.getActivityId());

        //流量卡不进行下发操作
        if(oActivity_plan!=null && com.ryx.credit.commons.utils.StringUtils.isNotBlank(oActivity_plan.getActCode()) && ("2204".equals(oActivity_plan.getActCode()) || "2004".equals(oActivity_plan.getActCode())) ){
            logger.info("导入物流数据,流量卡不进行下发操作，活动代码{}={}={}" ,oActivity_plan.getActCode(),logcId, JSONObject.toJSON(logistics));
            listOLogisticsDetailSn.forEach(detail -> {
                detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                detail.setSbusMsg("流量卡不下发");
                detail.setuTime(date);
                oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
            });
            //存储到流量卡表
            try {
                internetCardService.orderInsertInternetCard(listOLogisticsDetailSn,planVo.getProCom());
            }catch (Exception e){
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"。错误信息:"+e.getLocalizedMessage(), "流量卡插入失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            }
            retMap.put("code", "1111");
            return retMap;
        }
        logger.info("进入物流发送阶段，联动各个业务平台！");
        if (PlatformType.whetherPOS(platForm.getPlatformType())) {
            logger.info("POS平台物流下发！");
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            if (null == order) throw new MessageException("查询订单数据失败！");
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            if (null == agentBusInfo) throw new MessageException("查询业务数据失败！");

            imsTermWarehouseDetail.setOrgId(agentBusInfo.getBusNum());
            imsTermWarehouseDetail.setMachineId(oActivity_plan.getBusProCode());
            imsTermWarehouseDetail.setPosSpePrice(oActivity_plan.getPosSpePrice());
            imsTermWarehouseDetail.setPosType(oActivity_plan.getPosType());
            imsTermWarehouseDetail.setStandTime(oActivity_plan.getStandTime());
            try {
                //机具下发接口
                logger.info("机具下发接口调用：logcId：{},batch：{},snList：{}",logcId,batch,snList.size());
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
                    retMap.put("code", "1111");
                    return retMap;
                } else {
                    //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败
                    AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+LogisticsDetailSendStatus.send_fail.msg, logistics.getProType()+"下发失败！");
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), posSendRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(posSendRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "2222");
                    return retMap;
                }
                //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败，禁止继续发送 ,人工介入
            } catch (MessageException e) {
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+e.getLocalizedMessage(), logistics.getProType()+"下发失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            } catch (Exception e) {
                //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败 ，禁止继续发送,人工介入
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+e.getLocalizedMessage(), logistics.getProType()+"下发失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
               throw e;
            }
            //首刷下发业务系统
        } else if (platForm.getPlatformType().equals(PlatformType.MPOS.code)) {
            logger.info("MPOS平台物流下发！");
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
                if (lowerHairMachineRes.isOK()) {
                    //机具下发成功，更新物流明细为下发成功
                    logger.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), lowerHairMachineRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                        detail.setSbusMsg(lowerHairMachineRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "1111");
                    return retMap;
                } else {
                    //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                    AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum(), logistics.getProType()+"下发失败！");
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), lowerHairMachineRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(lowerHairMachineRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "2222");
                    return retMap;
                }
            } catch (MessageException e) {
                //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum() + "错误信息：" + e.getLocalizedMessage(), logistics.getProType()+"机具下发失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            } catch (Exception e) {
                //机具下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+e.getLocalizedMessage(), logistics.getProType()+"机具下发失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            }
        }else if(PlatformType.SSPOS.code.equals(platForm.getPlatformType())){
            logger.info("SSPOS平台物流下发！");
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            if (null == order) throw new MessageException("查询订单数据失败！");
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            if (null == agentBusInfo)throw new MessageException("查询业务数据失败！");

            OLogistics logistics_send = oLogisticsMapper.selectByPrimaryKey(logistics.getId());
            imsTermWarehouseDetail.setOrgId(agentBusInfo.getBusNum());
            imsTermWarehouseDetail.setMachineId(oActivity_plan.getBusProCode());
            imsTermWarehouseDetail.setPosSpePrice(oActivity_plan.getPosSpePrice());
            imsTermWarehouseDetail.setPosType(oActivity_plan.getPosType());
            imsTermWarehouseDetail.setStandTime(oActivity_plan.getStandTime());
            imsTermWarehouseDetail.setDeliveryTime(DateUtil.format(logistics.getSendDate(),"yyyy-MM-dd"));
            try {
                LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
                lowerHairMachineVo.setPlatformType(platForm.getPlatformType());
                lowerHairMachineVo.setSnList(snList);
                lowerHairMachineVo.setImsTermWarehouseDetail(imsTermWarehouseDetail);
                //机具下发接口
                logger.info("导入物流：下发到实时分润平台请求参数:{}",JSONObject.toJSONString(lowerHairMachineVo));
                AgentResult posSendRes = termMachineService.lowerHairMachine(lowerHairMachineVo);
                logger.info("导入物流：下发到首刷平台结果:{}",posSendRes.getMsg());
                if(posSendRes.isOK()){
                    //下发成功
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                        detail.setSbusMsg(posSendRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "1111");
                    return retMap;
                }else{
                    //下发失败
                    AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+LogisticsDetailSendStatus.send_fail.msg, logistics.getProType()+"机具下发失败！");
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), posSendRes.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(posSendRes.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "2222");
                    return retMap;
                }
            } catch (MessageException e) {
                //机具下发失败
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+MailUtil.printStackTrace(e), logistics.getProType()+"机具下发失败！");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            } catch (Exception e) {
                //机具下发失败，更新物流明细为下发失败，并更新物流为发送失败 ，禁止继续发送,人工介入
                AppConfig.sendEmail(emailArr, "SN开始："+logistics.getSnBeginNum()+",SN结束："+logistics.getSnEndNum()+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                e.printStackTrace();
                logger.info("下发物流接口调用异常：物流编号:{},批次编号:{},时间:{},错误信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), e.getLocalizedMessage());
                throw e;
            }
        } else if (PlatformType.RDBPOS.code.equals(platForm.getPlatformType())) {
            logger.info("RDBPOS平台物流下发！");
            //瑞大宝机具下发
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            Agent agent = agentMapper.selectByPrimaryKey(order.getAgentId());
            if (null == oActivity_plan) throw new MessageException("活动信息异常！");
            if (null == order.getOrderPlatform()) throw new MessageException("订单信息中平台异常！");
            if (null == agentBusInfo) throw new MessageException("查询业务数据失败！");

            String orderPlatForm = order.getOrderPlatform();
            String branchId = orderPlatForm.substring(0, orderPlatForm.indexOf("_"));
            String oldAgencyId = orderPlatForm.substring(orderPlatForm.indexOf("_") + 1);

            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("taskId", logistics.getId());//批次号（唯一值,主键,我们用物流ID）
            reqMap.put("termBegin", logistics.getSnBeginNum());//起始SN
            reqMap.put("termEnd", logistics.getSnEndNum());//结束SN
            reqMap.put("agencyId", agentBusInfo.getBusNum());//划拨目标
            reqMap.put("oldAgencyId", oldAgencyId);//划拨机构
            reqMap.put("branchId", branchId);//品牌id
            reqMap.put("termPolicyId", oActivity_plan.getBusProCode());//活动代码
            reqMap.put("inBoundDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));//物流下发，当前时间
            reqMap.put("agencyName", agent.getAgName());//划拨目标

            try {
                //发送接口
                String json = JsonUtil.objectToJson(reqMap);
                logger.info("RDB机具下发接口请求参数:{}", json);
                String respResult = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.requestTransfer"), json);
                if (!StringUtils.isNotBlank(respResult)) throw new Exception("瑞大宝下发接口返回值为空，请联系管理员！");
                JSONObject respJson = JSONObject.parseObject(respResult);
                if (!(null != respJson.getString("code") && null != respJson.getString("success") && respJson.getString("code").equals("0000") && respJson.getBoolean("success"))) {
                    logger.info("RDB下发返回异常:" + respResult);
                    throw new Exception(null != respJson.getString("msg") ? respJson.getString("msg") : "瑞大宝，下发接口，返回值异常，请联系管理员!");
                }

                try {
                    //查询结果接口
                    String retJson = JSONObject.toJSONString(FastMap.fastMap("taskId", logistics.getId()));
                    String retString = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkTermResult"), retJson);
                    if (!StringUtils.isNotBlank(retString)) throw new Exception("瑞大宝,查询,下发接口,返回值为空，请联系管理员！");

                    JSONObject resJson = JSONObject.parseObject(retString);
                    logger.info("RDB机具下发查询接口返回值:{}", retString);
                    if (null != resJson.getString("code") && resJson.getString("code").equals("0000") && null != resJson.getBoolean("success") && resJson.getBoolean("success")) {
                        //下发成功，更新物流明细为下发成功
                        logger.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), resJson.getString("msg"));
                        listOLogisticsDetailSn.forEach(detail -> {
                            detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                            detail.setSbusMsg(resJson.getString("msg"));
                            detail.setuTime(date);
                            oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                        });
                        retMap.put("code", "1111");
                        return retMap;
                    } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                        //下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                        AppConfig.sendEmail(emailArr, "SN码" + logistics.getSnBeginNum() + "-" + logistics.getSnEndNum(), "瑞大宝机具下发失败");
                        logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), resJson.getString("msg"));
                        listOLogisticsDetailSn.forEach(detail -> {
                            detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                            detail.setSbusMsg(resJson.getString("msg"));
                            detail.setuTime(date);
                            oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                        });
                        retMap.put("code", "2222");
                        retMap.put("msg", resJson.getString("msg"));
                        return retMap;
                    } else if (null != resJson.getString("code") && resJson.getString("code").equals("2001") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                        //下发处理中，返回到明细处理，明细继续循环处理，一直到业务系统处理完成
                        listOLogisticsDetailSn.forEach(detail -> {
                            detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                            detail.setSbusMsg(resJson.getString("msg"));
                            detail.setuTime(date);
                            oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                        });
                        retMap.put("code", "0000");
                        return retMap;
                    } else {
                        throw new Exception("瑞大宝，查询下发接口，返回值不符合要求，请联系管理员！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } catch (Exception e) {
                //发送异常邮件
                e.printStackTrace();
                AppConfig.sendEmail(emailArr, "机具下发失败，SN码:" + logistics.getSnBeginNum() + "-" + logistics.getSnEndNum() + "。失败原因：" + e.getLocalizedMessage(), "瑞大宝机具下发失败");
                throw e;
            }
        } else if (PlatformType.RJPOS.code.equals(platForm.getPlatformType())) {
            logger.info("RJPOS平台物流下发！");
            //瑞+物流下发
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            if (null == oActivity_plan) throw new MessageException("活动信息异常！");
            if (null == order.getOrderPlatform()) throw new MessageException("订单信息中平台异常！");
            if (null == agentBusInfo) throw new MessageException("查询业务数据失败！");

            //查询顶级机构
            Map orgMap = orgPlatformMapper.selectByMap(FastMap.fastMap("busPlatform", agentBusInfo.getBusPlatform()).putKeyV("organNum", agentBusInfo.getOrganNum()));
            if (null == orgMap || null == orgMap.get("PLATCODE")) throw new MessageException("顶级机构码为空，请补全顶级机构！");

            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("taskId", logistics.getId());//批次号（唯一值,主键,我们用物流ID）
            reqMap.put("machineId", oActivity_plan.getBusProCode());//活动代码
            reqMap.put("posSnBegin", logistics.getSnBeginNum());//起始终端号
            reqMap.put("posSnEnd", logistics.getSnEndNum());//结束终端号
            reqMap.put("newOrgId", agentBusInfo.getBusNum());//划拨目标
            reqMap.put("deliveryTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//物流下发，当前时间
            reqMap.put("orgId", orgMap.get("PLATCODE"));//顶级机构
            reqMap.put("createPerson", AppConfig.getProperty("rjpos.agent.name"));//创建人
            if (null != oActivity_plan.getPosType() && (oActivity_plan.getPosType().equals("1") || oActivity_plan.getPosType().equals("2"))) {
                reqMap.put("posType", oActivity_plan.getPosType());//机具类型
                if (null == oActivity_plan.getPosSpePrice() || null == oActivity_plan.getStandTime())
                    return FastMap.fastMap("code", "2222").putKeyV("msg", "活动对应的特价机[押金]或[达标时间]不能为空!");
                reqMap.put("posSpePrice", oActivity_plan.getPosSpePrice());//押金
                reqMap.put("standTime", oActivity_plan.getStandTime());//达标时间
            } else if (null == oActivity_plan.getPosType() || oActivity_plan.getPosType().equals("0")) {
                reqMap.put("posType", "0");//机具类型
            }

            try {
                LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
                lowerHairMachineVo.setJsonString(JSONObject.toJSONString(reqMap));
                lowerHairMachineVo.setPlatformType(platForm.getPlatformType());
                //物流下发
                AgentResult lowerResult = termMachineService.lowerHairMachine(lowerHairMachineVo);
                //下发异常
                if (0 != lowerResult.getStatus()) throw new Exception(lowerResult.getMsg());
                //查询结果
                AgentResult queryResult = termMachineService.queryLogisticsResult(FastMap.fastMap("taskId", logistics.getId()), platForm.getPlatformType());

                //更新处理结果
                if (1 == queryResult.getStatus()) {
                    //下发成功，更新物流明细为下发成功
                    logger.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), queryResult.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                        detail.setSbusMsg(queryResult.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "1111");
                    return retMap;
                } else if (0 == queryResult.getStatus()) {
                    //下发处理中，更新物流明细为原始状态
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                        detail.setSbusMsg(queryResult.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "0000");
                    return retMap;
                } else {
                    //下发失败，更新物流明细为下发失败
                    logger.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), queryResult.getMsg());
                    listOLogisticsDetailSn.forEach(detail -> {
                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                        detail.setSbusMsg(queryResult.getMsg());
                        detail.setuTime(date);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    });
                    retMap.put("code", "2222");
                    retMap.put("msg", null != queryResult.getMsg()? queryResult.getMsg():"失败，瑞+未返回失败原因。");
                    return retMap;
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppConfig.sendEmail(emailArr, "机具下发失败，SN码:" + logistics.getSnBeginNum() + "-" + logistics.getSnEndNum() + "。失败原因：" + e.getLocalizedMessage(), "瑞+机具下发失败");
                throw e;
            }
        } else {
            retMap.put("code", "2222");
            return retMap;
        }
    }
}
