package com.ryx.credit.service.agent;

/**
 * 作者：cx
 * 时间：2019/4/3
 * 描述：
 */
public interface ColinfoTaskService {
    /**
     * 9:00 - 21:30
     */
//    @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 0/30 9-21 * * ? ")
    void synColinfoToPayment();

    /**
     * 定时查询交易
     * 30分钟执行一次
     */
//  @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    void synColinfoToQueryPayment();
}
