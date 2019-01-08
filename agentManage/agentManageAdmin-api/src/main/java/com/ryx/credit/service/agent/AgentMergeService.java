package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.math.BigDecimal;

/**
 * Created by RYX on 2019/1/7.
 */
public interface AgentMergeService {

    AgentResult approvalAgentMergeTask(AgentVo agentVo, String userId, String busId) throws Exception;

    AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus)throws Exception;

}
