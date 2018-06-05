package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.InterfaceRequestRecord;
import com.ryx.credit.pojo.admin.agent.InterfaceRequestRecordExample;
import com.ryx.credit.pojo.admin.agent.InterfaceRequestRecordWithBLOBs;

import java.util.List;

public interface InterfaceRequestRecordMapper {
    int countByExample(InterfaceRequestRecordExample example);

    int deleteByExample(InterfaceRequestRecordExample example);

    int insert(InterfaceRequestRecordWithBLOBs record);

    int insertSelective(InterfaceRequestRecordWithBLOBs record);

    List<InterfaceRequestRecordWithBLOBs> selectByExampleWithBLOBs(InterfaceRequestRecordExample example);

    List<InterfaceRequestRecord> selectByExample(InterfaceRequestRecordExample example);

    InterfaceRequestRecordWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InterfaceRequestRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(InterfaceRequestRecordWithBLOBs record);

    int updateByPrimaryKey(InterfaceRequestRecord record);
}