package com.ryx.credit.common.enumc;

/**
 * @Auther: lrr
 * @Date: 2020/1/16 10:40
 * @Description:逻辑版本
 */
public enum LogicalVersion {
    ZERO("0", "历史数据"), ONE("1", "新数据");

    public String code;

    public String msg;

    LogicalVersion(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
