package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CRuleCondition;
import com.ryx.credit.pojo.admin.CRuleConditionExample;

import java.math.BigDecimal;
import java.util.List;

public interface CRuleConditionMapper {
    int countByExample(CRuleConditionExample example);

    int deleteByExample(CRuleConditionExample example);

    int insert(CRuleCondition record);

    int insertSelective(CRuleCondition record);

    List<CRuleCondition> selectByExample(CRuleConditionExample example);

    CRuleCondition selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRuleCondition record);

    int updateByPrimaryKey(CRuleCondition record);
}