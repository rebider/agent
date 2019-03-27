package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cx on 2018/5/29.
 */
public enum BusActRelBusType {
    //数据修改申请类型需要加入到此枚举中
    Agent("/BusActRelBusType/Agent","代理商入网审批"),
    Business("/BusActRelBusType/Business","业务审批"),
    DC_Agent("/BusActRelBusType/DC_Agent","代理商修改"),
    DC_Colinfo("/BusActRelBusType/DC_Colinfo","代理商账户修改申请"),
    ORDER("/BusActRelBusType/ORDER","订单审批"),
    STAGING("/BusActRelBusType/STAGING","退单分期"),
    OTHER_DEDUCTION("/BusActRelBusType/OTHER_DEDUCTION","其他扣款分期"),
    ASSESS_DEDUCTION("/BusActRelBusType/ASSESS_DEDUCTION","考核扣款分期"),
    THAW("/BusActRelBusType/THAW","分润解冻申请"),
    PkType("/BusActRelBusType/PkType","订单补款"),
    refund("/BusActRelBusType/refund","退货审批"),
    TOOLS("/BusActRelBusType/TOOLS","机具扣款调整申请"),
    COMPENSATE("/BusActRelBusType/COMPENSATE","退补差价审批"),
    POSTAX("/BusActRelBusType/POSTAX","税点调整申请"),
    POSCHECK("/BusActRelBusType/POSCHECK","分润比例考核"),
    POSREWARD("/BusActRelBusType/POSREWARD","POS特殊奖励"),
    POSHUDDLEREWARD("/BusActRelBusType/POSHUDDLEREWARD","POS抱团奖励申请"),
    QUIT("/BusActRelBusType/QUIT","代理商退出申请"),
    MERGE("/BusActRelBusType/MERGE","代理商合并申请"),
    agentTerminal("/BusActRelBusType/agentTerminal","终端划拨申请"),
    agentQuitRefund("/BusActRelBusType/agentQuitRefund","代理商退出申请退款"),
    capitalChange("/BusActRelBusType/capitalChange","保证金变更申请");


    public String key;
    public String msg;

    BusActRelBusType(String k,String s){
        key = k;
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

    public static Map<String,Object> getItemMap(){
        BusActRelBusType[] valus = BusActRelBusType.values();
        Map<String,Object> resultMap = new HashMap<>();
        for (BusActRelBusType busActRelBusType : valus) {
            resultMap.put(busActRelBusType.name(),busActRelBusType.msg);
        }
        return resultMap;
    }

    public static String getNameByKey(String name){
        BusActRelBusType[] valus = BusActRelBusType.values();
        for (BusActRelBusType busActRelBusType : valus) {
            if(busActRelBusType.name().equals(name)){
                return busActRelBusType.key;
            }
        }
        return null;
    }

}
