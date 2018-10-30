package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/7/23.
 * 付款明细表里的ID源类型
 */
public enum  PamentIdType {

   ORDER_FKD("ORDER_FKD","订单付款单"),
   ORDER_BCJ("ORDER_BCJ","补差价产生欠款打款"),
   ORDER_BZJ("ORDER_BZJ","订单保证金分期,服务费等分期类型");
    public String code;

    public String msg;

    PamentIdType(String c, String m){
        this.code=c;
        this.msg =m;
    }


}
