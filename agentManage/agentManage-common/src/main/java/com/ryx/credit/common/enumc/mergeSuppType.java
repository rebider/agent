package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 合并补缴类型
 * Created by liudh on 2018/9/58.
 */
public enum mergeSuppType {

    XXDK(new BigDecimal("1"),"线下补款"),
    DLSDK(new BigDecimal("2"),"代理商代扣"),
    W(new BigDecimal("3"),"无");


    public BigDecimal key;
    public String msg;

    mergeSuppType(BigDecimal k, String s){
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
        mergeSuppType[] busType = mergeSuppType.values();
        for(mergeSuppType bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        mergeSuppType[] busType = mergeSuppType.values();
        for(mergeSuppType bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        mergeSuppType[] busType = mergeSuppType.values();
        for(mergeSuppType bt : busType){
            if(bt.key.compareTo(mergeSuppType.W.getValue())!=0){
                hashMap.put(bt.key,bt.msg);
            }
        }
        return hashMap;
    }

}
