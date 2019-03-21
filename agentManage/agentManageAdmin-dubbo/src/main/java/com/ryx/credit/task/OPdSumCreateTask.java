package com.ryx.credit.task;

import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.service.order.IOPdSumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 作者：chenliang
 * 时间：2019/3/18
 * 描述：分润抵扣机具欠款分期汇总
 */
@Component
public class OPdSumCreateTask {
    private Logger logger = LoggerFactory.getLogger(OPdSumCreateTask.class);
    @Autowired
    IOPdSumService oPdSumService;

    /**
     * 每月1号的0点30分
     */
    @Scheduled(cron = "0 30 0 1 * ?")
    public void CreateOPdSum() {

        logger.info("======分润抵扣机具欠款分期汇总开始任务执行======");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, +1);
        String date = DateUtil.format(c.getTime(), DateUtil.DATE_FORMAT_yyyyMM);
        logger.info("======分润抵扣机具欠款分期汇总任务执行======统计月份{}", date);
        try {
            oPdSumService.insertOPdSum();
            logger.info("======分润抵扣机具欠款分期汇总完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("======分润抵扣机具欠款分期汇总异常");
        }


    }
}

