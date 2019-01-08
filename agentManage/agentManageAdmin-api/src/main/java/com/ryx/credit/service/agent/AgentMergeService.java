package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;

import java.math.BigDecimal;

/**
 * Created by RYX on 2019/1/7.
 */
public interface AgentMergeService {

    AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus)throws Exception;
}
