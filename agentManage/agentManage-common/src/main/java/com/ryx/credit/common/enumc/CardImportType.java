package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 导入类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum CardImportType {

    XGG("1","新国都"),
    TY("2","天瑜"),
    LD("3","联迪"),
    XDL("4","新大陆"),
    COM("5","通用");

    public String code;

    public String msg;

    CardImportType(String c, String m){
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
        CardImportType[] status = CardImportType.values();
        for(CardImportType cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<String, Object> getSelectMap(){
        Map<String, Object> resultMap = new HashMap<>();
        CardImportType[] status = CardImportType.values();
        for(CardImportType cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
