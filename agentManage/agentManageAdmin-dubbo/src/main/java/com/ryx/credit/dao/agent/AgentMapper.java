package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentMapper {
    int countByExample(AgentExample example);

    int deleteByExample(AgentExample example);

    int insert(Agent record);

    int insertSelective(Agent record);

    List<Agent> selectByExample(AgentExample example);

    Agent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    List<Map<String,Object>> queryDeptName(String agDocPro);

    List<Map<String,Object>> queryDeptNameDis(String agDocDistrict);

    List<AgentoutVo> excelAgent( @Param("agent")Agent agent);

    int queryAgentListViewCount(Map<String,Object> map);

    List<Map<String,Object>> queryAgentListView(Map<String,Object> map);
}