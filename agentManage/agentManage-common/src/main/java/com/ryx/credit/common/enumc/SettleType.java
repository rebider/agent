package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 挂账类型
 */
public enum SettleType {
    ORDER_ADJUST(new BigDecimal("0"),"订单调整");

    public BigDecimal key;
    public String msg;

    SettleType(BigDecimal k, String s){
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
        SettleType[] busType = SettleType.values();
        for(SettleType bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        SettleType[] busType = SettleType.values();
        for(SettleType bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        SettleType[] busType = SettleType.values();
        for(SettleType bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
