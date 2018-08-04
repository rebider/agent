package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;

import java.util.List;
import java.util.Map;

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
    public void applyAdjustment(ProfitDeduction profitDeduction, String userId, String workId) throws ProcessException;

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

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @return
     * @throws ProcessException
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    /**
     * 根据扣款ID 查询对应的明细信息
     * @param stagId
     * @return
     */
    public List<ProfitStagingDetail> getProfitStagingDetailByStagId(String stagId);

    /**
     * 审批退回，修改申请信息
     * @param profitDeduction
     * @param detailId
     * @throws Exception
     */
    public void editToolDeduct(ProfitDeduction profitDeduction, String detailId)throws Exception;

    /**
     * 批量新增机具扣款分期数据
     * @param maps
     */
    public List<Map<String, Object>> batchInsertDeduct(List<Map<String, Object>> maps, String deductionDate);

    /**
     * 扣款分期调整后，本月信息补全
     * @param detailList
     */
    public void deductCompletionInfo(List<Map<String, Object>> detailList);
}
