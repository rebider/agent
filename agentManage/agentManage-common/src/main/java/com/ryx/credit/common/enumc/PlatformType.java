package com.ryx.credit.common.enumc;


import java.util.HashMap;
import java.util.Map;

/**
 * pos/手刷 平台类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum PlatformType {

    POS("POS","POS"),
    ZPOS("ZPOS","智能POS"),
    ZHPOS("ZHPOS","智慧POS"),
    MPOS("MPOS","手刷"),
    RDBPOS("RDBPOS","瑞大宝");

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

    public static Map<String,Object> getContentMap(){
        Map<String,Object> resultMap = new HashMap<>();
        PlatformType[] fundType = PlatformType.values();
        for(PlatformType cc : fundType){
            if(!cc.code.equals("ZPOS")){
                resultMap.put(cc.code,cc.msg);
            }
        }
        return resultMap;
    }

    public static Map<String,Object> getContentMapPosMpos(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(PlatformType.POS.getValue(),PlatformType.POS.getContent());
        resultMap.put(PlatformType.MPOS.getValue(),PlatformType.MPOS.getContent());
        return resultMap;
    }


    /**
     * 判断是否属于POS平台
     * @param platformTypeCode
     * @return
     */
    public static Boolean whetherPOS(String platformTypeCode){
        return platformTypeCode.equals(PlatformType.POS.code) || platformTypeCode.equals(PlatformType.ZPOS.code) || platformTypeCode.equals(PlatformType.ZHPOS.code);
    }
}
