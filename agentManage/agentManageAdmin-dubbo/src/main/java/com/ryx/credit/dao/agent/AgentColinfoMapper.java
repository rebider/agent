package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfoExample;

import java.util.List;

public interface AgentColinfoMapper {
    int countByExample(AgentColinfoExample example);

    int deleteByExample(AgentColinfoExample example);

    int insert(AgentColinfo record);

    int insertSelective(AgentColinfo record);

    List<AgentColinfo> selectByExample(AgentColinfoExample example);

    AgentColinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentColinfo record);

    int updateByPrimaryKey(AgentColinfo record);

    List<AgentColinfo> proceedsQuery(String id);

    List<AgentColinfo> queryBusConinfoList(String busId);
}