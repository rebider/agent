package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/6/6.
 */
public enum DataChangeApyType {

    AGENT("代理商"),AGENT_COLINFO("代理商账户修改申请");

    public String  msg;

    DataChangeApyType(String s){
        msg = s;
    }
}
