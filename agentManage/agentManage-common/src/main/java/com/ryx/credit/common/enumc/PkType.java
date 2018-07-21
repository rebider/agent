package com.ryx.credit.common.enumc;

/**
 * 补款类型
 */
public enum PkType {
    OfflineMoney("1","线下打款分期补款"),
    FenRunOverdue("2","分润逾期扣款补款");

    public String code;

    public String msg;

    PkType(String c, String m){
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
    public static String gePkTypeValue(String value){
        PkType[] pkType = PkType.values();
        for(PkType cc : pkType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
