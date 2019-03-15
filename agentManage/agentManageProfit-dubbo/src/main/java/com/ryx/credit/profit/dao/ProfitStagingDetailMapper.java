package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    /**
     * 根据任务id查询名称
     * @param taskId
     * @return
     */
   Map<String,Object> byTaskId(@Param("taskId") String taskId);

}