package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 冻结申请类型
 * Created by ssx on 2020-6-9 12:36:54.
 */
public enum FreezeRequestType {

    Freeze(1,"申请冻结"),
    UnFreeze(2,"申请解冻"),
    Modify(3,"申请修改");

    public BigDecimal status;

    public String  msg;
    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.status;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }
    FreezeRequestType(int status, String s){
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getAgStatusString(BigDecimal s){
        if(s==null)return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.status.compareTo(s)==0){
                return agStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgStatusString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (FreezeRequestType agStatus : FreezeRequestType.values()) {
            if(agStatus.name().equals(s)){
                return agStatus.status;
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
            if(agStatus.status.compareTo(s)==0){
                return agStatus.msg;
            }
        }
        return "";
    }
}
