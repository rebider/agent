package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PPosHuddleReward;
import com.ryx.credit.profit.pojo.PPosHuddleRewardExample;
import com.ryx.credit.profit.pojo.PosHuddleRewardDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PPosHuddleRewardMapper {
    long countByExample(PPosHuddleRewardExample example);

    int deleteByExample(PPosHuddleRewardExample example);

    int insert(PPosHuddleReward record);

    int insertSelective(PPosHuddleReward record);

    List<PPosHuddleReward> selectByExample(PPosHuddleRewardExample example);

    PPosHuddleReward selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PPosHuddleReward record, @Param("example") PPosHuddleRewardExample example);

    int updateByExample(@Param("record") PPosHuddleReward record, @Param("example") PPosHuddleRewardExample example);

    int updateByPrimaryKeySelective(PPosHuddleReward record);

    int updateByPrimaryKey(PPosHuddleReward record);

    List<PPosHuddleReward>  selectByList(@Param("listQuvery") List listQuvery);
    long   selectByCount(@Param("listQuvery") List listQuvery);
}