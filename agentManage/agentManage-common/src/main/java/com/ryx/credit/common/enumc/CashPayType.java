package com.ryx.credit.common.enumc;

/**
 * 作者：cx
 * 时间：2018/10/29
 * 描述：
 */
public enum CashPayType {

    PAYMENT("PAYMENT","订单付款单"),
    SUPPLEMENT("SUPPLEMENT","补款"),
    REFUNDPRICEDIFF("REFUNDPRICEDIFF","补差价");


    public String code;
    public String msg;

    CashPayType(String c, String s){
        code = c;
        msg = s;
    }

    public static String getItemString(String key){
        CashPayType[] valus = CashPayType.values();
        for (CashPayType cashPayTypeItem : valus) {
            if(cashPayTypeItem.name().equals(key)){
                return cashPayTypeItem.msg;
            }
        }
        return null;
    }
}
