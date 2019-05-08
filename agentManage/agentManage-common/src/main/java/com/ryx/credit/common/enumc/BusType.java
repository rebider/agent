package com.ryx.credit.common.enumc;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum BusType {

    ZQZF("1","二代直签直发"),
    JG("2","机构"),
    JGYD("3","机构一代"),
    YDX("5","一代X"),
    BZYD("6","标准一代"),
    ZQBZF("8","直签不直发"),
    ZQ("9","直签");


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

    public static String getContentByValue(String value){
        BusType[] busType = BusType.values();
        for(BusType bt : busType){
            if(bt.key.equals(value)){
                return bt.msg;
            }
        }
        return "";
    }

}
