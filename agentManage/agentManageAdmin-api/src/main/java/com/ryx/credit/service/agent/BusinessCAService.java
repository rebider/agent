package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;


public interface BusinessCAService {
    
	/**
     * 代理商工商认证
     * @param agentBusinfoName
     * @return ResultVO
     */
    AgentResult agentBusinessCA(String agentBusinfoName);
}
