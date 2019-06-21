package com.ryx.credit.common.enumc;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 终端划拨   pos/手刷 平台类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum TerminalPlatformType {

    POS(new BigDecimal("1"),"POS"),
    MPOS(new BigDecimal("2"),"手刷"),
    RDBPOS(new BigDecimal("3"),"瑞大宝");

    public BigDecimal code;

    public String msg;

    TerminalPlatformType(BigDecimal c, String m){
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
        TerminalPlatformType[] fundType = TerminalPlatformType.values();
        for(TerminalPlatformType cc : fundType){
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<BigDecimal,String> getContentMap(){
        Map<BigDecimal,String> resultMap = new HashMap<>();
        TerminalPlatformType[] fundType = TerminalPlatformType.values();
        for(TerminalPlatformType cc : fundType){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
