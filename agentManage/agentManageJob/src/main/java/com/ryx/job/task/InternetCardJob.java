package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.internet.service.InternetCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/17 11:26
 * @Param
 * @return
 **/
@Service("internetCardJob")
public class InternetCardJob  implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(SynColinfoToPaymentJob.class);

    @Autowired
    private InternetCardService internetCardService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======InternetCardJob:{}，开始",shardingContext.getJobName());
            internetCardService.taskDisposeInternetCard();
            logger.info("======InternetCardJob:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("InternetCardJob:"+shardingContext.getJobName()+",出错",e);
        }
    }

}
