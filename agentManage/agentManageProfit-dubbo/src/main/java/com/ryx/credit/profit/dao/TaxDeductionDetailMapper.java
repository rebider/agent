package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;
import com.ryx.credit.profit.pojo.TaxDeductionDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaxDeductionDetailMapper {
    long countByExample(TaxDeductionDetailExample example);

    int deleteByExample(TaxDeductionDetailExample example);

    int insert(TaxDeductionDetail record);

    int insertSelective(TaxDeductionDetail record);

    List<TaxDeductionDetail> selectByExample(TaxDeductionDetailExample example);


    TaxDeductionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaxDeductionDetail record);

    int updateByPrimaryKey(TaxDeductionDetail record);

    List<Map<String,Object>> queryTaxDeductionAgentList(Map<String,Object> params);

    List<TaxDeductionDetail> queryAndSubordinate(@Param("detail") TaxDeductionDetail taxDeductionDetail, @Param("page") Page page);

    long queryCountAndSubordinate(TaxDeductionDetail taxDeductionDetail);

    void deleteByMonth(@Param("profitMonth") String profitMonth);

    List<Map<String,Object>> queryTaxDeductionZFAgentList(Map<String,Object> params);

    Map<String, Object> profitCount(TaxDeductionDetailExample example);

    Map<String, Object> profitCountWithSubordinate(Map<String, Object> param);
}