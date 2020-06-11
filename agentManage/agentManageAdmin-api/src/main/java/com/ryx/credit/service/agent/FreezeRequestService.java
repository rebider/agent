package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.FreezeRequest;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;

import java.util.Map;

/**
 * 冻结申请
 */
public interface FreezeRequestService {
    PageInfo agentFreezeList(Map map, Page page);

    AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult agentFreezeModify(AgentFreezePort agentFreezePort)throws MessageException;

    FreezeRequest queryFreezeRequestById(String id);

    AgentResult approvalTask(AgentFreezePort agentFreezePort,String userId)throws Exception ;

    AgentResult agentFreezeFinish(String insid, String actname) throws Exception ;

}
