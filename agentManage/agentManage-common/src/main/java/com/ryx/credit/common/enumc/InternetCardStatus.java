package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 物联网卡状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum InternetCardStatus {

    NORMAL(new BigDecimal("1"),"正常"),
    UNKNOWN(new BigDecimal("0"),"未知");

    public BigDecimal code;

    public String msg;

    InternetCardStatus(BigDecimal c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
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
    public static String getContentByValue(BigDecimal value){
        InternetCardStatus[] status = InternetCardStatus.values();
        for(InternetCardStatus cc : status){
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }

    public static BigDecimal getContentByMsg(String msg){
        InternetCardStatus[] status = InternetCardStatus.values();
        for(InternetCardStatus cc : status){
            if(cc.msg.equals(msg)){
                return cc.code;
            }
        }
        return null;
    }

    public static Map<BigDecimal, Object> getSelectMap(){
        Map<BigDecimal, Object> resultMap = new HashMap<>();
        InternetCardStatus[] status = InternetCardStatus.values();
        for(InternetCardStatus cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

    public static void main(String[] args){
        System.out.println(getContentByMsg("正常"));
    }
}
