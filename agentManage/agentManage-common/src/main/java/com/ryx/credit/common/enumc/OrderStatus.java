package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/20.
 */
public enum OrderStatus {
    CREATE("1","新建"),
    ENABLE("2","生效"),
    LOCK("3","锁定状态"),
    UNENABLE("4","失效"),
    REVOKE("5","已撤销");

    public String code;

    public String msg;

    public BigDecimal status;

    OrderStatus(String c, String m){
        this.code=c;
        this.msg =m;
        this.status = new BigDecimal(c);
    }
}
