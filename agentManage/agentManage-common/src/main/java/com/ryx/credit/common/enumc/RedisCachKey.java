package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/9/6.
 */
public enum RedisCachKey {
    USER_NAMES("USER_NAMES", "用戶信息"),
    AGENT_BUSINFO("AGENT_BUSINFO:", "代理商业务信息"),
    AGENT_BANK("AGENT_BANK:", "三要素认证信息"),
    AGENTINFO("AGENTINFO:", "代理商信息"),
    DREGIONS("DREGIONS:", "区域信息"),
    DPOSREGION("DPosRegion:", "POS区域信息"),
    TERMINAL_TRANSFER("TERMINAL_TRANSFER:", "终端划拨"),
    APP_SPLIT("APP_SPLIT:", "补差价退货申请拆分"),
    INTERNET_CARD("INTERNET_CARD:", "物联网卡批处理"),
    TASK_DISPOSEIN_TERNET_CARD("TASK_DISPOSEIN_TERNET_CARD", "物联网卡定时任务锁"),
    RENEW_CARD("RENEW_CARD:", "物联网卡续费/注销"),
    INSERT_SYS_KEY("synColinfoToPayment_lock:", "打款账户同步清结算"),
    QUERY_SYS_KEY("synColinfoToQueryPayment_lock:", "打款账户同步清结算查询"),
    AGENT_FREEZE_LOCK("AGENT_FREEZE_LOCK:", "代理商冻结锁"),
    AGENT_UN_FREEZE_LOCK("AGENT_UN_FREEZE_LOCK:", "代理商解冻锁"),
    CARDRENEW22ONOFF("OnOff:CardRenew22OnOff", "续费22号限制开关");

    public String code;

    public String msg;

    RedisCachKey(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
