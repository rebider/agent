package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2020/6/19 10:30
 * @Description:结算卡修改状态
 */
public enum AmendStatus {
    DXG(0,"待修改"),
    XGZ(1,"修改中"),
    YXG(2,"已修改");
    public BigDecimal status;

    public String  msg;

    AmendStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }

}
