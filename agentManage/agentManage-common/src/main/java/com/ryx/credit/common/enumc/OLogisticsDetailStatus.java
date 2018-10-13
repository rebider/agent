package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/10.
 */
public enum OLogisticsDetailStatus {


    STATUS_FH("1","发货"),
    STATUS_TH("2","退货"),
    STATUS_KC("0","库存"),
    RECORD_STATUS_HIS("3","历史"),
    RECORD_STATUS_LOC("2","锁定"),
    RECORD_STATUS_VAL("1","有效"),
    RECORD_STATUS_DEL("0","删除");

    public BigDecimal code;

    public String msg;

    OLogisticsDetailStatus(String c, String m){
        this.code = new BigDecimal(c);
        this.msg = m;
    }

}
