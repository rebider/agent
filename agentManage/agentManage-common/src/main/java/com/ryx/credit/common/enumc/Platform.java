package com.ryx.credit.common.enumc;


/**
 * 平台类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/9/12 9:11
 */
public enum Platform {

    RYX("0001","手刷-瑞银信"),
    RS("2000","手刷-瑞刷"),
    TP("1001","手刷-贴牌"),
    RHB("5000","手刷-瑞和宝"),
    RYXHD("1111","手刷-瑞银信活动"),
    RSHD("3000","手刷-瑞刷活动"),
    RHBZF("6000","手刷-瑞和宝直发平台"),
    RZT("4000","手刷-瑞众通"),
    POS("100003","POS"),
    ZPOS("100002","智能POS"),
    REF("100004","POS-瑞e付"),
    RYF("100005","POS-瑞银付"),
    RXS("100006","POS-瑞享送"),
    RF("100007","POS-瑞付"),
    RYT("100008","POS-瑞银通"),
    RLS("100009","POS-瑞乐送"),
    RYS("100010","POS-瑞银送"),
    RET("100011","POS-瑞易通");

    public String code;

    public String msg;

    Platform(String c, String m){
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
        Platform[] fundType = Platform.values();
        for(Platform cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
