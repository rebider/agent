package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgCertType {

    SFZ(1,"身份证"),JGZ(2,"军官证");

    public BigDecimal status;

    public String  msg;

    AgCertType(int status, String s){
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getAgCertTypeString(BigDecimal s){
        if(s==null)return null;
        for (AgCertType agStatus : AgCertType.values()) {
            if(agStatus.status.compareTo(s)==0){
                return agStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgCertTypeMsgString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (AgCertType agStatus : AgCertType.values()) {
            if(agStatus.msg.equals(s)){
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static BigDecimal getAgCertTypeNameString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (AgCertType agStatus : AgCertType.values()) {
            if(agStatus.name().equals(s)){
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }
}
