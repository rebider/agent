package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhl on 2019/2/12.
 * 保证金：退款方式
 */
public enum RefundType {

    DKJJQK(new BigDecimal("1"), "抵扣机具欠款"),
    XXDK(new BigDecimal("2"), "线下打款"),
    DKJJQK_XXDK(new BigDecimal("3"), "抵扣机具欠款+线下打款");

    public BigDecimal code;

    public String msg;

    RefundType(BigDecimal c, String m) {
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
        RefundType[] refundTypes = RefundType.values();
        for (RefundType cc : refundTypes) {
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
        RefundType[] refundTypes = RefundType.values();
        for (RefundType cc : refundTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

}
