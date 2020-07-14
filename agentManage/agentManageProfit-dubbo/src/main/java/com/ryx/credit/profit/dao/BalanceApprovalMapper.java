package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.BalanceApproval;
import com.ryx.credit.profit.pojo.BalanceApprovalExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BalanceApprovalMapper {
    long countByExample(BalanceApprovalExample example);

    int deleteByExample(BalanceApprovalExample example);

    int insert(BalanceApproval record);

    int insertSelective(BalanceApproval record);

    List<BalanceApproval> selectByExample(BalanceApprovalExample example);

    List<Map<String, Object>> queryDetailByBatchNo(@Param("batchNo")String batchNo);

    int updateAgentPayByBalanceIdAndBatchNo(@Param("balanceId")String balanceId, @Param("batchNo")String batchNo, @Param("approvalStatus")String approvalStatus);

    /**
     * 根据原先审批状态更新审批结果
     * @param batchNo
     * @return
     */
    int updateBalanceApproval(@Param("batchNo")String batchNo,@Param("originalStatus") String originalStatus, @Param("approvalStatus")String approvalStatus);

    List<Map<String, Object>> getBalanceApprovalList(BalanceApproval balanceApproval);

    int updateBalanceApprovalAcct(BalanceApproval approval);

    BigDecimal countAmtByBatchNo(BalanceApprovalExample example);
}