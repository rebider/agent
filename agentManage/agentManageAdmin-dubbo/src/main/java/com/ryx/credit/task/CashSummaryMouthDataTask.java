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
    public void CashSummaryMouth(){
        orderService.CashSummaryMouth();
    }




}
