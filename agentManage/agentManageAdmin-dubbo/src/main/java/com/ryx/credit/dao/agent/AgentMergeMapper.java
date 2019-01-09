package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.AgentMergeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentMergeMapper {
    long countByExample(AgentMergeExample example);

    int deleteByExample(AgentMergeExample example);

    int insert(AgentMerge record);

    int insertSelective(AgentMerge record);

    List<AgentMerge> selectByExample(AgentMergeExample example);

    AgentMerge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentMerge record);

    int updateByPrimaryKey(AgentMerge record);

    List<Map<String,Object>> selectAgentMergeList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int selectAgentMergeCount(@Param("map") Map<String, Object> map);
}