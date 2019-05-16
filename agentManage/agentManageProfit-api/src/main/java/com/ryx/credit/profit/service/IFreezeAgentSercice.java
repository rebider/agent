package com.ryx.credit.profit.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.FreezeOperationRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IFreezeAgentSercice {
    PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo);

    void operationFreezeDate(List<FreezeOperationRecord> freezeOperationRecords, String user);

    boolean applyThawAgent(List<FreezeOperationRecord> records, String userId, String batch, String workId);

    PageInfo getFreezeData(FreezeAgent freezeAgent, String isQuerySubordinate, Page page, String orgId);

    List<FreezeAgent> getFreezeList();

    PageInfo getselectDetailFreezeDate(Map<String, Object> param, PageInfo pageInfo);

    void completeTaskEnterActivity(String activId, String result);

    int updateThawAgentData(List<FreezeOperationRecord> records, List<FreezeAgent> freezeAgents) throws Exception;

    List<FreezeAgent> getThawDataByBatch(String batch);

    Map<String, Object> getThawOperator(String batch);

    AgentResult approvalThawTask(AgentVo agentVo, String userId) throws Exception;

    void delFreezeAgentById(List<String> list);

    List<FreezeOperationRecord> getCheckHistoryDate(FreezeOperationRecord freezeOperationRecord);

    Integer queryDayFreezeCount();

    List<FreezeAgent> queryDayFreezeDate(@Param("startNum") Integer startNum, @Param("endNum") Integer endNum);
    List<FreezeOperationRecord> selectByExample(FreezeOperationRecordExample example);
    int updateByPrimaryKeySelective(FreezeOperationRecord record);
}
