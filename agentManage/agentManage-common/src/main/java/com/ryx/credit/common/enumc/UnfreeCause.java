package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhl on 2020/5/15.
 */
public enum UnfreeCause {

    // "XXBL","SPJS" 作废
    // 基础信息缺失冻结、结算卡变更冻结  解冻原因都为：系统解冻

    XXBL("XXBL","信息补录解冻"),
    SPJS("RRDJ","审批结束解冻"),
    XTJD("XTJD","系统解冻");

    public String code;

    public String msg;

    UnfreeCause(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        UnfreeCause[] unfreeCause = UnfreeCause.values();
        for (UnfreeCause cc : unfreeCause) {
            if (cc.code.equals(value)) {
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<Object, Object> getContentMap() {
        UnfreeCause[] unfreeCause = UnfreeCause.values();
        Map<Object, Object> resultMap = new HashMap<>();
        for (UnfreeCause cc : unfreeCause) {
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
