package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;

import java.util.Map;

/**
 * Created by RYX on 2018/10/9.
 */
public interface PosOrgStatisticsService {

    AgentResult posOrgStatistics(String busPlatform,String orgId,String busId,String termType)throws Exception;

    AgentResult posOrgStatistics(String orgId,String termType)throws Exception;

    AgentResult posOrgStatistics(Map map)throws Exception;
}
