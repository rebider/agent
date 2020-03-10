package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.ActBusRelScanView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2020/3/10
 * 描述：月末触发任务
 */
@Service("mothOfEndJob")
public class MothOfEndJob implements SimpleJob {

    private Logger logger  = LoggerFactory.getLogger(MothOfEndJob.class);
    @Autowired
    private ActBusRelScanView actBusRelScanView;
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("===触发月末===");
        actBusRelScanView.triggerMothEndEvent();
        logger.info("===触发月末===");
    }
}
