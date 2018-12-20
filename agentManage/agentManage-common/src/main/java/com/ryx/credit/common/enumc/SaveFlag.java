package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum SaveFlag {

    ZC("1","暂存"),
    TJSP("2","提交审批");


    public String key;
    public String msg;

    SaveFlag(String k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }

    public static String getContentByValue(String value){
        SaveFlag[] busType = SaveFlag.values();
        for(SaveFlag bt : busType){
            if(bt.key.equals(value)){
                return bt.msg;
            }
        }
        return "";
    }

}
