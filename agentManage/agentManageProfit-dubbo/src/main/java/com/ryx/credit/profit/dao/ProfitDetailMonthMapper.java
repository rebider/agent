package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;

import java.math.BigDecimal;
import java.util.List;

public interface ProfitDetailMonthMapper {
    int countByExample(ProfitDetailMonthExample example);

    int deleteByExample(ProfitDetailMonthExample example);

    int insert(ProfitDetailMonth record);

    int insertSelective(ProfitDetailMonth record);

    List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example);

    /**
     * 根据月份获取该月分润明细列表
     * @param profitDate
     * @return
     */
    List<ProfitDetailMonth> selectByDate(String profitDate);

    /**
     * 根据ids汇总他们的实际分润（税前计算时为基础分润汇总）
     * @param ids
     * @return
     */
    BigDecimal findByIds(List<String> ids);

    ProfitDetailMonth selectByPrimaryKey(String id);

    ProfitDetailMonth selectByAgentPid(String agentPid);

    ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record);

    int updateByPrimaryKeySelective(ProfitDetailMonth record);

    int updateByPrimaryKey(ProfitDetailMonth record);
}