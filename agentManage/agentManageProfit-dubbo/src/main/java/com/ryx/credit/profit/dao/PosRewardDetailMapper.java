package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.pojo.PosRewardDetailExample;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface PosRewardDetailMapper {
    long countByExample(PosRewardDetailExample example);

    int deleteByExample(PosRewardDetailExample example);

    int insert(PosRewardDetail record);

    int insertSelective(PosRewardDetail record);

    List<PosRewardDetail> selectByExampleWithBLOBs(PosRewardDetailExample example);

    List<PosRewardDetail> selectByExample(PosRewardDetailExample example);

    PosRewardDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PosRewardDetail record, @Param("example") PosRewardDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") PosRewardDetail record, @Param("example") PosRewardDetailExample example);

    int updateByExample(@Param("record") PosRewardDetail record, @Param("example") PosRewardDetailExample example);

    int updateByPrimaryKeySelective(PosRewardDetail record);

    int updateByPrimaryKeyWithBLOBs(PosRewardDetail record);

    int updateByPrimaryKey(PosRewardDetail record);

    long getRewardDetailCount(Map<String, Object> param);

    List<Map<String, Object>> getRewardDetailList(Map<String, Object> param);

    void updateRewradData(String profitDate);

    List<String> queryChildLevelByAgentId(String agentId);

    String querySuperAgentId(String agentId);
}