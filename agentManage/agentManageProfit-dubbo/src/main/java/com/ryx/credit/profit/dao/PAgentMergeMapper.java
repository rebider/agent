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

    PAgentMerge selectBySubAgentId(String subAgentId);

    /**
     * 根据subAgentId查询是否存在与本代理商合并的主代理商或副代理商
     * @param subAgentId
     * @return
     */
    List<PAgentMerge> selectByAgentId(String subAgentId);
}