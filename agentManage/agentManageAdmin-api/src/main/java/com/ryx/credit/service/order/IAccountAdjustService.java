package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 调账服务（退货、退差价时使用）
 * @Date: 15:43 2018/7/24
 */
public interface IAccountAdjustService {

    /**
     * @Author: Zhang Lei
     * @Description: 调账预算（生成草稿，不会真正进行欠款抵扣和生成退款记录）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣欠款
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Date: 15:47 2018/7/24
     */
    Map<String, Object> adjustPreCalculate(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId, String srcId) throws ProcessException;


    /**
     * @Author: Zhang Lei
     * @Description: 调账（真正进行欠款抵扣和生成退款记录）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣欠款
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Param userid 操作用户
     * @Date: 15:47 2018/7/24
     */
    Map<String, Object> adjust(BigDecimal adjustAmt, String adjustType, int isAdjustOrder, String agentId,String userid, String srcId) throws ProcessException;
}
