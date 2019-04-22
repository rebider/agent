package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.FreezeOperationRecordExample;
import java.util.List;

public interface FreezeOperationRecordMapper {
    long countByExample(FreezeOperationRecordExample example);

    int deleteByExample(FreezeOperationRecordExample example);

    int insert(FreezeOperationRecord record);

    int insertSelective(FreezeOperationRecord record);

    List<FreezeOperationRecord> selectByExample(FreezeOperationRecordExample example);
}