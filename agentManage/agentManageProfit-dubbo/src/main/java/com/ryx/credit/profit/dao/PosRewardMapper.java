package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosReward;
import com.ryx.credit.profit.pojo.PosRewardExample;

import java.util.List;

public interface PosRewardMapper {
    long countByExample(PosRewardExample example);

    int deleteByExample(PosRewardExample example);

    int insert(PosReward record);

    int insertSelective(PosReward record);

    List<PosReward> selectByExample(PosRewardExample example);

    PosReward selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosReward record);

    int updateByPrimaryKey(PosReward record);

    List<PosReward> selectByMonth(PosReward posReward);

    /**
     * 查询此交易月份是否已申请
     * @param verifyMonth
     * @return
     */
//    PosReward selectByVerifyMonth(String verifyMonth);
}