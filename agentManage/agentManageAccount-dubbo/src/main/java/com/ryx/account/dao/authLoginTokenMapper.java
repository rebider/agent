package com.ryx.account.dao;

import com.ryx.account.pojo.authLoginToken;
import com.ryx.account.pojo.authLoginTokenExample;

import java.util.List;

public interface authLoginTokenMapper {
    long countByExample(authLoginTokenExample example);

    int deleteByExample(authLoginTokenExample example);

    int insert(authLoginToken record);

    int insertSelective(authLoginToken record);

    List<authLoginToken> selectByExample(authLoginTokenExample example);

    authLoginToken selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(authLoginToken record);

    int updateByPrimaryKey(authLoginToken record);
}