package com.ryx.credit.service;

import com.ryx.credit.common.result.AgentResult;

/**
 * 作者：cx
 * 时间：2019/9/18
 * 描述：
 */
public interface AgentKafkaService {

    /**
     * 发送业务消息
     * @param agentId
     * @param agentName
     * @param busId
     * @param ktype
     * @param topic
     * @param message
     * @return
     */
    public AgentResult sendPayMentMessage(String agentId,String agentName,String busId,String busnum,String ktype,String topic, String message);

    /**
     * 放存量消息
     * @param id
     * @return
     */
    public AgentResult sendPayMentMessage(String id);

}
