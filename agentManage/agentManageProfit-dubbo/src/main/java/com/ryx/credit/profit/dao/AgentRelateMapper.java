package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AgentRelateMapper {
    long countByExample(AgentRelateExample example);

    int deleteByExample(AgentRelateExample example);

    int insert(AgentRelate record);

    int insertSelective(AgentRelate record);

    List<AgentRelate> selectByExample(AgentRelateExample example);

    AgentRelate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AgentRelate record, @Param("example") AgentRelateExample example);

    int updateByExample(@Param("record") AgentRelate record, @Param("example") AgentRelateExample example);

    int updateByPrimaryKeySelective(AgentRelate record);

    int updateByPrimaryKey(AgentRelate record);

    List<Map<String, Object>> getList(Map<String, Object> param);

    Map<String,String> queryParentAgentByAgentId(String agentId);
}