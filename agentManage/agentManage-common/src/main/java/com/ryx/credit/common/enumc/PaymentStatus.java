package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/20.
 * 付款单明细状态
 */
public enum PaymentStatus {
    DS("0","待审"),
    DF("1","待付款"),
    BF("2","部分付款"),
    YQ("3","逾期"),
    JQ("4","结清"),
    FKING("5","付款中");

    public BigDecimal code;

    public String msg;

    PaymentStatus(String c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }
}
