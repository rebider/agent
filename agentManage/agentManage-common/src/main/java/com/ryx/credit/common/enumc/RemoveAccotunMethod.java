package com.ryx.credit.common.enumc;

/**
 * @Auther: lrr
 * @Date: 2019/9/19 11:37
 * @Description:销账方式
 */
public enum RemoveAccotunMethod {
    XXDK("XXDK", "线下打款"),
    FRDK("FRDK", "分润抵扣");
    public String code;

    public String msg;

    RemoveAccotunMethod(String c, String m){
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
    public static String getRemoveAccotunMethod(String value){
        RemoveAccotunMethod[] payType = RemoveAccotunMethod.values();
        for(RemoveAccotunMethod cc : payType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }
}