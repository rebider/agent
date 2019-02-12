package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgCapitalType {
    //    资金类型(押金，押金退款，扣款，扣款分期，补款，奖励，服务费等)
    YAJIN("押金"),
    REIHEBAOBZJ("瑞和宝保证金"),
    REIHEBAOFWF("瑞和宝服务费"),
    S0BAOZHENGJIN("S0保证金"),
    BAOZHENGJIN("保证金"),
    FUWUFEI("服务费");

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

    /**
     * 获取下拉列表
     * @return
     */
    public static Map<BigDecimal, Object> getContentMap() {
        Map<BigDecimal, Object> resultMap = new HashMap<>();
        RefundType[] refundTypes = RefundType.values();
        for (RefundType cc : refundTypes) {
            resultMap.put(cc.code, cc.msg);
        }
        return resultMap;
    }

}
