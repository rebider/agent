package com.ryx.account.dao;

import com.ryx.account.pojo.authSysCode;
import com.ryx.account.pojo.authSysCodeExample;

import java.util.List;

public interface authSysCodeMapper {
    long countByExample(authSysCodeExample example);

    int deleteByExample(authSysCodeExample example);

    int insert(authSysCode record);

    int insertSelective(authSysCode record);

    List<authSysCode> selectByExample(authSysCodeExample example);

    authSysCode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(authSysCode record);

    int updateByPrimaryKey(authSysCode record);
}