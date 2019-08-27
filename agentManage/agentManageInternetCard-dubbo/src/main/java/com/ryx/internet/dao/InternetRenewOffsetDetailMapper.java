package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetRenewOffsetDetail;
import com.ryx.internet.pojo.InternetRenewOffsetDetailExample;

import java.util.List;

public interface InternetRenewOffsetDetailMapper {
    long countByExample(InternetRenewOffsetDetailExample example);

    int deleteByExample(InternetRenewOffsetDetailExample example);

    int insert(InternetRenewOffsetDetail record);

    int insertSelective(InternetRenewOffsetDetail record);

    List<InternetRenewOffsetDetail> selectByExample(InternetRenewOffsetDetailExample example);

    InternetRenewOffsetDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InternetRenewOffsetDetail record);

    int updateByPrimaryKey(InternetRenewOffsetDetail record);
}