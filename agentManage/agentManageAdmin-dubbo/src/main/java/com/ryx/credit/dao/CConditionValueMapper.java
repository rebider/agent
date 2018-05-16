package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CConditionValue;
import com.ryx.credit.pojo.admin.CConditionValueExample;

import java.math.BigDecimal;
import java.util.List;

public interface CConditionValueMapper {
    int countByExample(CConditionValueExample example);

    int deleteByExample(CConditionValueExample example);

    int insert(CConditionValue record);

    int insertSelective(CConditionValue record);

    List<CConditionValue> selectByExample(CConditionValueExample example);

    CConditionValue selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CConditionValue record);

    int updateByPrimaryKey(CConditionValue record);
}