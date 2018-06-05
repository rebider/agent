package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRelExample;

import java.util.List;

public interface AgentColinfoRelMapper {
    int countByExample(AgentColinfoRelExample example);

    int deleteByExample(AgentColinfoRelExample example);

    int insert(AgentColinfoRel record);

    int insertSelective(AgentColinfoRel record);

    List<AgentColinfoRel> selectByExample(AgentColinfoRelExample example);

    AgentColinfoRel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentColinfoRel record);

    int updateByPrimaryKey(AgentColinfoRel record);
}