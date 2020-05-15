package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhl on 2020/5/15.
 */
public enum UnfreePerson {

    XTJD("XTJD","系统解冻");

    public String code;

    public String msg;

    UnfreePerson(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        UnfreePerson[] unfreePerson = UnfreePerson.values();
        for (UnfreePerson cc : unfreePerson) {
            if (cc.code.equals(value)) {
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<Object, Object> getContentMap() {
        UnfreePerson[] unfreePerson = UnfreePerson.values();
        Map<Object, Object> resultMap = new HashMap<>();
        for (UnfreePerson cc : unfreePerson) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.code;
    }

    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }

}
