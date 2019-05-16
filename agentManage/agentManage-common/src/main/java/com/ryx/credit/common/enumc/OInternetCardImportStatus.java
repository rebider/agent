package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 导入类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum OInternetCardImportStatus {

    UNTREATED(new BigDecimal("1"),"未处理"),
    SUCCESS(new BigDecimal("2"),"成功"),
    FAIL(new BigDecimal("3"),"失败"),
    TIMEOUT(new BigDecimal("4"),"超时");


    public BigDecimal code;

    public String msg;

    OInternetCardImportStatus(BigDecimal c, String m){
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
        OInternetCardImportStatus[] status = OInternetCardImportStatus.values();
        for(OInternetCardImportStatus cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<BigDecimal, Object> getSelectMap(){
        Map<BigDecimal, Object> resultMap = new HashMap<>();
        OInternetCardImportStatus[] status = OInternetCardImportStatus.values();
        for(OInternetCardImportStatus cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
