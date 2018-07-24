package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @Auther: RYX
 * @Date: 2018/7/20 15:29
 * @Description:补款状态
 */
public enum SchStatus {
    ONE(1, "暂存"), TWO(2, "有效"), THREE(3, "已完成"), FOUR(4, "失败");
    private BigDecimal status;
    private String msg;

    public BigDecimal getValue() {
        return this.status;
    }

    public String getContent() {
        return this.msg;
    }

    SchStatus(int status, String s) {
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getAgCertTypeString(BigDecimal s) {
        if (s == null) return null;
        for (SchStatus agStatus : SchStatus.values()) {
            if (agStatus.status.compareTo(s) == 0) {
                return agStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgCertTypeMsgString(String s) {
        if (StringUtils.isEmpty(s)) return null;
        for (SchStatus agStatus : SchStatus.values()) {
            if (agStatus.msg.equals(s)) {
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static BigDecimal getAgCertTypeNameString(String s) {
        if (StringUtils.isEmpty(s)) return null;
        for (SchStatus agStatus : SchStatus.values()) {
            if (agStatus.name().equals(s)) {
                return agStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static String getMsg(BigDecimal s) {
        if (s == null) return null;
        for (SchStatus schStatus : SchStatus.values()) {
            if (schStatus.status.compareTo(s) == 0) {
                return schStatus.msg;
            }
        }
        return "";
    }
}