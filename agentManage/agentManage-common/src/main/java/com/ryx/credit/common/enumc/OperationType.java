package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lhl on 2019/2/12.
 * 保证金：操作类型
 */
public enum OperationType {

    KQ(new BigDecimal("1"), "扣款"),
    TK(new BigDecimal("2"), "退款");

    public BigDecimal code;

    public String msg;

    OperationType(BigDecimal c, String m) {
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
        OperationType[] operationTypes = OperationType.values();
        for (OperationType cc : operationTypes) {
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
        Map<BigDecimal, Object> resultMap = new LinkedHashMap<>();
        OperationType[] operationTypes = OperationType.values();
        for (OperationType cc : operationTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

}
