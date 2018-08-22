package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitDirectExample;

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
    /*List<ProfitDirect> selectByWhere(ProfitDirect record);*/

    void updateFristAgentStatus(String agentPid);

    //直发分润查询
    List<ProfitDirect> selectByFristAgentPid(ProfitDirect profitDirect);
    //直发分润修改状态
    void  updateByStatus(ProfitDirect profitDirectSingleList);
}