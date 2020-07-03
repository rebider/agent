package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.agent.FreezeRequest;
import com.ryx.credit.pojo.admin.agent.FreezeRequestDetail;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.AgentUnFreezeBatcthVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.FreeRequestVo;

import java.util.List;
import java.util.Map;

/**
 * 冻结申请
 */
public interface FreezeRequestService {
    PageInfo agentFreezeList(Map map, Page page);

    AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult agentUnFreeze(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult agentFreezeModify(AgentFreezePort agentFreezePort)throws MessageException;

    AgentResult approvalTask(AgentVo agentVo, String userId)throws Exception ;

    AgentResult agentFreezeFinish(String insid, String actname) throws Exception ;

    FreezeRequest queryFreezeRequestById(String id);

    AgentResult addList(List<List<Object>> list, String userid) throws Exception;

    AgentResult batchUnFreeze(AgentUnFreezeBatcthVo agentUnFreezeBatcthVo, String userid) throws Exception;

}
