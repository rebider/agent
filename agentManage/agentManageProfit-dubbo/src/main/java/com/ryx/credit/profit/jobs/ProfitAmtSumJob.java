package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.service.OrganTranMonthDetailService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: 分润金额汇总任务
 * @date 2018/7/2911:34
 */
@Service("profitAmtSumJob")
@Transactional(rollbackFor=RuntimeException.class)
public class ProfitAmtSumJob {

    private static final Logger LOG = Logger.getLogger(ProfitAmtSumJob.class);


    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private ProfitMonthService profitMonthServiceImpl;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+settleMonth);
        LOG.info("获取分润数据");
        try {
            ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
            profitDetailMonth.setProfitDate(settleMonth);
            // 获取分润明细数据对分润汇总进行求和赋值
            List<ProfitDetailMonth> profitDetailMonthList = profitMonthServiceImpl.getProfitDetailMonthList(null, profitDetailMonth);
            if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
                profitDetailMonthList.stream().forEach( profitDetailMonthTemp->{
                    BigDecimal payProfitAmt = profitDetailMonthTemp.getPayProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPayProfitAmt();
                    BigDecimal tranProfitAmt = profitDetailMonthTemp.getTranProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getTranProfitAmt();
                    BigDecimal ryxProfitAmt = profitDetailMonthTemp.getRyxProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getRyxProfitAmt();
                    BigDecimal ryxHdProfitAmt = profitDetailMonthTemp.getRyxHdProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getRyxHdProfitAmt();
                    BigDecimal tpProfitAmt = profitDetailMonthTemp.getTpProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getTpProfitAmt();
                    BigDecimal rsProfitAmt = profitDetailMonthTemp.getRsProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getRsProfitAmt();
                    BigDecimal rsHdProfitAmt = profitDetailMonthTemp.getRsHdProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getRsHdProfitAmt();
                    BigDecimal zfProfitAmt = profitDetailMonthTemp.getZfProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getZfProfitAmt();
                    BigDecimal posZqSupplyProfitAmt = profitDetailMonthTemp.getPosZqSupplyProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPosZqSupplyProfitAmt();
                    BigDecimal mposZqSupplyProfitAmt = profitDetailMonthTemp.getMposZqSupplyProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getMposZqSupplyProfitAmt();
                    profitDetailMonthTemp.setProfitSumAmt(payProfitAmt.add(tranProfitAmt).add(ryxProfitAmt).add(ryxHdProfitAmt).add(tpProfitAmt)
                            .add(rsProfitAmt).add(rsHdProfitAmt).add(zfProfitAmt).add(posZqSupplyProfitAmt).add(mposZqSupplyProfitAmt));
                    profitDetailMonthServiceImpl.update(profitDetailMonthTemp);
                    payProfitAmt = null;
                    tranProfitAmt = null;
                    ryxProfitAmt = null;
                    ryxHdProfitAmt = null;
                    tpProfitAmt = null;
                    rsProfitAmt = null;
                    rsHdProfitAmt = null;
                    zfProfitAmt = null;
                    posZqSupplyProfitAmt = null;
                    mposZqSupplyProfitAmt = null;
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

    }
}
