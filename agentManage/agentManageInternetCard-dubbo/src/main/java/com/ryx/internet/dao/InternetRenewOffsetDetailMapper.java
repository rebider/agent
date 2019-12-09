package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetRenewOffsetDetail;
import com.ryx.internet.pojo.InternetRenewOffsetDetailExample;

import java.util.List;
import java.util.Map;

public interface InternetRenewOffsetDetailMapper {
    long countByExample(InternetRenewOffsetDetailExample example);

    int deleteByExample(InternetRenewOffsetDetailExample example);

    int insert(InternetRenewOffsetDetail record);

    int insertSelective(InternetRenewOffsetDetail record);

    List<InternetRenewOffsetDetail> selectByExample(InternetRenewOffsetDetailExample example);

    InternetRenewOffsetDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InternetRenewOffsetDetail record);

    int updateByPrimaryKey(InternetRenewOffsetDetail record);

    List<Map<String,Object>> queryMonthSumOffsetAmt(Map<String,Object> reqMap);

    List<InternetRenewOffsetDetail> internetRenewOffsetDetailList(InternetRenewOffsetDetailExample example);

    int internetRenewOffsetDetailCount(InternetRenewOffsetDetailExample example);

}