package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Author: Zhang Lei
 * @Description: 调账类型
 * @Date: 16:08 2018/7/24
 */
public enum AdjustType {

    TKTH("TKTH", "退款退货"), TCJ("TCJ", "退差价");

    public String adjustType;

    public String msg;

    AdjustType(String adjustType, String msg) {
        this.adjustType = adjustType;
        this.msg = msg;
    }
}
