package com.ryx.credit.profit.dao;

import java.util.List;
import java.util.Map;

import com.ryx.credit.profit.pojo.ProfitSupplyTax;
import com.ryx.credit.profit.pojo.ProfitSupplyTaxExample;
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

    List<Map<String,Object>> getZqTaxAgents(Map<String,Object> params);

    List<Map<String,Object>> getZfInvoiceAgents(Map<String,Object> params);

    List<Map<String,Object>> getZfTaxAgents(Map<String,Object> params);


    List<Map<String, Object>> getClassificationList(Map<String, Object> param);

    long getClassificationCount(Map<String, Object> param);

    void deleteByMonth(@Param("profitMonth") String profitMonth);

    Map<String,Object> profitCountWithSubordinate(Map<String,Object> param);

    Map<String,Object>profitCount(Map<String,Object> param);
}