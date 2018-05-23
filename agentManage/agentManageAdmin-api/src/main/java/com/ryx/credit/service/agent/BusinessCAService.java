package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.ResultVO;


public interface BusinessCAService {
    
	/**
     * 代理商工商认证
     * @param agentBusinfoName
     * @return ResultVO
     */
    public ResultVO agentBusinessCA(String agentBusinfoName);
}
