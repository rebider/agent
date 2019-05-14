package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.DataHistoryRecord;
import com.ryx.credit.pojo.admin.agent.DataHistoryRecordExample;
import java.util.List;

public interface DataHistoryRecordMapper {

    long countByExample(DataHistoryRecordExample example);

    int deleteByExample(DataHistoryRecordExample example);

    int insert(DataHistoryRecord record);

    int insertSelective(DataHistoryRecord record);

    List<DataHistoryRecord> selectByExampleWithBLOBs(DataHistoryRecordExample example);

    List<DataHistoryRecord> selectByExample(DataHistoryRecordExample example);

    DataHistoryRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataHistoryRecord record);

    int updateByPrimaryKeyWithBLOBs(DataHistoryRecord record);

    int updateByPrimaryKey(DataHistoryRecord record);
}