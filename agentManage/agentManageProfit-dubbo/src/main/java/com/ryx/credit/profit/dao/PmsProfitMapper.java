package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PmsProfit;
import com.ryx.credit.profit.pojo.PmsProfitExample;

import java.util.List;

public interface PmsProfitMapper {
    long countByExample(PmsProfitExample example);

    int deleteByExample(PmsProfitExample example);

    int insert(PmsProfit record);

    int insertSelective(PmsProfit record);

    List<PmsProfit> selectByExample(PmsProfitExample example);

    PmsProfit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProfit record);

    int updateByPrimaryKey(PmsProfit record);
}