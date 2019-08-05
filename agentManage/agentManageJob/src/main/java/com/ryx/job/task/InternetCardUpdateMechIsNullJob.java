package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.service.InternetCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 更新商户为空的数据
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/17 11:26
 * @Param
 * @return
 **/
@Service("internetCardUpdateMechIsNullJob")
public class InternetCardUpdateMechIsNullJob implements DataflowJob<OInternetCard> {

    private static Logger log = LoggerFactory.getLogger(InternetCardUpdateMechIsNullJob.class);

    @Autowired
    private InternetCardService internetCardService;


    @Override
    public List<OInternetCard> fetchData(ShardingContext shardingContext) {
        return internetCardService.fetchDataMechIsNull();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<OInternetCard> list) {
        long t1 = System.currentTimeMillis();
        for (OInternetCard internetCard : list) {
            internetCardService.processDataUpdateMechIsNull(internetCard);
        }
        long t2 = System.currentTimeMillis();
        log.info("为空定时任务更新商户信息，处理时间:{} ms", (t2 - t1));
    }
}
