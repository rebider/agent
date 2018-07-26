package com.ryx.credit.common.enumc;

/**
 * @Author Lihl
 * @Date 2018/07/25
 * 物流类型
 */
public enum LogType {

    Deliver("1","发货物流"),
    Refund("2","退货物流");

    public String code;

    public String msg;

    LogType(String c, String m){
        this.code = c;
        this.msg = m;
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
        LogType[] logTypes = LogType.values();
        for(LogType lo : logTypes){
            if(lo.code.equals(value)){
                return lo.msg;
            }
        }
        return "";
    }

}
