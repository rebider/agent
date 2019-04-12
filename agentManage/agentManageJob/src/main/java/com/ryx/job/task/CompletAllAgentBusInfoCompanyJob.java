package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 作者：cx
 * 时间：2019/4/12
 * 描述：修复代理商业务平台的打款公司
 */
@Service("completAllAgentBusInfoCompanyJob")
public class CompletAllAgentBusInfoCompanyJob implements SimpleJob {
    private static Logger logger = LoggerFactory.getLogger(CompletAllAgentBusInfoCompanyJob.class);
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("开始修复代理商业务平台的打款公司");
       AgentResult ag =  agentBusinfoService.completAllAgentBusInfoCompany();
        logger.info("开始修复代理商业务平台的打款公司,修复结果{}",ag.getMsg());
    }
}
