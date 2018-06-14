package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum BusinessStatus {

    Enabled(1,"启用"),pause(2,"注销");

    public BigDecimal status;

    public String  msg;

    BusinessStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }
}
