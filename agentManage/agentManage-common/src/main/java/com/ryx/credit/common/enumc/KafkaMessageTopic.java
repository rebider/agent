package com.ryx.credit.common.enumc;

/**
 * 作者：cx
 * 时间：2019/9/18
 * 描述：
 */
public enum KafkaMessageTopic {
    agent_Payment("agent_Payment","付款信息"),
    agent_Freeze("agentFreeze","冻结信息"),
    CardChange("CardChange","结算卡信息变更通知");

    public String code;

    public String msg;

    KafkaMessageTopic(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
