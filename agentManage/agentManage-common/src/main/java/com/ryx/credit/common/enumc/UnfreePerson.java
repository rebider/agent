package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhl on 2020/5/15.
 */
public enum UnfreePerson {

    XTJD(new BigDecimal("0"),"系统解冻");

    public BigDecimal code;

    public String msg;

    UnfreePerson(BigDecimal c, String m) {
        this.code = c;
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(BigDecimal value) {
        UnfreePerson[] unfreePerson = UnfreePerson.values();
        for (UnfreePerson cc : unfreePerson) {
            if (cc.code.compareTo(value)==0) {
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
    public BigDecimal getValue() {
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
