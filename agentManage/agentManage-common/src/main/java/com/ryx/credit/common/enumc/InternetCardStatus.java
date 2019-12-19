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
    NOACTIVATE(new BigDecimal("2"),"待激活"),
    STOP(new BigDecimal("3"),"停用"),  //停机可以启用
    LOGOUT(new BigDecimal("4"),"注销"),  //注销不能在开机
    UNKNOWN(new BigDecimal("0"),"未知"),
    //揭阳移动状态
    test(new BigDecimal("5"),"测试期"),
    silent(new BigDecimal("6"),"沉默期"),
    inventory(new BigDecimal("7"),"库存期"),
    bespeakClose(new BigDecimal("8"),"预约销户");



    public BigDecimal code;

    public String msg;

    InternetCardStatus(BigDecimal c, String m){
        this.code=c;
        this.msg =m;
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

    /**
     * 揭阳移动状态关系映射
     * @param mobileStatus
     * @return
     */
    public static BigDecimal getCardStatusByJYMobile(String mobileStatus){
        if(mobileStatus.equals("normal")){
            return NORMAL.code;
        }
        if(mobileStatus.equals("stop")){
            return STOP.code;
        }
        if(mobileStatus.equals("preclose")){
            return LOGOUT.code;
        }
        InternetCardStatus[] internetCardStatuss = InternetCardStatus.values();
        for(InternetCardStatus internetCardStatus : internetCardStatuss){
            if(mobileStatus.equals(internetCardStatus.name())){
                return internetCardStatus.code;
            }
        }
        return new BigDecimal(-1);
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

}
