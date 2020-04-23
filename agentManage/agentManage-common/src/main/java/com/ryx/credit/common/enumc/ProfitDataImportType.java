package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Author chenliang
 * @Date 2020/4/17 16:22
 * @Version 1.0
 */
public enum ProfitDataImportType {
    DYDL("77","第一次导入"),
    DEDL("88","第二次导入"),
    DSDL("99","第三次导入");

    public String key;

    public String msg;

    ProfitDataImportType(String c, String m){
        this.key = c;
        this.msg = m;
    }
}
