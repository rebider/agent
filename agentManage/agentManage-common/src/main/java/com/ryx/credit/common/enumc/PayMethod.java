package com.ryx.credit.common.enumc;

/**
 *付款方式
 */
public enum  PayMethod {
    OfflineMoney("1", "线下打款");
    public String code;

    public String msg;

    PayMethod(String c, String m){
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
    public static String getPayMethod(String value){
        PayMethod[] payType = PayMethod.values();
        for(PayMethod cc : payType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }


}