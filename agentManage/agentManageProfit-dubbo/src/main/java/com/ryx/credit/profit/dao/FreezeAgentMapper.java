package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;

import java.util.List;

public interface FreezeAgentMapper {
    long countByExample(FreezeAgentExample example);

    int deleteByExample(FreezeAgentExample example);

    int insert(FreezeAgent record);

    int insertSelective(FreezeAgent record);

    List<FreezeAgent> selectByExample(FreezeAgentExample example);

    FreezeAgent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeAgent record);

    int updateByPrimaryKey(FreezeAgent record);
}