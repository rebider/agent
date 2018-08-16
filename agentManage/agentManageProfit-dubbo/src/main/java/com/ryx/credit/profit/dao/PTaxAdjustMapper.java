package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.pojo.PTaxAdjustExample;

import java.math.BigDecimal;
import java.util.List;

public interface PTaxAdjustMapper {
    long countByExample(PTaxAdjustExample example);

    int deleteByExample(PTaxAdjustExample example);

    int insert(PTaxAdjust record);

    int insertSelective(PTaxAdjust record);

    List<PTaxAdjust> selectByExample(PTaxAdjustExample example);

    PTaxAdjust selectByPrimaryKey(String id);

    PTaxAdjust selectByAgentPid(String agentPid);

    int updateByPrimaryKeySelective(PTaxAdjust record);

    int updateByPrimaryKey(PTaxAdjust record);

    BigDecimal getTax(String agentPid);
}