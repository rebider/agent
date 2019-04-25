package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhl on 2019/2/12.
 * 资金流水：原数据类型
 */
public enum SrcType {

    BZJ(new BigDecimal("1"), "保证金管理"),
    RW(new BigDecimal("2"), "入网"),
    FRHRU(new BigDecimal("3"), "分润入账"),;

    public BigDecimal code;

    public String msg;

    SrcType(BigDecimal c, String m) {
        this.code = c;
        this.msg = m;
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

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        SrcType[] srcTypes = SrcType.values();
        for (SrcType cc : srcTypes) {
            if (cc.code.equals(value)) {
                return cc.msg;
            }
        }
        return "";
    }

    /**
     * 获取下拉列表
     * @return
     */
    public static Map<BigDecimal, Object> getContentMap() {
        Map<BigDecimal, Object> resultMap = new HashMap<>();
        SrcType[] srcTypes = SrcType.values();
        for (SrcType cc : srcTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

}
