package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgStatus {

    Create("新建"),Approving("审批中"),Approved("审批通过"),Refuse("审批拒绝");

    public String  msg;

    AgStatus(String s){
        msg = s;
    }
}
