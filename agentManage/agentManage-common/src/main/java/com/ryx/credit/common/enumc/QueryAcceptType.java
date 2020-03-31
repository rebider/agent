package com.ryx.credit.common.enumc;

public enum QueryAcceptType {
    userId("0","用户id"),
    acceCode("1","受理组code");
    public String code;
    public String desc;

    QueryAcceptType(String c , String d) {
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
    public static String getQueryAcceptType(String value){
        QueryAcceptType[] status = QueryAcceptType.values();
        for(QueryAcceptType rs : status){
            if(rs.code.equals(value)){
                return rs.desc;
            }
        }
        return "";
    }
}
