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
 * 描述：定时查询交易 0 0/30 * * * ?
 */
@Service("synColinfoToQueryPaymentJob")
public class SynColinfoToQueryPaymentJob   implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(SynColinfoToPaymentJob.class);
    @Autowired
    private ColinfoTaskService colinfoTaskService;
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======job:{}，开始",shardingContext.getJobName());
            colinfoTaskService.synColinfoToQueryPayment();
            logger.info("======job:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("======Job:"+shardingContext.getJobName()+",出错",e);
        }

    }
}
