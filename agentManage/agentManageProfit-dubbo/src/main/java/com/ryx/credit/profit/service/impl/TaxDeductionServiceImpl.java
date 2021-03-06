package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TaxDeductionDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;
import com.ryx.credit.profit.service.ITaxDeductionService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 扣税
 * @Date: 9:42 2018/12/20
 */
@Service("taxDeductionService")
public class TaxDeductionServiceImpl implements ITaxDeductionService {

    Logger logger = LoggerFactory.getLogger(TaxDeductionServiceImpl.class);

    @Autowired
    TaxDeductionDetailMapper taxDeductionDetailMapper;
    @Autowired
    IdService idService;
    @Autowired
    ProfitDetailMonthMapper profitDetailMonthMapper;


    /**
     * @Author: Zhang Lei
     * @Description: 所有代理商扣税计算
     * @Date: 19:30 2018/12/20
     */
    @Override
    public void taxDeductionComputer() {
        Map<String, Object> params = new HashMap<>();
        String month = (String) params.get("profitMonth");
        final String profitMonth = month == null ? new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -1)) : month;
        params.put("profitMonth", profitMonth);
        params.put("preMonth", new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -2)));

        logger.info("=========={}月扣税计算开始=========", profitMonth);
        logger.info("清除数据");
        taxDeductionDetailMapper.deleteByMonth(profitMonth);

        //直签代理商扣税
        List<Map<String, Object>> agentList = taxDeductionDetailMapper.queryTaxDeductionAgentList(params);
        if (agentList == null) {
            throw new RuntimeException("查询直签扣税代理商失败");
        }

        agentList.parallelStream().forEach(map -> {
            try {
                String agentId = (String) map.get("AGENT_ID");
                String agentName = map.get("AGENT_NAME") == null ? "" : (String) map.get("AGENT_NAME");
                String parentAgentId = map.get("PARENT_AGENT_ID") == null ? "" : (String) map.get("PARENT_AGENT_ID");
                String parentAgentName = map.get("PARENT_AGENT_NAME") == null ? "" : (String) map.get("PARENT_AGENT_NAME");
                logger.info("直签代理商计算扣税,{}，{}", agentId, parentAgentId);
                BigDecimal blAmt = (BigDecimal) map.get("BL_AMT");  //保理
                BigDecimal daysProfitAmt = (BigDecimal) map.get("DAYS_PROFIT_AMT"); //日结
                BigDecimal returnMoney = (BigDecimal) map.get("RETURN_MONEY");  //机具返现
                BigDecimal basicsProfitAmt = (BigDecimal) map.get("BASICS_PROFIT_AMT"); //基础分润
                BigDecimal dzAmt = (BigDecimal) map.get("DZ_AMT");  //代代理商垫付款项
                BigDecimal adjustAmt = (BigDecimal) map.get("ADJUST_AMT");//调整金额
                BigDecimal preTaxBase = (BigDecimal) map.get("PRE_TAX_BASE");   //上月留底基数
                BigDecimal preNotDeductionTaxAmt = (BigDecimal) map.get("PRE_NOT_DEDUCTION_TAX_AMT");   //上月未扣
                BigDecimal tax = new BigDecimal((String) map.get("TAX"));   //税率
                BigDecimal machinAmt = (BigDecimal) map.get("MACHIN_AMT");  //机具实收货款
                String busPlatform = (String) map.get("BUS_PLATFORM");
                //扣税明细
                TaxDeductionDetail taxDeductionDetail = new TaxDeductionDetail();
                taxDeductionDetail.setId(idService.genId(TabId.P_TAX_DEDUCTION_DETAIL));
                taxDeductionDetail.setAgentId(agentId);
                taxDeductionDetail.setAgentName(agentName);
                taxDeductionDetail.setProfitMonth(profitMonth);
                taxDeductionDetail.setAgentPid("");
                taxDeductionDetail.setParentAgentId(parentAgentId);
                taxDeductionDetail.setParentAgentName(parentAgentName);
                taxDeductionDetail.setPreLdAmt(preTaxBase);
                taxDeductionDetail.setDayProfitAmt(daysProfitAmt);
                taxDeductionDetail.setDayBackAmt(returnMoney);
                taxDeductionDetail.setBasicProfitAmt(basicsProfitAmt);
                taxDeductionDetail.setBlAmt(blAmt);
                taxDeductionDetail.setMerchanOrderAmt(machinAmt);
                taxDeductionDetail.setAgentDfAmt(dzAmt);
                taxDeductionDetail.setAdjustAmt(adjustAmt);
                taxDeductionDetail.setTaxRate(tax);
                taxDeductionDetail.setPreNotDeductionAmt1(preNotDeductionTaxAmt);
                taxDeductionDetail.setAddTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setRealTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setNotDeductionTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setBusPlatform(busPlatform);
                //计算扣税
                doTaxDeduction(taxDeductionDetail, "I");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("扣税计算异常：{},{}", map.get("AGENT_ID"), map.get("PARENT_AGENT_ID"));
                throw e;
            }

        });

        //直发代理商扣税
        List<Map<String, Object>> agentList2 = taxDeductionDetailMapper.queryTaxDeductionZFAgentList(params);
        if (agentList2 == null) {
            throw new RuntimeException("查询直发扣税代理商失败");
        }

        agentList2.parallelStream().forEach(map -> {
            try {
                String agentId = (String) map.get("AGENT_ID");
                String agentName = map.get("AGENT_NAME") == null ? "" : (String) map.get("AGENT_NAME");
                String parentAgentId = map.get("PARENT_AGENT_ID") == null ? "" : (String) map.get("PARENT_AGENT_ID");
                String parentAgentName = map.get("PARENT_AGENT_NAME") == null ? "" : (String) map.get("PARENT_AGENT_NAME");
                String firstAgentId = map.get("FRIST_AGENT_ID") == null ? "" : (String) map.get("FRIST_AGENT_ID");
                String firstAgentName = map.get("FRIST_AGENT_NAME") == null ? "" : (String) map.get("FRIST_AGENT_NAME");
                logger.info("直发代理商计算扣税,{}，{}", agentId, parentAgentId);
                BigDecimal payDailyAmt = (BigDecimal) map.get("PAY_DAILY_AMT");  //打款成功日分润
                BigDecimal tranDailyAmt = (BigDecimal) map.get("TRAN_DAILY_AMT"); //交易日分润汇总
                BigDecimal parentBuckle = (BigDecimal) map.get("PARENT_BUCKLE");  //代下级扣款
                BigDecimal profitAmt = (BigDecimal) map.get("PROFIT_AMT"); //月分润
                BigDecimal supplyAmt = (BigDecimal) map.get("SUPPLY_AMT");  //退单补款
                BigDecimal buckleAmt = (BigDecimal) map.get("BUCKLE_AMT");//退单扣款
                BigDecimal preTaxBase = (BigDecimal) map.get("PRE_TAX_BASE");   //上月留底基数
                BigDecimal preNotDeductionTaxAmt = (BigDecimal) map.get("PRE_NOT_DEDUCTION_TAX_AMT");   //上月未扣
                BigDecimal tax = (BigDecimal) map.get("TAX");   //税率
                BigDecimal adjustAmt = (BigDecimal) map.get("ADJUST_AMT");   //调整金额
                String busPlatform = (String) map.get("BUS_PLATFORM");

                //扣税明细
                TaxDeductionDetail taxDeductionDetail = new TaxDeductionDetail();
                taxDeductionDetail.setId(idService.genId(TabId.P_TAX_DEDUCTION_DETAIL));
                taxDeductionDetail.setAgentId(agentId);
                taxDeductionDetail.setAgentName(agentName);
                taxDeductionDetail.setFristAgentId(firstAgentId);
                taxDeductionDetail.setFristAgentName(firstAgentName);
                taxDeductionDetail.setProfitMonth(profitMonth);
                taxDeductionDetail.setAgentPid("");
                taxDeductionDetail.setParentAgentId(parentAgentId);
                taxDeductionDetail.setParentAgentName(parentAgentName);
                taxDeductionDetail.setPreLdAmt(preTaxBase);
                taxDeductionDetail.setDayProfitAmt(payDailyAmt);//已打款日分润
                taxDeductionDetail.setDayBackAmt(BigDecimal.ZERO);
                taxDeductionDetail.setBasicProfitAmt(profitAmt.add(supplyAmt).subtract(buckleAmt).subtract(parentBuckle));//涉税前月分润=月份润 +退单补款-退单扣款
                taxDeductionDetail.setBlAmt(BigDecimal.ZERO);
                taxDeductionDetail.setMerchanOrderAmt(BigDecimal.ZERO);
                taxDeductionDetail.setAgentDfAmt(BigDecimal.ZERO);
                taxDeductionDetail.setAdjustAmt(adjustAmt);
                taxDeductionDetail.setTaxRate(tax);
                taxDeductionDetail.setPreNotDeductionAmt1(preNotDeductionTaxAmt);
                taxDeductionDetail.setAddTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setRealTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setNotDeductionTaxAmt(BigDecimal.ZERO);
                taxDeductionDetail.setBusPlatform(busPlatform);
                //计算扣税
                doTaxDeduction_zf(taxDeductionDetail, "I");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("扣税计算异常：{},{}", map.get("AGENT_ID"), map.get("PARENT_AGENT_ID"));
                throw e;
            }

        });

    }

    /**
     * @Author: Zhang Lei
     * @Description: 计算扣税-直签代理商
     * @Date: 19:29 2018/12/20
     */
    private void doTaxDeduction(TaxDeductionDetail tdd, String type) {

        //本月扣税基数
        BigDecimal taxBase = tdd.getPreLdAmt().add(tdd.getDayProfitAmt()).add(tdd.getDayBackAmt()).add(tdd.getBasicProfitAmt())
                .add(tdd.getBlAmt()).subtract(tdd.getMerchanOrderAmt()).add(tdd.getAgentDfAmt())
                .add(tdd.getAdjustAmt());
        tdd.setTaxBase(taxBase);

        //本月扣税基数小于等于0时，表示代理商给我司款项多，不进行扣税计算，基数留底到下月
        if (taxBase.compareTo(BigDecimal.ZERO) > 0) {
            //本月新增扣税
            BigDecimal addTaxAmt = taxBase.multiply(tdd.getTaxRate());
            tdd.setAddTaxAmt(addTaxAmt);
        }

        //本月应扣 = 上月未扣足 + 本月新增
        BigDecimal supposedTaxAmt = tdd.getAddTaxAmt().add(tdd.getPreNotDeductionAmt1());
        tdd.setSupposedTaxAmt(supposedTaxAmt);

        //月分润明细
        ProfitDetailMonth param = new ProfitDetailMonth();
        param.setProfitDate(tdd.getProfitMonth());
        param.setAgentId(tdd.getAgentId());
        param.setParentAgentId(tdd.getParentAgentId());
        ProfitDetailMonth profitDetailMonth = profitDetailMonthMapper.selectByIdAndParent(param);
        if (profitDetailMonth == null) {
            throw new RuntimeException("查询月汇总数据为空" + JSONObject.toJSONString(param));
        }
        logger.debug(JSONObject.toJSONString(profitDetailMonth));

        //涉税前月分润
        BigDecimal basickProfitAmt = profitDetailMonth.getBasicsProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getBasicsProfitAmt();

        //实扣
        BigDecimal realAmt = BigDecimal.ZERO;
        if (basickProfitAmt.compareTo(supposedTaxAmt) >= 0) {
            realAmt = supposedTaxAmt;
        } else {
            realAmt = basickProfitAmt;
        }

        //本月未扣足 = 本月应扣 - 本月实扣
        BigDecimal notDeductionAmt = supposedTaxAmt.subtract(realAmt);

        tdd.setSupposedTaxAmt(supposedTaxAmt);
        tdd.setRealTaxAmt(realAmt);
        tdd.setNotDeductionTaxAmt(notDeductionAmt);
        if ("I".equals(type)) {
            taxDeductionDetailMapper.insertSelective(tdd);
        } else if ("U".equals(type)) {
            taxDeductionDetailMapper.updateByPrimaryKeySelective(tdd);
        }

        //更新月分润明细
        profitDetailMonth.setDeductionTaxMonthAmt(realAmt);
        //profitDetailMonth.setBasicsProfitAmt(basickProfitAmt.subtract(realAmt));
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);

    }

    /**
     * @Author: Zhang Lei
     * @Description: 计算扣税-直发代理商
     * @Date: 19:29 2018/12/20
     */
    private void doTaxDeduction_zf(TaxDeductionDetail tdd, String type) {

        //本月扣税基数
        BigDecimal taxBase = tdd.getPreLdAmt().add(tdd.getDayProfitAmt()).add(tdd.getDayBackAmt()).add(tdd.getBasicProfitAmt())
                .add(tdd.getBlAmt()).subtract(tdd.getMerchanOrderAmt()).add(tdd.getAgentDfAmt())
                .add(tdd.getAdjustAmt());
        tdd.setTaxBase(taxBase);

        //本月扣税基数小于等于0时，表示代理商给我司款项多，不进行扣税计算，基数留底到下月
        if (taxBase.compareTo(BigDecimal.ZERO) > 0) {
            //本月新增扣税
            BigDecimal addTaxAmt = taxBase.multiply(tdd.getTaxRate());
            tdd.setAddTaxAmt(addTaxAmt);
        }

        //本月应扣 = 上月未扣足 + 本月新增
        BigDecimal supposedTaxAmt = tdd.getAddTaxAmt().add(tdd.getPreNotDeductionAmt1());
        tdd.setSupposedTaxAmt(supposedTaxAmt);


        //涉税前月分润
        BigDecimal basickProfitAmt = tdd.getBasicProfitAmt() == null ? BigDecimal.ZERO : tdd.getBasicProfitAmt();

        //实扣
        BigDecimal realAmt = BigDecimal.ZERO;
        if (basickProfitAmt.compareTo(supposedTaxAmt) >= 0) {
            realAmt = supposedTaxAmt;
        } else {
            realAmt = basickProfitAmt;
        }

        //本月未扣足 = 本月应扣 - 本月实扣
        BigDecimal notDeductionAmt = supposedTaxAmt.subtract(realAmt);

        tdd.setSupposedTaxAmt(supposedTaxAmt);
        tdd.setRealTaxAmt(realAmt);
        tdd.setNotDeductionTaxAmt(notDeductionAmt);
        if ("I".equals(type)) {
            taxDeductionDetailMapper.insertSelective(tdd);
        } else if ("U".equals(type)) {
            taxDeductionDetailMapper.updateByPrimaryKeySelective(tdd);
        }

    }
}
