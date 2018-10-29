package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.pojo.PosRewardDetailExample;
import java.util.List;

public interface PosRewardDetailMapper {
    long countByExample(PosRewardDetailExample example);

    int deleteByExample(PosRewardDetailExample example);

    int insert(PosRewardDetail record);

    int insertSelective(PosRewardDetail record);

    List<PosRewardDetail> selectByExample(PosRewardDetailExample example);
}