package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.agent.AgentColinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lhl on 2019/4/17.
 * 代理商收款账户不唯一
 */
@Service("checkAgentColinfoJob")
public class CheckAgentColinfoJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(CheckAgentColinfoJob.class);
    @Autowired
    private AgentColinfoService agentColinfoService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======job:{},开始", shardingContext.getJobName());
            agentColinfoService.checkAgentColinfo();
            logger.info("======job:{},结束", shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("======Job:" + shardingContext.getJobName() + ",出错" ,e);
        }
    }

}
