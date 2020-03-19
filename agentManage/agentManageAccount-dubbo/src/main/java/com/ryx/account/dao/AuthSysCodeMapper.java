package com.ryx.account.dao;

import com.ryx.account.pojo.AuthSysCode;
import com.ryx.account.pojo.AuthSysCodeExample;

import java.util.List;

public interface AuthSysCodeMapper {
    long countByExample(AuthSysCodeExample example);

    int deleteByExample(AuthSysCodeExample example);

    int insert(AuthSysCode record);

    int insertSelective(AuthSysCode record);

    List<AuthSysCode> selectByExample(AuthSysCodeExample example);

    AuthSysCode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthSysCode record);

    int updateByPrimaryKey(AuthSysCode record);
}