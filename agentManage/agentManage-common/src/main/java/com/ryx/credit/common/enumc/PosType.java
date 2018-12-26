package com.ryx.credit.common.enumc;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * pos类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum PosType {

    ZERO(new BigDecimal("0"),"普通机"),
    ONE(new BigDecimal("1"),"特价机"),
    TWO(new BigDecimal("2"),"特价机（无押金）");

    public BigDecimal code;

    public String msg;

    PosType(BigDecimal c, String m){
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
        PosType[] fundType = PosType.values();
        for(PosType cc : fundType){
            if(cc.code.toString().equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<BigDecimal,Object> getContentMap(){
        Map<BigDecimal,Object> resultMap = new HashMap<>();
        PosType[] fundType = PosType.values();
        for(PosType cc : fundType){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
