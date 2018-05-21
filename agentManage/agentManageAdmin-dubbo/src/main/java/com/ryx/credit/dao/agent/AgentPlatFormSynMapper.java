package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSynExample;

import java.util.List;

public interface AgentPlatFormSynMapper {
    int countByExample(AgentPlatFormSynExample example);

    int deleteByExample(AgentPlatFormSynExample example);

    int insert(AgentPlatFormSyn record);

    int insertSelective(AgentPlatFormSyn record);

    List<AgentPlatFormSyn> selectByExample(AgentPlatFormSynExample example);

    AgentPlatFormSyn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentPlatFormSyn record);

    int updateByPrimaryKey(AgentPlatFormSyn record);
}