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

    DATACHANGEAPP("DATACHANGEAPP","数据修改审批"),
    NETINAPP("NETINAPP","入网业务审批"),
    BUSAPP("BUSAPP","业务审批"),

    OBASE("OBASE","订单基础信息"),
    OGOODS("OGOODS","订单商品信息"),
    OLOGISTICS("OLOGISTICS","订单物流信息"),
    ORETURN("ORETURN","订单退货信息"),
    ORLOGI("ORLOGI","订单退货物流信息");


//    NetInAdd("netInAdd","新增入网"),
//    NetInEdit("netInEdit","入网修改"),
//    NetInUpgrade("netInUpgrade","入网升级"),
//    IdentityAuth("identityAuth","身份认证");

    public String code;

    public String msg;

    public String notifyType;

    AgImportType(String c, String m){

        this.code=c;
        this.msg =m;

        if(c.equals("DATACHANGEAPP")){
            notifyType = "netInEdit";
        }else if(c.equals("NETINAPP")){
            notifyType = "netInAdd";
        }else if(c.equals("BUSAPP")){
            notifyType = "netInAdd";
        }else{
            notifyType = "netInAdd";
        }

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


    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static AgImportType getAgImportTypeByValue(String value){
        AgImportType[] fundType = AgImportType.values();
        for(AgImportType cc : fundType){
            if(cc.code.equals(value)){
                return cc;
            }
        }
        return null;
    }


}
