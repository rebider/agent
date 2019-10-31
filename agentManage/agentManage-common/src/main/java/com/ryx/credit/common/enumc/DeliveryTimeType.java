package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

public enum DeliveryTimeType {

    ZERO("00","发货时间正常调整"),
    ONE("01","延期发货天数");

    public String code;

    public String msg;

    DeliveryTimeType(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        DeliveryTimeType[] status = DeliveryTimeType.values();
        for(DeliveryTimeType cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static String getContentByMsg(String msg){
        DeliveryTimeType[] status = DeliveryTimeType.values();
        for(DeliveryTimeType cc : status){
            if(cc.msg.equals(msg)){
                return cc.code;
            }
        }
        return null;
    }

    public static Map<String, Object> getSelectMap(){
        Map<String, Object> resultMap = new HashMap<>();
        DeliveryTimeType[] status = DeliveryTimeType.values();
        for(DeliveryTimeType cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
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
