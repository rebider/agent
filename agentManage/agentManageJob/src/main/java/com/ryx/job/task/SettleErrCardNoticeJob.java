package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.profit.service.PBalanceSerialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 分润出款结算卡异常通知处理任务
 * @author  cqt
 * @date    2020-04-30 15:25
 */
@Service("settleErrCardNoticeJob")
public class SettleErrCardNoticeJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(SettleErrCardNoticeJob.class);

    @Autowired
    private PBalanceSerialService pBalanceSerialService;

    /**
     * 每半小时执行一次
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("****************分润出款结算卡异常通知处理任务开始{}**************",shardingContext.getJobName());
        //List<Map<String,String>>  list = pBalanceSerialService.getNeedNoticeList();
        //if(list.size() > 0){
        //    pBalanceSerialService.updateNoticeStatus(list,"01");
        //}
        logger.info("****************分润出款结算卡异常通知处理任务结束{}**************",shardingContext.getJobName());
    }
}
