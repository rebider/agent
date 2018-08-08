package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.GetMethod;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yangmx
 * @desc 机具分润扣款、机具扣款分期数据导入
 */
@Service
public class ToolsDeductJob {

    private static Logger LOG = LoggerFactory.getLogger(ToolsDeductJob.class);
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private ToolsDeductService toolsDeductService;
    @Autowired
    private ProfitDeductionService profitDeductionService;

//    @Scheduled(cron = "0 0/20/40 10 20 * ?")
    public void execut(){
        //String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        String deductDate = "2018-08";
        //String beforeDeductDate = LocalDate.now().plusMonths(-2).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);;
        String beforeDeductDate = "2018-07";
        try {
            int count = profitDeductionService.getProfitDeductionCount(DeductionType.MACHINE.getType(), deductDate);
            LOG.info("当月已初始化机具扣款分期数据：{} 条", count);
            List<Map<String, Object>> list = iPaymentDetailService.getShareMoney(GetMethod.AGENTDATE.code, null, deductDate);
            if(list!= null && !list.isEmpty()){
                LOG.info("接口获取机具扣款分期数据：{} 条", list.size());
                if(list.size() > count && count == 0 ){
                    List<Map<String, Object>> successList = toolsDeductService.batchInsertDeduct(list, deductDate);
                    LOG.info("机具扣款分期入库成功：{} 条", successList.size());
                    try {
                        //通知结果
                        iPaymentDetailService.uploadStatus(successList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            List<Map<String, Object>> detailList = profitDeductionService.getDeductDetail(deductDate);
            if(detailList != null && !detailList.isEmpty()){
                LOG.info("上月存在调整成功的机具扣款：{} 条，调整金额补充到本月扣款", detailList.size());
                toolsDeductService.deductCompletionInfo(detailList);
            }
            this.initNormalDeductDetail(beforeDeductDate, deductDate);
            this.initNotDeductDetailList(beforeDeductDate, deductDate);
        } catch (Exception e){
            LOG.error("初始化机具扣款数据失败");
            e.printStackTrace();
        }
    }

    /**
     * 上个月未扣足订单，且没有调整扣款金额，下月还有还款计划的订单，将未扣足金额调整到下期还款计划中
     * @param beforeDeductDate
     * @param deductDate
     */
    private void initNormalDeductDetail(String beforeDeductDate, String deductDate) {
        List<Map<String, Object>> normalDeductDetailList = toolsDeductService.getNotDeductDetail(beforeDeductDate, deductDate, "1");
        if(normalDeductDetailList != null && !normalDeductDetailList.isEmpty()){
            LOG.info("上个月未扣足订单，且没有调整扣款金额，下月还有还款计划的订单，将未扣足金额调整到下期还款计划中：{}条", normalDeductDetailList.size());
            normalDeductDetailList.forEach(map -> {
                ProfitDeduction profitDeduction = new ProfitDeduction();
                profitDeduction.setSourceId(map.get("SOURCE_ID").toString());
                profitDeduction.setAgentId(map.get("AGENT_ID").toString());
                profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                profitDeduction.setDeductionDate(deductDate);
                List<ProfitDeduction> stagesProfitDeduction = profitDeductionService.getProfitDeduction(profitDeduction);
                if(stagesProfitDeduction != null && !stagesProfitDeduction.isEmpty()){
                    ProfitDeduction updateProfitDeduction = stagesProfitDeduction.get(0);
                    BigDecimal sumAmt = updateProfitDeduction.getSumDeductionAmt().add(new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                    updateProfitDeduction.setSumDeductionAmt(sumAmt);
                    updateProfitDeduction.setMustDeductionAmt(sumAmt);
                    updateProfitDeduction.setUpperNotDeductionAmt(new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                    profitDeductionService.updateProfitDeduction(updateProfitDeduction);
                }
            });
        }
    }

    /**
     * 上月未扣足订单，且没有调整扣款金额，也没有后续分期计划，自动转换为下月扣款计划
     * @param beforeDeductDate
     * @param deductDate
     */
    private void initNotDeductDetailList(String beforeDeductDate, String deductDate) {
        List<Map<String, Object>> notDeductDetailList = toolsDeductService.getNotDeductDetail(beforeDeductDate, deductDate, "2");
        if(notDeductDetailList != null && !notDeductDetailList.isEmpty()){
            LOG.info("上月未扣足订单，且没有调整扣款金额，也没有后续分期计划，自动转换为下月扣款计划：{}条", notDeductDetailList.size());
            notDeductDetailList.forEach(map -> {
                ProfitDeduction profitDeduction = new ProfitDeduction();
                profitDeduction.setDeductionDate(deductDate);
                profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
                profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setSourceId(map.get("SOURCE_ID").toString());
                profitDeduction.setUpperNotDeductionAmt(new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                profitDeduction.setCreateDateTime(new Date());
                profitDeduction.setParentAgentId(map.get("PARENT_AGENT_ID") == null ? "" : map.get("PARENT_AGENT_ID").toString());
                profitDeduction.setParentAgentPid(map.get("PARENT_AGENT_PID") == null ? "" : map.get("PARENT_AGENT_PID").toString());
                profitDeduction.setAgentId(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                profitDeduction.setAgentPid(map.get("AGENT_PID") == null ? "" : map.get("AGENT_PID").toString());
                profitDeduction.setAgentName(map.get("AGENT_NAME") == null ? "" : map.get("AGENT_NAME").toString());
                profitDeduction.setSumDeductionAmt(new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                profitDeduction.setAddDeductionAmt(BigDecimal.ZERO);
                profitDeduction.setMustDeductionAmt(new BigDecimal(map.get("NOT_DEDUCTION_AMT").toString()));
                profitDeductionService.insert(profitDeduction);
            });
        }
    }
}