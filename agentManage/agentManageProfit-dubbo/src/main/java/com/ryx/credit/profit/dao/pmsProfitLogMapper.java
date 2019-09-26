package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.pmsProfitLog;
import com.ryx.credit.profit.pojo.pmsProfitLogExample;

import java.util.List;

public interface pmsProfitLogMapper {
    long countByExample(pmsProfitLogExample example);

    int deleteByExample(pmsProfitLogExample example);

    int insert(pmsProfitLog record);

    int insertSelective(pmsProfitLog record);

    List<pmsProfitLog> selectByExample(pmsProfitLogExample example);

    pmsProfitLog selectByPrimaryKey(String batchNo);

    int updateByPrimaryKeySelective(pmsProfitLog record);

    int updateByPrimaryKey(pmsProfitLog record);
}