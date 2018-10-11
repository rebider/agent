package com.ryx.credit.common.enumc;


/**
 * pos/手刷 平台类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum PlatformType {

    POS("POS","POS"),
    ZPOS("ZPOS","ZPOS"),
    MPOS("MPOS","手刷");

    public String code;

    public String msg;

    PlatformType(String c, String m){
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
        PlatformType[] fundType = PlatformType.values();
        for(PlatformType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }


    public static PlatformType getContentEnum(String value){
        PlatformType[] fundType = PlatformType.values();
        for(PlatformType cc : fundType){
            if(cc.code.equals(value)){
                return cc;
            }
        }
        return null;
    }
}
