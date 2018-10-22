package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitAdjustM;
import com.ryx.credit.profit.pojo.ProfitAdjustMExample;
import java.util.List;

public interface ProfitAdjustMMapper {
    long countByExample(ProfitAdjustMExample example);

    int deleteByExample(ProfitAdjustMExample example);

    int insert(ProfitAdjustM record);

    int insertSelective(ProfitAdjustM record);

    List<ProfitAdjustM> selectByExample(ProfitAdjustMExample example);
}