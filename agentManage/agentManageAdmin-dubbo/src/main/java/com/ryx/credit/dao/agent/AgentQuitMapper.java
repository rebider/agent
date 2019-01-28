package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.pojo.admin.agent.AgentQuitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentQuitMapper {
    long countByExample(AgentQuitExample example);

    int deleteByExample(AgentQuitExample example);

    int insert(AgentQuit record);

    int insertSelective(AgentQuit record);

    List<AgentQuit> selectByExample(AgentQuitExample example);

    AgentQuit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentQuit record);

    int updateByPrimaryKey(AgentQuit record);

    List<Map<String, Object>> queryAgentQuitList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryAgentQuitCount(@Param("map") Map<String, Object> map);
}