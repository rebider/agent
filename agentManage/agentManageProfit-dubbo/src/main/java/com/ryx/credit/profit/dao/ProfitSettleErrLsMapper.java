package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSettleErrLsExample;
import java.util.List;

public interface ProfitSettleErrLsMapper {
    int countByExample(ProfitSettleErrLsExample example);

    int deleteByExample(ProfitSettleErrLsExample example);

    int insert(ProfitSettleErrLs record);

    int insertSelective(ProfitSettleErrLs record);

    List<ProfitSettleErrLs> selectByExample(ProfitSettleErrLsExample example);

    ProfitSettleErrLs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitSettleErrLs record);

    int updateByPrimaryKey(ProfitSettleErrLs record);
}