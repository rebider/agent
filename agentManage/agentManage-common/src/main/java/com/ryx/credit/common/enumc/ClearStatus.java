package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 *
 * @Auther: RYX
 * @Date: 2018/8/2 15:39
 * @Description:
 */
public enum ClearStatus {
    UNCLEARED(0, "未结清"),
    CLEARED(1, "已结清");

    public BigDecimal status;

    public String msg;

    ClearStatus(int status, String s) {
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getClearString(BigDecimal s) {
        if (s == null) return null;
        for (ClearStatus clearStatus : ClearStatus.values()) {
            if (clearStatus.status.compareTo(s) == 0) {
                return clearStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getClearMsgString(String s) {
        if (StringUtils.isEmpty(s)) return null;
        for (ClearStatus clearStatus : ClearStatus.values()) {
            if (clearStatus.msg.equals(s)) {
                return clearStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static BigDecimal getClearNameString(String s) {
        if (StringUtils.isEmpty(s)) return null;
        for (ClearStatus clearStatus : ClearStatus.values()) {
            if (clearStatus.name().equals(s)) {
                return clearStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

}