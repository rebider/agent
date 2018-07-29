package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaodw
 * @Title: RefundJob
 * @ProjectName agentManage
 * @Description: 退单任务
 * @date 2018/7/2911:33
 */
@Component("refundJob")
public class RefundJob {
    private static final Logger LOG = Logger.getLogger(RefundJob.class);
    private static final  String URL =  AppConfig.getProperty("refund_url");

    @Autowired
    private StagingService stagingServiceImpl;

    @Autowired
    private ProfitDeductionService profitDeductionService;

    @Scheduled(cron = "0/5 0 * * * ?")
    public void deal() {
        LOG.info("每月定时获取退单数据。");
        getRefundList();

        LOG.info("对数据汇总并生成退单明细。");

        LOG.info("对数据汇总并生成扣款信息。");


    }

    /*** 
    * @Description:  获取退单列表
    * @return:  返回的退单列表
    * @Author: zhaodw 
    * @Date: 2018/7/29 
    */ 
    private JSONObject getRefundList() {
        // 上月的开始及结束日期
        LocalDate date = LocalDate.now().plusMonths(-1);
        String chargebackEnd = date.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        String chargebackStart = date.with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);

        Map<String, String> param = new HashMap<>(2);
        param.put("chargebackStart", chargebackStart);
        param.put("chargebackEnd", chargebackEnd);

        String result = HttpClientUtil.doPost(URL, param);
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }

}
