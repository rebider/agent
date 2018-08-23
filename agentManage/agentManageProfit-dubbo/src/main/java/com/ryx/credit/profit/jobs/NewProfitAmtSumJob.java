package com.ryx.credit.profit.jobs;

import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhaodw
 * @Title: NewProfitAmtSumJob
 * @ProjectName agentManage
 * @Description: 新版分润金额汇总任务
 * @date 2018/7/2911:34
 */
@Service("newProfitAmtSumJob")
@Transactional(rollbackFor=RuntimeException.class)
public class NewProfitAmtSumJob {

    private static final Logger LOG = Logger.getLogger(NewProfitAmtSumJob.class);

    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private TransProfitDetailService transProfitDetailService;

    @Autowired
    private ProfitMonthService profitMonthServiceImpl;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {

    }
}
