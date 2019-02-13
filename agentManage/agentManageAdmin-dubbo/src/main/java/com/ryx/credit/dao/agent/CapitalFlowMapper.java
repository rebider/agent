package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;
import com.ryx.credit.pojo.admin.agent.CapitalFlowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CapitalFlowMapper {
    long countByExample(CapitalFlowExample example);

    int deleteByExample(CapitalFlowExample example);

    int insert(CapitalFlow record);

    int insertSelective(CapitalFlow record);

    List<CapitalFlow> selectByExample(CapitalFlowExample example);

    CapitalFlow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CapitalFlow record);

    int updateByPrimaryKey(CapitalFlow record);

    List<Map<String, Object>> queryCapitalFlowList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryCapitalFlowCount(@Param("map") Map<String, Object> map);
}