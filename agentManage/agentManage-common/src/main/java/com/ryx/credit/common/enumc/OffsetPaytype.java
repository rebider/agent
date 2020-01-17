package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

public enum OffsetPaytype {

    THTK("THTK","退货退款"),
    DDTZ("DDTZ","订单调整"),
    DDBK("DDBK","订单补款"),
    DDXZ("DDXZ","订单销账"),
    FRDK("FRDK","分润抵扣"),
    DDMD("DDMD","订单换活动");

    public String code;

    public String  msg;

    OffsetPaytype(String code, String s){
        this.code = code;
        msg = s;
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
        AnnoStat[] annoStats = AnnoStat.values();
        for (AnnoStat cc : annoStats) {
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }
}
