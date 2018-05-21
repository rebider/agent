package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentContract;
import com.ryx.credit.pojo.admin.agent.AgentContractExample;
import com.ryx.credit.pojo.admin.agent.AgentContractKey;

import java.util.List;

public interface AgentContractMapper {
    int countByExample(AgentContractExample example);

    int deleteByExample(AgentContractExample example);

    int insert(AgentContract record);

    int insertSelective(AgentContract record);

    List<AgentContract> selectByExample(AgentContractExample example);

    AgentContract selectByPrimaryKey(AgentContractKey key);

    int updateByPrimaryKeySelective(AgentContract record);

    int updateByPrimaryKey(AgentContract record);
}