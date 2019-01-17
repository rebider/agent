package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2019/01/11
 * @Desc 合并类型
 */
public enum MergeType {

    busPlat(new BigDecimal("0"), "业务平台合并"),
    busPlat_Name(new BigDecimal("1"), "业务平台改名称");

    public BigDecimal code;

    public String msg;

    MergeType(BigDecimal c, String m) {
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
        MergeType[] mergeTypes = MergeType.values();
        for (MergeType cc : mergeTypes) {
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
    public static Map<BigDecimal,Object> getContentMap() {
        Map<BigDecimal,Object> resultMap = new HashMap<>();
        MergeType[] mergeTypes = MergeType.values();
        for (MergeType cc : mergeTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

}
