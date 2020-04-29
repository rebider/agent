package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.agent.AgentDebitCardFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author menglf
 * @Description
 * 数据提供：智慧POS/PLUS的一级代理商的结算卡信息，放置对账文件的FTP
 * @Date 2023/4/24 11:26
 * @Param
 * @return
 **/
@Service("agentDebitCardFileJob")
public class AgentDebitCardFileJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(SynColinfoToPaymentJob.class);

    @Autowired
    private AgentDebitCardFileService agentDebitCardFileService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            logger.info("======AgentDebitCardFileJob:{}，开始",shardingContext.getJobName());
            FastMap status = agentDebitCardFileService.exportsForZHposOrPlus();
            logger.info("======AgentDebitCardFileJob:{}，结束,执行结果：{}",shardingContext.getJobName(), status.toString());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("AgentDebitCardFileJob:"+shardingContext.getJobName()+",出错",e);
        }
    }

}
