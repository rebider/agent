package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.pojo.PProfitFactorExample;
import java.util.List;
import java.util.Map;

public interface PProfitFactorMapper {
    long countByExample(PProfitFactorExample example);

    long getSumFactor(PProfitFactor record);

    int deleteByExample(PProfitFactorExample example);

    int insert(PProfitFactor record);

    int insertSelective(PProfitFactor record);

    List<PProfitFactor> selectByExample(PProfitFactorExample example);

    PProfitFactor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PProfitFactor record);

    int updateByPrimaryKey(PProfitFactor record);


    List<Map<String,Object>> getProfitFactorList(Map <String, Object> param);

    Long getProfitFactorCount(Map <String, Object> param);

    PProfitFactor selectByData(PProfitFactor profitFactor);
}