package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitAdjust;
import com.ryx.credit.profit.pojo.ProfitAdjustExample;
import com.ryx.credit.profit.pojo.ProfitAdjustM;

import java.util.List;

public interface ProfitAdjustMapper {
    long countByExample(ProfitAdjustExample example);

    int deleteByExample(ProfitAdjustExample example);

    int insert(ProfitAdjust record);

    int insertSelective(ProfitAdjust record);

    List<ProfitAdjust> selectByExample(ProfitAdjustExample example);

    ProfitAdjust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitAdjust record);

    int updateByPrimaryKey(ProfitAdjust record);

    void insertAdjustM(ProfitAdjustM profitAdjustM);
}