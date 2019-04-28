package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.order.OsnOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/4/23
 * 描述：
 */
@Service("osnFaHuoGenSnDetailJob")
public class OsnFaHuoGenSnDetailJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(OsnFaHuoGenSnDetailJob.class);

    @Autowired
    private OsnOperateService osnOperateService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("sn明细生成任务开始执行");
        osnOperateService.genLogicDetailTask();
        logger.info("sn明细生成任务结束执行");
    }
}
