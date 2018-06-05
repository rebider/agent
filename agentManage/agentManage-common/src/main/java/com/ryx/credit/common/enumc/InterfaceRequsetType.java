package com.ryx.credit.common.enumc;


/**
 * 接口请求类型
 */
public enum InterfaceRequsetType {

	MERCHANT_CA("MERCHANT_CA","代理商工商认证");

    public String code;

    public String msg;

    InterfaceRequsetType(String c, String m){
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
        InterfaceRequsetType[] fundType = InterfaceRequsetType.values();
        for(InterfaceRequsetType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
