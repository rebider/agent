package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.ProfitDeduction;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

    @Override
    public void applyAdjustment(ProfitDeduction profitDeduction, String userId) throws ProcessException {

        LocalDate date = LocalDate.now();
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setId(idService.genId(TabId.P_STAGING_DETAIL));
        profitStagingDetail.setCurrentStagCount(1);
        profitStagingDetail.setDeductionDate(date.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        BigDecimal actualDeductionAmt = profitDeduction.getMustDeductionAmt().subtract(profitDeduction.getActualDeductionAmt());
        profitStagingDetail.setMustAmt(actualDeductionAmt);
        profitStagingDetail.setRealAmt(actualDeductionAmt);
        profitStagingDetail.setRemark(profitDeduction.getRemark());
        profitStagingDetail.setSourceId(profitDeduction.getSourceId());
        profitStagingDetail.setStagId(profitDeduction.getId());
        profitStagingDetail.setStatus(StagingDetailStatus.N.getStatus());
        profitStagingDetailMapper.insertSelective(profitStagingDetail);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "toolsInstallment", null, null, null);
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

    @Override
    public void completeTaskEnterActivity(String insid, String status) {
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
                    BigDecimal actualDeductionAmt = profitDeduction.getActualDeductionAmt().subtract(profitStagingDetail.getRealAmt());
                    profitDeduction.setActualDeductionAmt(actualDeductionAmt);
                    profitDeduction.setStagingStatus(DeductionStatus.PASS.getStatus());
                    profitDeduction.setRemark(profitStagingDetail.getRemark());
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
}
