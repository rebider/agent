package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.agent.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2019/2/1
 * 描述：代理商账户完善任务
 */
@Service("agentLoginAccountCreateJob")
public class AgentLoginAccountCreateJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(AgentLoginAccountCreateJob.class);
    @Autowired
    private AgentService agentService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            AgentResult agentResult = agentService.createAgentAccount();
            logger.info("任务：{},执行结果:{}",shardingContext.getJobName(),agentResult.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AgentLoginAccountCreateTask异常：",e);
        }
    }
}
