package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 作者：cx
 * 时间：2018/10/29
 * 描述：
 */
public enum OInvoiceSrcType {

    RETURNORDER(new BigDecimal("1"),"退货");


    public BigDecimal code;
    public String msg;

    OInvoiceSrcType(BigDecimal c, String s){
        code = c;
        msg = s;
    }

}
