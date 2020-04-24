package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.PBalanceSerial;
import com.ryx.credit.profit.pojo.PBalanceSerialExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PBalanceSerialMapper {
    long countByExample(PBalanceSerialExample example);

    int deleteByExample(PBalanceSerialExample example);

    int insert(PBalanceSerial record);

    int insertSelective(PBalanceSerial record);

    List<PBalanceSerial> selectByExample(PBalanceSerialExample example);

    List<Map<String,String>> getListByMap(@Param("param") Map<String,String> param, @Param("page") Page page);

    long getCountByMap(@Param("param")Map<String,String> param);

    List<Map<String,String>> getRefundLog(String balanceId);

}