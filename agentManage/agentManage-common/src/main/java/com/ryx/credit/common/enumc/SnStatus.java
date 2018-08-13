package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * SN状态
 * Created by cx on 2018/5/22.
 */
public enum SnStatus {

    FH(1, "原订单发货"), THZ(2, "原订单退货申请中"), THDH(3, "原订单退货打回修改"), THWC(4, "原订单退货完成"),THWL(5, "退货物流发货");

    public BigDecimal code;

    public String msg;

    SnStatus(int s, String m) {
        code = new BigDecimal(s);
        msg = m;
    }

}
