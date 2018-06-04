package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 代理商新曾
     * @param agent
     * @param attrId
     * @return
     * @throws ProcessException
     */
    Agent insertAgent(Agent agent, List<String> attrId) throws ProcessException;

    Agent getAgentById(String id);

    int updateAgent(Agent agent);

    Agent updateAgentVo(Agent agent)throws Exception;





}
