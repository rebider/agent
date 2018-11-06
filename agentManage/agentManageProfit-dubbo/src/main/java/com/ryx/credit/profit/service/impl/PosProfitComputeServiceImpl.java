package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    private IPosRewardService iPosRewardService;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private TransProfitDetailService transProfitDetailService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;
    @Autowired
    private  PosRewardSDetailService posRewardSDetailService;
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
        posMap.put("posRewardAmt", BigDecimal.ZERO);//POS奖励
        posMap.put("posAssDeductAmt", BigDecimal.ZERO);//POS奖励考核扣款
        String computType = map.get("computType").toString();
        String currentDate = map.get("currentDate").toString();
        if(map.get("agentId") == null){
            return posMap;
        }
        //根据代理唯一AG码，查询POS奖励明细
        if(Objects.equals(posMap.get("agentType"), AGENT_TYPE_2)
                || Objects.equals(posMap.get("agentType"), AGENT_TYPE_6)
                || Objects.equals(posMap.get("agentType"), AGENT_TYPE_3)) {
            PosRewardDetail posRewardDetail = new PosRewardDetail();
            posRewardDetail.setPosAgentId(posMap.get("agentId").toString());
            posRewardDetail.setProfitPosDate(currentDate);
            PosRewardDetail detail = posRewardSDetailService.getPosRewardDetail(posRewardDetail);
            if(detail == null){
                LOG.info("POS奖励计算，响应参数：{}", map);
                return posMap;
            }
            String standard = detail.getPosStandard() == null ? "0" : detail.getPosStandard();
            String posAmt = detail.getPosAmt() == null ? "0" : detail.getPosAmt();
            LOG.info("代理商唯一码：{}，POS奖励交易量：{}，奖励标准：{}", detail.getPosAgentId(), posAmt, standard);
            BigDecimal posRoward = new BigDecimal(posAmt).multiply(new BigDecimal(standard)).setScale(2, BigDecimal.ROUND_HALF_UP);
            LOG.info("代理商唯一码：{}，POS奖励：{}", detail.getPosAgentId(), posRoward);
            detail.setPosOwnReward(posRoward.toString());
            String posDowdReward = detail.getPosDownReward() == null ? "0" : detail.getPosDownReward();
            BigDecimal roward = posRoward.subtract(new BigDecimal(posDowdReward));
            detail.setPosReawrdProfit(roward.toString());
            posRewardSDetailService.updatePosRewardDetail(detail);
            posMap.put("posRewardAmt", roward);
            if(Objects.equals(posMap.get("agentType"), AGENT_TYPE_3)){
                //找到他的上级POS奖励明细，进行扣减奖励
                String parentAgentId = this.obtainParentAgentId(detail.getPosMechanismId());
                if(parentAgentId != null ){
                    LOG.info("代理商唯一码：{}，上级代理商唯一码：{}", detail.getPosAgentId(), parentAgentId);
                    PosRewardDetail parentDetail = new PosRewardDetail();
                    parentDetail.setPosAgentId(parentAgentId);
                    parentDetail.setProfitPosDate(currentDate);
                    PosRewardDetail parentPosRewardDetail = posRewardSDetailService.getPosRewardDetail(parentDetail);
                    if(parentPosRewardDetail != null){
                        ////扣减上级POS奖励明细中奖励分润值
                        String parentPosDowdReward = parentPosRewardDetail.getPosDownReward() == null ? "0" : parentPosRewardDetail.getPosDownReward();
                        BigDecimal parentDowdReward = new BigDecimal(parentPosDowdReward).add(posRoward);
                        String parentOwnReward= parentPosRewardDetail.getPosOwnReward() == null ? "0" : parentPosRewardDetail.getPosOwnReward();
                        BigDecimal profitAmt = new BigDecimal(parentOwnReward).subtract(parentDowdReward);
                        parentPosRewardDetail.setPosDownReward(parentDowdReward.toString());
                        parentPosRewardDetail.setPosReawrdProfit(profitAmt.toString());
                        posRewardSDetailService.updatePosRewardDetail(parentPosRewardDetail);

                        //扣减上级基础分润中POS奖励值
                        this.deductParentAgentPosRewardAmt(parentPosRewardDetail, profitAmt, computType);
                    } else {
                        LOG.info("代理商唯一码：{}，没有找到上级代理商唯一码的POS奖励明细数据", detail.getPosAgentId());
                    }
                } else {
                    LOG.info("代理商唯一码：{}，没有找到上级代理商唯一码", detail.getPosAgentId());
                }
            }

            //季度奖励考核
            if(Objects.equals(posMap.get("agentType"), AGENT_TYPE_2)
                    || Objects.equals(posMap.get("agentType"), AGENT_TYPE_6)) {
                BigDecimal deductAmt = this.rewardAssessment(detail,currentDate);
                posMap.put("posAssDeductAmt", deductAmt);
            }
        }
        LOG.info("POS奖励计算，响应参数：{}", posMap);
        return posMap;
    }

    /**
     * 奖励数据重置
     */
    @Override
    public void clearDetail(){
        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        posRewardSDetailService.clearPosRewardDetail(currentDate);
    }

    /**
     * POS季度奖励考核
     */
    private BigDecimal rewardAssessment(PosRewardDetail detail, String currentDate) {
        PosReward posReward = new PosReward();
        posReward.setAgentId(detail.getPosAgentId());
        posReward.setApplyStatus("1");
        List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);
        List<String> month = new ArrayList<String>();
        String assessDate = null;
        PosReward posRewardTemp = null;
        if(posRewardList != null || !posRewardList.isEmpty()){
            for(PosReward rewardTemp:posRewardList){
                if(Objects.equals(rewardTemp.getTotalEndMonth().replaceAll("-",""), currentDate)){
                    LOG.info("代理商唯一码：{}，到达考核月份:{}", detail.getPosAgentId(), currentDate);
                    assessDate = currentDate;
                    posRewardTemp = rewardTemp;
                    List<String> list = getMonthBetween(rewardTemp.getTotalConsMonth(), rewardTemp.getCreditConsMonth());
                    list.forEach(s -> {
                        month.add(s.replace("-",""));
                    });
                }
            }

        }
        BigDecimal deductAmt = BigDecimal.ZERO;
        if(assessDate == null || posRewardTemp == null){
            return deductAmt;
        }

        PosRewardDetail posRewardDetail = new PosRewardDetail();
        posRewardDetail.setPosAgentId(detail.getPosAgentId());
        posRewardDetail.setProfitPosDate(assessDate);
        PosRewardDetail assessDetail = posRewardSDetailService.getPosRewardDetail(posRewardDetail);
        if(new BigDecimal(assessDetail.getPosCurrentCount()).compareTo(posRewardTemp.getGrowAmt().multiply(new BigDecimal("10000"))) >= 0){
            LOG.info("代理商唯一码：{}，考核交易量达到活动指标，不进行扣减奖励", detail.getPosAgentId());
            return deductAmt;
        }

        if(month.size() > 0){
            LOG.info("代理商唯一码：{}，特殊奖励预发周期月份:{}", detail.getPosAgentId(), JSONObject.toJSON(month).toString());
            for(String previewDate : month){
                BigDecimal deductRewardAmt = this.obtainGlobalTemp(posRewardDetail, previewDate);
                LOG.info("代理商唯一码：{}，特殊奖励预发周期月份:{}，考核奖励扣款金额：{}", detail.getPosAgentId(), previewDate, deductRewardAmt);
                deductAmt = deductAmt.add(deductRewardAmt);
            }
            return deductAmt;
        }
        return deductAmt;
    }

    /**
     * 判断预发周期，在那个季度的通用奖励模板中，返回扣款奖励
     * @param posRewardDetail
     * @param previewDate
     * @return
     */
    private BigDecimal obtainGlobalTemp(PosRewardDetail posRewardDetail, String previewDate) {
        BigDecimal deductRewardAmt = BigDecimal.ZERO;
        List<PosRewardTemplate> posRewardTemplates =  this.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return deductRewardAmt;
        }
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                List<String> list = getMonthBetween(spl[0], spl[1]);
                for(String activitDate : list){
                    if(Objects.equals(activitDate, previewDate)){
                        BigDecimal deductAmt = this.againComputeReward(posRewardTemplate, posRewardDetail, previewDate);
                        deductRewardAmt = deductRewardAmt.add(deductAmt);
                        if(deductAmt.compareTo(BigDecimal.ZERO) > 0){
                            end = true;
                            break;
                        }
                    }
                }
                if(end){
                    break;
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), previewDate)){
                    BigDecimal deductAmt = this.againComputeReward(posRewardTemplate, posRewardDetail, previewDate);
                    deductRewardAmt = deductRewardAmt.add(deductAmt);
                    if(deductAmt.compareTo(BigDecimal.ZERO) > 0){
                        break;
                    }
                }
            }
        }
        return deductRewardAmt;
    }

    /**
     * 按照通用模板重新计算奖励，然后用特殊奖励-通用奖励=考核扣款
     * @param posRewardTemplate
     * @param posRewardDetail
     */
    private BigDecimal againComputeReward(PosRewardTemplate posRewardTemplate, PosRewardDetail posRewardDetail, String previewDate) {
        posRewardDetail.setProfitPosDate(previewDate);
        PosRewardDetail tranDateDetail = posRewardSDetailService.getPosRewardDetail(posRewardDetail);
        BigDecimal currentTranSum = BigDecimal.ZERO;
        BigDecimal compareTranSum = BigDecimal.ZERO;
        BigDecimal currentCreditSum = BigDecimal.ZERO;
        BigDecimal compareCreditSum = BigDecimal.ZERO;
        if(tranDateDetail != null){
            currentTranSum = currentTranSum.add(new BigDecimal(tranDateDetail.getPosCurrentCount()==null ? "0" : tranDateDetail.getPosCurrentCount()));
            compareTranSum = compareTranSum.add(new BigDecimal(tranDateDetail.getPosCompareCount()==null ? "0" : tranDateDetail.getPosCompareCount()));
            currentCreditSum = currentCreditSum.add(new BigDecimal(tranDateDetail.getPosCurrentLoanCount()==null ? "0" : tranDateDetail.getPosCurrentLoanCount()));
            compareCreditSum = compareCreditSum.add(new BigDecimal(tranDateDetail.getPosCompareLoanCount()==null ? "0" : tranDateDetail.getPosCompareLoanCount()));
        }
        BigDecimal tranAmt = currentTranSum.subtract(compareTranSum);
        if(tranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && tranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0 ){
            LOG.info("代理商ID：{}，交易总额达标范围：{}万~{}万，奖励比例：{}",
                    posRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            BigDecimal creditSum = currentCreditSum.subtract(compareCreditSum);
            if(creditSum.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal posRewardAmt = creditSum.multiply(posRewardTemplate.getProportion()).setScale(2,BigDecimal.ROUND_HALF_UP);
                BigDecimal posReawrdProfit = tranDateDetail.getPosReawrdProfit() == null ? BigDecimal.ZERO : new BigDecimal(tranDateDetail.getPosReawrdProfit());
                return posReawrdProfit.subtract(posRewardAmt);
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            LOG.info("代理商ID：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万",
                    posRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            return BigDecimal.ZERO;
        }
    }


    /**
     * 根据代理商查找上级代理商ID
     */
    private String obtainParentAgentId(String busNum){
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), PLATFORM_CODE, busNum);
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            return agentBusInfo.get(0).getAgentId();
        }
        return null;
    }

    /**
     * 扣减上级代理商月分润
     */
    private void deductParentAgentPosRewardAmt(PosRewardDetail parentPosRewardDetail, BigDecimal profitAmt, String computType) {
        List<ProfitDetailMonth> list = profitMonthService.getAgentProfit(parentPosRewardDetail.getPosAgentId(),
                parentPosRewardDetail.getProfitPosDate(), "");
        if(list == null || list.isEmpty()){
            LOG.error("未查询到上级代理商唯一码：{}", parentPosRewardDetail.getPosAgentId());
            return;
        }
        ProfitDetailMonth profitDetailMonth= list.get(0);
        BigDecimal praentBasicsProfitAmt = profitDetailMonth.getBasicsProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getBasicsProfitAmt();
        ProfitDetailMonth update = new ProfitDetailMonth();
        update.setId(profitDetailMonth.getId());
        if(praentBasicsProfitAmt.compareTo(profitAmt) > 0){
            update.setPosRewardAmt(profitAmt);
            update.setBasicsProfitAmt(praentBasicsProfitAmt.subtract(profitAmt));
        } else if(profitAmt.compareTo(praentBasicsProfitAmt) == 0){
            update.setPosRewardAmt(profitAmt);
            update.setBasicsProfitAmt(BigDecimal.ZERO);
        } else if(profitAmt.compareTo(praentBasicsProfitAmt) > 0){
            update.setPosRewardAmt(profitAmt);
            update.setBasicsProfitAmt(BigDecimal.ZERO);
            update.setPosRewardDeductionAmt(profitAmt.subtract(praentBasicsProfitAmt));
        } else if(praentBasicsProfitAmt.compareTo(BigDecimal.ZERO) <= 0) {
            update.setPosRewardDeductionAmt(profitAmt);
        }
        profitDetailMonthServiceImpl.updateByPrimaryKeySelective(update);
        //新增扣款明细
        ProfitDeduction deductDetail = new ProfitDeduction();
        deductDetail.setAgentId(profitDetailMonth.getAgentId());
        deductDetail.setAgentPid(profitDetailMonth.getAgentPid());
        deductDetail.setDeductionDate(parentPosRewardDetail.getProfitPosDate());
        deductDetail.setDeductionDesc(PLATFORM_CODE);
        deductDetail.setDeductionType(DeductionType.POS_REWARD_DEDUCT.getType());
        deductDetail.setActualDeductionAmt(profitAmt);
        deductDetail.setMustDeductionAmt(profitAmt);
        deductDetail.setRemark("下级代理商扣减上级代理商pos奖励，下级代理的ID"+parentPosRewardDetail.getPosAgentId());
        deductDetail.setId(profitDetailMonth.getId());
        if(Objects.equals(computType, "1")){
            profitDeducttionDetailService.insertDeducttionDetail(deductDetail);
        }
    }


    /**
     * 初始化POS奖励的基础数据
     */
    @Override
    public void otherOperate() {
        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        posRewardSDetailService.deleteCurrentDate(currentDate);
        TransProfitDetail detail = new TransProfitDetail();
        detail.setProfitDate(currentDate);
        detail.setBusCode(PLATFORM_CODE);
        List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detail);
        list.forEach(transProfitDetail -> {
            if(transProfitDetail.getAgentId() == null){
                return;
            }
            PosRewardDetail posrewardDetail = new PosRewardDetail();
            posrewardDetail.setPosAgentId(transProfitDetail.getAgentId());
            posrewardDetail.setPosAgentName(transProfitDetail.getAgentName());
            posrewardDetail.setProfitPosDate(transProfitDetail.getProfitDate());
            posrewardDetail.setPosMechanismType(transProfitDetail.getAgentType());
            posrewardDetail.setPosMechanismId(transProfitDetail.getBusNum());

            BigDecimal currentTranSumAmt = transProfitDetail.getPosRewardAmt() == null ? transProfitDetail.getInTransAmt() : transProfitDetail.getPosRewardAmt();
            LOG.info("代理商唯一码：{}，当月交易总量（不包括下级交易量）：{}", transProfitDetail.getAgentId(), currentTranSumAmt);
            BigDecimal currentCreditTranSumAmt = transProfitDetail.getPosCreditAmt() == null ? BigDecimal.ZERO : transProfitDetail.getPosCreditAmt();
            LOG.info("代理商唯一码：{}，当月贷记交易总量（不包括下级贷记交易量）：{}", transProfitDetail.getAgentId(), currentCreditTranSumAmt);

            //找代理商下级AG码list
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("busNum", transProfitDetail.getBusNum());
            map.put("agentId", transProfitDetail.getAgentId());
            List<String> childAgentList = this.queryChildLevelByBusNum(map);
            if(childAgentList.size() > 0){
                BigDecimal childInTransAmt = BigDecimal.ZERO;
                BigDecimal childCreditTransAmt = BigDecimal.ZERO;
                List<TransProfitDetail> tranList = profitDetailMonthServiceImpl.getChildTransProfitDetailList(childAgentList, transProfitDetail.getProfitDate(), null);
                if(tranList != null && !tranList.isEmpty()){
                    for(TransProfitDetail detail1 : tranList){
                        childInTransAmt = childInTransAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                        childCreditTransAmt = childCreditTransAmt.add(detail1.getPosCreditAmt() == null ? BigDecimal.ZERO : detail1.getPosCreditAmt());
                    }
                    LOG.info("代理商唯一码：{}，当月下级代理商交易总量：{}", posrewardDetail.getPosAgentId(), childInTransAmt);
                    LOG.info("代理商唯一码：{}，当月下级代理商贷记交易总量：{}", posrewardDetail.getPosAgentId(), childCreditTransAmt);
                    currentTranSumAmt = currentTranSumAmt.add(childInTransAmt);
                    currentCreditTranSumAmt = currentCreditTranSumAmt.add(childCreditTransAmt);
                }
            } else {
                LOG.info("代理商唯一码：{}，该代理商没有下级代理商", map.get("agentId"));
            }
            posrewardDetail.setPosCurrentCount(currentTranSumAmt.toString());
            posrewardDetail.setPosCurrentLoanCount(currentCreditTranSumAmt.toString());

            //根据通用奖励模板补充，对比月交易量、对比月贷记交易量
            this.obtainCurrencyRewardTemp(currentDate, posrewardDetail);

            //计算奖励标准
            if(Objects.equals(transProfitDetail.getAgentType(), AGENT_TYPE_2)
                    || Objects.equals(transProfitDetail.getAgentType(), AGENT_TYPE_6)) {
                PosReward posReward = new PosReward();
                posReward.setAgentId(transProfitDetail.getAgentId());
                posReward.setApplyStatus("1");
                List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);
                if(posRewardList == null || posRewardList.isEmpty()){
                    this.computRewardStandard(posrewardDetail, currentDate);
                } else {
                    BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
                    LOG.info("代理商唯一码：{}，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
                    posrewardDetail.setPosAmt(posAmt.toString());
                    posrewardDetail.setPosStandard(posRewardList.get(0).getGrowAmt().toString());
                    posrewardDetail.setPosRemark("TS_Template");
                }
            } else if(Objects.equals(transProfitDetail.getAgentType(), AGENT_TYPE_3))  {
                this.computRewardStandard(posrewardDetail, currentDate);
            } else {
                posrewardDetail.setPosAmt("0");
                posrewardDetail.setPosOwnReward("0");
                posrewardDetail.setPosStandard("0");
                posrewardDetail.setPosDownReward("0");
                posrewardDetail.setPosReawrdProfit("0");
            }
            posRewardSDetailService.insert(posrewardDetail);
        });
    }

    /**
     * 查询下级商户AG码
     * @param map
     * @return
     */
    private List<String> queryChildLevelByBusNum(Map<String, Object> map){
        List<AgentBusInfo> busList = agentBusinfoService.queryChildLevelByBusNum(null, PLATFORM_CODE, map.get("busNum").toString());
        List<String> agentIdList = new ArrayList<String>();
        if(busList != null && !busList.isEmpty()) {
            busList.forEach(agentBusInfo -> {
                agentIdList.add(agentBusInfo.getAgentId());
            });
        }
        LOG.info("代理商唯一码：{}，下级代理商唯一码列表：{}", map.get("agentId"),  JSONObject.toJSON(agentIdList).toString());
        return agentIdList;
    }

    /**
     * 获取通用奖励模板
     * @param currentDate
     * @param posrewardDetail
     */
    private void obtainCurrencyRewardTemp(String currentDate, PosRewardDetail posrewardDetail) {
        List<PosRewardTemplate> posRewardTemplates =  this.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return;
        }
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                List<String> list = getMonthBetween(spl[0], spl[1]);
                for(String activitDate : list){
                    if(Objects.equals(activitDate.replaceAll("-",""), currentDate)){
                        if(this.obtainContrastMonthTrans(currentDate, posRewardTemplate, posrewardDetail)){
                            end = true;
                            break;
                        }
                    }
                }
                if(end){
                    break;
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), currentDate)){
                    this.obtainContrastMonthTrans(currentDate, posRewardTemplate, posrewardDetail);
                }
            }
        }
    }

    /**
     * 缓存中获取通用奖励模板
     * @return
     */
    private List<PosRewardTemplate> getPosRewardTemplateList() {
        String tmep = redisTemplate.opsForValue().get("POS_REWARD_TEMP");
        List list = JSONObject.parseObject(tmep, List.class);
        if(list != null && !list.isEmpty()){
            List<PosRewardTemplate> posRewardTemplates = new ArrayList<PosRewardTemplate>(list.size());
            list.forEach(lists -> {
                JSON json = (JSON) JSONObject.parse(lists.toString());
                PosRewardTemplate posRewardTemplate = JSONObject.toJavaObject(json, PosRewardTemplate.class);
                posRewardTemplates.add(posRewardTemplate);
            });
            return posRewardTemplates;
        } else {
            return  posRewardTemplateService.getPosRewardTemplateList();
        }
    }

    /**
     * 到达活动奖励月份，获取活动对比月交易量
     * @param currentDate
     * @param posRewardTemplate
     * @param posrewardDetail
     */
    private boolean obtainContrastMonthTrans(String currentDate, PosRewardTemplate posRewardTemplate, PosRewardDetail posrewardDetail) {
        String tranContrastMonth = posRewardTemplate.getTranContrastMonth().replaceAll("-", "");
        String creditTranContrastMonth = posRewardTemplate.getCreditTranContrastMonth().replaceAll("-", "");
        LOG.info("查询对比月交易量，代理商唯一码：{}，交易对比月：{}，贷记对比月：{}", posrewardDetail.getPosAgentId(), tranContrastMonth, creditTranContrastMonth);
        TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(posrewardDetail.getPosAgentId(),
                tranContrastMonth, posrewardDetail.getPosMechanismType());
        BigDecimal tranContrastMonthAmt = transProfitDetail == null ? BigDecimal.ZERO : transProfitDetail.getPosRewardAmt();
        LOG.info("代理商唯一码：{}，交易对比月（不包括下级交易量）：{}", posrewardDetail.getPosAgentId(), tranContrastMonthAmt);

        TransProfitDetail creditDetail = profitDetailMonthServiceImpl.getTransProfitDetail(posrewardDetail.getPosAgentId(),
                creditTranContrastMonth, posrewardDetail.getPosMechanismType());
        BigDecimal creditTranContrastMonthAmt = creditDetail == null ? BigDecimal.ZERO : creditDetail.getPosCreditAmt();
        LOG.info("代理商唯一码：{}，贷记交易对比月（不包括下级贷记交易量）：{}", posrewardDetail.getPosAgentId(), creditTranContrastMonthAmt);

        //找代理商下级AG码list
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busNum", posrewardDetail.getPosMechanismId());
        map.put("agentId", posrewardDetail.getPosAgentId());
        List<String> agentIdList = this.queryChildLevelByBusNum(map);
        if (agentIdList.size() > 0) {
            BigDecimal tranChildInTransAmt = BigDecimal.ZERO;
            List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, tranContrastMonth, null);
            if (list != null && !list.isEmpty()) {
                for (TransProfitDetail detail1 : list) {
                    tranChildInTransAmt = tranChildInTransAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                }
                LOG.info("代理商唯一码：{}，交易对比月下级代理商交易总量：{}", posrewardDetail.getPosAgentId(), tranChildInTransAmt);
                tranContrastMonthAmt = tranContrastMonthAmt.add(tranChildInTransAmt);
            }


            BigDecimal creditChildInTransAmt = BigDecimal.ZERO;
            List<TransProfitDetail> creditList = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, creditTranContrastMonth, null);
            if (creditList != null && !creditList.isEmpty()) {
                for (TransProfitDetail detail1 : creditList) {
                    creditChildInTransAmt = creditChildInTransAmt.add(detail1.getPosCreditAmt() == null ? BigDecimal.ZERO : detail1.getPosCreditAmt());
                }
                LOG.info("代理商唯一码：{}，贷记对月下级代理商贷记交易总量：{}", posrewardDetail.getPosAgentId(), creditChildInTransAmt);
                creditTranContrastMonthAmt = creditTranContrastMonthAmt.add(creditChildInTransAmt);
            }
        }
        posrewardDetail.setPosCompareCount(tranContrastMonthAmt.toString());
        posrewardDetail.setPosCompareLoanCount(creditTranContrastMonthAmt.toString());
        return true;
    }

    /**
     * 循环奖励模板
     * @param posrewardDetail
     * @param currentDate
     */
    private void computRewardStandard(PosRewardDetail posrewardDetail, String currentDate) {
        List<PosRewardTemplate> posRewardTemplates =  this.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return;
        }
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                List<String> list = getMonthBetween(spl[0], spl[1]);
                for(String activitDate : list){
                    if(Objects.equals(activitDate.replaceAll("-",""), currentDate)){
                        if(this.obtainRewardStandard(posrewardDetail,posRewardTemplate)){
                            end = true;
                            break;
                        }
                    }
                }
                if(end){
                    break;
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), currentDate)){
                    this.obtainRewardStandard(posrewardDetail,posRewardTemplate);
                }
            }
        }
    }

    /**
     * 获取奖励标准
     * @param posrewardDetail
     * @param posRewardTemplate
     * @return
     */
    private boolean obtainRewardStandard(PosRewardDetail posrewardDetail, PosRewardTemplate posRewardTemplate) {
        BigDecimal tranSumAmt = new BigDecimal(posrewardDetail.getPosCurrentCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareCount()));
        if(tranSumAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && tranSumAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0 ){

            BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
            LOG.info("代理商唯一码：{}，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
            posrewardDetail.setPosAmt(posAmt.toString());
            if(posAmt.compareTo(BigDecimal.ZERO) > 0){
                LOG.info("代理商唯一码：{}，交易总额达标范围：{}万~{}万，奖励比例：{}", posrewardDetail.getPosAgentId(),
                        posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
                posrewardDetail.setPosStandard(posRewardTemplate.getProportion().toString());
                posrewardDetail.setPosRemark("TY_Template");
            }
            return true;
        } else {
            LOG.info("代理商唯一码：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万", posrewardDetail.getPosAgentId(),
                    tranSumAmt, posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
            posrewardDetail.setPosStandard("0");
            return false;
        }
    }

    private static List<String> getMonthBetween(String minDate, String maxDate) {
        try {
            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> list = PosProfitComputeServiceImpl.getMonthBetween("2018-06", "2018-06");
        list.forEach(s -> {
            System.out.println(s);
        });

//        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
//        System.out.println(currentDate);
    }
}
