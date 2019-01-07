package com.ryx.credit.task;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.dao.order.CashSummaryMouthMapper;
import com.ryx.credit.pojo.admin.order.CashSummaryMouth;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/1/7
 * 描述：代理商月度打款金额开票不开票信息统计
 */
@Component
public class CashSummaryMouthDataTask {

    private Logger logger = LoggerFactory.getLogger(CashSummaryMouthDataTask.class);

    @Autowired
    private CashSummaryMouthMapper cashSummaryMouthMapper;
    @Autowired
    private OrderService orderService;



    /**
     * 每月1号的0点十分
     */
    @Scheduled(cron = "0 10 0 1 * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void CashSummaryMouth(){
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行======");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,-1);
        String date = DateUtil.format(c.getTime(),DateUtil.DATE_FORMAT_yyyyMM);
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行======统计月份{}",date);
        //不开票信息
        List<CashSummaryMouth> res =  cashSummaryMouthMapper.selectCashSummaryMouthData(date,"0");
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行=统计月份:{}=不开票信息:{}",date,res.size());
        for (CashSummaryMouth re : res) {
            try {
                AgentResult summaryMouth = orderService.insertSelectiveCashSummaryMouth(re);
                if(summaryMouth.isOK()){
                    logger.info("======代理商月度打款金额不开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }else{
                    logger.info("======代理商月度打款金额不开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("======代理商月度打款金额开票不开票信息统计任务执行异常({})=统计月份:{}=代理商:{}=金额:{}",e.getLocalizedMessage(),date,re.getAgentId(),re.getPayAmount());
            }
        }

        //开票信息
        res =  cashSummaryMouthMapper.selectCashSummaryMouthData(date,"1");
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行=统计月份:{}=开票信息:{}",date,res.size());
        for (CashSummaryMouth re : res) {
            try {
                AgentResult summaryMouth = orderService.insertSelectiveCashSummaryMouth(re);
                if(summaryMouth.isOK()){
                    logger.info("======代理商月度打款金额开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }else{
                    logger.info("======代理商月度打款金额开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("======代理商月度打款金额开票信息统计任务执行异常({})=统计月份:{}=代理商:{}=金额:{}",e.getLocalizedMessage(),date,re.getAgentId(),re.getPayAmount());
            }
        }
    }




}
