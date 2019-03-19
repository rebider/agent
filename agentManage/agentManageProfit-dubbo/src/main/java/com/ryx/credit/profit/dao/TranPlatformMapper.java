package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TranPlatform;
import com.ryx.credit.profit.pojo.TranPlatformExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TranPlatformMapper {
    long countByExample(TranPlatformExample example);

    int deleteByExample(TranPlatformExample example);

    int insert(TranPlatform record);

    int insertSelective(TranPlatform record);

    List<TranPlatform> selectByExample(TranPlatformExample example);

    TranPlatform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranPlatform record);

    int updateByPrimaryKey(TranPlatform record);

    List<Map<String,String>> selectAllTranPlatform();

    List<String> selectAllPlatformType();

    Map<String,Object> getTranAmtByMonth(String tranMonth);

}