package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.CapitalFlow;
import com.ryx.credit.pojo.admin.agent.CapitalFlowExample;

import java.util.List;

public interface CapitalFlowMapper {
    long countByExample(CapitalFlowExample example);

    int deleteByExample(CapitalFlowExample example);

    int insert(CapitalFlow record);

    int insertSelective(CapitalFlow record);

    List<CapitalFlow> selectByExample(CapitalFlowExample example);

    CapitalFlow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CapitalFlow record);

    int updateByPrimaryKey(CapitalFlow record);
}