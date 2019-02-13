package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApply;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CapitalChangeApplyMapper {
    long countByExample(CapitalChangeApplyExample example);

    int deleteByExample(CapitalChangeApplyExample example);

    int insert(CapitalChangeApply record);

    int insertSelective(CapitalChangeApply record);

    List<CapitalChangeApply> selectByExample(CapitalChangeApplyExample example);

    CapitalChangeApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CapitalChangeApply record);

    int updateByPrimaryKey(CapitalChangeApply record);

    List<Map<String, Object>> queryCapitalChangeList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryCapitalChangeCount(@Param("map") Map<String, Object> map);
}