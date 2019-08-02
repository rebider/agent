package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.service.order.TerminalTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author Chen Liang
 * @Description //TODO
 * @Date 2019/08/01
 * @Param
 * @return
 **/
@Service("terminalTransferJob")
public class TerminalTransferJob implements SimpleJob {
    private static Logger logger = LoggerFactory.getLogger(TerminalTransferJob.class);

    @Autowired
    private TerminalTransferService terminalTransferService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======TerminalTransferJob:{}，开始",shardingContext.getJobName());
            terminalTransferService.queryTerminalTransferResult();
            logger.info("======TerminalTransferJob:{}，结束",shardingContext.getJobName());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("TerminalTransferJob:"+shardingContext.getJobName()+",出错",e);
        }
    }
}
