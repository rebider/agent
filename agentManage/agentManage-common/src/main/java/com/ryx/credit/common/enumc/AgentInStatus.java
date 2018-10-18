package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/5/30.
 */
public enum AgentInStatus {

    IN(1,"已入网"),NO(0,"未入网"),NO_ACT(2,"入网未激活");

    public BigDecimal status;

    public String  msg;

    AgentInStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }
}
