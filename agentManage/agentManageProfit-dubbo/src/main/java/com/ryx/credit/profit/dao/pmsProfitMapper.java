package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.pmsProfit;
import com.ryx.credit.profit.pojo.pmsProfitExample;

import java.util.List;

public interface pmsProfitMapper {
    long countByExample(pmsProfitExample example);

    int deleteByExample(pmsProfitExample example);

    int insert(pmsProfit record);

    int insertSelective(pmsProfit record);

    List<pmsProfit> selectByExample(pmsProfitExample example);

    pmsProfit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(pmsProfit record);

    int updateByPrimaryKey(pmsProfit record);
}