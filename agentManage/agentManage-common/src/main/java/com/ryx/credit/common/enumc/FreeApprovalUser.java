package com.ryx.credit.common.enumc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: ssx
 * @Description: 冻结审批人
 * @Date: 2020-6-19 10:36:16
 */
public enum FreeApprovalUser {

    RJ("RJ", "瑞嘉"),
    NOT_RJ("NOT_RJ", "非瑞嘉"),
    FINANCE("FINANCE","财务发起流程审批人");

    public String key;
    public String msg;

    FreeApprovalUser(String k, String s){
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
        FreeApprovalUser[] busType = FreeApprovalUser.values();
        for(FreeApprovalUser bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value){
        FreeApprovalUser[] busType = FreeApprovalUser.values();
        for(FreeApprovalUser bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<String, String> getValueMap(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        FreeApprovalUser[] busType = FreeApprovalUser.values();
        for(FreeApprovalUser bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }
}
