package com.ryx.credit.common.enumc;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum VisibleStatus {

    ONT("1","全部可见"),
    TWO("2","部分可见");


    public String key;
    public String msg;

    VisibleStatus(String k, String s){
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
        VisibleStatus[] busType = VisibleStatus.values();
        for(VisibleStatus bt : busType){
            if(bt.key.equals(value)){
                return bt.msg;
            }
        }
        return "";
    }

}
