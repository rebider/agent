package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.pmsProfitTemp;
import com.ryx.credit.profit.pojo.pmsProfitTempExample;
import com.ryx.credit.profit.pojo.pmsProfitTempKey;
import com.ryx.credit.profit.pojo.pmsProfitTempWithBLOBs;

import java.util.List;

public interface pmsProfitTempMapper {
    long countByExample(pmsProfitTempExample example);

    int deleteByExample(pmsProfitTempExample example);

    int insert(pmsProfitTempWithBLOBs record);

    int insertSelective(pmsProfitTempWithBLOBs record);

    List<pmsProfitTempWithBLOBs> selectByExampleWithBLOBs(pmsProfitTempExample example);

    List<pmsProfitTemp> selectByExample(pmsProfitTempExample example);

    pmsProfitTempWithBLOBs selectByPrimaryKey(pmsProfitTempKey key);

    int updateByPrimaryKeySelective(pmsProfitTempWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(pmsProfitTempWithBLOBs record);

    int updateByPrimaryKey(pmsProfitTemp record);
}