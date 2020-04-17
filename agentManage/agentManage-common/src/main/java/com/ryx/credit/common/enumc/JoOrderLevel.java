package com.ryx.credit.common.enumc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 工单-优先级
 * Created by lhl on 2020/2/24.
 */
public enum JoOrderLevel {

    Routine("1","常规"),
    Urgent("3","紧急");

    public String key;
    public String msg;

    JoOrderLevel(String k, String s){
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
        JoOrderLevel[] joLevel = JoOrderLevel.values();
        for (JoOrderLevel level : joLevel) {
            if (level.key.compareTo(value)==0) {
                return level.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String value) {
        JoOrderLevel[] joLevel = JoOrderLevel.values();
        for (JoOrderLevel level : joLevel) {
            if (level.msg.equals(value)) {
                return level.key;
            }
        }
        return null;
    }

    public static Map<String, String> getValueMap() {
        Map<String, String> hashMap = new LinkedHashMap<>();
        JoOrderLevel[] joLevel = JoOrderLevel.values();
        for (JoOrderLevel level : joLevel) {
            hashMap.put(level.key, level.msg);
        }
        return hashMap;
    }

}
