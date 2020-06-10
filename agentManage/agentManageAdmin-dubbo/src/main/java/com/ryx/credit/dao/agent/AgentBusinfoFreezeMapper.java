package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentBusinfoFreeze;
import com.ryx.credit.pojo.admin.agent.AgentBusinfoFreezeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentBusinfoFreezeMapper {
    long countByExample(AgentBusinfoFreezeExample example);

    int deleteByExample(AgentBusinfoFreezeExample example);

    int insert(AgentBusinfoFreeze record);

    int insertSelective(AgentBusinfoFreeze record);

    List<AgentBusinfoFreeze> selectByExample(AgentBusinfoFreezeExample example);

    AgentBusinfoFreeze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentBusinfoFreeze record);

    int updateByPrimaryKey(AgentBusinfoFreeze record);

    List queryAbfreezeListView(@Param("map")Map map, @Param("page")Page page);

    int queryAbfreezeListCount(@Param("map")Map map);

    List<AgentBusinfoFreeze> queryAgentBusFreeze(@Param("map")Map map);

    int deleteByAgentId(String agentId);
}