package com.ryx.credit.common.enumc;


import java.util.HashMap;
import java.util.Map;

/**
 * 注销状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/1 18:11
 */
public enum InternetLogoutStatus {

    ZXZ("ZXZ","注销中"),
    TJCLZ("TJCLZ","停机处理中"),
    TJSB("TJSB","停机失败"),
    DZX("DZX","待注销"),//停机成功
    ZXCG("ZXCG","注销成功"),
    SX("SX","失效");

    public String code;

    public String msg;

    InternetLogoutStatus(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        InternetLogoutStatus[] fundType = InternetLogoutStatus.values();
        for(InternetLogoutStatus cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static  Map<Object, Object> getContentMap(){
        Map<Object, Object> map = new HashMap<>();
        InternetLogoutStatus[] fundType = InternetLogoutStatus.values();
        for(InternetLogoutStatus cc : fundType){
            map.put(cc.getValue(),cc.getContent());
        }
        return map;
    }

    /**
     * 根据内容获取值
     * @param content
     * @return
     */
    public static String getValueByContent(String content){
        InternetLogoutStatus[] fundType = InternetLogoutStatus.values();
        for(InternetLogoutStatus cc : fundType){
            if(cc.getContent().equals(content)){
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
