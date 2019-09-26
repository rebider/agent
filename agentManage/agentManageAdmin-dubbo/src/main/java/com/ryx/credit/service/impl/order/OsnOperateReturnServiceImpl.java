package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OsnOperateReturnService;
import com.ryx.credit.service.order.OsnOperateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 作者：cx
 * 时间：2019/5/9
 * 描述：
 */
@Service("osnOperateReturnService")
public class OsnOperateReturnServiceImpl implements OsnOperateReturnService {

    private static Logger logger = LoggerFactory.getLogger(OsnOperateReturnServiceImpl.class);

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
    @Resource(name = "osnOperateReturnService")
    private OsnOperateReturnService osnOperateReturnService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private ImsTermAdjustDetailService imsTermAdjustDetailService;
    @Autowired
    private OReturnOrderDetailMapper returnOrderDetailMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;

    /**
     * 查询物流
     * @param logType
     * @param sendStatus
     * @return
     */
    @Override
    public List<String> queryLogicInfoIdByStatus(LogType logType, LogisticsSendStatus sendStatus) {
        //return super.queryLogicInfoIdByStatus(logType,sendStatus);
        return null;
    }

    @Override
    public void genLogicDetailTask() {
        //查询所有审核通过、退货物流、没有联动的
        List<String> list = oLogisticsMapper.queryLogicInfoIdByStatusAndApproved(FastMap.fastMap("logType", LogType.Refund.code).putKeyV("sendStatus",LogisticsSendStatus.none_send.code).putKeyV("retSchedule",RetSchedule.WC.code));
        if (list.size() > 0) {
            logger.info("退货物流处理 开始执行sn明细生成任务");
            for (String id : list) {
                //根据id
                OLogisticsExample example = new OLogisticsExample();
                example.or().andSendStatusEqualTo(LogisticsSendStatus.none_send.code).andIdEqualTo(id);
                List<OLogistics> logistics_list = oLogisticsMapper.selectByExample(example);
                OLogistics logistics = null;
                if (logistics_list.size() > 0) {
                    logistics = logistics_list.get(0);
                }
                //此处处理退货发货
                ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
                if (logistics != null && StringUtils.isNotEmpty(receiptPlan.getReturnOrderDetailId())) {
                    logger.info("退货物流处理 更新物流状态为生成明细中:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                    logistics.setSendStatus(LogisticsSendStatus.gen_detail_ing.code);
                    if (1 == oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                        try {
                            logger.info("退货物流处理 处理物流生成明细:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                            if (osnOperateReturnService.genOlogicDetailInfo(id)) {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                                if (1 != oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                                    logger.info("退货物流处理 物流生成明细成功，更新数据库失败:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                                } else {
                                    logger.info("退货物流处理 物流生成明细成功，更新数据库成功:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                                }
                            } else {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                                if (1 != oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                                    logger.info("退货物流处理 物流生成明细失败，更新数据库失败:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                                } else {
                                    logger.info("退货物流处理 物流生成明细失败，更新数据库成功:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                                }
                            }
                        } catch (MessageException e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getMsg());
                            if (1 != oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                                logger.info("退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum(), e.getLocalizedMessage());
                            } else {
                                logger.info("退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum(), e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            AppConfig.sendEmails("logisticId:" + id + "错误信息:" + MailUtil.printStackTrace(e), "任务生成物流明细错误报警osnOperateReturnServiceImpl");
                        } catch (Exception e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getLocalizedMessage().length() > 30 ? e.getLocalizedMessage().substring(0, 30) : e.getLocalizedMessage());
                            if (1 != oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                                logger.info("退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum(), e.getLocalizedMessage());
                            } else {
                                logger.info("退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum(), e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            AppConfig.sendEmails("logisticId:" + id + "错误信息:" + MailUtil.printStackTrace(e), "任务生成物流明细错误报警osnOperateReturnServiceImpl");
                        }
                    }
                }

            }
            logger.info("开始执行sn明细生成结束");
        }
    }

    /**
     * 更新物流为下发处理中，任务更新状态
     * @param nodecount
     * @param shardingItem
     * @return
     * @throws Exception
     */
    @Override
    public List<String> fetchFhData(int nodecount, int shardingItem) throws Exception {
        //查询待处理的物流列表，并更新成处理中
        List<String> data = oLogisticsMapper.queryLogicInfoIdByStatusAndApproved(FastMap.fastMap("logType", LogType.Refund.code).putKeyV("sendStatus", LogisticsSendStatus.gen_detail_sucess.code).putKeyV("retSchedule",RetSchedule.WC.code));

        //更新物流为下发处理中，任务更新状态，下次不再查询
        data.forEach(ids -> {
            OLogistics oLogistics = oLogisticsMapper.selectByPrimaryKey(ids);
            oLogistics.setSendStatus(LogisticsSendStatus.send_ing.code);
            if (oLogisticsMapper.updateByPrimaryKeySelective(oLogistics) != 1) {
                logger.info("查询待处理的物流列表，并更新成处理中失败:{}", ids);
            }
        });
        return data;
    }

    /**
     * 联动业务平台进行退货转发
     *
     * @param ids
     * @return
     */
    @Override
    public boolean processData(List<String> ids) {

        //查询物流失败接收人
        List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
        String[] emailArr = new String[dicts.size()];
        for (int i = 0; i < dicts.size(); i++) {
            emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
        }

        if (ids != null && ids.size() > 0) {
            for (String id:ids) {
                OLogisticsExample example = new OLogisticsExample();
                example.or().andSendStatusEqualTo(LogisticsSendStatus.send_ing.code).andIdEqualTo(id);
                List<OLogistics> logistics_list = oLogisticsMapper.selectByExample(example);
                OLogistics logistics = null;
                //物流赋值
                if (logistics_list.size() > 0) {
                    logistics = logistics_list.get(0);
                }
                //查询到物流才退货转发
                if (logistics != null) {

                    //根据物流id查找sn明细，并更新物流明细发送状态为待发送状态，200单位数量为1批次，避免接口错误进行接口请求数量限制。
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    oLogisticsDetailExample.or()
                            .andLogisticsIdEqualTo(id)
                            .andSendStatusEqualTo(LogisticsDetailSendStatus.none_send.code)
                            .andStatusEqualTo(Status.STATUS_1.status);
                    oLogisticsDetailExample.setOrderByClause(" sn_num asc ");

                    //每次处理两百条数据
                    //oLogisticsDetailExample.setPage(new Page(0, 200));
                    List<OLogisticsDetail> logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);

                    //批次格式为YYYYMMddHHmmss + inerBatch
                    String date = DateUtil.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
                    BigDecimal inerBatch = BigDecimal.ONE;
                    String batch = date + inerBatch;

                    try {
                        //退货下发，0处理中,1成功，2失败
                        Map<String, Object> sendInfoRet = osnOperateReturnService.sendInfoToBusinessSystem(logisticsDetails, id, new BigDecimal(batch));
                        //根据业务平台返回值更新物流明细
                        if (null != sendInfoRet.get("status") && "0".equals(sendInfoRet.get("status"))) {
                            //处理中，不更改明细，更改物流初始状态
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                            if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                logger.info("更新物流状态为生成明细中失败：", id, batch);
                            }
                        } else if (null != sendInfoRet.get("status") && "1".equals(sendInfoRet.get("status"))) {
                            //成功，更新物流明细
                            while (logisticsDetails.size() > 0) {
                                if (!osnOperateReturnService.updateDetailBatch(logisticsDetails, new BigDecimal(batch), LogisticsDetailSendStatus.send_ing.code))
                                    throw new Exception("明细更新失败（下发成功）");
                                //调用接口发送到业务系统，如果接口返回异常，更新物流明细发送失败，不在进行发送
                                logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                                inerBatch = inerBatch.add(BigDecimal.ONE);
                                batch = date + inerBatch.intValue();
                            }
                            //检查是否包含有未发送的sn,如果没有更新为处理成功 ，如果处理中的物流没有
                            if (LogisticsSendStatus.send_ing.code.compareTo(logistics.getSendStatus()) == 0) {
                                if (oLogisticsDetailMapper.countByExample(oLogisticsDetailExample) == 0) {
                                    logistics.setSendStatus(LogisticsSendStatus.send_success.code);
                                    logistics.setSendMsg("联动业务系统成功");
                                    if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                        logger.info("物流明细发送业务系统处理异常，更新数据库失败：", id, batch);
                                    }
                                }
                            }
                        } else if (null != sendInfoRet.get("status") && "2".equals(sendInfoRet.get("status"))) {
                            //失败，更新物流明细
                            AppConfig.sendEmail(emailArr, "SN：" + logistics.getSnBeginNum()+"-" + logistics.getSnEndNum() + "错误信息："+null == sendInfoRet.get("msg")?"接口返回值异常！":(String)sendInfoRet.get("msg"), logistics.getProName() + "退货下发失败！！！");
                            while (logisticsDetails.size() > 0) {
                                if (!osnOperateReturnService.updateDetailBatch(logisticsDetails, new BigDecimal(batch), LogisticsDetailSendStatus.send_fail.code))
                                    throw new Exception("物流明细更新失败（下发失败）");
                                //调用接口发送到业务系统，如果接口返回异常，更新物流明细发送失败，不在进行发送
                                logisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                                inerBatch = inerBatch.add(BigDecimal.ONE);
                                batch = date + inerBatch.intValue();
                            }
                            //检查是否包含有未发送的sn,如果没有更新为处理成功 ，如果处理中的物流没有
                            if (LogisticsSendStatus.send_ing.code.compareTo(logistics.getSendStatus()) == 0) {
                                if (oLogisticsDetailMapper.countByExample(oLogisticsDetailExample) == 0) {
                                    logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                                    logistics.setSendMsg(null == sendInfoRet.get("msg")?"接口返回值异常！":(String)sendInfoRet.get("msg"));
                                    if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                                        logger.info("物流明细发送业务系统处理异常，更新数据库失败,{},{}", id, batch);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        AppConfig.sendEmails("logisticId:" + id + "错误信息:" + MailUtil.printStackTrace(e), "物流任务，退货下发错误报警！！！");
                        logisticsDetails.forEach(det -> {
                            OLogisticsDetail detail = oLogisticsDetailMapper.selectByPrimaryKey(det.getId());
                            detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                            detail.setSbusMsg(e.getLocalizedMessage());
                            oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                        });
                        //更新物流为发送失败停止发送，人工介入
                        logistics.setSendStatus(LogisticsSendStatus.send_fail.code);
                        logistics.setSendMsg(e.getLocalizedMessage());
                        if (oLogisticsMapper.updateByPrimaryKeySelective(logistics) != 1) {
                            logger.info("物流明细发送业务系统处理异常，更新数据库失败：", id, batch);
                        }
                    }

                    //发送邮件
                    /*logistics = oLogisticsMapper.selectByPrimaryKey(id);
                    if (StringUtils.isNotEmpty(logistics.getcUser())) {
                        CUser cUser = cUserMapper.selectById(logistics.getcUser());
                        if (cUser != null && StringUtils.isNotEmpty(cUser.getUserEmail())) {
                            //从字典中取出相应的邮件人
                            List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
                            String[] emailArr = new String[dicts.size()];
                            for (int i = 0; i < dicts.size(); i++) {
                                emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                            }
                            if (LogisticsSendStatus.send_fail.equals(logistics.getSendStatus()) || LogisticsSendStatus.send_part_fail.equals(logistics.getSendStatus())) {
                                //发送异常邮件
                                AppConfig.sendEmail(emailArr, "物流发送失败，号码段:" + logistics.getSnBeginNum() + "-" + logistics.getSnBeginNum() + "(" + logistics.getSendMsg() + ")", "物流发送失败，号码段:" + logistics.getSnBeginNum());
                            } else if (LogisticsSendStatus.send_success.equals(logistics.getSendStatus())) {
                                //发送成功邮件
                                AppConfig.sendEmail(emailArr, "物流发送成功，号码段:" + logistics.getSnBeginNum() + "-" + logistics.getSnEndNum() + "(" + logistics.getSendMsg() + ")", "物流发送成功,号码段:" + logistics.getSnBeginNum());
                            }
                        }
                    }*/
                }
            }
        }
        return true;
    }

    /**
     * 更新物流明细发送状态
     *
     * @param datas
     * @param batch
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateDetailBatch(List<OLogisticsDetail> datas, BigDecimal batch, BigDecimal code) throws Exception {
        Calendar c = Calendar.getInstance();
        for (OLogisticsDetail oLogisticsDetail : datas) {
            oLogisticsDetail.setuTime(c.getTime());
            oLogisticsDetail.setSendStatus(code);
            oLogisticsDetail.setSbusBatch(batch);
            oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
            if (1 != oLogisticsDetailMapper.updateByPrimaryKeySelective(oLogisticsDetail)) {
                throw new MessageException("更新物流明细失败:" + oLogisticsDetail.getId());
            }
        }
        return true;
    }

    /**
     * 生成物流明细
     * @param logcId
     * @return
     * @throws Exception
     */
    @Override
    public boolean genOlogicDetailInfo(String logcId) throws Exception {

        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logcId);
        if (null == logistics) throw new MessageException("退货查询物流信息异常！");

        String planId = logistics.getReceiptPlanId();
        String cUser = logistics.getcUser();
        String logisticsId = logistics.getId();
        String startSn = logistics.getSnBeginNum();
        String endSn = logistics.getSnEndNum();

        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(planId);
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
        List<OSubOrderActivity>  OSubOrderActivitylist = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);

        //排单的活动 下发到业务系统使用此活动
        OActivity oActivity_plan = oActivityMapper.selectByPrimaryKey(planVo.getActivityId());

        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
        //1.起始SN序列号  2.结束SN序列号  3.开始截取的位数   4.结束截取的位数
        if (StringUtils.isBlank(startSn)) {
            logger.info("起始SN序列号为空{}:", startSn);
            throw new ProcessException("起始SN序列号为空");
        }
        if (StringUtils.isBlank(endSn)) {
            logger.info("结束SN序列号为空{}:", endSn);
            throw new ProcessException("结束SN序列号为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(logisticsId)){
            logger.info("物流id为空{}:", logisticsId);
            throw new ProcessException("物流id为空");
        }
        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        OLogisticsExample.Criteria criteria = oLogisticsExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(logisticsId);
        List<OLogistics> oLogistics = oLogisticsMapper.selectByExample(oLogisticsExample);
        if (null==oLogistics || oLogistics.size()==0){
            logger.info("无此物流信息{}:", logisticsId);
            throw new ProcessException("无此物流信息");
        }

        List<String> idList = oLogisticsService.idList(startSn, endSn);

        if (null != idList && idList.size() > 0) {
            for (String idSn : idList) {

                //查询发货锁定
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(idSn)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andRecordStatusEqualTo(Status.STATUS_2.status);
                oLogisticsDetailExample.setOrderByClause(" c_time desc ");

                List<OLogisticsDetail>  OLogisticsDetaillist_fahuo =  logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(OLogisticsDetaillist_fahuo.size()>0) {
                    throw new MessageException(OLogisticsDetaillist_fahuo.get(0).getSnNum()+"已处于发货锁定状态");
                }

                //查询退货锁定
                oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(idSn)
                        .andStatusEqualTo(Status.STATUS_2.status)
                        .andRecordStatusEqualTo(Status.STATUS_2.status);
                oLogisticsDetailExample.setOrderByClause(" c_time desc ");
                List<OLogisticsDetail>  OLogisticsDetaillist_tuihuo =  logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                OLogisticsDetail detail = new OLogisticsDetail();
                if(OLogisticsDetaillist_tuihuo.size()>0) {
                    detail = OLogisticsDetaillist_tuihuo.get(0);
                }

                //id，物流id，创建人，更新人，状态
                detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                detail.setId(idService.genId(TabId.o_logistics_detail));
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logisticsId);
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(oActivity_plan!=null){
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
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = returnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setVersion(Status.STATUS_1.status);
                if (1 != logisticsDetailMapper.insertSelective(detail)) {
                    logger.info("添加失败");
                    throw new ProcessException("添加失败");
                }
            }
        }
        return true;
    }

    /**
     * 发送到业务系统
     *
     * @param datas
     * @param logcId
     * @param batch
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> sendInfoToBusinessSystem(List<OLogisticsDetail> datas, String logcId, BigDecimal batch) throws Exception {

        //物流信息
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logcId);
        if (null == logistics) throw new MessageException("退货查询物流信息异常！");
        //订单信息
        OOrder oOrder = oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
        if (null == oOrder) throw new MessageException("退货查询订单信息异常！");
        //机构信息
        PlatForm platForm = platFormMapper.selectByPlatFormNum(oOrder.getOrderPlatform());
        if (null == platForm) throw new MessageException("退货查询平台信息异常！");
        //排单信息
        ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
        if (null == receiptPlan) throw new MessageException("退货查询排单信息异常！");
        //活动信息
        OActivity oActivity = oActivityMapper.selectByPrimaryKey(receiptPlan.getActivityId());
        if (null == oActivity) throw new MessageException("退货查询活动信息异常！");

        String user = logistics.getcUser();
        String proType = logistics.getProType();
        String beginSn = logistics.getSnBeginNum();
        String endSn = logistics.getSnEndNum();

        if (receiptPlan.getSendProNum() == null || receiptPlan.getSendProNum().compareTo(BigDecimal.ZERO) == 0) {// 发货数量
            receiptPlan.setSendProNum(new BigDecimal(String.valueOf(logistics.getSendNum())));
        } else {
            receiptPlan.setSendProNum(receiptPlan.getSendProNum().add(new BigDecimal(String.valueOf(logistics.getSendNum()))));
        }
        receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
        receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
        if (receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan) != 1) {
            throw new MessageException("更新排单数据失败！");
        }

        //流量卡不进行下发操作
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(oActivity.getActCode()) && ("2204".equals(oActivity.getActCode()) || "2004".equals(oActivity.getActCode()))) {
            logger.info("导入物流数据,流量卡不进行下发操作，活动代码", oActivity.getActCode(), logistics.getId(), JSONObject.toJSON(logistics));
            return FastMap.fastMap("status", "2").putKeyV("msg", "流量卡不下发!");
        }

        //进行机具调整操作
        if (PlatformType.whetherPOS(platForm.getPlatformType())) {
            logger.info("pos发货 更新库存记录:", proType, beginSn, endSn);
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            if (null == agentBusInfo) throw new MessageException("查询订单业务数据失败！");
            ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
            imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
            imsTermAdjustDetail.setMachineId(oActivity.getBusProCode());
            OLogistics posLogistics = oLogisticsMapper.selectByPrimaryKey(logcId);
            logger.info("退货上传物流下发到POS系统:", user, logistics.getId(), datas.toString());
            try {
                AgentResult ar = imsTermAdjustDetailService.insertImsTermAdjustDetail(datas, imsTermAdjustDetail);
                if (ar.isOK()) {
                    return FastMap.fastMap("status", "1");
                } else {
                    return FastMap.fastMap("status", "2").putKeyV("msg", null == ar.getMsg()?"ZPOS接口返回值为空":ar.getMsg());
                }
            } catch (Exception e) {
                logger.info("机具退货调整POS接口调用异常" + posLogistics.getId(), e);
                throw new Exception("ZPOS机具退货调整POS接口调用异常");
            }
        } else if (PlatformType.MPOS.code.equals(platForm.getPlatformType())) {
            logger.info("首刷发货 更新库存记录:{}:{}", proType, beginSn, endSn);
            //起始sn
            OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
            exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(logistics.getSnBeginNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsstart = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
            OLogisticsDetail detailstart = logisticsDetailsstart.get(0);

            //结束sn
            OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
            exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(logistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsend = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
            OLogisticsDetail detailend = logisticsDetailsend.get(0);

            AdjustmentMachineVo vo = new AdjustmentMachineVo();
            vo.setOptUser(user);
            vo.setSnStart(detailstart.getSnNum() + detailstart.getTerminalidCheck());
            vo.setSnEnd(detailend.getSnNum() + detailend.getTerminalidCheck());
            vo.setSnNum(logistics.getSendNum().toString());

            //发货订单的业务编号
            OOrder order = oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
            AgentBusInfo busInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            vo.setNewBusNum(busInfo.getBusNum());
            vo.setPlatformType(platForm.getPlatformType());

            //退货订单的业务编号
            OReturnOrderDetail oReturnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(receiptPlan.getReturnOrderDetailId());

            OOrder orderreturn = oOrderMapper.selectByPrimaryKey(oReturnOrderDetail.getOrderId());
            AgentBusInfo returnbusInfo = agentBusInfoMapper.selectByPrimaryKey(orderreturn.getBusId());
            vo.setOldBusNum(returnbusInfo.getBusNum());
            vo.setPlatformNum(returnbusInfo.getBusPlatform());
            //新活动
            vo.setNewAct(oActivity.getBusProCode());

            //老活动查询 老活动采用退货明细中的活动编号,如果没有活动编号从退货明细中查询订单里的活动，此处补差价后会出现问题，已添加actid进行修复，此处为兼容老的数据
            if (oReturnOrderDetail.getActid() == null || StringUtils.isEmpty(oReturnOrderDetail.getActid())) {
                OSubOrderExample old_OSubOrder = new OSubOrderExample();
                old_OSubOrder.or()
                        .andOrderIdEqualTo(oReturnOrderDetail.getOrderId())
                        .andProIdEqualTo(oReturnOrderDetail.getProId())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrder> list_osub_old = oSubOrderMapper.selectByExample(old_OSubOrder);

                if (list_osub_old.size() == 0) {
                    throw new MessageException("退货机具活动信息未找到");
                }
                OSubOrder old_suborder = list_osub_old.get(0);
                OSubOrderActivityExample example_old_activity = new OSubOrderActivityExample();
                example_old_activity.or().andSubOrderIdEqualTo(old_suborder.getId()).andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrderActivity> list_old_act = subOrderActivityMapper.selectByExample(example_old_activity);
                if (list_old_act.size() == 0) {
                    throw new MessageException("退货机具活动信息未找到");
                }
                OSubOrderActivity old_act = list_old_act.get(0);
                vo.setOldAct(old_act.getBusProCode());
            } else {
                OActivity return_sn_activity = oActivityMapper.selectByPrimaryKey(oReturnOrderDetail.getActid());
                vo.setOldAct(return_sn_activity.getBusProCode());
            }
            //同平台下发，不同平台不下发
            if (busInfo.getBusPlatform().equals(returnbusInfo.getBusPlatform())) {
                try {
                    logger.info("退货上传物流下发到首刷系统:{}:{}:{}:{}", user, logistics.getId(), vo.getSnStart(), vo.getSnEnd());
                    AgentResult mposXF = termMachineService.adjustmentMachine(vo);
                    logger.info("机具退货调整首刷接口调用:{}:{}:{}:{}:{}", user, logistics.getId(), vo.getSnStart(), vo.getSnEnd(), mposXF.getMsg());
                    if (mposXF.isOK()) {
                        return FastMap.fastMap("status", "1");
                    } else {
                        return FastMap.fastMap("status", "2").putKeyV("msg", null == mposXF.getMsg()?"MPOS接口返回值为空":mposXF.getMsg());
                    }
                } catch (Exception e) {
                    throw new Exception(e.getLocalizedMessage());
                }
            } else {
                return FastMap.fastMap("status", "2").putKeyV("msg", "不同平台不下发，手动调整");
            }
        } else if (PlatformType.SSPOS.code.equals(platForm.getPlatformType())) {
            logger.info("pos发货 更新库存记录:{}:{}", proType, beginSn, endSn);
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            if (null == agentBusInfo) {
                throw new MessageException("查询订单业务数据失败！");
            }

            AdjustmentMachineVo vo = new AdjustmentMachineVo();
            vo.setPlatformType(platForm.getPlatformType());
            ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
            imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
            imsTermAdjustDetail.setMachineId(oActivity.getBusProCode());
            OLogistics ssLogistics = oLogisticsMapper.selectByPrimaryKey(logcId);
            logger.info("退货上传物流下发到POS系统:{}:{}:{}", user, logistics.getId(), datas.toString());
            try {
                vo.setImsTermAdjustDetail(imsTermAdjustDetail);
                vo.setLogisticsDetailList(datas);
                AgentResult ar = termMachineService.adjustmentMachine(vo);
                if (ar.isOK()) {
                    return FastMap.fastMap("status", "1");
                } else {
                    return FastMap.fastMap("status", "2").putKeyV("msg", null == ar.getMsg()?"SSPOS接口返回信息为空":ar.getMsg());
                }
            } catch (Exception e) {
                throw new Exception(e.getLocalizedMessage());
            }
        } else if (PlatformType.RDBPOS.code.equals(platForm.getPlatformType())) {
            Map<String, Object> reqMap = new HashMap<String, Object>();
            List<Map<String, Object>> reqList = new ArrayList<Map<String, Object>>();
            AdjustmentMachineVo vo = new AdjustmentMachineVo();
            //物流对应业务信息
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            //退货前信息（退货子订单明细-->>查询原订单-->>查询原来排单-->>查询原来业务信息表--busNum）
            Map<String, Object> agentBusInfoMap = agentBusInfoMapper.selectByReturnDetailId(FastMap.fastMap("returnOrderDetailId", receiptPlan.getReturnOrderDetailId()));
            if (null == agentBusInfoMap.get("BUSNUM") || null == agentBusInfoMap.get("BUSPROCODE") || null == agentBusInfoMap.get("BUSPLATFORM")) throw new Exception("退货原订单查询异常,请联系管理员!");

            //封装参数
            reqList.add(FastMap.
                    fastMap("terminalNoStart", logistics.getSnBeginNum()).//终端编号起始
                    putKeyV("terminalNoEnd", logistics.getSnEndNum()). //终端编号结束
                    putKeyV("agencyId", agentBusInfo.getBusNum()).//终端政策id
                    putKeyV("terminalPolicyId", oActivity.getBusProCode()).//代理商A码
                    putKeyV("inBoundDate", (new SimpleDateFormat("yyyyMMdd")).format(new Date()))); //发货时间
            reqMap.put("taskId", logistics.getId()); //唯一值
            reqMap.put("branchId", agentBusInfo.getBusPlatform().substring(0, agentBusInfo.getBusPlatform().indexOf("_"))); //当前品牌id
            reqMap.put("oldAgencyId", agentBusInfoMap.get("BUSNUM")); //代理商A码
            reqMap.put("oldTerminalPolicyId", agentBusInfoMap.get("BUSPROCODE")); //旧终端政策id
            reqMap.put("terminalNos", reqList); //修改终端政策列表
            vo.setParamMap(reqMap);
            vo.setPlatformType(PlatformType.RDBPOS.code);

            //机具调整
            try {
                AgentResult rdbXF = termMachineService.adjustmentMachine(vo);
                if (null != rdbXF.getStatus() && rdbXF.getStatus() == 0) {
                    //处理中
                    return FastMap.fastMap("status", "0");
                } else if (null != rdbXF.getStatus() && rdbXF.getStatus() == 1) {
                    //处理成功
                    return FastMap.fastMap("status", "1");
                } else {
                    //处理失败
                    return FastMap.fastMap("status", "2").putKeyV("msg", null == rdbXF.getMsg()?"RDB接口返回信息为空":rdbXF.getMsg());
                }
            }catch (Exception e){
                throw new Exception("RDB接口异常！！！");
            }
        } else {
            return FastMap.fastMap("status", "2").putKeyV("msg", "未实现的物流平台");
        }
    }
}
