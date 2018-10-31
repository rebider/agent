package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: RYX
 * @Date: 2018/9/20 10:54
 * @Description:订单线下打款方式
 */
public enum  PayType {
    YHHK("YHHK", "银行汇款"),
    FRDK("FRDK", "分润抵扣");
    public String code;

    public String msg;

    PayType(String c, String m){
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
    public static String getPayType(String value){
        PayType[] payType = PayType.values();
        for(PayType cc : payType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<String,Object> getAllOption(){
        Map<String,Object> resultMap = new HashMap<>();
        PayType[] payType = PayType.values();
        for(PayType type : payType){
            resultMap.put(type.code,type.msg);
        }
        return resultMap;
    }

    public static Map<String,Object> getYHHKOption(){
        Map<String,Object> resultMap = new HashMap<>();
        PayType[] payType = PayType.values();
        for(PayType type : payType){
            if(type.code.equals("YHHK"))
            resultMap.put(type.code,type.msg);
        }
        return resultMap;
    }

}