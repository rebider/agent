package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.internet.service.InternetCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 更新已修改的商户信息（是续费，正常）
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/17 11:26
 * @Param
 * @return
 **/
@Service("internetCardUpdateMechJob")
public class InternetCardUpdateMechJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(InternetCardUpdateMechJob.class);

    @Autowired
    private InternetCardService internetCardService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======InternetCardUpdateMechJob:{}，开始",shardingContext.getJobName());
            internetCardService.taskUpdateMech();
            logger.info("======InternetCardUpdateMechJob:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("InternetCardUpdateMechJob:"+shardingContext.getJobName()+",出错",e);
        }
    }

}
