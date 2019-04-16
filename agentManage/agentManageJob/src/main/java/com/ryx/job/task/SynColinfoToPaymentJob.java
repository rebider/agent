package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.agent.ColinfoTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2019/4/3
 * 描述：收款账户同步出款表 0 0/30 9-21 * * ?
 */
@Service("synColinfoToPaymentJob")
public class SynColinfoToPaymentJob   implements SimpleJob {
    private static Logger logger = LoggerFactory.getLogger(SynColinfoToPaymentJob.class);

    @Autowired
    private ColinfoTaskService colinfoTaskService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======job:{}，开始",shardingContext.getJobName());
            colinfoTaskService.synColinfoToPayment();
            logger.info("======job:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("Job:"+shardingContext.getJobName()+",出错",e);
        }
    }
}
