package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.AgentMergeExample;

import java.util.List;

public interface AgentMergeMapper {
    long countByExample(AgentMergeExample example);

    int deleteByExample(AgentMergeExample example);

    int insert(AgentMerge record);

    int insertSelective(AgentMerge record);

    List<AgentMerge> selectByExample(AgentMergeExample example);

    AgentMerge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentMerge record);

    int updateByPrimaryKey(AgentMerge record);
}