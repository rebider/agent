package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.pojo.PAgentMergeExample;
import java.util.List;

public interface PAgentMergeMapper {
    long countByExample(PAgentMergeExample example);

    int deleteByExample(PAgentMergeExample example);

    int insert(PAgentMerge record);

    int insertSelective(PAgentMerge record);

    List<PAgentMerge> selectByExample(PAgentMergeExample example);
}