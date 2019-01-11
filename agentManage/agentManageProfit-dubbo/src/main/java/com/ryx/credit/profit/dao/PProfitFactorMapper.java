package com.ryx.credit.profit.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.pojo.PProfitFactorExample;
import org.apache.ibatis.annotations.Param;

public interface PProfitFactorMapper {
    long countByExample(PProfitFactorExample example);

    BigDecimal getSumFactor(PProfitFactor record);

    int deleteByExample(PProfitFactorExample example);

    int insert(PProfitFactor record);

    int insertSelective(PProfitFactor record);

    List<PProfitFactor> selectByExample(PProfitFactorExample example);

    PProfitFactor selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PProfitFactor record, @Param("example") PProfitFactorExample example);

    int updateByExample(@Param("record") PProfitFactor record, @Param("example") PProfitFactorExample example);

    int updateByPrimaryKeySelective(PProfitFactor record);

    int updateByPrimaryKey(PProfitFactor record);


    List<Map<String,Object>> getProfitFactorList(Map <String, Object> param);

    Long getProfitFactorCount(Map <String, Object> param);

    PProfitFactor selectByData(PProfitFactor profitFactor);

    int resetDataFactor();

    Map<String, Object> profitCount(Map<String, Object> param);
}