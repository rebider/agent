package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.GetMethod;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.tools.jar.Main;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
//        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        String deductDate = "2019-02";
        try {

            //判断当月数据是否已经初始化
            int count = profitDeductionService.getProfitDeductionCount(DeductionType.MACHINE.getType(), deductDate);
            LOG.info("当月已初始化机具扣款分期数据：{} 条", count);
            //调用接口查询分期数据
            List<Map<String, Object>> list = iPaymentDetailService.getShareMoney(GetMethod.AGENTDATE.code, null, deductDate);
            if(list!= null && !list.isEmpty()){
                LOG.info("接口获取机具扣款分期数据：{} 条", list.size());
                if(list.size() > count && count != 0 ){
                    //批量插入机具扣款分期数据
                    List<Map<String, Object>> successList = toolsDeductService.batchInsertDeduct(list, deductDate);
                    LOG.info("机具扣款分期入库成功：{} 条", successList.size());
                    //通知结果
                    try {
                        iPaymentDetailService.uploadStatus(successList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //查询上月存在分期调整的数据，
            List<Map<String, Object>> detailList = profitDeductionService.getDeductDetail(deductDate);
            if(detailList != null && !detailList.isEmpty()){
                LOG.info("上月调整成功的机具扣款：{} 条", detailList.size());
                //补全本月扣款信息（上月未扣足金额）
                toolsDeductService.deductCompletionInfo(detailList);
            }
        } catch (Exception e){
            LOG.error("初始化机具扣款数据失败");
            e.printStackTrace();
        }
    }

    /**
     * 平台编号
     * @param agentId 代理商编号
     * @param paltformNo 平台编号
     * @param deductDate 扣款月份
     * @param agentProfitAmt 代理商分润金额
     * @param parentAgentProfitAmt 担保代理商分润金额
     */
    public void computeToolsDeduct(String agentId, String paltformNo, String deductDate, BigDecimal agentProfitAmt, BigDecimal parentAgentProfitAmt){
        LOG.info("机具分润扣款：agentId：{}，paltformNo：{}，deductDate：{}，agentProfitAmt：{}，parentAgentProfitAmt：{}",
                agentId, paltformNo, deductDate, agentProfitAmt, parentAgentProfitAmt);
        if(StringUtils.isBlank(agentId) || StringUtils.isBlank(paltformNo) || StringUtils.isBlank(deductDate)
        || agentProfitAmt == null || parentAgentProfitAmt == null){
            LOG.error("机具分润扣款失败，请求参数为空");
        }
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentId);
        profitDeduction.setAgentPid(paltformNo);
        profitDeduction.setDeductionDate(deductDate);
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if(list != null && !list.isEmpty()){
            for (ProfitDeduction profitDeductionList:  list) {
                LOG.info("机具分润扣款：代理商编号：{}，机具类型：{}，代理商分润：{}，担保代理商分润：{}",
                        agentId, paltformNo, agentProfitAmt.toString(), parentAgentProfitAmt.toString());
                LOG.info("扣款信息：代理商编号：{}，扣款总额：{}，应扣：{}",profitDeductionList.getAgentId(),profitDeductionList.getSumDeductionAmt(),profitDeductionList.getMustDeductionAmt());
                if(agentProfitAmt.compareTo(profitDeductionList.getMustDeductionAmt()) >= 0 ){
                    agentProfitAmt = agentProfitAmt.subtract(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    profitDeductionService.updateProfitDeduction(profitDeductionList);
                } else {
                    //未扣足部分
                    BigDecimal notDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(agentProfitAmt);
                    //代理商月分润已扣完。
                    agentProfitAmt = BigDecimal.ZERO;
                    //从担保代理商分润进行扣款
                    if(parentAgentProfitAmt.compareTo(notDeductionAmt) >= 0){
                        parentAgentProfitAmt = parentAgentProfitAmt.subtract(notDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    } else {
                        //担保代理商月分润不足
                        BigDecimal parentProfitNotDeductionAmt = notDeductionAmt.subtract(parentAgentProfitAmt);
                        //担保代理商月分润已扣完。
                        parentAgentProfitAmt = BigDecimal.ZERO;
                        profitDeductionList.setNotDeductionAmt(parentProfitNotDeductionAmt);
                        BigDecimal actualDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(parentProfitNotDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
                    }
                    profitDeductionList.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    profitDeductionService.updateProfitDeduction(profitDeductionList);
                }
            }
        }
        LOG.info("机具扣款结束。代理商剩余分润：{}，担保代理商剩余分润：{}", agentProfitAmt, parentAgentProfitAmt);
    }
}