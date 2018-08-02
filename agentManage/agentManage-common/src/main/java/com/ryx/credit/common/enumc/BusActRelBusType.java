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
    STAGING("退单分期"),
    OTHER_DEDUCTION("其他扣款退单分期"),
    THAW("分润解冻申请"),
    PkType("订单补款"),
    refund("退货审批"),
    TOOLS("机具扣款调整申请"),
    COMPENSATE("退补差价审批"),
    POSTAX("税点调整申请"),
    POSCHECK("POS考核奖励申请"),
    POSREWARD("POS奖励申请");;
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
