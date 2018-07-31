package com.ryx.credit.common.enumc;

/**
 * @Auther: RYX
 * @Date: 2018/7/31 11:29
 * @Description:分润获取的方式
 */
public enum GetMethod {
    AGENTORDER("AGENTORDER", "代理商订单分期"), AGENTDATE("AGENTDATE", "所有当月分期");

    public String code;

    public String msg;

    GetMethod(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}