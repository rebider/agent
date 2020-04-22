package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum JoTaskStatus {


    WSL("0","未受理"),
    SLZ("1","受理中"),
    YHF("2","已回复");

    public String key;
    public String msg;

    JoTaskStatus(String k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(String value){
        JoTaskStatus[] busType = JoTaskStatus.values();
        for(JoTaskStatus bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value){
        JoTaskStatus[] busType = JoTaskStatus.values();
        for(JoTaskStatus bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<String, String>  getValueMap(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        JoTaskStatus[] busType = JoTaskStatus.values();
        for(JoTaskStatus bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
