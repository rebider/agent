package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.OOrderExample;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.job.dao.OOrderJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2019/4/2
 * 描述：这是一个简单的任务
 */
@Service("mySimpleTask")
public class MySimpleTask implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(MySimpleTask.class);

    @Autowired
    private OOrderJobMapper oOrderMapper;
    @Autowired
    private AgentService agentService;
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("MySimpleTask======jobName,{},getJobParameter:{},getShardingItem:{},getShardingParameter:{},getShardingTotalCount:{},getTaskId:{}",
                    shardingContext.getJobName(),
                    shardingContext.getJobParameter(),
                    shardingContext.getShardingItem(),
                    shardingContext.getShardingParameter(),
                    shardingContext.getShardingTotalCount(),
                    shardingContext.getTaskId());
            OOrderExample example = new OOrderExample();
            example.or().andStatusEqualTo(Status.STATUS_1.status);
            logger.info("MySimpleTask=====orderCount={}",oOrderMapper.countByExample(example)+"");
            AgentResult agentResult =  agentService.isAgent("123");
            logger.info("MySimpleTask=====isAgent={}",agentResult.getMsg()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
