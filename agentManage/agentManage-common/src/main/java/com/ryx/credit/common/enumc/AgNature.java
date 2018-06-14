package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgNature {

    SY(1,"私营"),GY(2,"国营");

    public BigDecimal status;

    public String  msg;

    AgNature(int status, String s){
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getAgNatureString(BigDecimal s){
        if(s==null)return null;
        for (AgNature agStatus : AgNature.values()) {
            if(agStatus.status.compareTo(s)==0){
                return agStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgNatureMsgString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (AgNature agStatus : AgNature.values()) {
            if(agStatus.msg.equals(s)){
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static BigDecimal getAgNatureNameString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (AgNature agStatus : AgNature.values()) {
            if(agStatus.name().equals(s)){
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }
}
