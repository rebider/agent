package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询类型
 * Created by ssx on 2020/2/4.
 */
public enum QueryType {


    ARRID("0", "欠款Id"),
    SRCID("1", "补款Id");

    public String code;

    public String msg;

    QueryType(String c, String m) {
        this.code = c;
        this.msg = m;
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

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        QueryType[] queryTypes = QueryType.values();
        for (QueryType cc : queryTypes) {
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
    public static Map<String, Object> getContentMap() {
        Map<String, Object> resultMap = new HashMap<>();
        QueryType[] queryTypes = QueryType.values();
        for (QueryType cc : queryTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }
}
