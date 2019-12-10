package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.service.QueryCardStatusJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 16:26
 * @Param
 * @return
 **/
@Service("internetCardQueryCardStatusJob")
public class InternetCardQueryCardStatusJob implements DataflowJob<OInternetCard> {

    private static Logger log = LoggerFactory.getLogger(InternetCardQueryCardStatusJob.class);

    @Autowired
    private QueryCardStatusJobService queryCardStatusJobService;


    @Override
    public List<OInternetCard> fetchData(ShardingContext shardingContext) {
        return queryCardStatusJobService.fetchDataUpdateCardStatus("");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<OInternetCard> list) {
        log.info("查询要更新状态的数据,不为空，processData开始");
        long t1 = System.currentTimeMillis();
        queryCardStatusJobService.processDataUpdateCardStatus(list);
        long t2 = System.currentTimeMillis();
        log.info("查询要更新状态的数据,为空，processData，处理时间:{} ms", (t2 - t1));
    }
}

