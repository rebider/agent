package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CCondition;
import com.ryx.credit.pojo.admin.CConditionExample;

import java.math.BigDecimal;
import java.util.List;

public interface CConditionMapper {
    int countByExample(CConditionExample example);

    int deleteByExample(CConditionExample example);

    int insert(CCondition record);

    int insertSelective(CCondition record);

    List<CCondition> selectByExample(CConditionExample example);

    CCondition selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CCondition record);

    int updateByPrimaryKey(CCondition record);
}