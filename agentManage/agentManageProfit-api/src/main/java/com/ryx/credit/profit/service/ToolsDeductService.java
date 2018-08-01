package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;

/**
 * @author yangmx
 * @desc 机具扣款调整实现
 */
public interface ToolsDeductService {

    /**
     * 机具扣款申请调整，进行审批流
     * @param profitDeduction
     * @param userId
     */
    public void applyAdjustment(ProfitDeduction profitDeduction, String userId) throws ProcessException;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    public void completeTaskEnterActivity(String insid, String status);

    /**
     * 查询机具扣款申请明细
     */
    public ProfitStagingDetail getProfitStagingDetail(String id);
}
