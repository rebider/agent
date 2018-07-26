package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;

import java.util.List;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款
 * @Date: 16:31 2018/7/24
 */
public interface IPaymentDetailService {

    /**
     * @Author: Zhang Lei
     * @Description: 查询代理商可抵扣欠款,先根据欠款类型排序，欠款类型相同的根据订单号排序
     * @Date: 16:52 2018/7/24
     */
    List<OPaymentDetail> getCanTakeoutPaymentsByAgentId(String agentId) throws ProcessException;
}
