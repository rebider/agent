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
public enum CardStatus {

    WZ(new BigDecimal("1"),"未知"),
    ZC(new BigDecimal("2"),"正常"),
    YJ(new BigDecimal("3"),"预警"),
    TJ(new BigDecimal("4"),"停机");

    public BigDecimal code;

    public String msg;

    CardStatus(BigDecimal c, String m){
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
    public static String getContentByValue(String value){
        CardStatus[] status = CardStatus.values();
        for(CardStatus cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<BigDecimal, Object> getSelectMap(){
        Map<BigDecimal, Object> resultMap = new HashMap<>();
        CardStatus[] status = CardStatus.values();
        for(CardStatus cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
