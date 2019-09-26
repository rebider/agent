package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/9/24 10:09
 * @Description:销账状态
 */
public enum  RemoveAccountStatus {
    WCL(new BigDecimal("1"), "未处理"),
    CLZ(new BigDecimal("2"), "处理中"),
    CLCG(new BigDecimal("3"), "处理失败"),
    CLSB(new BigDecimal("4"), "处理成功");

    public BigDecimal code;

    public String msg;

    RemoveAccountStatus(BigDecimal c, String m) {
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
        RemoveAccountStatus[] refundTypes = RemoveAccountStatus.values();
        for (RemoveAccountStatus cc : refundTypes) {
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
        RemoveAccountStatus[] refundTypes = RemoveAccountStatus.values();
        for (RemoveAccountStatus cc : refundTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }
}