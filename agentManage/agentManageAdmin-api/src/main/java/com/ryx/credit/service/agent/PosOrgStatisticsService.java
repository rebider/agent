package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;

/**
 * Created by RYX on 2018/10/9.
 */
public interface PosOrgStatisticsService {

    AgentResult posOrgStatistics(String busPlatform,String orgId)throws Exception;
    
}
