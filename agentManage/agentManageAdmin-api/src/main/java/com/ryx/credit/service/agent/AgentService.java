package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.agent.Agent;

import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/23.
 */
public interface AgentService {
    /**
     * 查询代理商信息
     * @param page
     * @param agent
     * @return
     */
    PageInfo queryAgentList(PageInfo page, Agent agent);
    PageInfo queryAgentAll(Page page, Map map);

    /**
     * 代理商新曾
     * @param agent
     * @param attrId
     * @return
     * @throws ProcessException
     */
    Agent insertAgent(Agent agent, List<String> attrId) throws ProcessException;

    Agent getAgentById(String id);

    List<CuserAgent>  queryByUserId(String userId);

    Agent queryAgentByUserId(String userId);

    AgentResult isAgent(String userId);

    int updateAgent(Agent agent);

    Agent updateAgentVo(Agent agent,List<String> attrs)throws Exception;

    Agent findAgentByActivId(String activId);

    void createBackUserbyAgent(String agentId);

    void createBackUserbyAgentByredis();

}
