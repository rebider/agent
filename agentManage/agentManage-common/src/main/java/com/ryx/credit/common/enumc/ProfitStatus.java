package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @author yangmx
 * @desc 分润状态枚举
 */
public enum ProfitStatus {
    /**
     * 0:正常、1：冻结、2：解冻中、3：解冻失败、4未分润、5：已分润、6：打款失败
     */
    STATUS_0(0),
    STATUS_1(1),
    STATUS_2(2),
    STATUS_3(3),
    STATUS_4(4),
    STATUS_5(5),
    STATUS_6(6);

    public BigDecimal status;

    ProfitStatus(int s) {
        status = new BigDecimal(s);
    }

    public String value(){
        return String.valueOf(status);
    }

}
