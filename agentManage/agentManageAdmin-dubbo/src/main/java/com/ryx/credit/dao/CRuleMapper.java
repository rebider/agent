package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CRule;
import com.ryx.credit.pojo.admin.CRuleExample;

import java.math.BigDecimal;
import java.util.List;

public interface CRuleMapper {
    int countByExample(CRuleExample example);

    int deleteByExample(CRuleExample example);

    int insert(CRule record);

    int insertSelective(CRule record);

    List<CRule> selectByExample(CRuleExample example);

    CRule selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRule record);

    int updateByPrimaryKey(CRule record);
}