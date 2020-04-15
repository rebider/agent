package com.ryx.credit.common.enumc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 工单查看权限组
 * Created by menglf on 2020/03/20
 */
public enum JoPowerViewGroup {


    YWB("Views_pro","省区"),
    CWB("Views_agent","代理商"),
    DLS("Views_business","业务部");

    public String key;
    public String msg;

    JoPowerViewGroup(String k, String s){
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
        JoPowerViewGroup[] busType = JoPowerViewGroup.values();
        for(JoPowerViewGroup bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value){
        JoPowerViewGroup[] busType = JoPowerViewGroup.values();
        for(JoPowerViewGroup bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<String, String>  getValueMap(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        JoPowerViewGroup[] busType = JoPowerViewGroup.values();
        for(JoPowerViewGroup bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
