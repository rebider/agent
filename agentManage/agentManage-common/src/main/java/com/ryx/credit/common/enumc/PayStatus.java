package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/20.
 */
public enum PayStatus {
//0未付款
//1部分付款
//2已结清
    NON_PAYMENT("0","0未付款"),
    PART_PAYMENT("1","1部分付款"),
    CLOSED("2","2已结清");

    public BigDecimal code;

    public String msg;


    PayStatus(String c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }
}
