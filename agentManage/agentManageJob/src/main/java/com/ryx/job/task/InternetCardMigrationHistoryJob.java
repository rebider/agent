package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardImport;
import com.ryx.internet.service.InternetCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 导入迁移到历史表
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/17 11:26
 * @Param
 * @return
 **/
@Service("internetCardMigrationHistoryJob")
public class InternetCardMigrationHistoryJob implements DataflowJob<OInternetCardImport> {

    private static Logger log = LoggerFactory.getLogger(InternetCardMigrationHistoryJob.class);

    @Autowired
    private InternetCardService internetCardService;


    @Override
    public List<OInternetCardImport> fetchData(ShardingContext shardingContext) {
        return internetCardService.queryMigrationHistory();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<OInternetCardImport> list) {
        long t1 = System.currentTimeMillis();
        for (OInternetCardImport internetCardImport : list) {
            try {
                internetCardService.migrationHistory(internetCardImport);
            } catch (MessageException e) {
                log.info("迁移出现异常");
                e.printStackTrace();
            }
        }
        long t2 = System.currentTimeMillis();
        log.info("为空定时任务更新商户信息，处理时间:{} ms", (t2 - t1));
    }
}
