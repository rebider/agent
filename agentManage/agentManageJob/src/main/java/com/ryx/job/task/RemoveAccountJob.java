package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.credit.service.order.ORemoveAccountService;
import com.ryx.credit.service.order.OsnOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/9/24 11:27
 * @Description:销账的任务
 */
@Service("removeAccountJob")
public class RemoveAccountJob implements DataflowJob<String> {

    private static Logger logger = LoggerFactory.getLogger(RemoveAccountJob.class);

    @Autowired
    private OsnOperateService osnOperateService;

    @Autowired
    private ORemoveAccountService oRemoveAccountService;

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        try {
            logger.debug("销账开始获取数据");
            return osnOperateService.fetchFhData(0,shardingContext.getShardingItem());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("销账开始处理失败",e);
        }
        return new ArrayList<>();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        logger.info("销账开始处理数据");
        if(osnOperateService.processData(list)){
            logger.info("销账开始处理成功");
        }else{
            logger.info("销账开始处理失败");
        }
    }

}