package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosHuddleRewardDetail;
import com.ryx.credit.profit.pojo.PosHuddleRewardDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PosHuddleRewardDetailMapper {
    long countByExample(PosHuddleRewardDetailExample example);

    int deleteByExample(PosHuddleRewardDetailExample example);

    int insert(PosHuddleRewardDetail record);

    int insertSelective(PosHuddleRewardDetail record);

    List<PosHuddleRewardDetail> selectByExample(PosHuddleRewardDetailExample example);

    int updateByExampleSelective(@Param("record") PosHuddleRewardDetail record, @Param("example") PosHuddleRewardDetailExample example);

    int updateByExample(@Param("record") PosHuddleRewardDetail record, @Param("example") PosHuddleRewardDetailExample example);

    List<PosHuddleRewardDetail> selectByHuddleCode(@Param("huddleCode")String huddleCode);


}