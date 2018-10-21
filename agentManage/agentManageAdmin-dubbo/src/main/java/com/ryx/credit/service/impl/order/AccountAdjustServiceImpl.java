package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.MapUtil;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.dao.agent.DataHistoryMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 调账服务（退货、退差价时使用）
 * @Date: 15:52 2018/7/24
 */
@Service("accountAdjustService")
public class AccountAdjustServiceImpl implements IAccountAdjustService {

    private static Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    @Resource
    IPaymentDetailService paymentDetailService;
    @Autowired
    OPaymentMapper paymentMapper;
    @Autowired
    OPaymentDetailMapper paymentDetailMapper;
    @Resource
    IdService idService;
    @Autowired
    DataHistoryMapper dataHistoryMapper;
    @Autowired
    ORefundAgentMapper refundAgentMapper;
    @Autowired
    OAccountAdjustMapper accountAdjustMapper;
    @Autowired
    OAccountAdjustDetailMapper accountAdjustDetailMapper;
    @Resource
    IOrderReturnService orderReturnService;


    public Map<String, Object> createTkeRecord(OPayment payment, BigDecimal thisPaymentOutstandingAmt) {
        Map<String, Object> oneTakeoutRecord = new HashMap<>();
        oneTakeoutRecord.put("orderId", payment.getOrderId());
        oneTakeoutRecord.put("paymentId", payment.getId());
        oneTakeoutRecord.put("payType", payment.getPayMethod());
        oneTakeoutRecord.put("payAmt", thisPaymentOutstandingAmt);
        oneTakeoutRecord.put("payment", payment);
        return oneTakeoutRecord;
    }

