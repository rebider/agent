package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.GetMethod;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Scheduled(cron = "0 0/5 9 * * ?")
    public void execut(){
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        try {
            List<Map<String, Object>> list = iPaymentDetailService.getShareMoney(GetMethod.AGENTDATE.code, null, deductDate);
            if(list!= null && !list.isEmpty()){
                LOG.info("接口获取机具扣款分期数据：{} 条", list.size());
                List<Map<String, Object>> successList = toolsDeductService.batchInsertDeduct(list, deductDate);
            }
            List<Map<String, Object>> detailList = profitDeductionService.getDeductDetail(deductDate);
            if(detailList != null && !detailList.isEmpty()){
                LOG.info("上月存在调整成功的机具扣款，新增一条到本月扣款中，：{} 条", detailList.size());
                toolsDeductService.deductCompletionInfo(detailList);
            }
        } catch (Exception e){
            LOG.error("初始化机具扣款数据失败");
            e.printStackTrace();
        }
    }

}