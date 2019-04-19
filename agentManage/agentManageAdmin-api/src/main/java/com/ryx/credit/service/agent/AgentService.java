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
    PageInfo queryAgentAll(Page page, Map map,Long userId);

    /**
     * 代理商新曾
     * @param agent
     * @param attrId
     * @return
     * @throws ProcessException
     */
    Agent insertAgent(Agent agent, List<String> attrId,String userId) throws ProcessException;

    Agent getAgentById(String id);

    List<CuserAgent>  queryByUserId(String userId);

    Agent queryAgentByUserId(String userId);

    AgentResult isAgent(String userId);

    int updateAgent(Agent agent);

    Agent updateAgentVo(Agent agent,List<String> attrs,String userId)throws Exception;

    Agent findAgentByActivId(String activId);

    void createBackUserbyAgent(String agentId);

    void createBackUserbyAgentByredis();

    int updateByPrimaryKeySelective(Agent record);

    /**
     * 检查代理商是否已经入网
     * @param agentId
     * @return
     */
    public AgentResult checkAgentIsIn(String agentId);

    PageInfo queryAgentTierList(Page page, Agent agent, Long userId);

    /**
     * 代理商解冻
     * @param agentId
     * @param cUser
     * @return
     * @throws Exception
     */
    AgentResult agentUnfreeze(String agentId, String cUser) throws Exception;


    void createUser(String agentId)throws Exception;

    List<Agent> getListByORGAndId(Map<String,String> map);
}
