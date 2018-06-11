package com.ryx.credit.common.enumc;


/**
 * 数据历史类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum AgImportType {

    BASICS("basics","基础信息"),
    BUSINESS("business","业务信息"),
    CONTRACT("contract","合同信息"),
    PAYMENT("payment","缴纳款项"),
    BASBUSR("BASBUSR","代理商业务关系"),
    GATHER("gather","收款账户");

    public String code;

    public String msg;

    AgImportType(String c, String m){
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
        AgImportType[] fundType = AgImportType.values();
        for(AgImportType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
