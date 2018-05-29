package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;

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
}