package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/7/25.
 */
public enum SettlementType {
    FKFQ("FKFQ","付款分期"),
    XXDK("XXDK","线下打款"),
    SF1("SF1","首付+分润分期"),
    SF2("SF2","首付+打款分期"),
    QT("QT","其他"),
    FRFQ("FRFQ","分润分期");

    public String code;

    public String msg;

    SettlementType(String c, String m){
        this.code=c;
        this.msg =m;
    }

    public static SettlementType getByType(String msg){
        SettlementType[] vs = SettlementType.values();
        for (SettlementType v : vs) {
            if(v.msg.equals(msg)){
                return v;
            }
        }
        return null;
    }

}
