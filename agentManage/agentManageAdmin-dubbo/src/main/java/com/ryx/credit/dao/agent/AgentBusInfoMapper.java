package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentBusInfoMapper {
    int countByExample(AgentBusInfoExample example);

    int deleteByExample(AgentBusInfoExample example);

    int insert(AgentBusInfo record);

    int insertSelective(AgentBusInfo record);

    List<AgentBusInfo> selectByExample(AgentBusInfoExample example);

    AgentBusInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentBusInfo record);

    int updateByPrimaryKey(AgentBusInfo record);

    List<Map<String,Object>> queryAgentBusList(@Param("par") Map<String,Object> par);

    long queryAgentBusListCount(@Param("par") Map<String,Object> par);

}