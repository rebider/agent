package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.agent.AgentFreezeExample;

import java.util.List;

public interface AgentFreezeMapper {
    long countByExample(AgentFreezeExample example);

    int deleteByExample(AgentFreezeExample example);

    int insert(AgentFreeze record);

    int insertSelective(AgentFreeze record);

    List<AgentFreeze> selectByExample(AgentFreezeExample example);

    AgentFreeze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentFreeze record);

    int updateByPrimaryKey(AgentFreeze record);
}