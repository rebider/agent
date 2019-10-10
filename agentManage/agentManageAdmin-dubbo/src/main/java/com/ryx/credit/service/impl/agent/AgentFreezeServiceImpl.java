package com.ryx.credit.service.impl.agent;

import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.service.agent.AgentFreezeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 *
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/10/10 9:14
 * @Param
 * @return
 **/
@Service("agentFreezeService")
public class AgentFreezeServiceImpl implements AgentFreezeService {

    @Autowired
    private AgentFreezeMapper agentFreezeMapper;




}
