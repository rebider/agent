package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetailExample;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * @author yangmx
 * @desc 机具扣款调整实现
 */
@Service("toolsDeductService")
public class ToolsDeductServiceImpl implements ToolsDeductService {
    Logger LOG = LoggerFactory.getLogger(ProfitMonthServiceImpl.class);
    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private ProfitStagingDetailMapper profitStagingDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;

    /**
     * 扣款总额=本月新增+上月未口足，
     * 调整金额=本月应扣，
     * 实际扣款数=本月实扣，
     * 未扣足=本月应扣-本月实扣
     * @param profitDeduction
     * @param userId
     * @throws ProcessException
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void applyAdjustment(ProfitDeduction profitDeduction, String userId, String workId) throws ProcessException {

        LocalDate date = LocalDate.now();
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setId(idService.genId(TabId.P_STAGING_DETAIL));
        profitStagingDetail.setCurrentStagCount(1);
        profitStagingDetail.setDeductionDate(date.getYear()+"-"+date.getMonthValue());
        BigDecimal mustDeductionAmt = profitDeduction.getSumDeductionAmt().subtract(profitDeduction.getMustDeductionAmt());
        profitStagingDetail.setMustAmt(mustDeductionAmt);
        profitStagingDetail.setRealAmt(BigDecimal.ZERO);
        profitStagingDetail.setRemark("机具扣款分期调整下月扣款明细");
        profitStagingDetail.setSourceId(profitDeduction.getSourceId());
        profitStagingDetail.setStagId(profitDeduction.getId());
        profitStagingDetail.setStatus(StagingDetailStatus.N.getStatus());
        profitStagingDetailMapper.insertSelective(profitStagingDetail);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            ProfitStagingDetailExample profitStagingDetailExample = new ProfitStagingDetailExample();
            profitStagingDetailExample.createCriteria().andIdEqualTo(profitStagingDetail.getId());
            profitStagingDetailMapper.deleteByExample(profitStagingDetailExample);
            LOG.error("机具扣款调整审批流启动失败，代理商ID：{}", profitDeduction.getAgentPid());
            throw new ProcessException("机具扣款调整审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(profitStagingDetail.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.TOOLS.name());
        record.setAgentId(profitDeduction.getAgentId());
        record.setAgentName(profitDeduction.getAgentName());
        try {
            taskApprovalService.addABusActRel(record);
            LOG.info("机具扣款调整申请审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            ProfitStagingDetailExample profitStagingDetailExample = new ProfitStagingDetailExample();
            profitStagingDetailExample.createCriteria().andIdEqualTo(profitStagingDetail.getId());
            profitStagingDetailMapper.deleteByExample(profitStagingDetailExample);
            LOG.error("机具扣款调整申请审批流启动失败{}");
            throw new ProcessException("机具扣款调整申请审批流启动失败!:{}",e.getMessage());
        }
        profitDeduction.setStagingStatus(DeductionStatus.REVIEWING.getStatus());
        profitDeduction.setMustDeductionAmt(profitDeduction.getSumDeductionAmt());
        profitDeductionMapper.updateByPrimaryKeySelective(profitDeduction);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status){
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                ProfitStagingDetail profitStagingDetail = profitStagingDetailMapper.selectByPrimaryKey(rel.getBusId());
                if (profitStagingDetail != null) {
                    ProfitDeduction profitDeduction = profitDeductionMapper.selectByPrimaryKey(profitStagingDetail.getStagId());
                    if(!Objects.equals(profitDeduction.getStagingStatus(),DeductionStatus.YES_WITHHOLD.getStatus())){
                        LOG.info("1.更新机具扣款申请状态为申请通过");
                        LOG.info("审批通过，未申请前本月实扣：{},申请扣款数：{}",profitDeduction.getActualDeductionAmt(),profitStagingDetail.getRealAmt());
                        profitDeduction.setStagingStatus(DeductionStatus.PASS.getStatus());
                        profitDeduction.setMustDeductionAmt(profitDeduction.getSumDeductionAmt().subtract(profitStagingDetail.getMustAmt()));
                        profitDeductionMapper.updateByPrimaryKeySelective(profitDeduction);
                    }
                    LOG.info("2更新审批流与业务对象");
                    rel.setStatus(Status.STATUS_2.status);
                    taskApprovalService.updateABusActRel(rel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("机具扣款调整审批流回调异常，activId：{}", insid);
        }
    }

    @Override
    public ProfitStagingDetail getProfitStagingDetail(String id) {
        if(StringUtils.isNotBlank(id)){
            return profitStagingDetailMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        LOG.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }

        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        LOG.info("创建下一审批流对象：{}", reqMap.toString());
        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }

    @Override
    public List<ProfitStagingDetail> getProfitStagingDetailByStagId(String stagId) {
        if(StringUtils.isNotBlank(stagId)){
            ProfitStagingDetailExample profitStagingDetailExample = new ProfitStagingDetailExample();
            ProfitStagingDetailExample.Criteria criteria = profitStagingDetailExample.createCriteria();
            criteria.andStagIdEqualTo(stagId);
            return profitStagingDetailMapper.selectByExample(profitStagingDetailExample);
        }
        return null;
    }

    @Override
    public void editToolDeduct(ProfitDeduction profitDeduction, String detailId) throws Exception{
        try {
            ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
            profitStagingDetail.setId(detailId);
            profitStagingDetail.setStagId(profitDeduction.getId());
            BigDecimal mustDeductionAmt = profitDeduction.getSumDeductionAmt().subtract(profitDeduction.getMustDeductionAmt());
            profitStagingDetail.setMustAmt(mustDeductionAmt);
            profitStagingDetail.setRemark("机具扣款分期调整下月扣款明细");
            profitStagingDetailMapper.updateByPrimaryKeySelective(profitStagingDetail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 扣款总额=本月新增+上月未口足，
     * 扣款总额=本月应扣，
     * 实际扣款数=本月实扣，
     * 未扣足=本月应扣-本月实扣
     * @param list
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public List<Map<String, Object>> batchInsertDeduct(List<Map<String, Object>> list, String deductionDate) throws ProcessException{
        if(list != null && !list.isEmpty()){
            List<Map<String, Object>> successList=  new ArrayList<Map<String, Object>>(list.size());
            list.forEach((Map<String, Object> map) -> {
                if(map.get("ID") != null ){
                    try {
                        ProfitDeductionExample profitDeductionExample = new ProfitDeductionExample();
                        ProfitDeductionExample.Criteria criteria = profitDeductionExample.createCriteria();
                        criteria.andSourceIdEqualTo(map.get("ID").toString());
                        criteria.andAgentPidEqualTo(map.get("AGENT_ID").toString());
                        criteria.andDeductionDateEqualTo(deductionDate);
                        criteria.andParentAgentPidEqualTo(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                        int count = profitDeductionMapper.countByExample(profitDeductionExample);
                        if(count == 0){
                            ProfitDeduction profitDeduction = new ProfitDeduction();
                            profitDeduction.setId(idService.genId(TabId.P_DEDUCTION));
                            profitDeduction.setParentAgentId(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                            profitDeduction.setParentAgentPid(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                            profitDeduction.setAgentId(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                            profitDeduction.setAgentPid(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                            profitDeduction.setAgentName(map.get("AG_NAME") == null ? "" : map.get("AG_NAME").toString());
                            profitDeduction.setDeductionDesc(map.get("ORDER_PLATFORM") == null ? "" : map.get("ORDER_PLATFORM").toString());
                            profitDeduction.setDeductionDate(deductionDate);
                            profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                            profitDeduction.setSumDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setAddDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setMustDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                            profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                            profitDeduction.setSourceId(map.get("ID").toString());
                            profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);
                            profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                            profitDeduction.setCreateDateTime(new Date());
                            profitDeductionMapper.insertSelective(profitDeduction);
                            Map<String, Object> successMap = new HashMap<String, Object>(2);
                            successMap.put("detailId", map.get("ID"));
                            successMap.put("srcId", profitDeduction.getId());
                            successList.add(successMap);
                        }
                    } catch (Exception e) {
                        LOG.error("初始化机具分期失败，分期流水号:{}",map.get("ID").toString());
                        e.printStackTrace();
                        throw new ProcessException("机具扣款数据初始化失败");
                    }
                }
            });

            LOG.info("机具扣款分期入库成功：{} 条", successList.size());
            try {
                if(successList.size() > 0){
                    //通知订单系统，订单付款中
                    iPaymentDetailService.uploadStatus(successList, PaySign.FKING.code);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessException("通知订单系统更新付款中状态异常");
            }
            return successList;
        }
        return null;
    }

    @Override
    public void deductCompletionInfo(List<Map<String, Object>> detailList) {
        detailList.forEach(map -> {
            ProfitDeductionExample profitDeductionExample = new ProfitDeductionExample();
            ProfitDeductionExample.Criteria criteria = profitDeductionExample.createCriteria();
            criteria.andSourceIdEqualTo(map.get("SOURCE_ID").toString());
            criteria.andAgentPidEqualTo(map.get("AGENT_ID").toString());
            criteria.andDeductionDateEqualTo(map.get("DEDUCTION_DATE").toString());
            criteria.andDeductionDescEqualTo(map.get("DEDUCTION_DESC").toString());
            int count = profitDeductionMapper.countByExample(profitDeductionExample);
            if(count == 0){
                ProfitDeduction profitDeduction = new ProfitDeduction();
                profitDeduction.setId(idService.genId(TabId.P_DEDUCTION));
                profitDeduction.setParentAgentId(map.get("PARENT_AGENT_ID") == null ? "" :map.get("PARENT_AGENT_ID").toString());
                profitDeduction.setParentAgentPid(map.get("PARENT_AGENT_ID")== null ? "" :map.get("PARENT_AGENT_ID").toString());
                profitDeduction.setAgentPid(map.get("AGENT_ID").toString());
                profitDeduction.setAgentId(map.get("AGENT_ID").toString());
                profitDeduction.setAgentName(map.get("AGENT_NAME")== null ? "" :map.get("AGENT_NAME").toString());
                profitDeduction.setDeductionDate(map.get("DEDUCTION_DATE").toString());
                profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                profitDeduction.setDeductionDesc(map.get("DEDUCTION_DESC").toString());
                profitDeduction.setSumDeductionAmt(new BigDecimal(map.get("MUST_AMT").toString()));
                profitDeduction.setAddDeductionAmt(new BigDecimal(map.get("MUST_AMT").toString()));
                profitDeduction.setMustDeductionAmt(new BigDecimal(map.get("MUST_AMT").toString()));
                profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setRemark("");
                profitDeduction.setSourceId(map.get("SOURCE_ID").toString());
                profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                profitDeduction.setCreateDateTime(new Date());
                profitDeductionMapper.insertSelective(profitDeduction);
            }
        });
    }

    @Override
    public void updateProfitStagingDetail(ProfitStagingDetail profitStagingDetail) {
        if(profitStagingDetail != null){
            profitStagingDetailMapper.updateByPrimaryKeySelective(profitStagingDetail);
        }
    }

    @Override
    public List<Map<String, Object>> getNotDeductDetail(String beforeDeductDate, String deductDate, String type) {
        if(StringUtils.isBlank(beforeDeductDate)|| StringUtils.isBlank(deductDate)){
            return null;
        }
        return profitDeductionMapper.getNotDeductDetail(beforeDeductDate, deductDate, type);
    }
}
