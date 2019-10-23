package com.ryx.credit.common.enumc;

/**
 * 公告关联表的type字段
 */
public enum RangType {
    bustype("0","业务"),
    plat("1","平台"),
    org("2","范围");
    public String code;
    public String desc;

    RangType(String c , String d) {
         code = c;
         desc = d;
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
        return this.desc;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getRefundStatus(String value){
        RangType[] status = RangType.values();
        for(RangType rs : status){
            if(rs.code.equals(value)){
                return rs.desc;
            }
        }
        return "";
    }
}
