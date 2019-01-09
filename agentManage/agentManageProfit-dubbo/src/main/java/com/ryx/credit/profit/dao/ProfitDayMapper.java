package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    BigDecimal totalMonthByAgentPid(ProfitDay record);

    BigDecimal totalRPByAgentPid(ProfitDay record);

    BigDecimal totalRPByAgentId(ProfitDay record);

    BigDecimal totalReturnByAgentPid(ProfitDay record);

    BigDecimal totalMonthByAgentId(ProfitDay record);

    BigDecimal totalProfitAndReturn(ProfitDay record);

    BigDecimal totalProfitAndReturnById(ProfitDay record);

    void deleteByDay(@Param("frDate") String frDate);

    List<Map<String,Object>> selectIncludePayComByExample(ProfitDayExample example);
}