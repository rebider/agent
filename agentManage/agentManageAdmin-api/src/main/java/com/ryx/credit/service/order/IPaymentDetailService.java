package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款
 * @Date: 16:31 2018/7/24
 */
public interface IPaymentDetailService {

    /**
     * @Author: Zhang Lei
     * @Description: 查询代理商可抵扣欠款, 先根据欠款类型排序，欠款类型相同的根据订单号排序
     * @Date: 16:52 2018/7/24
     */
    List<OPaymentDetail> getCanTakeoutPaymentsByAgentId(String agentId) throws ProcessException;

    /**
     * 提供分润接口
     */
    List<Map<String, Object>> getShareMoney(String method, String agentId, String time) throws ParseException;

    /**
     * @Author: Zhang Lei
     * @Description: 查询付款单
     * @Date: 9:29 2018/7/28
     */
    OPayment getPaymentById(String paymentId) throws ProcessException;

    /**
     * @Author: Zhang Lei
     * @Description: 查询一个付款单下付款明细，可根据付款状态筛选
     * @Date: 9:31 2018/7/28
     */
    List<OPaymentDetail> getPaymentDetails(String paymentId, String... paymentStatus) throws ProcessException;
}
