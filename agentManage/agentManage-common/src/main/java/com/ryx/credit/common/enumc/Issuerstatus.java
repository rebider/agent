package com.ryx.credit.common.enumc;

/**
 * 流量卡发卡方
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum Issuerstatus {

    JY_MOBILE("JY","揭阳移动"),
    YT_MOBILE("YT","烟台移动");

    public String code;

    public String msg;

    Issuerstatus(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        Issuerstatus[] status = Issuerstatus.values();
        for(Issuerstatus cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static String getContentByMsg(String msg){
        Issuerstatus[] status = Issuerstatus.values();
        for(Issuerstatus cc : status){
            if(cc.msg.equals(msg)){
                return cc.code;
            }
        }
        return "";
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

}
