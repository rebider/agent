package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;
import java.util.List;
import java.util.Map;

public interface ProfitSupplyMapper {
    int countByExample(ProfitSupplyExample example);

    int deleteByExample(ProfitSupplyExample example);

    int insert(ProfitSupply record);

    int insertSelective(ProfitSupply record);

    List<ProfitSupply> selectByExample(ProfitSupplyExample example);

    ProfitSupply selectByPrimaryKey(String id);

    ProfitSupply selectByAgentMonth(ProfitSupply record);

    int updateByPrimaryKeySelective(ProfitSupply record);

    int updateByPrimaryKey(ProfitSupply record);

    Long getProfitSupplyCount(Map<String, Object> param);

    Long getTotalByMonthAndPid(ProfitSupply record);

    List<Map<String,Object>> getProfitSupplyList(Map <String, Object> param);
}