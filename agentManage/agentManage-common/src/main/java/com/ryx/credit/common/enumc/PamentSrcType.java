package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/7/23.
 * 付款明细表里的ID源类型
 */
public enum PamentSrcType {

    PamentSrcType_XXBK("XXBK","线下补款");

    public String code;

    public String msg;

    PamentSrcType(String c, String m){
        this.code=c;
        this.msg =m;
    }

    public static String getSrcTypeValue(String value){
        PamentSrcType[] srcType = PamentSrcType.values();
        for(PamentSrcType cc : srcType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }
}
