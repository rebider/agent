package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfoExample;

import java.util.List;

public interface AgentMergeBusInfoMapper {
    long countByExample(AgentMergeBusInfoExample example);

    int deleteByExample(AgentMergeBusInfoExample example);

    int insert(AgentMergeBusInfo record);

    int insertSelective(AgentMergeBusInfo record);

    List<AgentMergeBusInfo> selectByExample(AgentMergeBusInfoExample example);

    AgentMergeBusInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentMergeBusInfo record);

    int updateByPrimaryKey(AgentMergeBusInfo record);
}