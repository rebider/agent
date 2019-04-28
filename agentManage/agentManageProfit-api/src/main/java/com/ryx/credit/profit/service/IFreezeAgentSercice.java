package com.ryx.credit.profit.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.FreezeAgent;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IFreezeAgentSercice {
    PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo);
    public void operationFreezeDate(List<FreezeOperationRecord> freezeOperationRecords, String user);
    public boolean applyThawAgent(List<FreezeOperationRecord> records,String userId,String batch,String workId);
    PageInfo getFreezeData(FreezeAgent freezeAgent, String isQuerySubordinate, Page page, String orgId);

    List<FreezeAgent> getFreezeList();
    public PageInfo getselectDetailFreezeDate(Map<String, Object> param, PageInfo pageInfo);
    public void completeTaskEnterActivity(String activId,String result);
    public int updateThawAgentData(List<FreezeOperationRecord> records,List<FreezeAgent> freezeAgents) throws Exception;
    public List<FreezeAgent> getThawDataByBatch(String batch);
    public Map<String,Object> getThawOperator(String batch);
    AgentResult approvalThawTask(AgentVo agentVo, String userId) throws Exception;
    void delFreezeAgentById(List<String> list);
    public List<FreezeOperationRecord> getCheckHistoryDate(FreezeOperationRecord freezeOperationRecord);
}
