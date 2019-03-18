package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 订单类型
 * Created by liudh on 2018/9/58.
 */
public enum OrderType {

    NEW(new BigDecimal("1"),"新订单"),
    OLD(new BigDecimal("2"),"老订单");


    public BigDecimal key;
    public String msg;

    OrderType(BigDecimal k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(BigDecimal value){
        OrderType[] busType = OrderType.values();
        for(OrderType bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        OrderType[] busType = OrderType.values();
        for(OrderType bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

}
