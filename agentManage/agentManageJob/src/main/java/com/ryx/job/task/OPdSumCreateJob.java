package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.service.order.IOPdSumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * 作者：cx
 * 时间：2019/4/3
 * 描述：
 */
@Service("oPdSumCreateJob")
public class OPdSumCreateJob   implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(OPdSumCreateJob.class);
    @Autowired
    IOPdSumService oPdSumService;

    /**
     * 每月1号的0点30分
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
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
            logger.error("======分润抵扣机具欠款分期汇总异常",e);
        }
    }
}
