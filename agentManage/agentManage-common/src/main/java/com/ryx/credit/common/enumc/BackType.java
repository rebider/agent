package com.ryx.credit.common.enumc;

/**
 * @Author Lihl
 * @Date 2019/01/17
 * @Desc POS 返回类型
 */
public enum BackType {

    dls("3","代理商"),
    sh("4","商户");

    public String key;
    public String msg;

    BackType(String k, String s) {
        key = k;
        msg = s;
    }

    public static String getItemString(String key) {
        BackType[] valus = BackType.values();
        for (BackType backType : valus) {
            if (backType.name().equals(key)) {
                return backType.msg;
            }
        }
        return null;
    }

    public static String getContentByValue(String value) {
        BackType[] busType = BackType.values();
        for (BackType bt : busType) {
            if (bt.key.equals(value)) {
                return bt.msg;
            }
        }
        return "";
    }

}
