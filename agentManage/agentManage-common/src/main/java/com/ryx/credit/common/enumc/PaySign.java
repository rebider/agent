package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Auther: lrr
 * @Date: 2018/10/30 14:33
 * @Description:支付状态
 */
public enum PaySign {
    JQ("4","结清"),
    FKING("5","付款中");

    public BigDecimal code;

    public String msg;

    PaySign(String c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }
}
