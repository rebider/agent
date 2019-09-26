package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PmsProfitTemp;
import com.ryx.credit.profit.pojo.PmsProfitTempExample;
import com.ryx.credit.profit.pojo.PmsProfitTempKey;
import com.ryx.credit.profit.pojo.PmsProfitTempWithBLOBs;

import java.util.List;

public interface PmsProfitTempMapper {
    long countByExample(PmsProfitTempExample example);

    int deleteByExample(PmsProfitTempExample example);

    int insert(PmsProfitTempWithBLOBs record);

    int insertSelective(PmsProfitTempWithBLOBs record);

    List<PmsProfitTempWithBLOBs> selectByExampleWithBLOBs(PmsProfitTempExample example);

    List<PmsProfitTemp> selectByExample(PmsProfitTempExample example);

    PmsProfitTempWithBLOBs selectByPrimaryKey(PmsProfitTempKey key);

    int updateByPrimaryKeySelective(PmsProfitTempWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PmsProfitTempWithBLOBs record);

    int updateByPrimaryKey(PmsProfitTemp record);
}