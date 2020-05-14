package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Auther: lrr
 * @Date: 2020/5/14 15:39
 * @Description:认证结果枚举
 */
public enum CerResStatus {
    SUCCESS(1, "认证成功"),
    FAIL(2, "认证失败"),
    INCONFORMITY(3, "认证成功,与本地信息不符"),
    DEFICIENCY(4, "认证失败,信息缺失");

    public BigDecimal status;

    public String msg;

    CerResStatus(int status, String s) {
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getCerResString(BigDecimal s) {
        if (s == null) return null;
        for (CerResStatus cerResStatus : CerResStatus.values()) {
            if (cerResStatus.status.compareTo(s) == 0) {
                return cerResStatus.name();
            }
        }
        return "";
    }
}
