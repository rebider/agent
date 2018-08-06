package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * SN状态
 * Created by cx on 2018/5/22.
 */
public enum SnStatus {

    FH(0, "发货"), TH(1, "退货"), THZ(2, "退货申请中");

    public BigDecimal code;

    public String msg;

    SnStatus(int s, String m) {
        code = new BigDecimal(s);
        msg = m;
    }

}
