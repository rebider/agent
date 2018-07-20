package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/20.
 */
public enum PaymentStatus {

    DF("1","待付款"),
    BF("2","部分付款"),
    YQ("3","逾期"),
    JQ("4","结清");

    public BigDecimal code;

    public String msg;

    PaymentStatus(String c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }
}
