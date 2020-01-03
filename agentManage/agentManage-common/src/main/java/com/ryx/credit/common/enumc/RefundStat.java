package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 退款状态
 */
public enum RefundStat {
    UNREFUND(new BigDecimal("0"),"无退款"),
    REFUNDING(new BigDecimal("1"),"退款中"),
    REFUNED(new BigDecimal("2"),"退款完成");

    public BigDecimal key;
    public String msg;

    RefundStat(BigDecimal k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(BigDecimal value){
        RefundStat[] busType = RefundStat.values();
        for(RefundStat bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        RefundStat[] busType = RefundStat.values();
        for(RefundStat bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        RefundStat[] busType = RefundStat.values();
        for(RefundStat bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
