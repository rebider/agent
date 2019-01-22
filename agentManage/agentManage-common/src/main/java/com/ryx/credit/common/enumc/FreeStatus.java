package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Author Lihl
 * @Date 2019/01/21
 * 代理商冻结解冻状态
 */
public enum FreeStatus {

    DJ(new BigDecimal("0"),"冻结"),
    JD(new BigDecimal("1"),"解冻");

    public BigDecimal code;

    public String msg;

    FreeStatus(BigDecimal c, String m){
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
    public static String getContentByValue(BigDecimal value) {
        FreeStatus[] freeStatus = FreeStatus.values();
        for (FreeStatus cc : freeStatus) {
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }

}
