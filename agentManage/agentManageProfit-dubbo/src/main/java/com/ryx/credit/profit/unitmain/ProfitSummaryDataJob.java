package com.ryx.credit.profit.unitmain;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 汇总 cxinfo 月分润汇总 汪勇
 */
@Service("profitSummaryDataJob")
public class ProfitSummaryDataJob {
    org.slf4j.Logger logger = LoggerFactory.getLogger(NewProfitMonthMposDataJob.class);

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitDetailMonthMapper detailMonthMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private ProfitDayMapper dayMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProfitComputerService computerService;
    @Autowired
    private AgentColinfoService agentColinfoService;

    private int index = 1;

    public void excute(String transDate) {
        MPos_Summary(transDate);
    }

    /**
     * 汇总数据
     * transDate 交易月份（空则为上一月）
     * 每月12号上午12点：@Scheduled(cron = "0 0 12 12 * ?")
     */
//    @Scheduled(cron = "0 6 11 22 * ?")
    @Transactional
    public void MPos_Summary(String transDate) {

        transDate = transDate == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6) : transDate;

        logger.info("=========={}手刷月分润汇总开始==========", transDate);
        long t1 = System.currentTimeMillis();


        List<TransProfitDetail> details = transProfitDetailMapper.selectListByDate(transDate);//手刷同步过来的小汇数据
        for (TransProfitDetail detail : details) {
            boolean isAdd = false;
            Agent agent = agentService.getAgentById(detail.getAgentId());
            logger.info("{}手刷月分润汇总{}", transDate, detail.getAgentId());
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentId(detail.getAgentId());
            where.setParentAgentId(detail.getParentAgentId());
            where.setProfitDate(transDate);
            ProfitDetailMonth detailMonth = detailMonthMapper.selectByIdAndParent(where);
            if (null == detailMonth) {
                detailMonth = new ProfitDetailMonth();
                isAdd = true;
            }
            detailMonth.setProfitDate(transDate);
            //BigDecimal transAmt = detailMonth.getTranAmt() == null ? BigDecimal.ZERO : detailMonth.getTranAmt();
            detailMonth.setMposTranAmt(detail.getInTransAmt());
            if (detail.getBusCode().equals("0001")) {//瑞银信
                detailMonth.setRyxProfitAmt(detail.getProfitAmt());
            } else if (detail.getBusCode().equals("3000")) {//瑞刷活动
                detailMonth.setRsHdProfitAmt(detail.getProfitAmt());
            } else if (detail.getBusCode().equals("4000")) {//瑞众通
                //detailMonth
            } else if (detail.getBusCode().equals("5000")) {//瑞和宝
               /* ProfitDay day = new ProfitDay();
                day.setAgentId(detail.getBusNum());
                day.setTransDate(transDate);
                BigDecimal rhbDay = dayMapper.totalProfitAndReturnById(day);//瑞和宝日结分润-
                rhbDay = rhbDay==null?BigDecimal.ZERO:rhbDay;
                detailMonth.setRhbProfitAmt(detail.getProfitAmt().subtract(rhbDay));*/
                detailMonth.setRhbProfitAmt(detail.getProfitAmt().subtract(detail.getNotaxAmt()));
            } else if (detail.getBusCode().equals("6000")) {//直发
                detailMonth.setZfProfitAmt(detail.getProfitAmt().subtract(detail.getNotaxAmt()));
            } else if (detail.getBusCode().equals("2000")) {//瑞刷
                detailMonth.setRsProfitAmt(detail.getProfitAmt());
            } else if (detail.getBusCode().equals("1111")) {//瑞银信活动
                detailMonth.setRyxHdProfitAmt(detail.getProfitAmt());
            } else {//贴牌
                detailMonth.setTpProfitAmt(detail.getProfitAmt());
            }

            if (detail.getSupplyAmt() != null && detail.getSupplyAmt().compareTo(BigDecimal.ZERO) > 0) {
                //detailMonth.setMposZqSupplyProfitAmt(detailMonth.getMposZqSupplyProfitAmt() == null ? detail.getSupplyAmt() : detailMonth.getMposZqSupplyProfitAmt().add(detail.getSupplyAmt()));//手刷补差
                detailMonth.setMposZqSupplyProfitAmt(detail.getSupplyAmt());//手刷补差
            }
            //获取账户信息
            List<AgentColinfo> agentColinfos = agentColinfoService.queryAgentColinfoService(detail.getAgentId(), null, AgStatus.Approved.status);
            if (agentColinfos != null && agentColinfos.size() > 0) {
                AgentColinfo agentColinfo = agentColinfos.get(0);
                detailMonth.setAccountId(agentColinfo.getCloBankAccount());
                detailMonth.setAccountName(agentColinfo.getCloRealname());
                detailMonth.setOpenBankName(agentColinfo.getCloBank());
                detailMonth.setBankCode(agentColinfo.getBranchLineNum());
                detailMonth.setTax(String.valueOf(agentColinfo.getCloTaxPoint()));
                detailMonth.setPayStatus(agentColinfo.getCloType().toString());
            }

            if (isAdd) {//新增汇总
                detailMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
                detailMonth.setAgentName(null == agent ? "" : agent.getAgName());
                detailMonth.setAgentId(detail.getAgentId());
                detailMonth.setParentAgentId(detail.getParentAgentId());
                detailMonth.setAgentPid(detail.getAgentId());
                detailMonthMapper.insertSelective(detailMonth);
            } else {//更新汇总
                detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
            }

        }

        long t2 = System.currentTimeMillis();
        logger.info("=========={}手刷月分润汇总结束,耗时{}ms==========", transDate, (t2 - t1));

    }


}
