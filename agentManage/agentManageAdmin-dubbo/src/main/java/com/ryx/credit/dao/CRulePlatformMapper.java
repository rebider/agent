package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CRulePlatform;
import com.ryx.credit.pojo.admin.CRulePlatformExample;

import java.math.BigDecimal;
import java.util.List;

public interface CRulePlatformMapper {
    int countByExample(CRulePlatformExample example);

    int deleteByExample(CRulePlatformExample example);

    int insert(CRulePlatform record);

    int insertSelective(CRulePlatform record);

    List<CRulePlatform> selectByExample(CRulePlatformExample example);

    CRulePlatform selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRulePlatform record);

    int updateByPrimaryKey(CRulePlatform record);
}