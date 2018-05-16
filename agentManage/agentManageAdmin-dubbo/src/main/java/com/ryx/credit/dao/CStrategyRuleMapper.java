package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CStrategyRule;
import com.ryx.credit.pojo.admin.CStrategyRuleExample;

import java.math.BigDecimal;
import java.util.List;

public interface CStrategyRuleMapper {
    int countByExample(CStrategyRuleExample example);

    int deleteByExample(CStrategyRuleExample example);

    int insert(CStrategyRule record);

    int insertSelective(CStrategyRule record);

    List<CStrategyRule> selectByExample(CStrategyRuleExample example);

    CStrategyRule selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CStrategyRule record);

    int updateByPrimaryKey(CStrategyRule record);
}