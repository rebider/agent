package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/9/6.
 */
public enum RedisCachKey {

    AGENT_BUSINFO("AGENT_BUSINFO:", "代理商业务信息"),DREGIONS("DREGIONS:", "区域信息"),DPOSREGION("DPosRegion:", "POS区域信息");
    public String code;

    public String msg;

    RedisCachKey(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
