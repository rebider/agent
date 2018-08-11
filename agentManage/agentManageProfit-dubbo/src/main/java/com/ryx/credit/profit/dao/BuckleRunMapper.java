package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.BuckleRun;
import com.ryx.credit.profit.pojo.BuckleRunExample;

import java.math.BigDecimal;
import java.util.List;

public interface BuckleRunMapper {
    long countByExample(BuckleRunExample example);

    int deleteByExample(BuckleRunExample example);

    int insert(BuckleRun record);

    int insertSelective(BuckleRun record);

    List<BuckleRun> selectByExample(BuckleRunExample example);

    BuckleRun selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BuckleRun record);

    int updateByPrimaryKey(BuckleRun record);

    BigDecimal getSumRunAmt(String agentId);

    List<BuckleRun> selectListByAgentId(String agentId);
}