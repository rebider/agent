package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupplyTax;
import com.ryx.credit.profit.pojo.ProfitSupplyTaxExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProfitSupplyTaxMapper {
    long countByExample(ProfitSupplyTaxExample example);

    int deleteByExample(ProfitSupplyTaxExample example);

    int insert(ProfitSupplyTax record);

    int insertSelective(ProfitSupplyTax record);

    List<ProfitSupplyTax> selectByExample(ProfitSupplyTaxExample example);

    int updateByExampleSelective(@Param("record") ProfitSupplyTax record, @Param("example") ProfitSupplyTaxExample example);

    int updateByExample(@Param("record") ProfitSupplyTax record, @Param("example") ProfitSupplyTaxExample example);

    long getProfitSupplyTaxCount(Map<String, Object> param);

    List<Map<String, Object>> getProfitSupplyTaxList(Map<String, Object> param);
}