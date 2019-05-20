package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosReward;
import com.ryx.credit.profit.pojo.PosRewardExample;

import java.util.List;
import java.util.Map;

public interface PosRewardMapper {
    long countByExample(PosRewardExample example);

    int deleteByExample(PosRewardExample example);

    int insert(PosReward record);

    int insertSelective(PosReward record);

    List<PosReward> selectByExample(PosRewardExample example);

    //更新按照考核月查询pos奖励申请
    List<PosReward> selectByEndMonth(PosRewardExample example);

    PosReward selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosReward record);

    int updateByPrimaryKey(PosReward record);

    List<Map<String, Object>> huddlePos(Map<String, Object> param);

    Map<String, Object> selectById(String id);

    Map<String, Object> selectByActiv(String activId);

    Map<String, Object> selectByTaskId(String taskId);

    List<PosReward> selectPosRewardByParams(Map<String,Object> posRewardPrams);
}