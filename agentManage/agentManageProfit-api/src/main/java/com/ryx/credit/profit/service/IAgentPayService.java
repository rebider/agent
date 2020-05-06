package com.ryx.credit.profit.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.BalanceApproval;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IAgentPayService {


    public PageInfo getBalanceApprovalList(Page page, BalanceApproval balanceApproval);

    public List<Map<String, Object>> getBalanceApprovalList(BalanceApproval balanceApproval);

    boolean applyAgentPay(String balanceBatchNo, List<String> balanceIds, String userId, String workId);

    List<Map<String,Object>> queryDetailByBatchNo(String batchNo);

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    AgentResult approvalAgentPayTask(AgentVo agentVo, String userId) throws Exception;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void completeTaskEnterActivity(String insid, String status);

    int updateAgentPayByBalanceIdAndBatchNo(String balanceId,String batchNo,String approvalStatus);

    long countByBatchNo(String batchNo);

    int updateBalanceApprovalAcct(BalanceApproval approval);
}
