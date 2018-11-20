package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
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
    protected RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TransProfitDetailService transProfitDetailService;
    @Autowired
    private  PosRewardSDetailService posRewardSDetailService;
    @Autowired
    private ProfitMonthService profitMonthService;
    private static final String PLATFORM_CODE = "100003";

    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        map.put("posRewardAmt", BigDecimal.ZERO);//POS奖励
        map.put("posAssDeductAmt", BigDecimal.ZERO);//POS奖励考核扣款
        return map;
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
     * 初始化POS奖励的基础数据
     */
    @Override
    public void otherOperate() {
        long khstart = System.currentTimeMillis();
        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        posRewardSDetailService.deleteCurrentDate(currentDate);
        TransProfitDetail detail = new TransProfitDetail();
        detail.setProfitDate(currentDate);
        detail.setBusCode(PLATFORM_CODE);
        List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detail);
        LOG.info("总条数：{}", list.size());
        List<PosRewardTemplate> posRewardTemplates =  this.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return;
        }
        List<PosRewardDetail> detailList = new ArrayList<PosRewardDetail>(list.size());
        list.parallelStream().forEach(transProfitDetail -> {
            if(transProfitDetail.getAgentId() == null){
                return;
            }
            PosRewardDetail posrewardDetail = new PosRewardDetail();
            posrewardDetail.setPosAgentId(transProfitDetail.getAgentId());
            posrewardDetail.setPosAgentName(transProfitDetail.getAgentName());
            posrewardDetail.setProfitPosDate(transProfitDetail.getProfitDate());
            posrewardDetail.setPosMechanismType(transProfitDetail.getAgentType());
            posrewardDetail.setPosMechanismId(transProfitDetail.getBusNum());
            posrewardDetail.setPosAmt("0");
            posrewardDetail.setPosOwnReward("0");
            posrewardDetail.setPosStandard("0");
            posrewardDetail.setPosDownReward("0");
            posrewardDetail.setPosReawrdProfit("0");
            posrewardDetail.setPosCheckDeductAmt("0");
            posrewardDetail.setPosCurrentCount("0");
            posrewardDetail.setPosCurrentLoanCount("0");

            //找代理商下级AG码list
            List<String> childAgentList = this.queryChildLevelByBusNum(transProfitDetail.getAgentId());
            posrewardDetail.setChildAgentIdList(JSONObject.toJSONString(childAgentList));
            //根据当前时间获取通用奖励模板
            PosRewardTemplate template = this.obtainCurrencyRewardTemp(currentDate, posRewardTemplates);
            childAgentList.add(posrewardDetail.getPosAgentId());
            //根据通用奖励模板补充，对比月交易量、对比月贷记交易量
            this.obtainContrastMonthTrans(currentDate, template, posrewardDetail, childAgentList);

            //获取奖励标准
            PosReward posReward = new PosReward();
            posReward.setAgentId(transProfitDetail.getAgentId());
            posReward.setApplyStatus("1");
            List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);
            if(posRewardList == null || posRewardList.isEmpty()){
                this.computRewardStandard(posrewardDetail, currentDate, posRewardTemplates);
            } else {
                this.computSpecialStandard(posrewardDetail, currentDate, posRewardList);
            }

            //计算分润奖励
            LOG.info("代理商唯一码：{}，POS奖励交易量：{}，奖励标准：{}", posrewardDetail.getPosAgentId(), posrewardDetail.getPosAmt(), posrewardDetail.getPosStandard());
            if(new BigDecimal(posrewardDetail.getPosAmt()).compareTo(BigDecimal.ZERO) > 0){
                BigDecimal posRoward = new BigDecimal(posrewardDetail.getPosAmt()).multiply(new BigDecimal(posrewardDetail.getPosStandard())).setScale(2, BigDecimal.ROUND_HALF_UP);
                LOG.info("代理商唯一码：{}，POS奖励：{}", posrewardDetail.getPosAgentId(), posRoward);
                posrewardDetail.setPosOwnReward(posRoward.toString());
                posrewardDetail.setPosReawrdProfit(posRoward.toString());
            }
            posRewardSDetailService.insert(posrewardDetail);
            detailList.add(posrewardDetail);
        });

        LOG.info("POS奖励明细基础数据导入、基础奖励金额计算结束");

        LOG.info("开始扣减机构一代、标准一代（包含下级）POS奖励");
        PosRewardDetail posDetatil = new PosRewardDetail();
        posDetatil.setProfitPosDate(currentDate);
        detailList.parallelStream().forEach(posRewardDetail -> {
            List childAgentList = JSONObject.parseObject(posRewardDetail.getChildAgentIdList(), List.class);
            BigDecimal downReward = BigDecimal.ZERO;
            if(childAgentList.size() > 0 ){
                List<PosRewardDetail> childDetail = posRewardSDetailService.getPosRewardDetailList(posDetatil, null, childAgentList);
                for (PosRewardDetail detail1 : childDetail){
                    downReward = downReward.add(new BigDecimal(detail1.getPosReawrdProfit()));
                }
            }
            LOG.info("代理商唯一码：{}，扣减机构一代、标准一代（包含下级）POS奖励：{}",posRewardDetail.getPosAgentId(), downReward);
            if(downReward.compareTo(BigDecimal.ZERO) != 0 ){
                PosRewardDetail updateDetail = new PosRewardDetail();
                updateDetail.setId(posRewardDetail.getId());
                updateDetail.setPosDownReward(downReward.toString());
                BigDecimal posReawrdProfit = new BigDecimal(posRewardDetail.getPosReawrdProfit()).subtract(downReward);
                updateDetail.setPosReawrdProfit(posReawrdProfit.toString());
                posRewardSDetailService.updatePosRewardDetail(updateDetail);
            }
        });
        LOG.info("扣减机构一代、标准一代（包含下级）POS奖励结束");

        LOG.info("开始计算申请特殊奖励的代理商，考核奖励扣款部分");
        detailList.parallelStream().forEach(posRewardDetail -> {
            PosReward posReward = new PosReward();
            posReward.setAgentId(posRewardDetail.getPosAgentId());
            posReward.setApplyStatus("1");
            List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);
            if(posRewardList != null || !posRewardList.isEmpty()){
                for(PosReward rewardTemp : posRewardList){
                    if(Objects.equals(rewardTemp.getTotalEndMonth().replaceAll("-",""), currentDate)){
                        LOG.info("考核代理商唯一码：{}，到达考核月份:{}", posRewardDetail.getPosAgentId(), currentDate);
                        if(new BigDecimal(posRewardDetail.getPosCurrentCount()).compareTo(rewardTemp.getGrowAmt().multiply(new BigDecimal("10000"))) >= 0){
                            LOG.info("考核代理商唯一码：{}，考核交易量达到活动指标，不进行扣减奖励", posRewardDetail.getPosAgentId());
                            break;
                        } else {
                            LOG.info("考核代理商唯一码：{}，考核未达标，对预发周期得月份按照通用奖励计算进行扣款", posRewardDetail.getPosAgentId());
                            List<String> previewDateList = getMonthBetween(rewardTemp.getTotalConsMonth(), rewardTemp.getCreditConsMonth());
                            BigDecimal deductAmt = BigDecimal.ZERO;
                            for(String previewDate: previewDateList){
                                posDetatil.setProfitPosDate(previewDate);
                                posDetatil.setPosAgentId(posRewardDetail.getPosAgentId());
                                PosRewardDetail previewRewardDetail = posRewardSDetailService.getPosRewardDetail(posDetatil);
                                if(previewRewardDetail != null){
                                    BigDecimal deductRewardAmt = this.obtainGlobalTemp(previewRewardDetail, previewDate, posRewardTemplates);
                                    deductAmt = deductAmt.add(deductRewardAmt);
                                    LOG.info("考核代理商唯一码：{}，特殊奖励预发周期月份:{}，考核奖励扣款金额：{}", posRewardDetail.getPosAgentId(), previewDate, deductRewardAmt);
                                }
                            }
                            LOG.info("考核代理商唯一码：{}，考核总扣款金额：{}", posRewardDetail.getPosAgentId(), deductAmt);
                            if(deductAmt.compareTo(BigDecimal.ZERO) != 0){
                                posRewardDetail.setPosCheckDeductAmt(deductAmt.abs().toString());
                                PosRewardDetail updateDetail = new PosRewardDetail();
                                updateDetail.setId(posRewardDetail.getId());
                                updateDetail.setPosCheckDeductAmt(deductAmt.abs().toString());
                                posRewardSDetailService.updatePosRewardDetail(updateDetail);
                            }
                        }
                    }
                }
            }
        });
        LOG.info("计算申请特殊奖励的代理商，考核奖励扣款，结束");

        LOG.info("更新基础分润中，POS奖励、考核扣款金额，开始");
        detailList.parallelStream().forEach(posRewardDetail -> {
            if(new BigDecimal(posRewardDetail.getPosCheckDeductAmt()).compareTo(BigDecimal.ZERO) > 0 ||
                    new BigDecimal(posRewardDetail.getPosReawrdProfit()).compareTo(BigDecimal.ZERO) > 0 ){
                LOG.info("代理商唯一码：{}，更新基础分润POS奖励信息",posRewardDetail.getPosAgentId());
                String superAgentId = posRewardSDetailService.getSuperAgentId(posRewardDetail.getPosAgentId());
                List<ProfitDetailMonth> profitMonth = profitMonthService.getAgentProfit(posRewardDetail.getPosAgentId(),
                        posRewardDetail.getProfitPosDate(), superAgentId);
                if(profitMonth != null && !profitMonth.isEmpty()){
                    ProfitDetailMonth detatil = new ProfitDetailMonth();
                    detatil.setId(profitMonth.get(0).getId());
                    detatil.setPosRewardDeductionAmt(new BigDecimal(posRewardDetail.getPosCheckDeductAmt()));
                    detatil.setPosRewardAmt(new BigDecimal(posRewardDetail.getPosReawrdProfit()));
                    profitDetailMonthServiceImpl.updateByPrimaryKeySelective(detatil);
                }
            }
        });
        LOG.info("更新基础分润中，POS奖励、考核扣款金额，结束");
        long khend = System.currentTimeMillis();
        LOG.info("考核处理时间"+(khend-khstart));
    }

    /**
     * 查询特殊奖励模板，按照预发周期，计算特殊奖励
     * @param posrewardDetail
     * @param currentDate
     * @param posRewardList
     */
    private void computSpecialStandard(PosRewardDetail posrewardDetail, String currentDate, List<PosReward> posRewardList) {
        posRewardList.forEach(posReward -> {
            List<String> list = getMonthBetween(posReward.getTotalConsMonth(), posReward.getCreditConsMonth());
            for (String date : list){
                if(Objects.equals(date, currentDate)){
                    BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
                    LOG.info("代理商唯一码：{}，预发周期内，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
                    posrewardDetail.setPosAmt(posAmt.toString());
                    posrewardDetail.setPosStandard(posRewardList.get(0).getRewardScale().toString());
                    posrewardDetail.setPosRemark("TS_Template");
                }
            }
        });
    }

    /**
     * 查询下级商户AG码
     * @return
     */
    private List<String> queryChildLevelByBusNum(String agentId){
        List<String> list  = posRewardSDetailService.queryChildLevelByAgentId(agentId);
        if(list != null && !list.isEmpty()){
            LOG.info("代理商唯一码：{}，下级代理商唯一码列表：{}", agentId,  JSONObject.toJSON(list).toString());
            return list;
        } else {
            LOG.info("代理商唯一码：{}，下级代理商唯一码列表：{}", agentId,  "[]");
            return new ArrayList<String>();
        }
    }

    /**
     * 获取通用奖励模板
     * @param currentDate
     */
    private PosRewardTemplate obtainCurrencyRewardTemp(String currentDate, List<PosRewardTemplate> posRewardTemplates) {
        PosRewardTemplate template = null;
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for(String activitDate : list){
                if(Objects.equals(activitDate.replaceAll("-",""), currentDate)){
                    template = posRewardTemplate;
                    end = true;
                    break;
                }
            }
            if(end){
                break;
            }
        }
        return template;
    }

    /**
     * 缓存中获取通用奖励模板
     * @return
     */
    private List<PosRewardTemplate> getPosRewardTemplateList() {
        String tmep = redisTemplate.opsForValue().get("POS_REWARD_TEMP");
        List list = JSONObject.parseObject(tmep, List.class);
        if(list != null && !list.isEmpty()){
            LOG.info("缓存获取通用奖励模板");
            List<PosRewardTemplate> posRewardTemplates = new ArrayList<PosRewardTemplate>(list.size());
            list.forEach(lists -> {
                JSON json = (JSON) JSONObject.parse(lists.toString());
                PosRewardTemplate posRewardTemplate = JSONObject.toJavaObject(json, PosRewardTemplate.class);
                posRewardTemplates.add(posRewardTemplate);
            });
            return posRewardTemplates;
        } else {
            LOG.info("数据库获取通用奖励模板");
            return  posRewardTemplateService.getPosRewardTemplateList();
        }
    }

    /**
     * 到达活动奖励月份，获取活动对比月交易量
     * @param currentDate
     * @param posRewardTemplate
     * @param posrewardDetail
     */
    private void obtainContrastMonthTrans(String currentDate, PosRewardTemplate posRewardTemplate,
                                             PosRewardDetail posrewardDetail, List<String> childAgentList) {
        List<String> date = new ArrayList<>();
        date.add(currentDate);
        String tranContrastMonth = posRewardTemplate.getTranContrastMonth().replaceAll("-", "");
        date.add(tranContrastMonth);
        String creditTranContrastMonth = posRewardTemplate.getCreditTranContrastMonth().replaceAll("-", "");
        date.add(creditTranContrastMonth);
        BigDecimal tranContrastMonthAmt = BigDecimal.ZERO;
        BigDecimal creditTranContrastMonthAmt = BigDecimal.ZERO;
        BigDecimal childInTransAmt = BigDecimal.ZERO;
        BigDecimal childCreditTransAmt = BigDecimal.ZERO;
        List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(childAgentList, date, null);
        if (list != null && !list.isEmpty()) {
            for (TransProfitDetail detail1 : list) {
                if(Objects.equals(detail1.getProfitDate(), tranContrastMonth)){
                    tranContrastMonthAmt = tranContrastMonthAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                } else if(Objects.equals(detail1.getProfitDate(), currentDate)) {
                    childInTransAmt = childInTransAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                    childCreditTransAmt = childCreditTransAmt.add(detail1.getPosCreditAmt() == null ? BigDecimal.ZERO : detail1.getPosCreditAmt());
                } else if(Objects.equals(detail1.getProfitDate(), creditTranContrastMonth)){
                    creditTranContrastMonthAmt = creditTranContrastMonthAmt.add(detail1.getPosCreditAmt() == null ? BigDecimal.ZERO : detail1.getPosCreditAmt());
                }
            }
        }
        LOG.info("代理商唯一码：{}，当月交易总量：{}", posrewardDetail.getPosAgentId(), childInTransAmt);
        LOG.info("代理商唯一码：{}，当月贷记交易总量：{}", posrewardDetail.getPosAgentId(), childCreditTransAmt);
        LOG.info("代理商唯一码：{}，交易对比月：{}，交易对比月交易总量：{}", posrewardDetail.getPosAgentId(), tranContrastMonth, tranContrastMonthAmt);
        LOG.info("代理商唯一码：{}，贷记对比月：{}，贷记对月贷记交易总量：{}", posrewardDetail.getPosAgentId(), creditTranContrastMonth, creditTranContrastMonthAmt);
        posrewardDetail.setPosCompareCount(tranContrastMonthAmt.toString());
        posrewardDetail.setPosCompareLoanCount(creditTranContrastMonthAmt.toString());
        posrewardDetail.setPosCurrentCount(new BigDecimal(posrewardDetail.getPosCurrentCount()).add(childInTransAmt).toString());
        posrewardDetail.setPosCurrentLoanCount(new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).add(childCreditTransAmt).toString());
    }

    /**
     * 循环奖励模板
     * @param posrewardDetail
     * @param currentDate
     */
    private void computRewardStandard(PosRewardDetail posrewardDetail, String currentDate, List<PosRewardTemplate> posRewardTemplates) {
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for(String activitDate : list){
                if(Objects.equals(activitDate.replaceAll("-",""), currentDate)){
                    BigDecimal proportion = this.obtainRewardStandard(posrewardDetail,posRewardTemplate);
                    if(proportion.compareTo(BigDecimal.ZERO) > 0){
                        end = true;
                        break;
                    }
                }
            }
            if(end){
                break;
            }
        }
    }

    /**
     * 获取奖励标准
     * @param posrewardDetail
     * @param posRewardTemplate
     * @return
     */
    private BigDecimal obtainRewardStandard(PosRewardDetail posrewardDetail, PosRewardTemplate posRewardTemplate) {
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
                return posRewardTemplate.getProportion();
            }
        } else {
            LOG.info("代理商唯一码：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万", posrewardDetail.getPosAgentId(),
                    tranSumAmt, posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
            posrewardDetail.setPosStandard("0");
        }
        return BigDecimal.ZERO;
    }

    /**
     * 判断预发周期，在那个季度的通用奖励模板中，返回扣款奖励
     */
    private BigDecimal obtainGlobalTemp(PosRewardDetail previewRewardDetail, String previewDate, List<PosRewardTemplate> posRewardTemplates) {
        boolean end = false;
        BigDecimal deductRewardAmt = BigDecimal.ZERO;
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for(String activitDate : list){
                if(Objects.equals(activitDate.replaceAll("-", ""), previewDate)){
                    BigDecimal deductAmt = this.againComputeReward(posRewardTemplate, previewRewardDetail, previewDate);
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
        }
        return deductRewardAmt;
    }

    /**
     * 按照通用模板重新计算奖励，然后用特殊奖励-通用奖励=考核扣款
     * @param posRewardTemplate
     */
    private BigDecimal againComputeReward(PosRewardTemplate posRewardTemplate, PosRewardDetail previewRewardDetail, String previewDate) {
        BigDecimal tranAmt = new BigDecimal(previewRewardDetail.getPosCurrentCount()).subtract(new BigDecimal(previewRewardDetail.getPosCompareCount()));
        if(tranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && tranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0 ){
            LOG.info("考核代理商ID：{}，交易总额达标范围：{}万~{}万，奖励比例：{}",
                    previewRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            BigDecimal creditSum = new BigDecimal(previewRewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(previewRewardDetail.getPosCompareLoanCount()));
            if(creditSum.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal posRewardAmt = creditSum.multiply(posRewardTemplate.getProportion()).setScale(2,BigDecimal.ROUND_HALF_UP);
                BigDecimal posReawrdProfit = previewRewardDetail.getPosReawrdProfit() == null ? BigDecimal.ZERO : new BigDecimal(previewRewardDetail.getPosReawrdProfit());
                //预发月分润的特殊奖励-预发月通用模板奖励-预发月下级代理商奖励=考核应扣金额
                return posReawrdProfit.subtract(posRewardAmt).subtract(new BigDecimal(previewRewardDetail.getPosDownReward()));
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            LOG.info("代理商ID：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万",
                    previewRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            return BigDecimal.ZERO;
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
