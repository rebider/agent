package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 冻结申请类型
 * Created by ssx on 2020-6-9 12:36:54.
 */
public enum FreezeRequestType {

    Freeze(0,"申请冻结"),
    UnFreeze(2,"申请解冻"),
    Modify(1,"申请修改");

    public BigDecimal code;

    public String  msg;
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
    FreezeRequestType(int status, String s){
        this.code = new BigDecimal(status);
        msg = s;
    }


    public static String getAgStatusString(BigDecimal s){
        if(s==null)return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.code.compareTo(s)==0){
                return agStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgStatusString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.name().equals(s)){
                return agStatus.code;
            }
        }
        return new BigDecimal(-1);
    }

    public static String getAgStatusByValue(String s){
        if(StringUtils.isEmpty(s))return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.name().equals(s)){
                return agStatus.msg;
            }
        }
        return "";
    }
    public static String getMsg(BigDecimal s){
        if(s==null)return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.code.compareTo(s)==0){
                return agStatus.msg;
            }
        }
        return "";
    }
}
