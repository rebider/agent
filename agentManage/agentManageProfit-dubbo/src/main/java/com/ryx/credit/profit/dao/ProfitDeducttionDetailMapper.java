package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProfitDeducttionDetailMapper {
    long countByExample(ProfitDeducttionDetailExample example);

    int deleteByExample(ProfitDeducttionDetailExample example);

    int insert(ProfitDeducttionDetail record);

    int insertSelective(ProfitDeducttionDetail record);

    List<ProfitDeducttionDetail> selectByExample(ProfitDeducttionDetailExample example);

    ProfitDeducttionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDeducttionDetail record);

    int updateByPrimaryKey(ProfitDeducttionDetail record);

    void clearComputData(@Param("profitDate") String profitDate, @Param("type")String type);
}