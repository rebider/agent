package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/21.
 * 付款明细类型
 */
public enum PaymentType {

    SF("SF","首付"),
    FRFQ("FRFQ","分润分期"),
    DKFQ("DKFQ","打款分期"),
    DK("DK","打款"),
    YJ("YJ","押金"),
    BZJ("BZJ","保证金");

    public String code;

    public String msg;

    PaymentType(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
