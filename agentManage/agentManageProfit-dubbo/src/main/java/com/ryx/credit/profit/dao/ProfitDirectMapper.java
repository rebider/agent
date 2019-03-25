package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitDirectExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProfitDirectMapper {
    long countByExample(ProfitDirectExample example);

    int deleteByExample(ProfitDirectExample example);

    int insert(ProfitDirect record);

    int insertSelective(ProfitDirect record);

    List<ProfitDirect> selectByExample(ProfitDirectExample example);

    List<ProfitDirect> selectByMonth(ProfitDirect record);

    List<ProfitDirect> selectBySupply();

    List<ProfitDirect> selectByBuckle();

    ProfitDirect selectByPrimaryKey(String id);

    ProfitDirect selectByAgentAndMon(ProfitDirect record);

    int updateByPrimaryKeySelective(ProfitDirect record);

    int updateByPrimaryKey(ProfitDirect record);

    List<Map<String,Object>> getProfitDirectList(Map<String, Object> param);

    Long getProfitDirectCount(Map <String, Object> param);

    BigDecimal getSubBuckleByMonth(ProfitDirect record);

    BigDecimal selectSumTaxAmt(ProfitDirect record);


    //直发分润导出
    List<ProfitDirect> selectByWhere(Map<String,Object> param);

    void updateFristAgentStatus(String agentId);

    //直发分润查询
    List<ProfitDirect> selectByFristAgentPid(ProfitDirect profitDirect);

    //根据唯一码查询业务平台编码
    List<String> selectByAgUniqNum(String agUniqNum);

    //直发分润修改状态
    void  updateByStatus(ProfitDirect profitDirectSingleList);

    BigDecimal selectAmtByDeal(String transMonth);

    void deleteByMonth(@Param("transMonth") String transMonth);

    BigDecimal selectSumTaxAmt2(ProfitDirect dirct);

    Map<String,Object> profitCount(Map<String, Object> param);

    void clearComputData(@Param("profitDate") String profitDate);
}