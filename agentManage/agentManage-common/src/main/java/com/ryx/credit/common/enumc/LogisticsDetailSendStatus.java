package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 作者：cx
 * 时间：2019/4/22
 * 描述：0：未联动
 1：联动成功
 2：联动失败
 3：联动处理中
 */
public enum LogisticsDetailSendStatus {
    none_send(new BigDecimal(0),"未联动"),
    send_success(new BigDecimal(1),"已联动"),
    send_fail(new BigDecimal(2),"联动失败"),
    send_ing(new BigDecimal(2),"联动业务系统处理中");

    public BigDecimal code;

    public String msg;

    LogisticsDetailSendStatus(BigDecimal c, String m){
        this.code = c;
        this.msg = m;
    }

}
