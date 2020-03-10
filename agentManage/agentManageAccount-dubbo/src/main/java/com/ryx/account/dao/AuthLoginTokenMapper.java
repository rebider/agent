package com.ryx.account.dao;

import com.ryx.account.pojo.AuthLoginToken;
import com.ryx.account.pojo.AuthLoginTokenExample;

import java.util.List;

public interface AuthLoginTokenMapper {
    long countByExample(AuthLoginTokenExample example);

    int deleteByExample(AuthLoginTokenExample example);

    int insert(AuthLoginToken record);

    int insertSelective(AuthLoginToken record);

    List<AuthLoginToken> selectByExample(AuthLoginTokenExample example);

    AuthLoginToken selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthLoginToken record);

    int updateByPrimaryKey(AuthLoginToken record);
}