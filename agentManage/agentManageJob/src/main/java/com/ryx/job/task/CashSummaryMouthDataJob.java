package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2019/4/3
 * 描述： 代理商月度打款金额开票不开票信息统计
 */
@Service("cashSummaryMouthDataJob")
public class CashSummaryMouthDataJob  implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(CashSummaryMouthDataJob.class);
    @Autowired
    private OrderService orderService;

    /**
     * 每月1号的0点十分
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======job:{}，开始",shardingContext.getJobName());
            orderService.CashSummaryMouth();
            logger.info("======job:{}，结束",shardingContext.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Job:"+shardingContext.getJobName()+",出错",e);

        }

    }
}
