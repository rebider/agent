package com.ryx.credit.common.enumc;


/**
 * 接口通知类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum NotifyType {

    /**
     * 数据库存Key
     */
    NetInAdd("netInAdd","新增入网"),
    NetInEdit("netInEdit","入网修改"),
    NetInUpgrade("netInUpgrade","入网升级"),
    IdentityAuth("identityAuth","身份证认证");

    public String code;

    public String msg;

    NotifyType(String c, String m){
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
        NotifyType[] notifyType = NotifyType.values();
        for(NotifyType cc : notifyType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
