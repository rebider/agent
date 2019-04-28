package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.order.OLogisticsDetailHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lhl on 2019/4/24.
 * 物流明细的sn明细订单历史数据进行清理，移动到历史表
 */
@Service("transferLogisticsHistoryJob")
public class TransferLogisticsHistoryJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(CheckAgentColinfoJob.class);
    @Autowired
    private OLogisticsDetailHService oLogisticsDetailHService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======job:{},开始", shardingContext.getJobName());
            oLogisticsDetailHService.transferHistoryData();
            logger.info("======job:{},结束", shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("======Job:" + shardingContext.getJobName() + ",出错" ,e);
        }
    }
}
