package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
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
    @Transactional
    public Map<String, Object> adjust(boolean isRealAdjust, BigDecimal adjustAmt, String adjustType,
                                      int isAdjustOrder, String agentId, String userid, String srcId, String srcType) throws ProcessException {

        Map<String, Object> result = new HashMap<>();

        try {

            //退款金额
            BigDecimal leftAmt = adjustAmt;

            //记录抵扣明细
            List<Map<String, Object>> takeoutList = new ArrayList<>();

            String batchcode_new = "";
            String batchcode_old = "";

            //需要抵扣欠款时，先抵扣代理商所有机具欠款（包含分润分期、线下分期），还剩余的金额走线下退款
            if (isAdjustOrder != 0) {

                //剩余金额大于0的时候循环抵扣机具欠款
                while (leftAmt.compareTo(BigDecimal.ZERO) > 0) {
                    //查询一条欠款
                    List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(agentId);

                    //当没有欠款时结束循环
                    if (paymentDetails == null || paymentDetails.size() <= 0) {
                        break;
                    }

                    //取第一条付款单处理
                    String paymentId = paymentDetails.get(0).getPaymentId();

                    //查询付款单和付款单明细
                    OPayment payment = paymentDetailService.getPaymentById(paymentId);

                    //退款可全部抵扣此订单欠款时，直接更新订单
                    if (payment.getOutstandingAmount().compareTo(leftAmt) <= 0) {
                        leftAmt = leftAmt.subtract(payment.getOutstandingAmount());

                        List<OPaymentDetail> oPaymentDetails = paymentDetailService.getPaymentDetails(paymentId, "DF", "BF", "YQ");
                        OPaymentDetail paymentDetail = oPaymentDetails.get(0);
                        //for (OPaymentDetail paymentDetail : oPaymentDetails) {
                            //一条抵扣记录
                            Map<String, Object> oneTakeoutRecord = new HashMap<>();
                            oneTakeoutRecord.put("orderId", payment.getOrderId());
                            oneTakeoutRecord.put("paymentId", paymentId);
                            oneTakeoutRecord.put("paymentDetailId", paymentDetail.getId());
                            oneTakeoutRecord.put("batchCode", paymentDetail.getBatchCode());
                            oneTakeoutRecord.put("paymentType", paymentDetail.getPaymentType());
                            oneTakeoutRecord.put("payType", paymentDetail.getPayType());
                            oneTakeoutRecord.put("payAmt", payment.getOutstandingAmount());
                            //oneTakeoutRecord.put("planPayTime", paymentDetail.getPlanPayTime());
                            //oneTakeoutRecord.put("planNum", paymentDetail.getPlanNum());
                            oneTakeoutRecord.put("payment", payment);
                            takeoutList.add(oneTakeoutRecord);
                        //}


                        //更新订单状态全部明细为支付完成
                        if (isRealAdjust) {
                            updatePaymentComplete(paymentId, srcId, srcType);
                        }



                        //退款不足以抵消订单欠款时，需要插入一条抵扣记录，并且更新付款计划
                    } else {

                        List<OPaymentDetail> oPaymentDetails = paymentDetailService.getPaymentDetails(paymentId, "DF", "BF", "YQ");

                        //生成新的付款计划。先计算剩余代付金额和剩余期数

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
                            //if ((detail.getPayType().equals(PaymentType.DKFQ.code) || detail.getPayType().equals(PaymentType.FRFQ.code)) && (detail.getPaymentStatus().equals(PaymentStatus.DF.code) || detail.getPaymentStatus().equals(PaymentStatus.YQ.code))) {
                            if (!detail.getPaymentStatus().equals(PaymentStatus.JQ.code)) {
                                outAmt = outAmt.add(detail.getPayAmount());
                                if (detail.getPayType().equals(PaymentType.DKFQ.code) || detail.getPayType().equals(PaymentType.FRFQ.code)){
                                    outPlanNum++;
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
                        result.put("planNows", planNows);
                        result.put("planNows_df", planNows_df);
                        result.put("planNows_complate", planNows_complate);
                        result.put("takeoutList", takeoutList);

                        //计算新付款计划
                        List<Map> calNews = new ArrayList<>();
                        if (outPlanNum > 0) {
                            calNews = StageUtil.stageOrder(outAmt, outPlanNum, startTime, 16);
                        }


                        //更新订单部分金额支付完成
                        if (isRealAdjust) {
                            updatePaymentOutstandingAmt(paymentId, leftAmt);
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
                                    paymentDetailMapper.insertSelective(newDeatilPlan);
                                }

                            }
                        }

                        result.put("planNews", planNews);


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
                        Map<String, Object> oneTakeoutRecord = new HashMap<>();
                        oneTakeoutRecord.put("orderId", payment.getOrderId());
                        oneTakeoutRecord.put("paymentId", paymentId);
                        //oneTakeoutRecord.put("paymentDetailId", oPaymentDetails.get(0).getId());
                        oneTakeoutRecord.put("batchCode", oPaymentDetails.get(0).getBatchCode());
                        oneTakeoutRecord.put("paymentType", oPaymentDetails.get(0).getPaymentType());
                        oneTakeoutRecord.put("payType", oPaymentDetails.get(0).getPayType());
                        oneTakeoutRecord.put("payAmt", leftAmt);
                        oneTakeoutRecord.put("srcId", srcId);
                        oneTakeoutRecord.put("srcType", srcType);
                        oneTakeoutRecord.put("payment", payment);
                        takeoutList.add(oneTakeoutRecord);

                        OPaymentDetail newDeatil = new OPaymentDetail();
                        newDeatil.setId(idService.genId(TabId.o_payment_detail));
                        newDeatil.setPaymentId(paymentId);
                        newDeatil.setPaymentType(oPaymentDetails.get(0).getPaymentType());
                        newDeatil.setOrderId(oPaymentDetails.get(0).getOrderId());
                        newDeatil.setPayType(oPaymentDetails.get(0).getPayType());
                        newDeatil.setPayAmount(leftAmt);
                        newDeatil.setRealPayAmount(leftAmt);
                        newDeatil.setPayTime(new Date());
                        newDeatil.setAgentId(agentId);
                        newDeatil.setSrcId(srcId);
                        newDeatil.setSrcType(srcType);
                        newDeatil.setPaymentStatus(PaymentStatus.JQ.code);
                        newDeatil.setcDate(new Date());
                        newDeatil.setcUser(userid);
                        newDeatil.setPlanNum(BigDecimal.ZERO);
                        newDeatil.setPlanPayTime(new Date());

                        if (isRealAdjust) {
                            paymentDetailMapper.insertSelective(newDeatil);
                        }

                        leftAmt = BigDecimal.ZERO;

                    }

                }
            }


            // 如果最终金额还有剩余，走线下退款
            if (leftAmt.compareTo(BigDecimal.ZERO) > 0) {
                ORefundAgent refundAgent = new ORefundAgent();
                refundAgent.setRefundType(adjustType);
                refundAgent.setSrcId(srcId);
                refundAgent.setRefundAmount(leftAmt);
                refundAgent.setAgentId(agentId);
                refundAgent.setcUser(userid);
                result.put("refund", refundAgent);
                if (isRealAdjust) {
                    refundAgent.setId(idService.genId(TabId.o_refund_agent));
                    refundAgentMapper.insertSelective(refundAgent);
                }
            }

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
                accountAdjustMapper.insertSelective(oAccountAdjust);

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
                    oAccountAdjustDetail.setPaymentDetailId(MapUtil.getString(map, "paymentDetailId"));
                    oAccountAdjustDetail.setBatchCodeNew(batchcode_new);
                    oAccountAdjustDetail.setBatchCodeOld(batchcode_old);
                    oAccountAdjustDetail.setAgentId(agentId);
                    oAccountAdjustDetail.setcUser(userid);
                    oAccountAdjustDetail.setcDate(new Date());
                    accountAdjustDetailMapper.insertSelective(oAccountAdjustDetail);
                }
            }

            //抵扣欠款金额
            BigDecimal takeAmt = BigDecimal.ZERO;
            takeAmt = adjustAmt.subtract(leftAmt);

            //更新退货表
            if (isRealAdjust && adjustType.equals(AdjustType.TKTH.adjustType)) {
                orderReturnService.doPlan(srcId, takeAmt, userid);
            }

            result.put("takeAmt", takeAmt);
        } catch (Exception e) {
            log.error("调账计算出现错误",e);
            throw new ProcessException("调账计算出现错误");
        }
        return result;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询调账记录和明细
     * @Date: 18:03 2018/8/3
     */
    @Override
    public Map<String, Object> getAccountAdjustDetail(String srcId, String adjustType, String userid, String agentId) {
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
        }

        return map;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新订单全部付款记录为完成
     * @Date: 11:22 2018/7/28
     */
    public void updatePaymentComplete(String paymentId, String srcId, String srcType) {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getPayAmount());
        payment.setOutstandingAmount(BigDecimal.ZERO);
        payment.setPayCompletTime(new Date());
        payment.setPayStatus(PayStatus.CLOSED.code);
        payment.setPlanSucTime(new Date());
        paymentMapper.updateByPrimaryKeySelective(payment);

        //更新明细
        paymentDetailMapper.updatePaymentDetailByPaymentId(paymentId, srcId, srcType);
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新订单部分金额支付完成
     * @Date: 11:22 2018/7/28
     */
    public void updatePaymentOutstandingAmt(String paymentId, BigDecimal payAmt) {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getRealAmount().add(payAmt));
        payment.setOutstandingAmount(payment.getOutstandingAmount().subtract(payAmt));
        payment.setPayStatus(PayStatus.PART_PAYMENT.code);
        paymentMapper.updateByPrimaryKeySelective(payment);
    }


}
