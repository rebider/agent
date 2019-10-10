package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;

/**
 * Created by RYX on 2019/10/10.
 */
public interface AgentFreezeService {

    PageInfo agentFreezeList(AgentFreeze agentFreeze, Page page);

    AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult agentUnFreeze(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult queryAgentFreeze(String agentId);
}
