package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;
import java.util.List;

public interface ProfitDayMapper {

    int countByExample(ProfitDayExample example);

    int deleteByExample(ProfitDayExample example);

    int insert(ProfitDay record);

    int insertSelective(ProfitDay record);

    List<ProfitDay> selectByExample(ProfitDayExample example);

    List<ProfitDay> selectByWhere(ProfitDay record);

    ProfitDay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDay record);

    int updateByPrimaryKey(ProfitDay record);

    long totalMonthByAgentPid(ProfitDay record);
}