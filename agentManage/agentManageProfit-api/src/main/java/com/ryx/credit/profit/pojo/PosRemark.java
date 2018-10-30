package com.ryx.credit.profit.pojo;

public enum PosRemark {
    TY_Template("TY_Template", "通用模板"),
    TS_Template("TS_Template", "特殊模板");
    public String posRemark;

    public String msg;

    PosRemark(String posRemark, String msg) {
        this.posRemark = posRemark;
        this.msg = msg;
    }
}
