package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/7/23.
 * 付款明细表里的源类型
 */
public enum PamentSrcType {

    XXBK("XXBK", "线下补款"),
    TUIKUAN_DIKOU("TUIKUAN_DIKOU", "退货退款抵扣"),
    CAPITAL_DIKOU("CAPITAL_DIKOU", "缴款抵扣"),
    TUICHAJIA_DIKOU("TUICHAJIA_DIKOU", "退差价抵扣"),
    FENRUN_DIKOU("FENRUN_DIKOU","分润抵扣"),
    AGENT_QUIT_DIKOU("FENRUN_DIKOU","代理商退出抵扣"),
    ORDER_ADJ_REFUND("ORDER_ADJ_REFUND","订单机具数量调整"),
    ORDER_ADJ_SETTLE("ORDER_ADJ_SETTLE","订单机具数量挂账"),
    XXXZ("XXXZ","线下销账");

    public String code;

    public String msg;

    PamentSrcType(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    public static String getSrcTypeValue(String value) {
        PamentSrcType[] srcType = PamentSrcType.values();
        for (PamentSrcType cc : srcType) {
            if (cc.code.equals(value)) {
                return cc.msg;
            }
        }
        return "";
    }
}
