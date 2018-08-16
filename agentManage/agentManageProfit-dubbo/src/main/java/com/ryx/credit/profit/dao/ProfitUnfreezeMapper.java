package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitUnfreeze;
import com.ryx.credit.profit.pojo.ProfitUnfreezeExample;
import java.util.List;

public interface ProfitUnfreezeMapper {
    long countByExample(ProfitUnfreezeExample example);

    int deleteByExample(ProfitUnfreezeExample example);

    int insert(ProfitUnfreeze record);

    int insertSelective(ProfitUnfreeze record);

    List<ProfitUnfreeze> selectByExample(ProfitUnfreezeExample example);

    ProfitUnfreeze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitUnfreeze record);

    int updateByPrimaryKey(ProfitUnfreeze record);
}