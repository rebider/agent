package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.internet.service.LogoutMobileStopJobService;
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
@Service("internetCardLogoutMobileStopJob")
public class InternetCardLogoutMobileStopJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(SynColinfoToPaymentJob.class);

    @Autowired
    private LogoutMobileStopJobService logoutMobileStopJobService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======logoutMobileStopJob:{}，开始",shardingContext.getJobName());
            logoutMobileStopJobService.logoutMobileStopJob();
            logger.info("======logoutMobileStopJob:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("logoutMobileStopJob:"+shardingContext.getJobName()+",出错",e);
        }
    }

}
