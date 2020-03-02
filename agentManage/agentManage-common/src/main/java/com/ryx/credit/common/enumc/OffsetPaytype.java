package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

public enum OffsetPaytype {

    THTK("THTK","退货退款"),
    DDTZ("DDTZ","机具数量调整退款"),
    DDBK("DDBK","线下打款"),
    DDXZ("DDXZ","线上销账补款"),
    FRDK("FRDK","分润抵扣"),
    DDMD("DDMD","换活动退款");

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
        OffsetPaytype[] annoStats = OffsetPaytype.values();
        for (OffsetPaytype cc : annoStats) {
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }
}
