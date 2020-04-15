package com.ryx.credit.common.enumc;
/**
 * 分润数据导入类型
 */
public enum ProfitDataImportType {

    FXSJDR("01","返现数据导入");

    public String key;
    public String msg;

    ProfitDataImportType(String k, String s){
        key = k;
        msg = s;
    }
}
