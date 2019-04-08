package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TranCheckData;
import com.ryx.credit.profit.pojo.TranCheckDataExample;

import java.util.List;

public interface TranCheckDataMapper {
    long countByExample(TranCheckDataExample example);

    int deleteByExample(TranCheckDataExample example);

    int insert(TranCheckData record);

    int insertSelective(TranCheckData record);

    List<TranCheckData> selectByExample(TranCheckDataExample example);

    TranCheckData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranCheckData record);

    int updateByPrimaryKey(TranCheckData record);

    int synchronizeTranCheckData(List<TranCheckData> list);
}