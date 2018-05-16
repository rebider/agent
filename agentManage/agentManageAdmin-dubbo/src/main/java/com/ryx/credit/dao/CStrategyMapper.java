package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CStrategy;
import com.ryx.credit.pojo.admin.CStrategyExample;

import java.math.BigDecimal;
import java.util.List;

public interface CStrategyMapper {
    int countByExample(CStrategyExample example);

    int deleteByExample(CStrategyExample example);

    int insert(CStrategy record);

    int insertSelective(CStrategy record);

    List<CStrategy> selectByExample(CStrategyExample example);

    CStrategy selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CStrategy record);

    int updateByPrimaryKey(CStrategy record);
}