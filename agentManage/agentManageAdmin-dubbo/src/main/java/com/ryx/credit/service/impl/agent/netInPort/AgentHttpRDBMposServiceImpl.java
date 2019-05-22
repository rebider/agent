package com.ryx.credit.service.impl.agent.netInPort;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 17:17
 * @Param
 * @return
 **/
@Service("agentHttpRDBMposServiceImpl")
public class AgentHttpRDBMposServiceImpl implements AgentNetInHttpService{


    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {
        return null;
    }

    @Override
    public AgentResult httpRequestNetIn(Map<String, Object> paramMap) throws Exception {
        return null;
    }

    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        return null;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        return null;
    }


}
