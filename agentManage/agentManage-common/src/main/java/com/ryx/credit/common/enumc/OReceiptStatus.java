package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/20.
 */
public enum OReceiptStatus {


    TEMPORARY_STORAGE("0","暂存"),
    WAITING_LIST("1","待排单"),
    DISPATCHED_ORDER("2","已排单");

    public BigDecimal code;

    public String msg;

    OReceiptStatus(String c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }
}
