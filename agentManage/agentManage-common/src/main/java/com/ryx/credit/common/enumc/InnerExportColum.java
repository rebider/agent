package com.ryx.credit.common.enumc;

public enum InnerExportColum {

    //导出排单信息
    InnerExportColum("BRANCH_NAME,INNER_LOGIN","排单导出字段");

    public String code;
    public String msg;
    public String []col;

    InnerExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        col= code.split(",");
    }
}
