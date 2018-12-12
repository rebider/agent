package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 物联网卡流量单位
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum ExceedFlowUnit {

    M("1","M"),
    G("2","G");

    public String code;

    public String msg;

    ExceedFlowUnit(String c, String m){
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
    public static String getContentByValue(String value){
        ExceedFlowUnit[] status = ExceedFlowUnit.values();
        for(ExceedFlowUnit cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<String, Object> getSelectMap(){
        Map<String, Object> resultMap = new HashMap<>();
        ExceedFlowUnit[] status = ExceedFlowUnit.values();
        for(ExceedFlowUnit cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
