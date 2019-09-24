package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.order.CompensateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/9/24
 * 描述：活动调整结果查询job
 */
@Service("compensateResQueryJob")
public class CompensateResQueryJob  implements DataflowJob<String> {

    private Logger logger = LoggerFactory.getLogger(CompensateResQueryJob.class);

    private CompensateService compensateService;

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        return compensateService.querySendingOrefundPriceDiffDetail();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        for (String s : list) {
            try {
                logger.debug("补差价明细结果查询"+s);
                AgentResult res = compensateService.dealQeruySendingReault(s);
                logger.info("补差价明细结果查询：{} 完成 {}",s,res.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("补差价明细结果查询：{} 异常 {}",s,e.getMessage());
            }
        }
    }
}
