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
    DC_Agent("/BusActRelBusType/DC_Agent","代理商业务修改"),
    DC_Colinfo("/BusActRelBusType/DC_Colinfo","代理商账户修改申请"),
    ORDER("/BusActRelBusType/ORDER","订单审批"),
    STAGING("/BusActRelBusType/STAGING","退单分期"),
    OTHER_DEDUCTION("/BusActRelBusType/OTHER_DEDUCTION","其他扣款分期"),
    ASSESS_DEDUCTION("/BusActRelBusType/ASSESS_DEDUCTION","考核扣款分期"),
    TOOL_SUPPLY("/BusActRelBusType/TOOL_SUPPLY","省区补款/代理商代扣"),
    THAW("/BusActRelBusType/THAW","分润解冻申请"),
    PkType("/BusActRelBusType/PkType","订单补款"),
    refund("/BusActRelBusType/refund","退货审批"),
    hisrefund("/BusActRelBusType/hisrefund","历史退货审批"),
    TOOLS("/BusActRelBusType/TOOLS","机具扣款调整申请"),
    COMPENSATE("/BusActRelBusType/COMPENSATE","活动调整审批"),
    POSTAX("/BusActRelBusType/POSTAX","税点调整申请"),
    POSCHECK("/BusActRelBusType/POSCHECK","分润比例考核"),
    POSREWARD("/BusActRelBusType/POSREWARD","POS特殊奖励"),
    POSHUDDLEREWARD("/BusActRelBusType/POSHUDDLEREWARD","POS抱团奖励申请"),
    QUIT("/BusActRelBusType/QUIT","代理商退出申请"),
    MERGE("/BusActRelBusType/MERGE","代理商合并申请"),
    agentRelate("/BusActRelBusType/agentRelate","代理商关联申请"),
    agentTerminal("/BusActRelBusType/agentTerminal","终端划拨申请"),
    agentQuitRefund("/BusActRelBusType/agentQuitRefund","代理商退出申请退款"),
    capitalChange("/BusActRelBusType/capitalChange","保证金变更申请"),
    CityApplyDeduction("/BusActRelBusType/cityApplyDeduction","其他扣款申请"),
    INVOICEAPPLY("/BusActRelBusType/invoiceApply","代理商发票审批"),
    CityApplySupply("/BusActRelBusType/CityApplySupply","省区其他补款申请"),
    thawAgentByCity("/BusActRelBusType/thawAgentByCity","代理商月分润解冻省区申请"),
    thawAgentByBusiness("/BusActRelBusType/thawAgentByBusiness","代理商月分润解冻业务申请"),
    profitTempalteApply("/BusActRelBusType/profitTempalteApply","分润模板线上申请"),
    orderAdjust("/BusActRelBusType/orderAdj","机具数量调整申请"),
    cashierApprove("/BusActRelBusType/cashierApprove","订单调整出纳申请"),
    cardRenew("/BusActRelBusType/cardRenew","物联网卡续费申请"),
    freeze("/BusActRelBusType/agentFreezeApproval","冻结解冻申请"),
    DC_AG_Colinfo("/BusActRelBusType/DC_AG_Colinfo","代理商发起基础信息修改申请"),
    cardLogout("/BusActRelBusType/cardLogout","物联网卡注销申请"),
    BALANCE_APPLY("/BusActRelBusType/BALANCE_APPLY","分润出款审批");


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
