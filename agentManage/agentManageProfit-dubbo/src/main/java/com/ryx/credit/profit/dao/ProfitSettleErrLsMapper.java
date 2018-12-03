package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSettleErrLsExample;
import java.util.List;
import java.util.Map;

public interface ProfitSettleErrLsMapper {
    int countByExample(ProfitSettleErrLsExample example);

    int deleteByExample(ProfitSettleErrLsExample example);

    int insert(ProfitSettleErrLs record);

    int insertSelective(ProfitSettleErrLs record);

    List<ProfitSettleErrLs> selectByExample(ProfitSettleErrLsExample example);

    ProfitSettleErrLs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitSettleErrLs record);

    int updateByPrimaryKey(ProfitSettleErrLs record);

    List<ProfitSettleErrLs> getNotDeductionProfitSettleErrLsList(Map<String, Object> param);

    List<ProfitSettleErrLs> getNotSupplyProfitSettleErrLsList(Map<String, Object> param);
}