package com.ryx.credit.common.enumc;

/**
 * @Author: Zhang Lei
 * @Description: 分润数据调整类型
 * @Date: 16:08 2019/9/25
 */
public enum PDataAdjustType {

    KS("KS", "扣税调整"), QP("QP", "欠票调整");

    public String adjustType;

    public String msg;

    PDataAdjustType(String adjustType, String msg) {
        this.adjustType = adjustType;
        this.msg = msg;
    }
}
