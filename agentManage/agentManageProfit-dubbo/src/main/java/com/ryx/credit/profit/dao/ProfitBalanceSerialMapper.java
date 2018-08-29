package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitBalanceSerial;
import com.ryx.credit.profit.pojo.ProfitBalanceSerialExample;
import java.util.List;

public interface ProfitBalanceSerialMapper {
    long countByExample(ProfitBalanceSerialExample example);

    int deleteByExample(ProfitBalanceSerialExample example);

    int insert(ProfitBalanceSerial record);

    int insertSelective(ProfitBalanceSerial record);

    List<ProfitBalanceSerial> selectByExample(ProfitBalanceSerialExample example);

    ProfitBalanceSerial selectByPrimaryKey(String balanceId);

    int updateByPrimaryKeySelective(ProfitBalanceSerial record);

    int updateByPrimaryKey(ProfitBalanceSerial record);
}