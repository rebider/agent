package com.ryx.credit.profit.dao;

import java.util.List;

import com.ryx.credit.profit.pojo.AgentRelateDetail;
import com.ryx.credit.profit.pojo.AgentRelateDetailExample;
import org.apache.ibatis.annotations.Param;

public interface AgentRelateDetailMapper {
    long countByExample(AgentRelateDetailExample example);

    int deleteByExample(AgentRelateDetailExample example);

    int insert(AgentRelateDetail record);

    int insertSelective(AgentRelateDetail record);

    List<AgentRelateDetail> selectByExample(AgentRelateDetailExample example);

    AgentRelateDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AgentRelateDetail record, @Param("example") AgentRelateDetailExample example);

    int updateByExample(@Param("record") AgentRelateDetail record, @Param("example") AgentRelateDetailExample example);

    int updateByPrimaryKeySelective(AgentRelateDetail record);

    int updateByPrimaryKey(AgentRelateDetail record);
}