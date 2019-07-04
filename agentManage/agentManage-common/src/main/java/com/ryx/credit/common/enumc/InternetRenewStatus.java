package com.ryx.credit.common.enumc;


import java.util.HashMap;
import java.util.Map;

/**
 * 续费状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/1 18:11
 */
public enum InternetRenewStatus {

    WXF("1","未续费"),
    YXF("2","已续费"),
    BFXF("3","部分续费"),
    XFZ("4","续费中");

    public String code;

    public String msg;

    InternetRenewStatus(String c, String m){
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
        InternetRenewStatus[] fundType = InternetRenewStatus.values();
        for(InternetRenewStatus cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }


    public static  Map<Object, Object> getContentMap(){
        Map<Object, Object> map = new HashMap<>();
        InternetRenewStatus[] fundType = InternetRenewStatus.values();
        for(InternetRenewStatus cc : fundType){
            map.put(cc.getValue(),cc.getContent());
        }
        return map;
    }

    public static void main(String[] args){
        System.out.println(getContentMap());
    }
}
