package com.ryx.credit.common.enumc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum JoOrderStatus {


    WCL("0","未处理"),
    CLZ("1","处理中"),
    YCL("2","已处理"),
    END("3","结束"),
    CANCLE("4","撤销");

    public String key;
    public String msg;

    JoOrderStatus(String k, String s){
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
        JoOrderStatus[] busType = JoOrderStatus.values();
        for(JoOrderStatus bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value){
        JoOrderStatus[] busType = JoOrderStatus.values();
        for(JoOrderStatus bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<String, String>  getValueMap(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        JoOrderStatus[] busType = JoOrderStatus.values();
        for(JoOrderStatus bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
