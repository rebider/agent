package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.commons.utils.DigestUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/2/1
 * 描述：代理商账户完善任务
 */
@Service("agentLoginAccountCreateTask")
public class AgentLoginAccountCreateTask  implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(AgentLoginAccountCreateTask.class);
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
