package com.ryx.internet.dao;

import com.ryx.internet.pojo.OInternetRenew;
import com.ryx.internet.pojo.OInternetRenewExample;

import java.util.List;

public interface OInternetRenewMapper {
    long countByExample(OInternetRenewExample example);

    int deleteByExample(OInternetRenewExample example);

    int insert(OInternetRenew record);

    int insertSelective(OInternetRenew record);

    List<OInternetRenew> selectByExample(OInternetRenewExample example);

    OInternetRenew selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInternetRenew record);

    int updateByPrimaryKey(OInternetRenew record);
}