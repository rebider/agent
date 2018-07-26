package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.pojo.admin.order.OAccountAdjustDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.ORefundAgent;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 调账服务（退货、退差价时使用）
 * @Date: 15:52 2018/7/24
 */
@Service("accountAdjustService")
public class AccountAdjustServiceImpl implements IAccountAdjustService {

    @Resource
    IPaymentDetailService paymentDetailService;


    /**
     * @Author: Zhang Lei
     * @Description: 调账预算（生成草稿，不会真正进行欠款抵扣和生成退款记录）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣欠款(0-不抵扣  1-抵扣)
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Date: 15:47 2018/7/24
     */
    @Override
    public Map<String, Object> adjustPreCalculate(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId, String srcId) throws ProcessException {
        return null;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 调账（真正进行欠款抵扣和生成退款记录）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣欠款(0-不抵扣  1-抵扣)
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Param userid 操作用户
     * @Date: 15:47 2018/7/24
     */
    @Override
    public Map<String, Object> adjust(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId, String userid, String srcId) throws ProcessException {
        return null;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 调账计算方法
     * @Date: 15:59 2018/7/24
     */
    public Map<String, Object> adjustCalculate(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId, String srcId, String userid) {
        Map<String, Object> result = new HashMap<>();

        //不需要抵扣欠款时，全部金额走线下退款，只生成退款记录
        if (isAdjustOrder == Status.STATUS_0.status.intValue()) {
            ORefundAgent refundAgent = new ORefundAgent();
            refundAgent.setRefundType(adjustType);
            refundAgent.setSrcId(srcId);
            refundAgent.setRefundAmount(adjustAmt);
            refundAgent.setAgentId(agentId);
            refundAgent.setcUser(userid);
            result.put("refund", refundAgent);
            return result;
        } else {
            //需要抵扣欠款时，先抵扣代理商所有欠款，包含付款分期欠款、首付、或其他导入的欠款类型，还剩余的金额走线下退款

            //a 查询代理商欠款
            List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(agentId);

            //b 循环抵扣欠款
            BigDecimal leftAmt = adjustAmt;
            String calOrderId = "";                 //当前计算订单
            BigDecimal planAmt = BigDecimal.ZERO;   //当前计算订单-分期待还总金额
            int planCount = 0;                      //当前计算订单-待还总期数
            Date planStatDate = null;               //当前计算订单-分期开始时间

            for (OPaymentDetail paymentDetail : paymentDetails) {

                //如果是机具订单欠款，计算剩余待还分期数和待还总额
                if (paymentDetail.getPaymentType().equals(PamentIdType.ORDER_FKD.code)) {
                    String nowOrderId = paymentDetail.getOrderId();

                    //新的订单
                    if (calOrderId.equals("")) {
                        calOrderId = nowOrderId;
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount = 1;
                        planStatDate = paymentDetail.getPlanPayTime();
                    } else if (calOrderId.equals(nowOrderId)) {
                        //还是当前计算订单，继续累加
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount++;
                    } else if (!calOrderId.equals(nowOrderId)) {
                        //到下一个订单

                        //c 完成上面订单的计算
                        if (leftAmt.compareTo(planAmt) < 0) {                       //订单欠款未全部抵扣

                            OAccountAdjustDetail accountAdjustDetail = new OAccountAdjustDetail();
                            break;
                        } else {                                                 //订单欠款全部抵扣
                            leftAmt = leftAmt.subtract(planAmt);
                            //如果是正式调账，更新订单状态
                            //记录调账明细
                        }

                        //d 开启下一个订单计算
                        calOrderId = nowOrderId;
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount = 1;
                        planStatDate = paymentDetail.getPlanPayTime();
                    }


                }

            }


            // 如果最终金额还有剩余，走线下退款

        }

return  null;
    }

    /*public Map<String, Object> adjustCalculate(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId, String srcId, String userid) {
        Map<String, Object> result = new HashMap<>();

        //不需要抵扣欠款时，全部金额走线下退款，只生成退款记录
        if (isAdjustOrder == Status.STATUS_0.status.intValue()) {
            ORefundAgent refundAgent = new ORefundAgent();
            refundAgent.setRefundType(adjustType);
            refundAgent.setSrcId(srcId);
            refundAgent.setRefundAmount(adjustAmt);
            refundAgent.setAgentId(agentId);
            refundAgent.setcUser(userid);
            result.put("refund", refundAgent);
            return result;
        } else {
            //需要抵扣欠款时，先抵扣代理商所有欠款，包含付款分期欠款、首付、或其他导入的欠款类型，还剩余的金额走线下退款

            //a 查询代理商欠款
            List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(agentId);

            //b 循环抵扣欠款
            BigDecimal leftAmt = adjustAmt;
            String calOrderId = "";                 //当前计算订单
            BigDecimal planAmt = BigDecimal.ZERO;   //当前计算订单-分期待还总金额
            int planCount = 0;                      //当前计算订单-待还总期数
            Date planStatDate = null;               //当前计算订单-分期开始时间

            for (OPaymentDetail paymentDetail : paymentDetails) {

                //如果是机具订单欠款，计算剩余待还分期数和待还总额
                if (paymentDetail.getPaymentType().equals(PamentIdType.ORDER_FKD.code)) {
                    String nowOrderId = paymentDetail.getOrderId();

                    //新的订单
                    if (calOrderId.equals("")) {
                        calOrderId = nowOrderId;
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount = 1;
                        planStatDate = paymentDetail.getPlanPayTime();
                    } else if (calOrderId.equals(nowOrderId)) {
                        //还是当前计算订单，继续累加
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount++;
                    } else if (!calOrderId.equals(nowOrderId)) {
                        //到下一个订单

                        //c 完成上面订单的计算
                        if (leftAmt.compareTo(planAmt) < 0) {                       //订单欠款未全部抵扣

                            OAccountAdjustDetail accountAdjustDetail = new OAccountAdjustDetail();
                            break;
                        } else {                                                 //订单欠款全部抵扣
                            leftAmt = leftAmt.subtract(planAmt);
                            //如果是正式调账，更新订单状态
                            //记录调账明细
                        }

                        //d 开启下一个订单计算
                        calOrderId = nowOrderId;
                        planAmt = planAmt.add(paymentDetail.getPayAmount());
                        planCount = 1;
                        planStatDate = paymentDetail.getPlanPayTime();
                    }


                }

            }


            // 如果最终金额还有剩余，走线下退款

        }


    }*/


    public void takeoutOrder(Map<String, Object> result, BigDecimal planAmt, int planCount, Date planStatDate, String orderId, BigDecimal takeoutAmt, int isPreCalculate) {

        //剩余未还
        BigDecimal leftAmt = planAmt.subtract(takeoutAmt);
        if(leftAmt.compareTo(BigDecimal.ZERO)>0){
            //如果是正式调账,未抵扣欠款重新生成付款计划，更新订单付款计划
            if(isPreCalculate==Status.STATUS_1.status.intValue()){
                List<Map> SF2_data =  StageUtil.stageOrder(leftAmt,planCount,planStatDate,16);

                //当前批次移入历史

                //新批次付款计划入库
                for (Map datum : SF2_data) {
                    /*OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(d.getTime()+"");
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(PaymentType.DKFQ.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DS.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(d);
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if(1!=oPaymentDetailMapper.insert(record)){
                        throw new ProcessException("分期处理");
                    }*/
                }

            }

            //记录调账明细

        }else {
            //更新订单状态

            //记录调账明细
        }



    }


}
