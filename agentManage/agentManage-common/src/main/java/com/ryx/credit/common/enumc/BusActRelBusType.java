package com.ryx.credit.common.enumc;

/**
 * Created by cx on 2018/5/29.
 */
public enum BusActRelBusType {
    Agent("代理商入网审批"),Business("业务审批");

    public String  msg;

    BusActRelBusType(String s){
        msg = s;
    }


    public static String getItemString(String key){
        BusActRelBusType[] valus = BusActRelBusType.values();
        for (BusActRelBusType busActRelBusType : valus) {
            if(busActRelBusType.name().equals(key)){
                return busActRelBusType.msg;
            }
        }
        return null;
    }
}
