package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/5/22.
 */
public enum Status {

    STATUS_0(0),STATUS_1(1);

    public BigDecimal status;

    Status(int s){
        status = new BigDecimal(s);
    }

}