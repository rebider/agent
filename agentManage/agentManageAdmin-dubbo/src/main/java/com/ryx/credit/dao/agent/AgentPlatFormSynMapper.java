package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSynExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentPlatFormSynMapper {

    int countByExample(AgentPlatFormSynExample example);

    int deleteByExample(AgentPlatFormSynExample example);

    int insert(AgentPlatFormSyn record);

    int insertSelective(AgentPlatFormSyn record);

    List<AgentPlatFormSyn> selectByExampleWithBLOBs(AgentPlatFormSynExample example);

    List<AgentPlatFormSyn> selectByExample(AgentPlatFormSynExample example);

    AgentPlatFormSyn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentPlatFormSyn record);

    int updateByPrimaryKeyWithBLOBs(AgentPlatFormSyn record);

    int updateByPrimaryKey(AgentPlatFormSyn record);

    List<Map<String,Object>> queryList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryCount(@Param("map")Map<String, Object> map);

    int updateByBusId(AgentPlatFormSyn record);
}