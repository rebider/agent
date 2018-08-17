package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.profit.PosOrganDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yangmx
 * @desc POS奖励分润计算
 */
@Service("posProfitComputeServiceImpl")
public class PosProfitComputeServiceImpl implements DeductService {
    private static Logger LOG = LoggerFactory.getLogger(PosProfitComputeServiceImpl.class);
    @Autowired
    private PosRewardTemplateService posRewardTemplateService;
    @Autowired
    private OrganTranMonthDetailService organTranMonthDetailService;
    @Autowired
    private IPosRewardService iPosRewardService;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    private AgentBusinfoService agentBusinfoService;

    /**
     *  2:机构 3:机构一代 6:标准一代
     */
    private static final String AGENT_TYPE_2 = "2";
    private static final String AGENT_TYPE_3 = "3";
    private static final String AGENT_TYPE_6 = "6";
    private static final String PLATFORM_CODE = "100003";

    /**
     * 上级代理商编号、代理商编号、类型（机构、机构一代、标准一代）、交易总额、贷记交易总额
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        LOG.info("POS奖励计算，请求参数：{}", map);
        Map<String, Object> posMap = new HashMap<String, Object>();
        posMap.putAll(map);
        posMap.put("posRewardAmt", BigDecimal.ZERO);
        posMap.put("posAssDeductAmt", BigDecimal.ZERO);

        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        if(Objects.equals(map.get("agentType"), AGENT_TYPE_2) || Objects.equals(map.get("agentType"), AGENT_TYPE_6)){
            LOG.info("POS奖励，代理商ID：{}，POS交易金额：{}，POS贷记交易金额：{}",
                    map.get("agentId"), map.get("posTranAmt"), map.get("posJlTranAmt"));
            PosReward posReward = new PosReward();
            posReward.setAgentId(map.get("agentId").toString());
            posReward.setApplyStatus("1");
            List<PosReward> posRewardList = iPosRewardService.selectByMonth(posReward);
            if(posRewardList == null || posRewardList.isEmpty()){
                this.obtainRewardTemp(deductDate, posMap);
            } else {
                this.specialRewardTemp(deductDate, posRewardList, posMap);
            }

            posRewardList.forEach(posReward1 -> {
                /** 到达考核月份，执行季度考核*/
                if(Objects.equals(posReward1.getTotalEndMonth(), deductDate)){
                    quarterAssessment(posReward1, posMap);
                }
            });
        } else if(Objects.equals(map.get("agentType"), AGENT_TYPE_3)){
            this.obtainParentAgentId(posMap);
            this.obtainRewardTemp(deductDate, posMap);
            if(new BigDecimal(posMap.get("posRewardAmt").toString()).compareTo(BigDecimal.ZERO) > 0
                    && posMap.get("parentAgentId") != null){
                this.deductParentAgentPosRewardAmt(deductDate, posMap);
            }
        }
        map.put("posRewardAmt", posMap.get("posRewardAmt"));
        map.put("posAssDeductAmt", posMap.get("posAssDeductAmt"));
        map.put("parentDeductPosRewardAmt", posMap.get("parentDeductPosRewardAmt"));
        map.put("parentAgentPid", posMap.get("parentAgentPid"));
        map.put("parentAgentId", posMap.get("parentAgentId"));
        LOG.info("POS奖励计算，响应参数：{}", map);
        return map;
    }

    /**
     * 获取代理商特殊POS奖励模板
     * @param deductDate
     * @param posRewardList
     * @param map
     */
    private void specialRewardTemp(String deductDate, List<PosReward> posRewardList, Map<String, Object> map) {
        posRewardList.forEach(posReward1 -> {
            if(posReward1.getTotalConsMonth().contains("~")){
                String[] spl = posReward1.getTotalConsMonth().trim().split("~");
                for (int i = 0; i < spl.length; i++) {
                    if(Objects.equals(spl[i], deductDate)){
                        this.specialTranOrCreditAmt(posReward1, map, deductDate);
                    }
                }
            } else {
                if(Objects.equals(posReward1.getTotalConsMonth(), deductDate)){
                    this.specialTranOrCreditAmt(posReward1, map, deductDate);
                }
            }
        });
    }

    /**
     * 按照特殊奖励模板计算
     * 查询交易对比月交易总量
     * 查询贷记对月月贷记交易总量
     * @param posReward1 特殊奖励模板
     * @param map
     * @param deductDate
     */
    private void specialTranOrCreditAmt(PosReward posReward1, Map<String, Object> map, String deductDate) {
        OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
        organTranMonthDetail.setAgentId(map.get("agentId").toString());
        organTranMonthDetail.setProfitDate(posReward1.getCreditConsMonth().replace("-",""));
        organTranMonthDetail.setAgentType(map.get("agentType").toString());
        List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
        if(list != null && !list.isEmpty()){
            OrganTranMonthDetail organTranAmtDetail = list.get(0);
            this.computeSpecialPosReward(organTranAmtDetail.getPosJlTranAmt(), posReward1, map);
        } else {
            LOG.info("代理商ID：{}，未查询到代理商月交易明细", map.get("agentId"));
        }
    }

    /**
     * 特殊POS奖励计算(四舍五入)
     * 更新月分润POS奖励
     * @param posJlTranAmt 对比月份贷记交易总量
     * @param posReward1 特殊奖励模板
     * @param map
     */
    private void computeSpecialPosReward(BigDecimal posJlTranAmt, PosReward posReward1, Map<String, Object> map) {
        LOG.info("代理商ID：{}，执行特殊POS奖励模板：{}", map.get("agentId"), JSONObject.toJSON(posReward1));
        if(new BigDecimal(map.get("posJlTranAmt").toString()).compareTo(posJlTranAmt) >= 0){
            BigDecimal actualposJlTranAmt = new BigDecimal(map.get("posJlTranAmt").toString()).subtract(posJlTranAmt);
            LOG.info("代理商ID：{}，特殊模板对比月贷记交易总额：{}，计算后贷记交易额：{}，特殊奖励比例：{}", map.get("agentId"), posJlTranAmt, actualposJlTranAmt, posReward1.getRewardScale());
            BigDecimal posRewardAmt = actualposJlTranAmt.multiply(posReward1.getRewardScale()).setScale(2,BigDecimal.ROUND_HALF_UP);
            map.put("posRewardAmt", posRewardAmt);
        } else {
            LOG.info("代理商ID：{}，特殊模板对比月贷记交易总额：{}，本月贷记交易额：{}，本月贷记交易额小于对比月贷记交易额，没有POS奖励", map.get("agentId"), posJlTranAmt, map.get("posJlTranAmt").toString());
        }
    }

    /**
     * 获取POS通用奖励模板
     * @param deductDate
     * @param map
     */
    private void obtainRewardTemp(String deductDate, Map<String, Object> map) {
        //先从缓存拿，没有在查询数据库
        List<PosRewardTemplate> posRewardTemplates = posRewardTemplateService.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return;
        }
        List<OrganTranMonthDetail> detailList = null;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                for (int i = 0; i < spl.length; i++) {
                    if(Objects.equals(spl[i], deductDate)){
                        if(detailList == null || detailList.isEmpty()){
                            OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
                            organTranMonthDetail.setAgentId(map.get("agentId").toString());
                            organTranMonthDetail.setProfitDate(posRewardTemplate.getTranContrastMonth().replace("-",""));
                            organTranMonthDetail.setAgentType(map.get("agentType").toString());
                            detailList = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
                            if(detailList != null  && !detailList.isEmpty()){
                                this.obtainTranOrCreditAmt(detailList.get(0), posRewardTemplate, map);
                            } else {
                                LOG.error("未找代理商：{},分润数据",map.get("agentId"));
                            }
                        } else {
                            OrganTranMonthDetail organTranMonthDetail = detailList.get(0);
                            this.obtainTranOrCreditAmt(organTranMonthDetail, posRewardTemplate, map);
                        }
                    }
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), deductDate)){
                    OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
                    organTranMonthDetail.setAgentId(map.get("agentId").toString());
                    organTranMonthDetail.setProfitDate(posRewardTemplate.getTranContrastMonth().replace("-",""));
                    organTranMonthDetail.setAgentType(map.get("agentType").toString());
                    detailList = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
                    if(detailList != null  && !detailList.isEmpty()){
                        this.obtainTranOrCreditAmt(detailList.get(0), posRewardTemplate, map);
                    }
                }
            }
        }
        detailList = null;
    }

    /**
     * 查询交易对比月交易总量
     * 查询贷记对月月贷记交易总量
     * @param posRewardTemplate
     * @param map
     */
    private void obtainTranOrCreditAmt(OrganTranMonthDetail organTranAmtDetail, PosRewardTemplate posRewardTemplate, Map<String, Object> map) {
        if(Objects.equals(posRewardTemplate.getCreditTranContrastMonth(), posRewardTemplate.getTranContrastMonth())){
            this.computePosReward(organTranAmtDetail.getPosTranAmt(),organTranAmtDetail.getPosJlTranAmt(), posRewardTemplate, map);
        } else {
            LOG.info("代理商ID：{}，查询贷记对比月交易月份：{}", map.get("agentId"), posRewardTemplate.getCreditTranContrastMonth());
            organTranAmtDetail.setProfitDate(posRewardTemplate.getCreditTranContrastMonth().replace("-",""));
            List<OrganTranMonthDetail> list2 = organTranMonthDetailService.getOrganTranMonthDetailList(organTranAmtDetail);
            if(list2 != null && !list2.isEmpty()){
                OrganTranMonthDetail creditTranAmtDetail = list2.get(0);
                this.computePosReward(organTranAmtDetail.getPosTranAmt(),creditTranAmtDetail.getPosJlTranAmt(), posRewardTemplate, map);
            } else {
                LOG.info("代理商ID：{}，未查询到代理商月交易明细", map.get("agentId"));
            }
        }
    }

    /**
     * 计算POS奖励(四舍五入)
     * 更新月分润POS奖励
     * @param posTranAmt 对比月份交易总量
     * @param posJlTranAmt 对比月份贷记交易总量
     * @param posRewardTemplate 奖励模板
     * @param map
     */
    private void computePosReward(BigDecimal posTranAmt, BigDecimal posJlTranAmt, PosRewardTemplate posRewardTemplate, Map<String, Object> map) {
        LOG.info("代理商ID：{}，奖励模板设定对比月交易总额：{}，奖励模板设定对比月贷记交易总额：{}", map.get("agentId"), posTranAmt, posJlTranAmt);
        BigDecimal actualTranAmt = new BigDecimal(map.get("posTranAmt").toString()).subtract(posTranAmt);
        if(actualTranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) >= 0
                && actualTranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) < 0 ){
            LOG.info("代理商ID：{}，交易总额达标范围：{}万~{}万", map.get("agentId"), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
            LOG.info("代理商ID：{}，执行POS通用奖励模板：{}", map.get("agentId"),  JSONObject.toJSON(posRewardTemplate));
            if(new BigDecimal(map.get("posJlTranAmt").toString()).compareTo(posJlTranAmt) >= 0){
                BigDecimal actualposJlTranAmt = new BigDecimal(map.get("posJlTranAmt").toString()).subtract(posJlTranAmt);
                BigDecimal posRewardAmt = actualposJlTranAmt.multiply(posRewardTemplate.getProportion()).setScale(2,BigDecimal.ROUND_HALF_UP);
                map.put("posRewardAmt", posRewardAmt);
                LOG.info("代理商ID：{}，（本月-对比月）交易总额：{}，(本月贷记-对比月贷记)贷记总额：{}，POS奖励金额：{}", map.get("agentId"), actualTranAmt, actualposJlTranAmt, map.get("posRewardAmt"));
            } else {
                LOG.info("代理商ID：{}，本月贷记交易总额小于对比月贷记交易额，没有POS奖励", map.get("agentId"), actualTranAmt);
            }
        } else {
            LOG.info("代理商ID：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万", map.get("agentId"), actualTranAmt, posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
        }
    }

    /**
     * 根据代理商查找上级代理商ID
     * @param posMap
     */
    private void obtainParentAgentId(Map<String, Object> posMap) throws Exception{
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), PLATFORM_CODE, posMap.get("agentId").toString());
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            posMap.put("parentAgentId", agentBusInfo.get(0).getBusNum());
            LOG.info("代理商ID：{}，上级代理商ID：{}", posMap.get("agentId"), agentBusInfo.get(0).getBusNum());
        }
    }

    /**
     * 扣减上级代理商月分润
     * @param deductDate
     * @param posMap
     */
    private void deductParentAgentPosRewardAmt(String deductDate, Map<String, Object> posMap) {
        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(posMap.get("parentAgentId").toString());
        criteria.andProfitDateEqualTo(deductDate.replace("-",""));
        List<ProfitDetailMonth> list = profitDetailMonthServiceImpl.selectByExample(example);
        if(list != null && !list.isEmpty()){
            ProfitDetailMonth profitDetailMonth = list.get(0);
            LOG.info("代理商ID：{}，上级代理商ID：{}，上级代理商分润：{}，上级代理POS奖励:{}，扣减奖励：{}", posMap.get("agentId"),
                    posMap.get("parentAgentId"), profitDetailMonth.getRealProfitAmt(), profitDetailMonth.getPosRewardAmt(), "-"+posMap.get("posRewardAmt"));
            BigDecimal praentRealProfitAmt = profitDetailMonth.getRealProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRealProfitAmt();
            BigDecimal praentPosRewardAmt = profitDetailMonth.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosRewardAmt();
            BigDecimal posRewardAmt = praentPosRewardAmt.subtract(new BigDecimal(posMap.get("posRewardAmt").toString()));
            if(praentRealProfitAmt.compareTo(BigDecimal.ZERO) > 0){
                ProfitDetailMonth update = new ProfitDetailMonth();
                update.setId(profitDetailMonth.getId());
                update.setPosRewardAmt(posRewardAmt);
                update.setRealProfitAmt(praentRealProfitAmt.subtract(new BigDecimal(posMap.get("posRewardAmt").toString())));
                profitDetailMonthServiceImpl.updateByPrimaryKeySelective(update);
            } else {
                posMap.put("parentDeductPosRewardAmt", posRewardAmt);
                posMap.put("parentAgentPid", profitDetailMonth.getAgentPid());
                posMap.put("parentAgentId", profitDetailMonth.getAgentId());
            }
        } else {
            LOG.error("代理商ID：{}，上级代理商ID：{}，未查询到上级代理商月交易明细", posMap.get("agentId"), posMap.get("parentAgentId"));
        }
    }

    /**
     * 季度考核扣款
     * @param posReward1
     * @param map
     */
    private void quarterAssessment(PosReward posReward1, Map<String, Object> map) {
        LOG.info("代理商ID：{}，到达活动考核月份", map.get("agentId"));
        Map<String, Object> assMap = new HashMap<String, Object>();
        assMap.putAll(map);
        BigDecimal posTranAmt = BigDecimal.ZERO;
        List<String> month = new ArrayList<String>();
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                month.add(spl[i].replace("-",""));
            }
        } else {
            month.add(posReward1.getTotalConsMonth());
        }
        //1、根据活动奖励月份，查询这个代理商活动月份交易总金额
        posTranAmt = posTranAmt.add(quarterAssessmentTranOrCreditAmt(assMap, month));

        //2、交易总金额与特殊奖励活动模板的总金额比较,累计交易量不满足奖励条件，按照通用POS奖励模板重新计算奖励
        if(posTranAmt.compareTo(posReward1.getGrowAmt().multiply(new BigDecimal("10000"))) >= 0){
            LOG.info("代理商ID：{}，代理商季度交易量达到活动指标，不进行扣减奖励", assMap.get("agentId"));
            return;
        }
        LOG.info("代理商ID：{}，总交易额：{}，活动设定总交易额：{}，代理商季度交易量指标不合格，进行扣减奖励", assMap.get("agentId"),posTranAmt ,posReward1.getGrowAmt().multiply(new BigDecimal("10000")));

        //3.按照通用奖励模板，重新计算代理活动月份奖励金额
        BigDecimal posRewardAmt = this.afreshPosRewardAmt(posReward1, assMap);

        //4、查找当前代理商（机构、标准一代）下所有代理商业务编码
        List<AgentBusInfo> busList = agentBusinfoService.queryChildLevelByBusNum(null, PLATFORM_CODE, assMap.get("agentId").toString());
        BigDecimal beforeChildPosRewardAmt = BigDecimal.ZERO;
        if(busList != null && !busList.isEmpty()){
            List<String> agentIdList = new ArrayList<String>(busList.size());
            busList.forEach(agentBusInfo -> {
                if(agentBusInfo.getBusNum() != null){
                    agentIdList.add(agentBusInfo.getBusNum());
                }
            });
            //5.查询这个代理商所有下属代理商的POS奖励金额
            beforeChildPosRewardAmt = beforeChildPosRewardAmt.add(this.getBeforePosRewardAmt(month, assMap, agentIdList));
        }
        LOG.info("代理商ID：{}，获取下属所有代理商的POS奖励金额：{}", assMap.get("agentId"), beforeChildPosRewardAmt);
        BigDecimal beforePosRewardAmt = this.getBeforePosRewardAmt(month, assMap, null);
        LOG.info("代理商ID：{}，之前活动期间奖励的POS金额：：{}", assMap.get("agentId"), beforePosRewardAmt);
        //6.核算POS奖励：之前按特殊奖励发放的POS奖励-本次按通用模板计算的POS奖励-下属代理商发放的奖励=考核季度实际扣款奖励
        BigDecimal posAssDeductAmt = beforePosRewardAmt.subtract(posRewardAmt).subtract(beforeChildPosRewardAmt);
        map.put("posAssDeductAmt", posAssDeductAmt);
    }

    /**
     * 重新计算POS奖励
     * @param posReward1
     * @param assMap
     */
    private BigDecimal afreshPosRewardAmt(PosReward posReward1, Map<String, Object> assMap) {
        BigDecimal posRewardAmt = BigDecimal.ZERO;
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
                organTranMonthDetail.setAgentId(assMap.get("agentId").toString());
                organTranMonthDetail.setProfitDate(spl[i].replace("-",""));
                organTranMonthDetail.setAgentType(assMap.get("agentType").toString());
                List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
                if(list != null && !list.isEmpty()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("agentType", assMap.get("agentType"));
                    map.put("agentId", assMap.get("agentId"));
                    map.put("agentPid", assMap.get("agentPid"));
                    map.put("posTranAmt", list.get(0).getPosTranAmt());
                    map.put("posJlTranAmt", list.get(0).getPosJlTranAmt());
                    this.obtainRewardTemp(spl[i], map);
                    BigDecimal refposRewardAmt = map.get("posRewardAmt") == null ? BigDecimal.ZERO : new BigDecimal(map.get("posRewardAmt").toString());
                    posRewardAmt = posRewardAmt.add(refposRewardAmt);
                    LOG.info("代理商ID：{}，月份：{}，采用通用模板计算的POS奖励：{}", assMap.get("agentId"), spl[i], posRewardAmt);
                }
            }
        } else {
            OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
            organTranMonthDetail.setAgentId(assMap.get("agentId").toString());
            organTranMonthDetail.setProfitDate(posReward1.getTotalConsMonth().replace("-",""));
            organTranMonthDetail.setAgentType(assMap.get("agentType").toString());
            List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
            if(list != null && !list.isEmpty()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("agentType", assMap.get("agentType"));
                map.put("agentId", assMap.get("agentId"));
                map.put("agentPid", assMap.get("agentPid"));
                map.put("posTranAmt", list.get(0).getPosTranAmt());
                map.put("posJlTranAmt", list.get(0).getPosJlTranAmt());
                this.obtainRewardTemp(posReward1.getTotalConsMonth(), map);
                BigDecimal refposRewardAmt = map.get("posRewardAmt") == null ? BigDecimal.ZERO : new BigDecimal(map.get("posRewardAmt").toString());
                posRewardAmt = posRewardAmt.add(refposRewardAmt);            }
        }
        LOG.info("代理商ID：{}，按照通用POS奖励模板重新计算POS奖励，重新计算的POS奖励：{}", assMap.get("agentId"), posRewardAmt);
        return posRewardAmt;
    }

    private BigDecimal getBeforePosRewardAmt(List<String> month, Map<String, Object> assMap, List<String> agentIdList) {
        BigDecimal beforePosRewardAmt = BigDecimal.ZERO;
        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
        if(agentIdList == null ){
            criteria.andAgentIdEqualTo(assMap.get("agentId").toString());
        } else {
            criteria.andAgentIdIn(agentIdList);
        }
        criteria.andProfitDateIn(month);
        List<ProfitDetailMonth> list = profitDetailMonthServiceImpl.selectByExample(example);
        if(list != null && !list.isEmpty()){
            for ( ProfitDetailMonth profitDetailMonth :list){
                beforePosRewardAmt = beforePosRewardAmt.add(profitDetailMonth.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosRewardAmt());
            }
        }
        return beforePosRewardAmt;
    }

    private BigDecimal quarterAssessmentTranOrCreditAmt(Map<String, Object> map, List<String> monthList) {
        OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
        organTranMonthDetail.setAgentId(map.get("agentId").toString());
        organTranMonthDetail.setAgentType(map.get("agentType").toString());
        BigDecimal posTranAmt = BigDecimal.ZERO;
        for(String deductDate : monthList){
            organTranMonthDetail.setProfitDate(deductDate);
            List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
            if( list != null && !list.isEmpty()){
                OrganTranMonthDetail detail = list.get(0);
                posTranAmt = posTranAmt.add(detail.getPosTranAmt());
            }
        }
        return posTranAmt;
    }
}
