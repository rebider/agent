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
 * @desc 机具扣款分期数据导入
 */
@Service
public class ToolsDeductJob {

    private static Logger LOG = LoggerFactory.getLogger(ToolsDeductJob.class);
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private ToolsDeductService toolsDeductService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void execut(){
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        try {
            List<Map<String, Object>> list = iPaymentDetailService.getShareMoney(GetMethod.AGENTDATE.code, null, deductDate);
            LOG.info("从订单系统，获取到需要扣款的机具欠款总计：{} 条", list  != null && !list.isEmpty() ? list.size() : 0);
            if(list!= null && !list.isEmpty()){
                toolsDeductService.batchInsertDeduct(list, deductDate);
            }
//            List<Map<String, Object>> detailList = profitDeductionService.getDeductDetail(deductDate);
//            LOG.info("上月调整成功的机具分期，新增到本月代理商机具扣款，总计调整成功：{} 条", detailList != null && !detailList.isEmpty() ? detailList.size() : 0);
//            if(detailList != null && !detailList.isEmpty()){
//                toolsDeductService.deductCompletionInfo(detailList);
//            }
        } catch (Exception e){
            LOG.error("初始化机具扣款数据失败");
            e.printStackTrace();
        }
    }

}