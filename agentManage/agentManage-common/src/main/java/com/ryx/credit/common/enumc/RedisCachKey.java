package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/9/6.
 */
public enum RedisCachKey {
    USER_NAMES("USER_NAMES", "用戶信息"),
    AGENT_BUSINFO("AGENT_BUSINFO:", "代理商业务信息"),
    AGENTINFO("AGENTINFO:", "代理商信息"),
    DREGIONS("DREGIONS:", "区域信息"),
    DPOSREGION("DPosRegion:", "POS区域信息"),
    TERMINAL_TRANSFER("TERMINAL_TRANSFER:", "终端划拨"),
    APP_SPLIT("APP_SPLIT:", "补差价退货申请拆分");

    public String code;

    public String msg;

    RedisCachKey(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
