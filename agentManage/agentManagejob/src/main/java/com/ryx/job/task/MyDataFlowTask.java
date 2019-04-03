package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/4/2
 * 描述：这是一个示例任务，数据流处理任务
 */
@Service("myDataFlowTask")
public class MyDataFlowTask implements DataflowJob {

    private Logger logger = LoggerFactory.getLogger(MyDataFlowTask.class);


    @Override
    public List fetchData(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0:
                logger.info("任务：{}，机器：{}",shardingContext.getJobName(),shardingContext.getShardingItem());
                return Arrays.asList("123","123123123");
            case 1:
                return new ArrayList();
            case 2:
                return new ArrayList();
            default:
                return  new ArrayList();
        }
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {
        logger.info("任务：{}，processData：{}",shardingContext.getJobName(),list);
    }
}
