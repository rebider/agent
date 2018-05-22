package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.Agent;

import java.util.List;

/**
 * 代理商基础信息管理服务类
 * Created by cx on 2018/5/22.
 */
public interface AgentService {


    /**
     * 添加代理商基础信息
     * @param agent
     * @param attrId
     * @return
     */
    public Agent insertAgent(Agent agent,List<String> attrId);


    /**
     * 根据Id获取代理商基础信息
     * @param id
     * @return
     */
    public Agent getAgentById(String id);




}
