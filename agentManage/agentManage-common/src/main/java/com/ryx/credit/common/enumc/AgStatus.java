package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum AgStatus {

    Create(1,"新建"),Approving(2,"审批中"),Approved(3,"审批通过"),Refuse(4,"审批拒绝"),Reject(5,"驳回");

    public BigDecimal status;

    public String  msg;

    AgStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }
}
