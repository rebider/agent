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
 * 描述：
 */
@Service("cashSummaryMouthDataJob")
public class CashSummaryMouthDataJob  implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(CashSummaryMouthDataJob.class);
    @Autowired
    private OrderService orderService;

    /**
     * 每月1号的0点十分
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("任务：{},执行开始:{}",shardingContext.getJobName());
        orderService.CashSummaryMouth();
        logger.info("任务：{},执行完成:{}",shardingContext.getJobName());

    }
}
