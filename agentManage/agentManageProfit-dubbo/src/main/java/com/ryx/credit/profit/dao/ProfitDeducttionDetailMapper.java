package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetailExample;
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
}