package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentContract;
import com.ryx.credit.pojo.admin.agent.AgentContractExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentContractMapper {
    int countByExample(AgentContractExample example);

    int deleteByExample(AgentContractExample example);

    int insert(AgentContract record);

    int insertSelective(AgentContract record);

    List<AgentContract> selectByExample(AgentContractExample example);

    AgentContract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentContract record);

    int updateByPrimaryKey(AgentContract record);

    List<AgentContract> compactQuery(String id);

    List<Map<String, Object>> getAgentContractList(@Param("map")Map<String, Object> map, @Param("page")Page page);

    int getAgentContractCount(@Param("map")Map<String, Object> map);
}