package com.ryx.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx
 * 时间：2019/4/2
 * 描述：前置后置任务分布式监听实现类
 */
public class ExmpDistributedListener extends AbstractDistributeOnceElasticJobListener {
    private Logger logger = LoggerFactory.getLogger(ExmpDistributedListener.class);

    public ExmpDistributedListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        logger.info("分布式监听器开始……{}",shardingContexts.getJobName());
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        logger.info("分布式监听器结束……{}",shardingContexts.getJobName());
    }
}
