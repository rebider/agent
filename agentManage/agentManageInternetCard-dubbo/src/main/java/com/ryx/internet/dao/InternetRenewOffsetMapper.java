package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetRenewOffset;
import com.ryx.internet.pojo.InternetRenewOffsetExample;

import java.util.List;

public interface InternetRenewOffsetMapper {
    long countByExample(InternetRenewOffsetExample example);

    int deleteByExample(InternetRenewOffsetExample example);

    int insert(InternetRenewOffset record);

    int insertSelective(InternetRenewOffset record);

    List<InternetRenewOffset> selectByExample(InternetRenewOffsetExample example);

    InternetRenewOffset selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(InternetRenewOffset record);

    int updateByPrimaryKey(InternetRenewOffset record);
}