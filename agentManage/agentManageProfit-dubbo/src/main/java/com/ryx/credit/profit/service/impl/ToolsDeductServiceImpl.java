package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusActRelBusType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    /**
     * 扣款总额=本月新增+上月未口足，
     * 调整金额=本月应扣，
     * 实际扣款数=本月实扣，
     * 未扣足=本月应扣-本月实扣
     * @param profitDeduction
     * @param userId
     * @throws ProcessException
     */
    @Override
    public void applyAdjustment(ProfitDeduction profitDeduction, String userId, String workId) throws ProcessException {

        LocalDate date = LocalDate.now();
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setId(idService.genId(TabId.P_STAGING_DETAIL));
        profitStagingDetail.setCurrentStagCount(1);
        profitStagingDetail.setDeductionDate(date.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        BigDecimal mustDeductionAmt = profitDeduction.getSumDeductionAmt().subtract(profitDeduction.getMustDeductionAmt());
        profitStagingDetail.setMustAmt(mustDeductionAmt);
        profitStagingDetail.setRealAmt(BigDecimal.ZERO);
        profitStagingDetail.setRemark("机具扣款分期调整下月还款计划");
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
            LOG.error("机具扣款调整审批流启动失败，代理商ID：{}", profitDeduction.getAgentId());
            throw new ProcessException("机具扣款调整审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(profitStagingDetail.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.TOOLS.name());
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
                    LOG.info("1.更新机具扣款申请状态为申请通过");
                    LOG.info("审批通过，未申请前本月实扣：{},申请扣款数：{}",profitDeduction.getActualDeductionAmt(),profitStagingDetail.getRealAmt());
                    profitDeduction.setStagingStatus(DeductionStatus.PASS.getStatus());
                    profitDeductionMapper.updateByPrimaryKeySelective(profitDeduction);
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

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void editToolDeduct(ProfitDeduction profitDeduction, String detailId) throws Exception{
        try {
            profitDeductionMapper.updateByPrimaryKeySelective(profitDeduction);
            ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
            profitStagingDetail.setId(detailId);
            profitStagingDetail.setStagId(profitDeduction.getId());
            BigDecimal mustDeductionAmt = profitDeduction.getSumDeductionAmt().subtract(profitDeduction.getMustDeductionAmt());
            profitStagingDetail.setMustAmt(mustDeductionAmt);
            profitStagingDetail.setRemark("机具扣款分期调整下月还款计划");
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
                if(map.get("ID") != null && map.get("ORDER_ID") != null ){
                    try {
                        ProfitDeduction profitDeduction = new ProfitDeduction();
                        profitDeduction.setId(map.get("ID").toString());
                        profitDeduction.setParentAgentId(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                        profitDeduction.setParentAgentPid(map.get("ORDER_PLATFORM") == null ? "" : map.get("ORDER_PLATFORM").toString());
                        profitDeduction.setAgentId(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                        profitDeduction.setAgentPid(map.get("ORDER_PLATFORM") == null ? "" : map.get("ORDER_PLATFORM").toString());
                        profitDeduction.setAgentName(map.get("AG_NAME") == null ? "" : map.get("AG_NAME").toString());
                        profitDeduction.setDeductionDate(deductionDate);
                        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                        profitDeduction.setSumDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                        profitDeduction.setAddDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                        profitDeduction.setMustDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                        profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                        profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                        profitDeduction.setSourceId(map.get("ORDER_ID").toString());
                        profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);
                        profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                        profitDeduction.setCreateDateTime(new Date());
                        profitDeductionMapper.insertSelective(profitDeduction);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ProcessException("机具扣款调整申请审批流启动失败!:{}",e.getMessage());
                    }
                    Map<String, Object> successMap = new HashMap<String, Object>(2);
                    successMap.put("detailId",map.get("ID"));
                    successMap.put("srcId",map.get("ORDER_ID"));
                    successList.add(successMap);
                }
            });
            return successList;
        }
        return null;
    }

    @Override
    public void deductCompletionInfo(List<Map<String, Object>> detailList) {
        if(detailList != null && !detailList.isEmpty()){
            detailList.forEach(map -> {
                // 查找扣款信息
                if(StringUtils.isNotBlank(map.get("SOURCE_ID").toString())){
                    ProfitDeductionExample profitDeductionExample = new ProfitDeductionExample();
                    ProfitDeductionExample.Criteria criteria = profitDeductionExample.createCriteria();
                    criteria.andDeductionDateEqualTo(map.get("DEDUCTION_DATE").toString());
                    criteria.andSourceIdEqualTo(map.get("SOURCE_ID").toString());
                    List<ProfitDeduction> list = profitDeductionMapper.selectByExample(profitDeductionExample);
                    if(list == null || list.isEmpty()){
                        LOG.error("本月已经不存在分期订单，将之前调整的扣款金额，新增到本月还款，流水号：{}",map.get("SOURCE_ID"));
                        ProfitDeduction profitDeduction = profitDeductionMapper.selectByPrimaryKey(map.get("STAG_ID").toString());
                        BigDecimal upperNotDeductionAmt = new BigDecimal(map.get("MUST_AMT").toString())
                                .add(map.get("NOT_DEDUCTION_AMT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                        profitDeduction.setId(idService.genId(TabId.P_DEDUCTION));
                        profitDeduction.setSourceId(map.get("SOURCE_ID").toString());
                        profitDeduction.setDeductionDate(map.get("DEDUCTION_DATE").toString());
                        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                        profitDeduction.setSumDeductionAmt(upperNotDeductionAmt);
                        profitDeduction.setAddDeductionAmt(new BigDecimal(map.get("MUST_AMT").toString()));
                        profitDeduction.setMustDeductionAmt(upperNotDeductionAmt);
                        profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                        profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                        profitDeduction.setUpperNotDeductionAmt(map.get("NOT_DEDUCTION_AMT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                        profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                        profitDeduction.setCreateDateTime(new Date());
                        profitDeductionMapper.insertSelective(profitDeduction);
                    } else {
                        try {
                            //计算补全信息
                            ProfitDeduction profitDeduction = list.get(0);
                            BigDecimal upperNotDeductionAmt = new BigDecimal(map.get("MUST_AMT").toString())
                                    .add(map.get("NOT_DEDUCTION_AMT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                            profitDeduction.setUpperNotDeductionAmt(upperNotDeductionAmt);
                            BigDecimal sumDeductionAmt = profitDeduction.getSumDeductionAmt().add(profitDeduction.getUpperNotDeductionAmt());
                            profitDeduction.setSumDeductionAmt(sumDeductionAmt);
                            profitDeduction.setMustDeductionAmt(sumDeductionAmt);
                            profitDeductionMapper.updateByPrimaryKeySelective(profitDeduction);
                        } catch (Exception e) {
                            LOG.error("补全机具扣款分期数据异常，流水号：{}",map.get("SOURCE_ID"));
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            LOG.info("本月没有调整机具分期的订单");
        }
    }

    @Override
    public void updateProfitStagingDetail(ProfitStagingDetail profitStagingDetail) {
        if(profitStagingDetail != null){
            profitStagingDetailMapper.updateByPrimaryKeySelective(profitStagingDetail);
        }
    }
}
