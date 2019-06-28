package com.ryx.credit.service.agent.netInPort;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RYX on 2019/5/21.
 */
public interface AgentNetInHttpService {

    Map<String,Object> packageParam(Map<String,Object> param);

    AgentResult httpRequestNetIn(Map<String,Object> paramMap)throws Exception;

    Map agencyLevelUpdateChangeData(Map data);

    AgentResult agencyLevelUpdateChange(Map data) throws Exception;

    Map<String,Object> packageParamUpdate(Map<String,Object> param);

    AgentResult httpRequestNetInUpdate(Map<String,Object> paramMap)throws Exception;

}
