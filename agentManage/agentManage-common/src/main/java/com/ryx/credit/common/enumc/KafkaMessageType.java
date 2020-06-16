package com.ryx.credit.common.enumc;

/**
 * 作者：cx
 * 时间：2019/9/18
 * 描述：
 */
public enum KafkaMessageType {
    PAYMENT("PAYMENT","付款信息"),
    FREEZE("FREEZE","冻结信息"),
    CARD("CARD","结算卡信息");

    public String code;

    public String msg;

    KafkaMessageType(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
