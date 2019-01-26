package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.pojo.admin.agent.AgentQuitExample;

import java.util.List;

public interface AgentQuitMapper {
    long countByExample(AgentQuitExample example);

    int deleteByExample(AgentQuitExample example);

    int insert(AgentQuit record);

    int insertSelective(AgentQuit record);

    List<AgentQuit> selectByExample(AgentQuitExample example);

    AgentQuit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentQuit record);

    int updateByPrimaryKey(AgentQuit record);
}