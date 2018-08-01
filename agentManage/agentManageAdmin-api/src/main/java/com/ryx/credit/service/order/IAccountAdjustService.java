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
     * @Description: 调账功能，退货退款和退差价时使用
     *
     * @Param isRealAdjust 是否真正调账（true-是，会更新数据库， false-否，只进行预算不更新数据库）
     * @Param adjustAmt 调账金额
     * @Param adjustType 调账类型
     * @Param isAdjustOrder 是否抵扣机具欠款(0-不抵扣  1-抵扣)
     * @Param agentId 代理商ID
     * @Param srcId 源ID（退货单ID、退差价ID）
     * @Param srcType 源类型（THTK-退货退款  TCJ-退差价）
     * @Param userid 操作用户
     * @Date: 15:47 2018/7/24
     */
    Map<String, Object> adjust(boolean isRealAdjust, BigDecimal adjustAmt, String adjustType,
                               int isAdjustOrder, String agentId, String userid, String srcId, String srcType) throws ProcessException;
}
