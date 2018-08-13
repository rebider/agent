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
        } else if(Objects.equals(map.get("agentType"), AGENT_TYPE_3)){
            this.obtainParentAgentId(posMap);
            this.obtainRewardTemp(deductDate, posMap);
            this.deductParentAgentPosRewardAmt(deductDate, posMap);
        }
        map.put("posRewardAmt", posMap.get("posRewardAmt"));
        map.put("posAssDeductAmt", posMap.get("posAssDeductAmt"));
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
            /** 到达考核月份，执行季度考核*/
            if(Objects.equals(posReward1.getTotalEndMonth(), deductDate)){
                quarterAssessment(posReward1, map);
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
        organTranMonthDetail.setProfitDate(deductDate);
        organTranMonthDetail.setAgentType(map.get("agentType").toString());
        List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
        if(list != null && !list.isEmpty()){
            OrganTranMonthDetail organTranAmtDetail = list.get(0);
            if(Objects.equals(posReward1.getCreditConsMonth(), organTranAmtDetail.getProfitDate())){
                this.computeSpecialPosReward(organTranAmtDetail.getPosJlTranAmt(), posReward1, map);
            } else {
                LOG.info("代理商ID：{}，查询贷记对比月交易月份：{}", map.get("agentId"), posReward1.getCreditConsMonth());
                organTranMonthDetail.setProfitDate(posReward1.getCreditConsMonth());
                List<OrganTranMonthDetail> list2 = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
                if(list2 != null && !list2.isEmpty()){
                    OrganTranMonthDetail creditTranAmtDetail = list2.get(0);
                    this.computeSpecialPosReward(creditTranAmtDetail.getPosJlTranAmt(), posReward1, map);
                } else {
                    LOG.info("代理商ID：{}，未查询到代理商月交易明细", map.get("agentId"));
                }
            }
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
        BigDecimal actualposJlTranAmt = new BigDecimal(map.get("posJlTranAmt").toString()).subtract(posJlTranAmt);
        LOG.info("代理商ID：{}，特殊模板对比月贷记交易总额：{}，计算后贷记交易额：{}", map.get("agentId"), posJlTranAmt, actualposJlTranAmt);
        BigDecimal posRewardAmt = actualposJlTranAmt.multiply(posReward1.getRewardScale()).setScale(2,BigDecimal.ROUND_HALF_UP);
        map.put("posRewardAmt", posRewardAmt);
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
        posRewardTemplates.forEach(posRewardTemplate -> {
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                for (int i = 0; i < spl.length; i++) {
                    if(Objects.equals(spl[i], deductDate)){
                        this.obtainTranOrCreditAmt(posRewardTemplate, map);
                    }
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), deductDate)){
                    this.obtainTranOrCreditAmt(posRewardTemplate, map);
                }
            }
        });
    }

    /**
     * 查询交易对比月交易总量
     * 查询贷记对月月贷记交易总量
     * @param posRewardTemplate
     * @param map
     */
    private void obtainTranOrCreditAmt(PosRewardTemplate posRewardTemplate, Map<String, Object> map) {
        OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
        organTranMonthDetail.setAgentId(map.get("agentId").toString());
        organTranMonthDetail.setProfitDate(posRewardTemplate.getTranContrastMonth());
        organTranMonthDetail.setAgentType(map.get("agentType").toString());
        List<OrganTranMonthDetail> list = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
        if(list != null && !list.isEmpty()){
            OrganTranMonthDetail organTranAmtDetail= list.get(0);
            if(Objects.equals(posRewardTemplate.getCreditTranContrastMonth(), organTranAmtDetail.getProfitDate())){
                this.computePosReward(organTranAmtDetail.getPosTranAmt(),organTranAmtDetail.getPosJlTranAmt(), posRewardTemplate, map);
            } else {
                LOG.info("代理商ID：{}，查询贷记对比月交易月份：{}", map.get("agentId"), posRewardTemplate.getCreditTranContrastMonth());
                organTranMonthDetail.setProfitDate(posRewardTemplate.getCreditTranContrastMonth());
                List<OrganTranMonthDetail> list2 = organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
                if(list2 != null && !list2.isEmpty()){
                    OrganTranMonthDetail creditTranAmtDetail = list2.get(0);
                    this.computePosReward(organTranAmtDetail.getPosTranAmt(),creditTranAmtDetail.getPosJlTranAmt(), posRewardTemplate, map);
                } else {
                    LOG.info("代理商ID：{}，未查询到代理商月交易明细", map.get("agentId"));
                }
            }
        } else {
            LOG.info("代理商ID：{}，未查询到代理商月交易明细", map.get("agentId"));
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
        LOG.info("代理商ID：{}，奖励模板对比月交易总额：{}，奖励模板对比月贷记交易总额：{}", map.get("agentId"), posTranAmt, posJlTranAmt);
        BigDecimal actualTranAmt = new BigDecimal(map.get("posTranAmt").toString()).subtract(posTranAmt);
        if(actualTranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && actualTranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0 ){
            LOG.info("代理商ID：{}，交易总额达标范围：{}万~{}万", map.get("agentId"), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
            LOG.info("代理商ID：{}，执行POS通用奖励模板：{}", map.get("agentId"),  JSONObject.toJSON(posRewardTemplate));
            BigDecimal actualposJlTranAmt = new BigDecimal(map.get("posJlTranAmt").toString()).subtract(posJlTranAmt);
            BigDecimal posRewardAmt = actualposJlTranAmt.multiply(posRewardTemplate.getProportion()).setScale(2,BigDecimal.ROUND_HALF_UP);
            map.put("posRewardAmt", posRewardAmt);
            LOG.info("代理商ID：{}，（本月-对比月）交易总额：{}，POS奖励金额：{}", map.get("agentId"), actualTranAmt, map.get("posRewardAmt"));
        } else {
            map.put("posRewardAmt", BigDecimal.ZERO);
            LOG.info("代理商ID：{}，交易总额未达标范围：{}万~{}万", map.get("agentId"), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
        }
    }

    /**
     * 根据代理商查找上级代理商ID
     * @param posMap
     */
    private void obtainParentAgentId(Map<String, Object> posMap) throws Exception{
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevel(new ArrayList<AgentBusInfo>(), PLATFORM_CODE, posMap.get("agentPid").toString());
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            posMap.put("parentAgentId", agentBusInfo.get(0).getBusNum());
            LOG.info("代理商ID：{}，上级代理商ID：{}", posMap.get("agentId"), agentBusInfo.get(0).getBusNum());
        } else {
            LOG.info("代理商ID：{}，未查询到上级代理商。", posMap.get("agentId"));
            throw new Exception("未查询到上级代理商信息");
        }
    }

    /**
     * 扣减上级代理商月分润
     * @param deductDate
     * @param posMap
     */
    private void deductParentAgentPosRewardAmt(String deductDate, Map<String, Object> posMap) {
        if(new BigDecimal(posMap.get("posRewardAmt").toString()).compareTo(BigDecimal.ZERO) > 0
                && posMap.get("parentAgentId") != null){
            ProfitDetailMonthExample example = new ProfitDetailMonthExample();
            ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
            criteria.andAgentIdEqualTo(posMap.get("parentAgentId").toString());
            criteria.andProfitDateEqualTo(deductDate);
            List<ProfitDetailMonth> list = profitDetailMonthServiceImpl.selectByExample(example);
            if(list != null && !list.isEmpty()){
                ProfitDetailMonth profitDetailMonth = list.get(0);
                BigDecimal praentProfitAmt = profitDetailMonth.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosRewardAmt();
                BigDecimal posRewardAmt = praentProfitAmt.subtract(new BigDecimal(posMap.get("posRewardAmt").toString()));
                LOG.info("代理商ID：{}，上级代理商ID：{}，上级代理POS奖励:{}，扣减奖励：{}", posMap.get("agentId"),
                        posMap.get("parentAgentId"), profitDetailMonth.getPosRewardAmt(), posMap.get("posRewardAmt"));
                ProfitDetailMonth update = new ProfitDetailMonth();
                update.setId(profitDetailMonth.getId());
                update.setPosRewardAmt(posRewardAmt);
                profitDetailMonthServiceImpl.updateByPrimaryKeySelective(update);
            } else {
                LOG.info("代理商ID：{}，上级代理商ID：{}，未查询到上级代理商月交易明细", posMap.get("agentId"), posMap.get("parentAgentId"));
            }
        } else {
            LOG.info("代理商ID：{}，上级代理商ID：{}，未查询到上级代理商或者没有POS奖励", posMap.get("agentId"), posMap.get("parentAgentId"));
        }
    }

    /**
     * 季度考核扣款
     * @param posReward1
     * @param map
     */
    private void quarterAssessment(PosReward posReward1, Map<String, Object> map) {
        Map<String, Object> assMap = new HashMap<String, Object>();
        assMap.putAll(map);
        //0、查询特殊奖励模板、获取活动奖励月份
        BigDecimal posTranAmt = BigDecimal.ZERO;
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                //1、根据活动奖励月份，查询这个代理商季度，交易总金额与POS奖励金额
                List<OrganTranMonthDetail> list = quarterAssessmentTranOrCreditAmt(assMap, spl[i]);
                if( list != null && !list.isEmpty()){
                    OrganTranMonthDetail organTranMonthDetail = list.get(0);
                    posTranAmt = posTranAmt.add(organTranMonthDetail.getPosTranAmt());
                }
            }
        } else {
            List<OrganTranMonthDetail> list = quarterAssessmentTranOrCreditAmt(assMap, posReward1.getTotalConsMonth());
            if( list != null && !list.isEmpty()){
                OrganTranMonthDetail organTranMonthDetail = list.get(0);
                posTranAmt = posTranAmt.add(organTranMonthDetail.getPosTranAmt());
            }
        }

        //2、交易总金额与特殊奖励活动模板的总金额比较,累计交易量不满足奖励条件，按照通用POS奖励模板重新计算奖励
        if(posTranAmt.compareTo(posReward1.getGrowAmt()) >= 0){
            LOG.info("代理商ID：{}，代理商季度交易量达到活动指标，不进行扣减奖励", assMap.get("agentId"));
            return;
        }
        LOG.info("代理商ID：{}，总交易额：{}，活动设定总交易额：{}，代理商季度交易量指标不合格，进行扣减奖励", assMap.get("agentId"),posTranAmt ,posReward1.getGrowAmt());
        List<String> month = new ArrayList<String>();
        BigDecimal posRewardAmt = BigDecimal.ZERO;
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                month.add(spl[i]);
                this.obtainRewardTemp(spl[i], assMap);
                posRewardAmt = posRewardAmt.add(new BigDecimal(assMap.get("posRewardAmt").toString()));
            }
        } else {
            month.add(posReward1.getTotalConsMonth());
            this.obtainRewardTemp(posReward1.getTotalConsMonth(), assMap);
            posRewardAmt = posRewardAmt.add(new BigDecimal(assMap.get("posRewardAmt").toString()));
        }
        //3、查找当前代理商（机构、标准一代）下所有代理商业务编码

        //4.查询到当前代理商季度每月POS的奖励
        BigDecimal beforePosRewardAmt = BigDecimal.ZERO;
        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(assMap.get("agentId").toString());
        criteria.andProfitDateIn(month);
        List<ProfitDetailMonth> list = profitDetailMonthServiceImpl.selectByExample(example);
        if(list != null && !list.isEmpty()){
            for ( ProfitDetailMonth profitDetailMonth :list){
                beforePosRewardAmt = beforePosRewardAmt.add(profitDetailMonth.getPosRewardAmt());
            }
        }
        //A-B-C=POS奖励考核扣款
//      BigDecimal posAssDeductAmt = beforePosRewardAmt.subtract(posRewardAmt).subtract(new BigDecimal("下级代理POS奖励汇总值"));
        BigDecimal posAssDeductAmt = beforePosRewardAmt.subtract(posRewardAmt);
        map.put("posAssDeductAmt", posAssDeductAmt);
    }

    private List<OrganTranMonthDetail> quarterAssessmentTranOrCreditAmt(Map<String, Object> map, String deductDate) {
        OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
        organTranMonthDetail.setAgentId(map.get("agentId").toString());
        organTranMonthDetail.setProfitDate(deductDate);
        organTranMonthDetail.setAgentType(map.get("agentType").toString());
        return organTranMonthDetailService.getOrganTranMonthDetailList(organTranMonthDetail);
    }
}
