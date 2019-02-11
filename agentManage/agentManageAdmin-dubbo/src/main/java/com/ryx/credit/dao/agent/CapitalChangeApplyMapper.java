package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.CapitalChangeApply;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApplyExample;

import java.util.List;

public interface CapitalChangeApplyMapper {
    long countByExample(CapitalChangeApplyExample example);

    int deleteByExample(CapitalChangeApplyExample example);

    int insert(CapitalChangeApply record);

    int insertSelective(CapitalChangeApply record);

    List<CapitalChangeApply> selectByExample(CapitalChangeApplyExample example);

    CapitalChangeApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CapitalChangeApply record);

    int updateByPrimaryKey(CapitalChangeApply record);
}