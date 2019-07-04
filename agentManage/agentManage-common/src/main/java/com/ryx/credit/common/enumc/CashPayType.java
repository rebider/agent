package com.ryx.credit.common.enumc;

/**
 * 作者：cx
 * 时间：2018/10/29
 * 描述：
 */
public enum CashPayType {

    PAYMENT("PAYMENT","订单付款单"),
    SUPPLEMENT("SUPPLEMENT","补款"),
    REFUNDPRICEDIFF("REFUNDPRICEDIFF","补差价"),
    AGENTMERGE("AGENTMERGE","代理商合并"),
    AGENTQUIT("AGENTQUIT","代理商退出"),
    CAPITALCHANGE("CAPITALCHANGE","保证金变更申请"),
    INTERNETRENEW("INTERNETRENEW","物联网网卡续费申请");


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

    public static CashPayType getContentEnum(String value){
        CashPayType[] fundType = CashPayType.values();
        for(CashPayType cc : fundType){
            if(cc.code.equals(value)){
                return cc;
            }
        }
        return null;
    }
}
