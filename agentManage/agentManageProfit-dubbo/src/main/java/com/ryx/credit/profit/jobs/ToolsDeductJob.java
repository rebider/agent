package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.GetMethod;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.tools.jar.Main;

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
        String deductDate = "2018-09";
        try {

            //判断当月数据是否已经初始化
            int count = profitDeductionService.getProfitDeductionCount(DeductionType.MACHINE.getType(), deductDate);
            LOG.info("当月已初始化机具扣款分期数据：{} 条", count);
            //调用接口查询分期数据
            List<Map<String, Object>> list = iPaymentDetailService.getShareMoney(GetMethod.AGENTDATE.code, null, deductDate);
            if(list!= null && !list.isEmpty()){
                LOG.info("接口获取机具扣款分期数据：{} 条", list.size());
                if(list.size() - count > 5){
                    //批量插入机具扣款分期数据
                    List<Map<String, Object>> successList = toolsDeductService.batchInsertDeduct(list, deductDate);
                    LOG.info("机具扣款分期入库成功：{} 条", successList.size());
                    //查询上月存在分期调整的数据，
                    List<Map<String, Object>> detailList = profitDeductionService.getDeductDetail(deductDate);
                    LOG.info("上月调整成功的机具扣款：{} 条", detailList.size());
                    //补全本月扣款信息（上月未扣足金额）
                    toolsDeductService.deductCompletionInfo(detailList);
                    //通知结果
                    iPaymentDetailService.uploadStatus(successList);
                }
            }
        } catch (Exception e){
            LOG.error("初始化机具扣款数据失败");
            e.printStackTrace();
        }
    }


    public void computeToolsDeduct(){


    }
}