package com.ryx.credit.service.agent.netInPort;

import com.ryx.credit.common.result.AgentResult;

import java.util.Map;

/**
 * Created by RYX on 2019/5/21.
 */
public interface AgentNetInHttpService {

    Map<String,Object> packageParam(Map<String,Object> param);

    AgentResult httpRequestNetIn(Map<String,Object> paramMap)throws Exception;
}
