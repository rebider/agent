package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Author: Zhang Lei
 * @Description: 退貨進度
 * @Date: 16:08 2018/7/24
 */
public enum RetSchedule {

    SPZ("1", "审批中"), DFH("2", "待发货"), FHZ("3", "发货中"), TKZ("4", "退款中"), WC("5", "完成"), JJ("6", "拒绝"), TH("7", "退回"), YFH("8", "已发货");

    public String code;

    public String msg;

    RetSchedule(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
