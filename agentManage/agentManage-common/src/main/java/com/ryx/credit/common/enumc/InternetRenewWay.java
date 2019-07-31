package com.ryx.credit.common.enumc;


import java.util.HashMap;
import java.util.Map;

/**
 * 续费方式
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/1 18:11
 */
public enum InternetRenewWay {

    XXBK("XXBK","线下补款"),
    XXBKGC("XXBKGC","线下补款+轧差商户"),
    FRDK("FRDK","分润抵扣"),
    FRDKGC("FRDKGC","分润抵扣+轧差商户");

    public String code;

    public String msg;

    InternetRenewWay(String c, String m){
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
        InternetRenewWay[] fundType = InternetRenewWay.values();
        for(InternetRenewWay cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }


    public static  Map<Object, Object> getContentMap(){
        Map<Object, Object> map = new HashMap<>();
        InternetRenewWay[] fundType = InternetRenewWay.values();
        for(InternetRenewWay cc : fundType){
            map.put(cc.getValue(),cc.getContent());
        }
        return map;
    }

    public static void main(String[] args){
        System.out.println(getContentMap());
    }
}