    public void updatePaymentDetail(OPaymentDetail updateOPaymentDetail, String srcId, String srcType) throws ProcessException {
        updateOPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
        updateOPaymentDetail.setRealPayAmount(updateOPaymentDetail.getPayAmount());
        updateOPaymentDetail.setPayTime(new Date());
        updateOPaymentDetail.setSrcId(srcId);
        updateOPaymentDetail.setSrcType(srcType);
        int counts = paymentDetailMapper.updateByPrimaryKeySelective(updateOPaymentDetail);
        if (counts <= 0) {
            throw new ProcessException("更新付款明细失败");
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 调账功能，退货退款和退差价时使用
     * @Param isRealAdjust 是否真正调账（true-是，会更新数据库， false-否，只进行预算不更新数据库）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣机具欠款(0-不抵扣  1-抵扣)
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Param srcType 源类型（使用PamentSrcType枚举）
     * @Param userid 操作用户
     * @Date: 15:47 2018/7/24
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public Map<String, Object> adjust(boolean isRealAdjust, BigDecimal adjustAmt, String adjustType,
                                      int isAdjustOrder, String agentId, String userid, String srcId, String srcType) throws ProcessException {

        Map<String, Object> result = new HashMap<>();

        try {

            //退款金额
            BigDecimal leftAmt = adjustAmt;

            //记录抵扣明细
            List<Map<String, Object>> takeoutList = new ArrayList<>();

            //记录已抵扣的付款单
            Set<String> takesPaymentId = new HashSet<>();

            String batchcode_new = "";
            String batchcode_old = "";

            //需要抵扣欠款时，先抵扣代理商所有机具欠款（包含分润分期、线下分期），还剩余的金额走线下退款
            if (isAdjustOrder != 0) {

                //查询欠款明细列表
                List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(agentId);
                for (OPaymentDetail ndetail : paymentDetails) {
                    //金额全部抵扣完时退出循环
                    if (leftAmt.compareTo(BigDecimal.ZERO) <= 0) {
                        break;
                    }
                    String paymentId = ndetail.getPaymentId();
                    //如果已处理过这个付款单，跳过
                    if (takesPaymentId.contains(paymentId)) {
                        continue;
                    }
                    //进行抵扣
                    takesPaymentId.add(paymentId);
                    //查询付款单和付款单明细
                    OPayment payment = paymentDetailService.getPaymentById(paymentId);
                    //查此付款单的可抵扣明细
                    List<OPaymentDetail> oPaymentDetails = paymentDetailService.getPaymentDetails(paymentId, "DF", "BF", "YQ");
                    //计算此付款单可抵扣金额
                    BigDecimal thisPaymentOutstandingAmt = BigDecimal.ZERO;
                    for (OPaymentDetail oneDetail : oPaymentDetails) {
                        thisPaymentOutstandingAmt = thisPaymentOutstandingAmt.add(oneDetail.getPayAmount());
                    }
                    //=================剩余退款金额可大于当前循环订单欠款======================
                    if (thisPaymentOutstandingAmt.compareTo(leftAmt) <= 0) {
                        //抵扣金额减去当前订单的欠款金额
                        leftAmt = leftAmt.subtract(thisPaymentOutstandingAmt);
                        //抵扣记录
                        takeoutList.add(createTkeRecord(payment, thisPaymentOutstandingAmt));
                        if (isRealAdjust) {
                            //更新付款明细
                            for (OPaymentDetail oneDetail : oPaymentDetails) {
                                OPaymentDetail updateOPaymentDetail = paymentDetailMapper.selectByPrimaryKey(oneDetail.getId());
                                //更新为结清
                                updatePaymentDetail(updateOPaymentDetail, srcId, srcType);
                            }
                            //更新付款单
                            if (payment.getOutstandingAmount().compareTo(thisPaymentOutstandingAmt) > 0) {
                                //更新为部分付款
                                updatePaymentOutstandingAmt(paymentId, thisPaymentOutstandingAmt);
                            } else if (payment.getOutstandingAmount().compareTo(thisPaymentOutstandingAmt) == 0) {
                                //更新为部分付款
                                updatePaymentComplete(paymentId, srcId, srcType);
                            } else {
                                throw new ProcessException("计算后订单剩余待还金额出现小于0的情况");
                            }
                        }
                        //==========================剩余抵扣金额小于订单待付金额=============================
                    } else {
                        //订单剩余待付
                        thisPaymentOutstandingAmt = leftAmt;
                        //抵扣记录 抵扣剩余所有金额
                        takeoutList.add(createTkeRecord(payment, leftAmt));
                        //========生成新的付款计划。先计算剩余代付金额和剩余期数======
                        List<OPaymentDetail> planNows = paymentDetailService.getPaymentDetails(paymentId);
                        //现在付款计划未还部分
                        List<OPaymentDetail> planNows_df = new ArrayList<>();
                        //现在付款计划已付部分
                        List<OPaymentDetail> planNows_complate = new ArrayList<>();
                        //付款分期未还金额
                        BigDecimal outAmt = BigDecimal.ZERO;
                        //分期待付期数
                        int outPlanNum = 0;
                        //重算起始分期期数
                        BigDecimal startPlanNum = BigDecimal.ONE;
                        //代付分期起始时间
                        Date startTime = null;
                        for (OPaymentDetail detail : planNows) {
                            if (!detail.getPaymentStatus().equals(PaymentStatus.JQ.code) && !detail.getPaymentStatus().equals(PaymentStatus.FKING.code)) {
                                outAmt = outAmt.add(detail.getPayAmount());
                                if (detail.getPayType().equals(PaymentType.DKFQ.code) || detail.getPayType().equals(PaymentType.FRFQ.code)) {
                                    outPlanNum = outPlanNum + 1;
                                    if (startTime == null) {
                                        startTime = detail.getPlanPayTime();
                                        startPlanNum = detail.getPlanNum().subtract(BigDecimal.ONE);
                                    }
                                }
                                planNows_df.add(detail);
                            } else {
                                planNows_complate.add(detail);
                            }
                        }

                        //待还金额需要减去可抵扣金额
                        outAmt = outAmt.subtract(leftAmt);

                        //计算新付款计划
                        List<Map> calNews = new ArrayList<>();
                        if (outPlanNum > 0) {
                            calNews = StageUtil.stageOrder(outAmt, outPlanNum, startTime, 16);
                        }


                        //新付款计划入库付款明细
                        List<OPaymentDetail> planNews = new ArrayList<>();
                        batchcode_new = new Date().getTime() + "";

                        if (planNows_df != null && planNows_df.size() > 0) {
                            batchcode_old = planNows_df.get(0).getBatchCode();
                            for (Map map : calNews) {
                                OPaymentDetail newDeatilPlan = new OPaymentDetail();
                                newDeatilPlan.setId(idService.genId(TabId.o_payment_detail));
                                newDeatilPlan.setPaymentId(paymentId);
                                newDeatilPlan.setBatchCode(batchcode_new);
                                newDeatilPlan.setPaymentType(planNows_df.get(0).getPaymentType());
                                newDeatilPlan.setOrderId(planNows_df.get(0).getOrderId());
                                newDeatilPlan.setPayType(planNows_df.get(0).getPayType());
                                newDeatilPlan.setPayAmount(MapUtil.getBigDecimal(map, "item"));
                                newDeatilPlan.setPaymentStatus(PaymentStatus.DF.code);
                                newDeatilPlan.setPlanNum(startPlanNum.add(MapUtil.getBigDecimal(map, "count")));
                                newDeatilPlan.setPlanPayTime((Date) map.get("date"));
                                newDeatilPlan.setcDate(new Date());
                                newDeatilPlan.setcUser(userid);
                                newDeatilPlan.setAgentId(agentId);
                                planNews.add(newDeatilPlan);
                                //新付款计划入库
                                if (isRealAdjust) {
                                    if(1!=paymentDetailMapper.insertSelective(newDeatilPlan)){
                                        throw new MessageException("添加付款明细异常");
                                    }
                                }

                            }
                        }
                        //付款明细中现在付款计划未还部分移入历史表
                        if (isRealAdjust) {
                            for (OPaymentDetail wh : planNows_df) {
                                DataHistory dataHistory = new DataHistory();
                                dataHistory.setId(idService.genId(TabId.data_history));
                                dataHistory.setDataId(paymentId);
                                dataHistory.setDataType(DataHistoryType.PAYMENT_DETAIL.code);
                                dataHistory.setDataCotent(JSONObject.toJSONString(wh));
                                dataHistory.setDataVersion(BigDecimal.ZERO);
                                dataHistory.setcTime(new Date());
                                dataHistory.setcUser(userid);
                                dataHistory.setStatus(BigDecimal.ONE);
                                dataHistoryMapper.insertSelective(dataHistory);
                                OPaymentDetailExample example = new OPaymentDetailExample();
                                example.or().andIdEqualTo(wh.getId());
                                paymentDetailMapper.deleteByExample(example);
                            }
                        }
                        //付款明细中插入退款抵扣记录
                        if (isRealAdjust) {
                            insertToPaymentDetail(paymentId, planNows_df.get(0), leftAmt, agentId, srcId, srcType, userid);
                        }
                        //===========更新订单部分金额支付完成=============
                        if (isRealAdjust) {
                            updatePaymentOutstandingAmt(paymentId, leftAmt);
                        }
                        result.put("planNows", planNows);
                        result.put("planNows_df", planNows_df);
                        result.put("planNows_complate", planNows_complate);
                        result.put("planNews", planNews);
                        //退款抵扣成0
                        leftAmt = BigDecimal.ZERO;
                    }
                }
            }
            result.put("takeoutList", takeoutList);
            // 如果最终金额还有剩余，走线下退款
            ORefundAgent refundAgent = new ORefundAgent();
//            if (leftAmt.compareTo(BigDecimal.ZERO) > 0) {
                refundAgent.setRefundType(adjustType);
                refundAgent.setSrcId(srcId);
                refundAgent.setRefundAmount(leftAmt);
                refundAgent.setAgentId(agentId);
                refundAgent.setcUser(userid);
                result.put("refund", refundAgent);
//            }
            if (isRealAdjust) {
                //生成调账记录
                OAccountAdjust oAccountAdjust = new OAccountAdjust();
                oAccountAdjust.setId(idService.genId(TabId.o_account_adjust));
                oAccountAdjust.setAdjustType(adjustType);
                oAccountAdjust.setSrcId(srcId);
                oAccountAdjust.setAdjustAmount(adjustAmt);
                oAccountAdjust.setIsAdjustOrder(new BigDecimal(isAdjustOrder));
                oAccountAdjust.setAgentId(agentId);
                oAccountAdjust.setcUser(userid);
                oAccountAdjust.setcDate(new Date());
                if(1!=accountAdjustMapper.insertSelective(oAccountAdjust)){
                    throw new MessageException("调账记录生成失败");
                }

                //生成调账抵扣明细
                for (Map<String, Object> map : takeoutList) {
                    OAccountAdjustDetail oAccountAdjustDetail = new OAccountAdjustDetail();
                    oAccountAdjustDetail.setId(idService.genId(TabId.o_account_adjust_detail));
                    oAccountAdjustDetail.setAdjustId(oAccountAdjust.getId());
                    oAccountAdjustDetail.setAdjustType(adjustType);
                    oAccountAdjustDetail.setSrcId(srcId);
                    oAccountAdjustDetail.setTakeOutAmount(MapUtil.getBigDecimal(map, "payAmt"));
                    oAccountAdjustDetail.setOrderId(MapUtil.getString(map, "orderId"));
                    oAccountAdjustDetail.setPayType(MapUtil.getString(map, "payType"));
                    oAccountAdjustDetail.setPaymentDetailId(MapUtil.getString(map, "paymentId"));
                    oAccountAdjustDetail.setBatchCodeNew(batchcode_new);
                    oAccountAdjustDetail.setBatchCodeOld(batchcode_old);
                    oAccountAdjustDetail.setAgentId(agentId);
                    oAccountAdjustDetail.setcUser(userid);
                    oAccountAdjustDetail.setcDate(new Date());
                    if(accountAdjustDetailMapper.insertSelective(oAccountAdjustDetail)!=1){
                        throw new MessageException("生成调账抵扣明细失败");
                    }
                }

                refundAgent.setId(idService.genId(TabId.o_refund_agent));
                refundAgent.setAdjustId(oAccountAdjust.getId());
                if(1!=refundAgentMapper.insertSelective(refundAgent)){
                    throw new MessageException("代理商退款单生成失败");
                }
            }
            //抵扣欠款金额
            BigDecimal takeAmt = BigDecimal.ZERO;
            takeAmt = adjustAmt.subtract(leftAmt);
            result.put("takeAmt", takeAmt);
            //更新退货表
            if (isRealAdjust && adjustType.equals(AdjustType.TKTH.adjustType)) {
                orderReturnService.doPlan(srcId, takeAmt, userid);
            }


        } catch (ProcessException e) {
            log.error("调账计算出现错误,{}", e.getMessage(), e);
            throw new ProcessException("调账计算出现错误," + e.getMessage());
        } catch (Exception e) {
            log.error("调账计算出现错误", e);
            throw new ProcessException("调账计算出现错误");
        }
        return result;
    }

    public void insertToPaymentDetail(String paymentId, OPaymentDetail paymentDetail, BigDecimal thisDetailPayAmt, String agentId, String srcId, String srcType, String userid) throws ProcessException {
        try {
            OPaymentDetail newDeatil = new OPaymentDetail();
            newDeatil.setId(idService.genId(TabId.o_payment_detail));
            newDeatil.setPaymentId(paymentId);
            newDeatil.setPaymentType(paymentDetail.getPaymentType());
            newDeatil.setOrderId(paymentDetail.getOrderId());
            newDeatil.setPayType(paymentDetail.getPayType());
            newDeatil.setPayAmount(thisDetailPayAmt);
            newDeatil.setRealPayAmount(thisDetailPayAmt);
            newDeatil.setPayTime(new Date());
            newDeatil.setAgentId(agentId);
            newDeatil.setSrcId(srcId);
            newDeatil.setSrcType(srcType);
            newDeatil.setPaymentStatus(PaymentStatus.JQ.code);
            newDeatil.setcDate(new Date());
            newDeatil.setcUser(userid);
            newDeatil.setPlanNum(BigDecimal.ZERO);
            newDeatil.setPlanPayTime(new Date());
            if(1!=paymentDetailMapper.insertSelective(newDeatil)){
                throw new MessageException("添加付款明细异常");
            }
        } catch (Exception e) {
            log.error("插入抵扣付款明细失败srcId={" + srcId + "},srcType{" + srcType + "}", e);
            throw new ProcessException("插入抵扣付款明细失败srcId={" + srcId + "},srcType{" + srcType + "}");
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询调账记录和明细
     * @Date: 18:03 2018/8/3
     */
    @Override
    public Map<String, Object> getAccountAdjustDetail(String srcId, String adjustType, String userid, String
            agentId) {
        Map<String, Object> map = new HashMap<>();
        OAccountAdjustExample oAccountAdjustExample = new OAccountAdjustExample();
        oAccountAdjustExample.or().andSrcIdEqualTo(srcId).andAdjustTypeEqualTo(adjustType);
        List<OAccountAdjust> oAccountAdjusts = accountAdjustMapper.selectByExample(oAccountAdjustExample);
        if (oAccountAdjusts == null || oAccountAdjusts.size() <= 0) {
            return null;
        } else {
            map.put("adjust", oAccountAdjusts.get(0));
            OAccountAdjustDetailExample example = new OAccountAdjustDetailExample();
            example.or().andSrcIdEqualTo(srcId).andAdjustTypeEqualTo(adjustType);
            List<OAccountAdjustDetail> details = accountAdjustDetailMapper.selectByExample(example);
            map.put("details", details);
            ORefundAgentExample refundAgentExample = new ORefundAgentExample();
            refundAgentExample.or().andAdjustIdEqualTo(oAccountAdjusts.get(0).getId());
            List<ORefundAgent> refundAgents = refundAgentMapper.selectByExample(refundAgentExample);
            if (refundAgents != null && refundAgents.size() > 0) {
                map.put("refund", refundAgents.get(0));
            }
        }

        return map;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新订单全部付款记录为完成
     * @Date: 11:22 2018/7/28
     */
    public void updatePaymentComplete(String paymentId, String srcId, String srcType) throws ProcessException {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getPayAmount());
        payment.setOutstandingAmount(BigDecimal.ZERO);
        payment.setPayCompletTime(new Date());
        payment.setPayStatus(PayStatus.CLOSED.code);
        payment.setPlanSucTime(new Date());
        int counts = paymentMapper.updateByPrimaryKeySelective(payment);
        if (counts <= 0) {
            throw new ProcessException("更新订单全部付款记录为完成");
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新订单部分金额支付完成
     * @Date: 11:22 2018/7/28
     */
    public void updatePaymentOutstandingAmt(String paymentId, BigDecimal payAmt) throws ProcessException {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getRealAmount().add(payAmt));
        payment.setOutstandingAmount(payment.getOutstandingAmount().subtract(payAmt));
        payment.setPayStatus(PayStatus.PART_PAYMENT.code);
        int counts = paymentMapper.updateByPrimaryKeySelective(payment);
        if (counts <= 0) {
            throw new ProcessException("更新订单部分金额支付完成失败");
        }
    }


}
