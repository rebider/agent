package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProfitDeductionMapper {
    int countByExample(ProfitDeductionExample example);

    int deleteByExample(ProfitDeductionExample example);

    void deleteById(String id);

    int insert(ProfitDeduction record);

    int insertSelective(ProfitDeduction record);

    BigDecimal totalBuckleByMonth(ProfitDeduction profitDeduction);

    BigDecimal getSettleErrDeductionAmt(ProfitDeduction profitDeduction);

    List<ProfitDeduction> selectByExample(ProfitDeductionExample example);

    ProfitDeduction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDeduction record);

    int updateByPrimaryKey(ProfitDeduction record);

    List<Map<String, Object>> getDeductDetail(String deductDate);

    BigDecimal getNotDeductionSum(String agentId);

    List<Map<String,Object>> getNotDeductDetail(@Param("beforeDeductDate") String beforeDeductDate, @Param("deductDate") String deductDate, @Param("type") String type);

    BigDecimal getCurrentDeductionAmtSum(ProfitDeduction profitDeduction);

    int resetDataDeduction(@Param("type") String type,@Param("date") String date);

    List<ProfitDeduction> selectDeductListByParams(Map<String,Object> param);

    /**获取未扣款**/
    BigDecimal getNotDeductionAmt(Map<String,String> param);

    /**查看代理商关联扣款*/
    List<Map<String,Object>> getRev1List(Map<String,String> param);

    /**查看代理商担保扣款*/
    List<Map<String,Object>> getRev2List(Map<String,String> param);

    void clearComputData(@Param("profitDate") String profitDate, @Param("decutionType") String decutionType);

    List<Map<String, Object>> getPorfitDataByAgentIdAndProfitMonth(@Param("agentId") String agentId, @Param("profitMonth") String profitMonth);
    /**获取机具扣款欠款*/
    BigDecimal getToolsDebt(Map<String,String> param);

    List<Map<String,String>> selectByExampleToolSupply(ProfitDeductionExample example);

}