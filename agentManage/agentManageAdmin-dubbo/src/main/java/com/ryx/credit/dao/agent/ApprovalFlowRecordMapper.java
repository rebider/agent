package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecordExample;

import java.util.List;
import java.util.Map;

public interface ApprovalFlowRecordMapper {
    long countByExample(ApprovalFlowRecordExample example);

    long selectByExampleWithBusActRelCount(Map par);

    List<ApprovalFlowRecord>selectByExampleWithBusActRel(Map par);

    int deleteByExample(ApprovalFlowRecordExample example);

    int insert(ApprovalFlowRecord record);

    int insertSelective(ApprovalFlowRecord record);

    List<ApprovalFlowRecord> selectByExample(ApprovalFlowRecordExample example);

    ApprovalFlowRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApprovalFlowRecord record);

    int updateByPrimaryKey(ApprovalFlowRecord record);
}