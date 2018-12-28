package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PBalanceSerialLs;
import com.ryx.credit.profit.pojo.PBalanceSerialLsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PBalanceSerialLsMapper {
    long countByExample(PBalanceSerialLsExample example);

    int deleteByExample(PBalanceSerialLsExample example);

    int deleteByPrimaryKey(String balanceId);

    int insert(PBalanceSerialLs record);

    int insertSelective(PBalanceSerialLs record);

    List<PBalanceSerialLs> selectByExample(PBalanceSerialLsExample example);

    PBalanceSerialLs selectByPrimaryKey(String balanceId);

    int updateByExampleSelective(@Param("record") PBalanceSerialLs record, @Param("example") PBalanceSerialLsExample example);

    int updateByExample(@Param("record") PBalanceSerialLs record, @Param("example") PBalanceSerialLsExample example);

    int updateByPrimaryKeySelective(PBalanceSerialLs record);

    int updateByPrimaryKey(PBalanceSerialLs record);

    List<Map<String,Object>> getPBalanceSerialLsList(Map<String, Object> param);

    Long  getPBalanceSerialLsCount(Map<String, Object> param);

    List getAGList(Map<String, Object> param);
}