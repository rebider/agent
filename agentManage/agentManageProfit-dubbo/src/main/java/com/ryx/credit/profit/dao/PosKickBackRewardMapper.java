package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosKickBackReward;
import com.ryx.credit.profit.pojo.PosKickBackRewardExample;

import java.util.List;
import java.util.Map;

public interface PosKickBackRewardMapper {
    long countByExample(PosKickBackRewardExample example);

    int deleteByExample(PosKickBackRewardExample example);

    int insert(PosKickBackReward record);

    int insertSelective(PosKickBackReward record);

    List<PosKickBackReward> selectByExample(PosKickBackRewardExample example);

    PosKickBackReward selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosKickBackReward record);

    int updateByPrimaryKey(PosKickBackReward record);

    Long  posKickbackRewardPageListCount(Map<String, Object> param);

    List<Map<String, Object>> posKickbackRewardPageList(Map<String, Object> param);

    List<Map<String, Object>> queryBusName();
}