package com.ryx.credit.common.enumc;


/**
 * 资金类型枚举
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum FundType {

    AGGENT("aggent","代理费"),
    CASH("cash","保证金"),
    SOCASH("socash","SO保证金");

    public String code;

    public String msg;

    FundType(String c, String m){
        this.code=c;
        this.msg =m;
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
    public static String getContentByValue(String value){
        FundType[] fundType = FundType.values();
        for(FundType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
