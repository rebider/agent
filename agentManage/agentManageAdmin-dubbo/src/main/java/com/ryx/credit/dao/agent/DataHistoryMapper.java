package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.pojo.admin.agent.DataHistoryExample;

import java.util.List;

public interface DataHistoryMapper {
    int countByExample(DataHistoryExample example);

    int deleteByExample(DataHistoryExample example);

    int insert(DataHistory record);

    int insertSelective(DataHistory record);

    List<DataHistory> selectByExampleWithBLOBs(DataHistoryExample example);

    List<DataHistory> selectByExample(DataHistoryExample example);

    DataHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataHistory record);

    int updateByPrimaryKeyWithBLOBs(DataHistory record);

    int updateByPrimaryKey(DataHistory record);
}