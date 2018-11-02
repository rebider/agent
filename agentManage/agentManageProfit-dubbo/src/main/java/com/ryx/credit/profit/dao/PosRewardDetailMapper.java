package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.pojo.PosRewardDetailExample;

import java.util.List;
import java.util.Map;

public interface PosRewardDetailMapper {
    long countByExample(PosRewardDetailExample example);

    int deleteByExample(PosRewardDetailExample example);

    int insert(PosRewardDetail record);

    int insertSelective(PosRewardDetail record);

    List<PosRewardDetail> selectByExample(PosRewardDetailExample example);

    PosRewardDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosRewardDetail record);

    int updateByPrimaryKey(PosRewardDetail record);

    long getRewardDetailCount(Map<String, Object> param);

    List<Map<String, Object>> getRewardDetailList(Map<String, Object> param);
}