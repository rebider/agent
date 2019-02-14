package com.ryx.credit.common.enumc;

import java.math.BigDecimal;


/**
 * 通知业务平台状态
 * Created by liudh on 2018/9/58.
 */
public enum PlatformStatus {

    FAIL(new BigDecimal("0"),"失败"),
    SUCCESS(new BigDecimal("1"),"成功"),
    NODISPOSE(new BigDecimal("2"),"未处理");


    public BigDecimal key;
    public String msg;

    PlatformStatus(BigDecimal k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(BigDecimal value){
        PlatformStatus[] busType = PlatformStatus.values();
        for(PlatformStatus bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }
}
