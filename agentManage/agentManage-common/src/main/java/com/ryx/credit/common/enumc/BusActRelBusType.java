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
}
