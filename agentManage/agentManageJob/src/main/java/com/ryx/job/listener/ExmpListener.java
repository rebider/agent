package com.ryx.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx
 * 时间：2019/4/2
 * 描述： 前置后置任务监听实现类，需实现ElasticJobListener接口
 */
public class ExmpListener  implements ElasticJobListener {
    private Logger logger = LoggerFactory.getLogger(ExmpListener.class);

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        logger.debug("开始调度任务：{}",shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        logger.debug("任务调度完成：{}",shardingContexts.getJobName());
    }
}
