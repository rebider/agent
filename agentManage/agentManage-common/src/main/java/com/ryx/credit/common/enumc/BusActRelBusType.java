package com.ryx.credit.common.enumc;

/**
 * Created by cx on 2018/5/29.
 */
public enum BusActRelBusType {
    //数据修改申请类型需要加入到此枚举中
    Agent("代理商入网审批"),
    Business("业务审批"),
    DC_Agent("代理商修改"),
    DC_Colinfo("代理商账户修改申请"),
    ORDER("订单审批"),
    PkType("订单补款"),
    COMPENSATE("退补差价审批");
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
