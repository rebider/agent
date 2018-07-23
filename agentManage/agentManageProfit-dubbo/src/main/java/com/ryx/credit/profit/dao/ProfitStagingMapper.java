package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.pojo.ProfitStagingExample;
import java.util.List;

public interface ProfitStagingMapper {
    int countByExample(ProfitStagingExample example);

    int deleteByExample(ProfitStagingExample example);

    int insert(ProfitStaging record);

    int insertSelective(ProfitStaging record);

    List<ProfitStaging> selectByExample(ProfitStagingExample example);

    ProfitStaging selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitStaging record);

    int updateByPrimaryKey(ProfitStaging record);
}