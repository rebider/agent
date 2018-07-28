package com.ryx.credit.common.enumc;

/**
 * 补差价类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/28 18:11
 */
public enum PriceDiffType {

    DETAIN_AMT("1","扣款"),
    REPAIR_AMT("2","补款");

    public String code;

    public String msg;

    PriceDiffType(String c, String m){
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
        PriceDiffType[] status = PriceDiffType.values();
        for(PriceDiffType cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
