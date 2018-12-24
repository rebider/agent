package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;
import com.ryx.credit.profit.pojo.TaxDeductionDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaxDeductionDetailMapper {
    long countByExample(TaxDeductionDetailExample example);

    int deleteByExample(TaxDeductionDetailExample example);

    int insert(TaxDeductionDetail record);

    int insertSelective(TaxDeductionDetail record);

    List<TaxDeductionDetail> selectByExample(TaxDeductionDetailExample example);

    TaxDeductionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaxDeductionDetail record);

    int updateByPrimaryKey(TaxDeductionDetail record);

    List<TaxDeductionDetail> queryAndSubordinate(@Param("detail") TaxDeductionDetail taxDeductionDetail, @Param("page") Page page);

    long queryCountAndSubordinate(TaxDeductionDetail taxDeductionDetail);
}