package com.ryx.credit.common.enumc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 工单受理权限组
 * Created by menglf on 2020/03/20.
 */
public enum JoPowerGroup {


    YWB("Group_Business","业务部"),
    CWB("Group_Finance","财务部"),
    DLS("Group_Agent","代理商"),
    FKB("Group_RiskManage","风控部"),
    QDB("Group_Channel","渠道部"),
    SQ("Group_Pro","省区");

    public String key;
    public String msg;

    JoPowerGroup(String k, String s){
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
        JoPowerGroup[] busType = JoPowerGroup.values();
        for(JoPowerGroup bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value){
        JoPowerGroup[] busType = JoPowerGroup.values();
        for(JoPowerGroup bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<String, String>  getValueMap(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        JoPowerGroup[] busType = JoPowerGroup.values();
        for(JoPowerGroup bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
