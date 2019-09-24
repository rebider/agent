package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.order.OsnOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 作者：cx
 * 时间：2019/4/23
 * 描述：
 */
@Service("osnTuiHuoGenSnDetailJob")
public class OsnReturnGenSnDetailJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(OsnReturnGenSnDetailJob.class);

    @Resource(name = "osnOperateReturnServiceImpl")
    private OsnOperateService osnReturnOperateService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("退货发货sn明细生成任务开始执行");
        osnReturnOperateService.genLogicDetailTask();
        logger.debug("退货发货sn明细生成任务结束执行");
    }
}