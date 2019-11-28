package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 挂账状态
 */
public enum SettleStatus {
    CREATE(new BigDecimal("0"),"已挂账"),
    PART_USED(new BigDecimal("1"),"部分使用"),
    ALL_USED(new BigDecimal("2"),"全部使用");

    public BigDecimal key;
    public String msg;

    SettleStatus(BigDecimal k, String s){
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
        SettleStatus[] busType = SettleStatus.values();
        for(SettleStatus bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        SettleStatus[] busType = SettleStatus.values();
        for(SettleStatus bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        SettleStatus[] busType = SettleStatus.values();
        for(SettleStatus bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
