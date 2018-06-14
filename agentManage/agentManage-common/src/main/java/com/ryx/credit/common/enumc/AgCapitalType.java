package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgCapitalType {
    //    资金类型(押金，押金退款，扣款，扣款分期，补款，奖励，服务费等)
    YAJIN("押金"),FUWUFEI("服务费");

    public String  msg;

    AgCapitalType(String s){
        msg = s;
    }

    public static String getAgNatureMsgString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (AgCapitalType agStatus : AgCapitalType.values()) {
            if(agStatus.msg.equals(s)){
                return agStatus.name();
            }
        }
        return "";
    }


}
