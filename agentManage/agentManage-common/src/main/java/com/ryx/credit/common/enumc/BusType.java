package com.ryx.credit.common.enumc;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum BusType {

    ZQ("1","直签"),
    JG("2","机构"),
    JGYD("3","机构一代"),
    JGZQ("4","机构直签"),
    YDX("5","一代X"),
    BZYD("6","标准一代"),
    EDZQ("7","二代直签"),
    ZQBZF("8","直签不直发");


    public String key;
    public String msg;

    BusType(String k, String s){
        key = k;
        msg = s;
    }


    public static String getItemString(String key){
        BusType[] valus = BusType.values();
        for (BusType busType : valus) {
            if(busType.name().equals(key)){
                return busType.msg;
            }
        }
        return null;
    }

}
