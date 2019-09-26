package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.internet.service.OInternetRenewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/***
 * 物联网卡生成轧差每日汇总
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/17 11:26
 * @Param
 * @return
 **/
@Service("internetCardOffsetDetailJob")
public class InternetCardOffsetDetailJob  implements SimpleJob {

    private static Logger log = LoggerFactory.getLogger(InternetCardOffsetDetailJob.class);

    @Autowired
    private OInternetRenewService internetRenewService;


    @Override
    public void execute(ShardingContext shardingContext) {
        long t1 = System.currentTimeMillis();
        try {
            log.info("======InternetCardOffsetDetailJob:{}，开始",shardingContext.getJobName());
            internetRenewService.processDataInternetCardOffset();
            log.info("======InternetCardOffsetDetailJob:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            log.error("InternetCardOffsetDetailJob:"+shardingContext.getJobName()+",出错",e);
        }
        long t2 = System.currentTimeMillis();
        log.info("物联网卡生成轧差明细每日汇总，处理时间:{} ms", (t2 - t1));
    }

}
