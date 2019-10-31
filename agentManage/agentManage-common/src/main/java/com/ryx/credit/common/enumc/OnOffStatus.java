package com.ryx.credit.common.enumc;

/**
 * 开关控制
 * @Author:
 * @Description:
 * @Date: 16:08 2018/7/24
 */
public enum OnOffStatus {

    ON("ON", "开"),
    OFF("OFF", "关");

    public String code;

    public String msg;

    OnOffStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
