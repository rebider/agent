package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.profit.dao.ProfitSupplyMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.ProfitMonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: 分润金额汇总任务
 * @date 2018/7/2911:34
 */
@Service("profitAmtSumJob")
@Transactional(rollbackFor = RuntimeException.class)
public class ProfitAmtSumJob {

    private static final Logger LOG = Logger.getLogger(ProfitAmtSumJob.class);


    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private ProfitMonthService profitMonthServiceImpl;

    @Autowired
    public RedisService redisService;

    @Autowired
    private ProfitSupplyMapper profitSupplyMapper;

    //@Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        LOG.info("分润月份" + settleMonth);
        LOG.info("获取分润数据");
        try {
            ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
            profitDetailMonth.setProfitDate(settleMonth);
//            profitDetailMonth.setAgentPid("AG20180817000000000006101"); //验证使用
            // 获取分润明细数据对分润汇总进行求和赋值
            List<ProfitDetailMonth> profitDetailMonthList = profitMonthServiceImpl.getProfitDetailMonthList(null, null, profitDetailMonth);
            if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
                profitDetailMonthList.stream().forEach(profitDetailMonthTemp -> {
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
                    BigDecimal rhbProfitAmt = profitDetailMonthTemp.getRhbProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getRhbProfitAmt();
                    BigDecimal posRewardAmt = profitDetailMonthTemp.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPosRewardAmt();

                    //退单补款
                    getTdSupplyAmt(profitDetailMonthTemp);

                    //机具返现
                    getToolsReturnAmt(profitDetailMonthTemp);

                    //分润汇总=付款交易分润额+出款交易分润额+瑞银信分润+瑞银信活动分润+贴牌分润+瑞刷分润+瑞刷活动分润+直发平台分润+POS直签补差分润+手刷直签补差分润+瑞和宝+POS奖励
                    profitDetailMonthTemp.setProfitSumAmt(payProfitAmt.add(tranProfitAmt).add(ryxProfitAmt).add(ryxHdProfitAmt).add(tpProfitAmt)
                            .add(rsProfitAmt).add(rsHdProfitAmt).add(zfProfitAmt).add(posZqSupplyProfitAmt).add(mposZqSupplyProfitAmt).add(rhbProfitAmt)
                            .add(posRewardAmt));

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
                redisService.delete("commitFinal");
                redisService.delete("payStatus");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

    }

    private void getTdSupplyAmt(ProfitDetailMonth profitDetailMonthTemp) {
        ProfitSupply profitSupply = new ProfitSupply();
        profitSupply.setParentAgentId(profitDetailMonthTemp.getParentAgentId());
        profitSupply.setAgentId(profitDetailMonthTemp.getAgentId());
        profitSupply.setSupplyDate(profitDetailMonthTemp.getProfitDate());
        profitSupply.setBusBigType("01");//退单补款大类标识
        profitSupply.setBusType("02");//pos退单补款小类标识
        // pos退单补款
        BigDecimal posSupply = profitSupplyMapper.getBuckleByMonthAndPid(profitSupply);
        profitDetailMonthTemp.setPosTdSupplyAmt(posSupply == null ? BigDecimal.ZERO : posSupply);
        // mpos退单补款
        profitSupply.setBusType("01");//手刷退单补款小类标识
        BigDecimal mposSupply = profitSupplyMapper.getBuckleByMonthAndPid(profitSupply);
        profitDetailMonthTemp.setMposTdSupplyAmt(mposSupply == null ? BigDecimal.ZERO : mposSupply);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 汇总机具返现
     * @Date: 17:05 2018/12/2
     */
    private void getToolsReturnAmt(ProfitDetailMonth profitDetailMonthTemp) {
        ProfitSupply profitSupply = new ProfitSupply();
        profitSupply.setParentAgentId(profitDetailMonthTemp.getParentAgentId());
        profitSupply.setAgentId(profitDetailMonthTemp.getAgentId());
        profitSupply.setSupplyDate(profitDetailMonthTemp.getProfitDate());
        profitSupply.setBusBigType("02");//机具返现大类标识
        BigDecimal posSupply = profitSupplyMapper.getBuckleByMonthAndPid(profitSupply);
        profitDetailMonthTemp.setToolsReturnAmt(posSupply == null ? BigDecimal.ZERO : posSupply);
    }

}
