package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import java.util.List;

public interface ProfitDetailMonthMapper {
    int countByExample(ProfitDetailMonthExample example);

    int deleteByExample(ProfitDetailMonthExample example);

    int insert(ProfitDetailMonth record);

    int insertSelective(ProfitDetailMonth record);

    List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example);

    ProfitDetailMonth selectByPrimaryKey(String id);

    ProfitDetailMonth selectByAgentPid(String agentPid);

    ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record);

    int updateByPrimaryKeySelective(ProfitDetailMonth record);

    int updateByPrimaryKey(ProfitDetailMonth record);
}