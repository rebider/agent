package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/10.
 */
public enum OLogisticsDetailOptType {
    ORDER("ORDER","订单"),
    BCJ("BCJ","补差价");



    public String code;

    public String msg;

    OLogisticsDetailOptType(String c, String m) {
        this.code = c;
        this.msg = m;
    }
}
