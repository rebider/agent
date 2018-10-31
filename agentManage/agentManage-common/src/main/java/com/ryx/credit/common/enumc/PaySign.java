package com.ryx.credit.common.enumc;
/**
 * @Auther: lrr
 * @Date: 2018/10/30 14:33
 * @Description:支付状态
 */
public enum PaySign {
    JQ("1","结清"),
    FKING("2","付款中");

    public String code;

    public String msg;

    PaySign(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
