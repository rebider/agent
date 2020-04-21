package com.ryx.credit.common.enumc;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/***
 * 冻结类型
 * @Author ssx
 * @Description //TODO
 * @Date 2020-4-21 10:33:10
 * @Param
 * @return
 **/
public enum FreeType {

    AGNET(1,"本级代理商"),
    SUB_AGENT(2,"直签下级代理商");

    public BigDecimal code;

    public String msg;

    FreeType(int c, String m){
        this.code = new BigDecimal(c);
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        FreeType[] freeCause = FreeType.values();
        for (FreeType cc : freeCause) {
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<Object, Object> getContentMap() {
        FreeType[] freeCause = FreeType.values();
        Map<Object, Object> resultMap = new HashMap<>();
        for (FreeType cc : freeCause) {
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
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

    public static String getmsg(BigDecimal key){
        FreeType[] valus = FreeType.values();
        for (FreeType busType : valus) {
            if(busType.code.compareTo(key)==0){
                return busType.msg;
            }
        }
        return null;
    }
}
