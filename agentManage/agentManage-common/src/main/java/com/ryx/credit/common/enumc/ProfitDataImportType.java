package com.ryx.credit.common.enumc;


import java.math.BigDecimal;

/**
 * @Author chenliang
 * @Date 2020/4/17 16:22
 * @Version 1.0
 */
public enum ProfitDataImportType {
    FRSJDR("00", "分润数据导入"),
    FXSJDR("01", "返现数据导入"),
    DYDL("77", "一次请款"),
    DEDL("88", "二次请款"),
    DSDL("99", "补出款");

    public String key;

    public String msg;

    ProfitDataImportType(String c, String m) {
        this.key = c;
        this.msg = m;
    }


    /**
     * 根据值获取内容
     *
     * @param key
     * @return
     */
    public static String getContentByValue(String key) {
        ProfitDataImportType[] profitDataImportTypes = ProfitDataImportType.values();
        for (ProfitDataImportType cc : profitDataImportTypes) {
            if (cc.key.equals(key)) {
                return cc.msg;
            }
        }
        return "";
    }
}
