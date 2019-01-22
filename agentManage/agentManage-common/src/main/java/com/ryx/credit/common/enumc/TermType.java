package com.ryx.credit.common.enumc;

/**
 * @Author liudh
 * @Date 2019/01/11
 * @Desc 手刷统计终端类型
 */
public enum TermType {

    Z("0", "升级查询终端"),
    O("1", "终端划拨");

    public String code;

    public String msg;

    TermType(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.code;
    }

    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        TermType[] mergeTypes = TermType.values();
        for (TermType cc : mergeTypes) {
            if (cc.code.equals(value)) {
                return cc.msg;
            }
        }
        return "";
    }

}
