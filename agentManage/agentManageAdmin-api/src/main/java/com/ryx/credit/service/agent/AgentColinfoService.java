package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cx on 2018/5/28.
 * com.ryx.credit.service.agent.AgentColinfoService
 */
public interface AgentColinfoService {
    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att)throws ProcessException;

    AgentResult saveAgentColinfoRel(AgentColinfoRel agentColinfoRel,String cUser)throws Exception;

    public List<AgentColinfo> queryAgentColinfoService(String agentId,String colId,BigDecimal appStatus);

    public int update(AgentColinfo a);
}
