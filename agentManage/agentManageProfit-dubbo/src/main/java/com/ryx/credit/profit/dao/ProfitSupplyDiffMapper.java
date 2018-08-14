package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
import com.ryx.credit.profit.pojo.ProfitSupplyDiffExample;
import java.util.List;

public interface ProfitSupplyDiffMapper {
    long countByExample(ProfitSupplyDiffExample example);

    int deleteByExample(ProfitSupplyDiffExample example);

    int insert(ProfitSupplyDiff record);

    int insertSelective(ProfitSupplyDiff record);

    List<ProfitSupplyDiff> selectByExample(ProfitSupplyDiffExample example);

    ProfitSupplyDiff selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitSupplyDiff record);

    int updateByPrimaryKey(ProfitSupplyDiff record);
}