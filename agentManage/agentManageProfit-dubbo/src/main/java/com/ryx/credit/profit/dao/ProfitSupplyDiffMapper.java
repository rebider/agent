package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
import com.ryx.credit.profit.pojo.ProfitSupplyDiffExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProfitSupplyDiffMapper {
    long countByExample(ProfitSupplyDiffExample example);

    int deleteByExample(ProfitSupplyDiffExample example);

    int insert(ProfitSupplyDiff record);

    int insertSelective(ProfitSupplyDiff record);

    List<ProfitSupplyDiff> selectByExample(ProfitSupplyDiffExample example);

    ProfitSupplyDiff selectByPrimaryKey(String id);

    BigDecimal selectAmtByWhere(ProfitSupplyDiff where);

    int updateByPrimaryKeySelective(ProfitSupplyDiff record);

    int updateByPrimaryKey(ProfitSupplyDiff record);

    void deleteByMonth(@Param("month") String month);
}