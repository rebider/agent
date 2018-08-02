package com.ryx.credit.dao;

import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.CuserAgentKey;

import java.util.List;

public interface CuserAgentMapper {
    long countByExample(CuserAgentExample example);

    int deleteByExample(CuserAgentExample example);

    int insert(CuserAgent record);

    int insertSelective(CuserAgent record);

    List<CuserAgent> selectByExample(CuserAgentExample example);

    CuserAgent selectByPrimaryKey(CuserAgentKey key);

    int updateByPrimaryKeySelective(CuserAgent record);

    int updateByPrimaryKey(CuserAgent record);
}