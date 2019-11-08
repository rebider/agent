package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum AdjStat {

    ZC(new BigDecimal("0"),"暂存"),
    APPROVAL(new BigDecimal("1"),"审批中"),
    SUCCESS(new BigDecimal("2"),"审批通过"),
    REFUSE(new BigDecimal("3"),"审批拒绝");

    public BigDecimal key;
    public String msg;

    AdjStat(BigDecimal k, String s){
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
        AdjStat[] busType = AdjStat.values();
        for(AdjStat bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        AdjStat[] busType = AdjStat.values();
        for(AdjStat bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        AdjStat[] busType = AdjStat.values();
        for(AdjStat bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
