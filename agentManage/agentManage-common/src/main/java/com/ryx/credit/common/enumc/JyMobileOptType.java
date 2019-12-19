package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 物联网卡清算轧差状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum JyMobileOptType {

    STOP("1","停机"),
    OPEN("2","开机");

    public String code;

    public String msg;

    JyMobileOptType(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        JyMobileOptType[] status = JyMobileOptType.values();
        for(JyMobileOptType cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static String getContentByMsg(String msg){
        JyMobileOptType[] status = JyMobileOptType.values();
        for(JyMobileOptType cc : status){
            if(cc.msg.equals(msg)){
                return cc.code;
            }
        }
        return null;
    }

    public static Map<String, Object> getSelectMap(){
        Map<String, Object> resultMap = new HashMap<>();
        JyMobileOptType[] status = JyMobileOptType.values();
        for(JyMobileOptType cc : status){
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
