package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：
 */
public enum Oreturntype {

    NEW("NEW","新订单"),
    OLD("OLD","历史订单"),
    OLDNOSN("OLDNOSN","历史订单无SN");

    public String code;

    public String msg;

    Oreturntype(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
