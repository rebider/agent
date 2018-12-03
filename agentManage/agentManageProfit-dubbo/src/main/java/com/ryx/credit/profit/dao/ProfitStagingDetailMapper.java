package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetailExample;
import java.util.List;

public interface ProfitStagingDetailMapper {
    int countByExample(ProfitStagingDetailExample example);

    int deleteByExample(ProfitStagingDetailExample example);

    int insert(ProfitStagingDetail record);

    int insertSelective(ProfitStagingDetail record);

    List<ProfitStagingDetail> selectByExample(ProfitStagingDetailExample example);

    ProfitStagingDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitStagingDetail record);

    int updateByPrimaryKey(ProfitStagingDetail record);

    ProfitStagingDetail getNextStagAmt(ProfitStagingDetail profitStagingDetail);
}