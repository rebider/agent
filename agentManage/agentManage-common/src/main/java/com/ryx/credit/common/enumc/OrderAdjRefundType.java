package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

public enum OrderAdjRefundType {
    CDFQ_GZ(new BigDecimal("0"),"冲抵分期+挂账"),
    CDFQ_XXTK(new BigDecimal("1"),"冲抵分期+线下退款");

    public BigDecimal code;

    public String msg;

    OrderAdjRefundType(BigDecimal c, String m){
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
        OrderAdjRefundType[] annoStats = OrderAdjRefundType.values();
        for (OrderAdjRefundType cc : annoStats) {
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }
}
