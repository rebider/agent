package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.ProfitMonthExample;
import java.util.List;

public interface ProfitMonthMapper {
    int countByExample(ProfitMonthExample example);

    int deleteByExample(ProfitMonthExample example);

    int insert(ProfitMonth record);

    int insertSelective(ProfitMonth record);

    List<ProfitMonth> selectByExample(ProfitMonthExample example);

    ProfitMonth selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitMonth record);

    int updateByPrimaryKey(ProfitMonth record);
}