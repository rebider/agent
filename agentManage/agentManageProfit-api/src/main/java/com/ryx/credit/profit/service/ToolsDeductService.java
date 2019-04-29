package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.*;
import org.apache.ibatis.annotations.Param;

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
    public List<Map<String, Object>> batchInsertDeduct(List<Map<String, Object>> maps, String deductionDate) throws ProcessException;;

    /**
     * 扣款分期调整后，本月信息补全
     * @param detailList
     */
    public void deductCompletionInfo(List<Map<String, Object>> detailList);

    /**
     * 更新分期明细表明细状态
     */
    public void updateProfitStagingDetail(ProfitStagingDetail profitStagingDetail);

    /**
     * 查询上月未扣足订单且没有调整扣款金额，也没有后续分期计划的订单
     */
    public List<Map<String, Object>> getNotDeductDetail(String beforeDeductDate, String deductDate, String type);

    /**
     * 省区补款审批流启动
     * @param pToolSupplys
     * @param userId
     * @param workId
     * @throws ProcessException
     * chenliang
     */
    public void applySupplystment(List<PToolSupply> pToolSupplys, String userId, String workId,PRemitInfo pRemitInfo) throws ProcessException;

    /**
     * 根据扣款id查询扣款记录
     * @param id
     * @return
     * chenliang
     */
    ProfitDeduction selectByPrimaryKey(String id);
    /**
     * 修改机具中线下补款明细/上级代扣的审批信息
     */
    void editToolSupply(List<PToolSupply> pToolSupplys, String detailId, PRemitInfo pRemitInfo);

    public void updateStatus(String activId, String type);



    public List<PToolSupply> selectByExample(PToolSupply pToolSupply);

    List<PRemitInfo> brCitySupplyId(String citySupplyId);

    public AgentResult approvalToolSupplyTask(AgentVo agentVo, String userId) throws ProcessException;
    public int updateByPrimaryKey(PRemitInfo pRemitInfo);

    PageInfo getSupplyPromptList(Map<String,Object> map, Page page,ProfitDeduction profitDeduction);
}
