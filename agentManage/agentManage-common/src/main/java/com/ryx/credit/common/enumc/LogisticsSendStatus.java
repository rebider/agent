package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 作者：cx
 * 时间：2019/4/22
 * 描述：0：未联动
 1：已联动
 2：联动失败，
 3：部分联动失败，
 4：生成明细失败，
 5：生成明细中，
 6：生成明细成功
 7：联动业务系统处理中
 */
public enum LogisticsSendStatus {
    none_send(new BigDecimal(0),"未联动"),
    send_success(new BigDecimal(1),"已联动"),
    send_fail(new BigDecimal(2),"联动失败"),
    send_part_fail(new BigDecimal(3),"部分联动失败"),
    gen_detail_fail(new BigDecimal(4),"生成明细失败"),
    gen_detail_ing(new BigDecimal(5),"生成明细中"),
    gen_detail_sucess(new BigDecimal(6),"生成明细成功"),
    send_ing(new BigDecimal(7),"联动业务系统处理中");

    public BigDecimal code;

    public String msg;

    LogisticsSendStatus(BigDecimal c, String m){
        this.code = c;
        this.msg = m;
    }

}
