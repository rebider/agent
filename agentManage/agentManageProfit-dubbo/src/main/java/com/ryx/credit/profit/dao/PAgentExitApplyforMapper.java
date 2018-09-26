package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PAgentExitApplyfor;
import com.ryx.credit.profit.pojo.PAgentExitApplyforExample;
import java.util.List;

public interface PAgentExitApplyforMapper {
    long countByExample(PAgentExitApplyforExample example);

    int deleteByExample(PAgentExitApplyforExample example);

    int insert(PAgentExitApplyfor record);

    int insertSelective(PAgentExitApplyfor record);

    List<PAgentExitApplyfor> selectByExample(PAgentExitApplyforExample example);

    PAgentExitApplyfor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PAgentExitApplyfor record);

    int updateByPrimaryKey(PAgentExitApplyfor record);
}