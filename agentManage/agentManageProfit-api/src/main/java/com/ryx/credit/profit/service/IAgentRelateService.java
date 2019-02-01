package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateDetail;
import com.ryx.credit.profit.pojo.AgentRelateExample;

import java.util.List;
import java.util.Map;

/**
 * @author renshenghao
 * @Description: 代理商机具扣款关联关系
 * @date 2019/01/24
 */
public interface IAgentRelateService {

    PageInfo getList(Map<String,Object> param,PageInfo pageInfo);

    int insertAgentRelate(AgentRelate agentRelate);

    int updateAgentRelate(AgentRelate agentRelate);

    List<AgentRelate> selectByExample(AgentRelateExample agentRelateExample);

    AgentRelate selectById(String id);

    Map<String,String> queryParentAgentByAgentId(String agentId);

    boolean applyAgentRelate(AgentRelate agentRelate, List<AgentRelateDetail> list, String userId, String workId);
}
