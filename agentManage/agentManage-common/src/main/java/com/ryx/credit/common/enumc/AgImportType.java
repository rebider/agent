package com.ryx.credit.common.enumc;


/**
 * 数据处理类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum AgImportType {

    BASICS("BASICS","基础信息"),
    BUSINESS("BUSINESS","业务信息"),
    CONTRACT("CONTRACT","合同信息"),
    PAYMENT("PAYMENT","缴纳款项"),
    BASBUSR("BASBUSR","代理商业务关系"),
    GATHER("GATHER","收款账户"),
    NETINAPP("NETIN","入网业务审批"),
    BUSAPP("BUSAPP","业务审批");

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
