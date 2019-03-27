package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.jobs.ProfitAmtSumJob;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private PosRewardSDetailService posRewardSDetailService;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private ProfitAmtSumJob profitAmtSumJob;
    @Autowired
    TransProfitDetailMapper transProfitDetailMapper;
    @Resource
    ProfitDeductionService profitDeductionService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Resource
    ProfitAgentServiceImpl profitAgentService;

    private static final String PLATFORM_CODE = "100003";
    /**
     * 2:机构 3:机构一代 6:标准一代
     */
    private static final String AGENT_TYPE_2 = "2";
    private static final String AGENT_TYPE_3 = "3";
    private static final String AGENT_TYPE_6 = "6";

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
    public void clearDetail() {
        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        posRewardSDetailService.clearPosRewardDetail(currentDate);
    }

    /**
     * 初始化POS奖励的基础数据
     */
    @Override
    public void otherOperate() {
        try {
            long khstart = System.currentTimeMillis();
            String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
            String preDate = LocalDate.now().plusMonths(-2).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
            LOG.info("{}pos奖励计算开始", currentDate);

            //清除pos奖励明细数据
            posRewardSDetailService.deleteCurrentDate(currentDate);
            //清除考核扣款数据-计算部分
            profitDeductionService.resetDataDeduction(DeductionType.POS_REWARD_CALCU_DEDUCT.getType());
            /*Supplier<TransProfitDetail> details = TransProfitDetail::new;
            TransProfitDetail detail = details.get();
            detail.setProfitDate(currentDate);
            detail.setBusCode(PLATFORM_CODE);
            List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detail);*/

            //交易分润月度明细
            Map<String, Object> params = new HashMap<>();
            params.put("profitDate", currentDate);
            params.put("busCode", PLATFORM_CODE);
            params.put("agentTypeList", "'2','3','6'");  //2:机构 3:机构一代 6:标准一代
            List<TransProfitDetail> list = transProfitDetailMapper.selectListByParams(params);
            LOG.info("总条数：{}", list.size());

            //通用奖励模板
            List<PosRewardTemplate> posRewardTemplates = this.getPosRewardTemplateList();
            if (posRewardTemplates == null || posRewardTemplates.isEmpty()) {
                LOG.error("业务部门未配置通用奖励模板");
                return;
            }

            //pos奖励明细
            List<PosRewardDetail> detailList = new ArrayList<PosRewardDetail>(list.size());
            list.parallelStream().forEach(transProfitDetail -> {
                if (transProfitDetail.getAgentId() == null) {
                    return;
                }
                PosRewardDetail posrewardDetail = new PosRewardDetail();
                posrewardDetail.setPosAgentId(transProfitDetail.getAgentId());
                posrewardDetail.setPosAgentName(transProfitDetail.getAgentName());
                posrewardDetail.setProfitPosDate(transProfitDetail.getProfitDate());
                posrewardDetail.setPosMechanismType(transProfitDetail.getAgentType());//机构类型
                posrewardDetail.setPosMechanismId(transProfitDetail.getBusNum());//业务码
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
                childAgentList.add(posrewardDetail.getPosAgentId());
                posrewardDetail.setChildAgentIdList(JSONObject.toJSONString(childAgentList));

                //根据当前时间获取通用奖励模板
                PosRewardTemplate template = this.obtainCurrencyRewardTemp(currentDate, posRewardTemplates);
                //根据通用奖励模板补充，对比月交易量、对比月贷记交易量
                this.obtainContrastMonthTrans(currentDate, template, posrewardDetail, childAgentList);

                //获取奖励标准
                if (Objects.equals(AGENT_TYPE_2, transProfitDetail.getAgentType())
                        || Objects.equals(AGENT_TYPE_3, transProfitDetail.getAgentType())
                        || Objects.equals(AGENT_TYPE_6, transProfitDetail.getAgentType())) {
                    PosReward posReward = new PosReward();
                    posReward.setAgentId(transProfitDetail.getAgentId());
                    posReward.setApplyStatus("1");

                    //pos奖励申请
                    List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);
                    if (posRewardList == null || posRewardList.isEmpty()) {
                        this.computRewardStandard(posrewardDetail, currentDate, posRewardTemplates);
                    } else {
                        this.computSpecialStandard(posrewardDetail, currentDate, posRewardList);
                    }


                    /*Map<String, Object> posRewardPrams = new HashMap<>();
                    posRewardPrams.put("agentId", transProfitDetail.getAgentId());
                    posRewardPrams.put("applyStatus", "1");
                    posRewardPrams.put("profitMonth", LocalDate.now().plusMonths(-1).toString().substring(0, 7));*/

                    //单个pos奖励申请
                    /*List<PosReward> posRewardList = iPosRewardService.selectPosRewardByParams(posRewardPrams);
                    if (posRewardList != null && !posRewardList.isEmpty()) {
                        this.computSpecialStandard(posrewardDetail, currentDate, posRewardList);
                    } else {
                        //抱团pos奖励申请
                        List<PPosHuddleReward> posHuddleRewardList = iPosRewardService.selectPosHuddleRewardByParams(posRewardPrams);
                        if (posHuddleRewardList != null && !posHuddleRewardList.isEmpty()) {
                            this.computSpecialHuddle(posrewardDetail, currentDate, posHuddleRewardList);
                        } else {
                            this.computRewardStandard(posrewardDetail, currentDate, posRewardTemplates);
                        }
                    }*/


                    //计算分润奖励
                    LOG.info("代理商唯一码：{}，POS奖励交易量：{}，奖励标准：{}", posrewardDetail.getPosAgentId(), posrewardDetail.getPosAmt(), posrewardDetail.getPosStandard());
                    if (new BigDecimal(posrewardDetail.getPosAmt()).compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal posRoward = new BigDecimal(posrewardDetail.getPosAmt()).multiply(new BigDecimal(posrewardDetail.getPosStandard())).setScale(2, BigDecimal.ROUND_HALF_UP);
                        LOG.info("代理商唯一码：{}，POS奖励：{}", posrewardDetail.getPosAgentId(), posRoward);
                        posrewardDetail.setPosOwnReward(posRoward.toString());
                        posrewardDetail.setPosReawrdProfit(posRoward.toString());
                    }
                }
                posRewardSDetailService.insert(posrewardDetail);
                detailList.add(posrewardDetail);
            });

            LOG.info("POS奖励明细基础数据导入、基础奖励金额计算结束");

            LOG.info("开始扣减机构POS奖励");
            PosRewardDetail posDetatil = new PosRewardDetail();
            posDetatil.setProfitPosDate(currentDate);
            List<PosRewardDetail> jgList = detailList.parallelStream().filter(posRewardDetail -> Objects.equals(AGENT_TYPE_2, posRewardDetail.getPosMechanismType())).collect(Collectors.toList());
            jgList.parallelStream().forEach(posRewardDetail -> {
                List childAgentList = JSONObject.parseObject(posRewardDetail.getChildAgentIdList(), List.class);
                childAgentList.remove(posRewardDetail.getPosAgentId());//不包含自己
                BigDecimal downReward = BigDecimal.ZERO;
                if (childAgentList.size() > 0) {
                    List<PosRewardDetail> childDetail = posRewardSDetailService.getPosRewardDetailList(posDetatil, null, childAgentList);
                    BigDecimal childDownReward = childDetail.stream().map(s -> new BigDecimal(s.getPosReawrdProfit())).reduce(BigDecimal::add).orElseGet(() -> new BigDecimal("0"));
                    downReward = downReward.add(childDownReward);
                }
                LOG.info("代理商唯一码：{}，扣减机构一代POS奖励：{}", posRewardDetail.getPosAgentId(), downReward);
                if (downReward.compareTo(BigDecimal.ZERO) > 0) {
                    PosRewardDetail updateDetail = new PosRewardDetail();
                    updateDetail.setId(posRewardDetail.getId());
                    if (new BigDecimal(posRewardDetail.getPosReawrdProfit()).compareTo(BigDecimal.ZERO) == 0) {
                        updateDetail.setPosDownReward(downReward.toString());
                    } else if (new BigDecimal(posRewardDetail.getPosReawrdProfit()).compareTo(downReward) >= 0) {
                        updateDetail.setPosDownReward(downReward.toString());
                        BigDecimal posReawrdProfit = new BigDecimal(posRewardDetail.getPosReawrdProfit()).subtract(downReward);
                        updateDetail.setPosReawrdProfit(posReawrdProfit.toString());
                    } else if (downReward.compareTo(new BigDecimal(posRewardDetail.getPosReawrdProfit())) > 0) {
                        updateDetail.setPosDownReward(downReward.toString());
                        updateDetail.setPosReawrdProfit("0");
                    }
                    posRewardSDetailService.updatePosRewardDetail(updateDetail);
                }
            });
            LOG.info("扣减机构POS奖励结束");

            LOG.info("开始计算申请特殊奖励的代理商，考核奖励扣款部分");

            PosReward posReward = new PosReward();
            posReward.setApplyStatus("1");


            //设置预发周期结束月
            posReward.setCreditConsMonth(LocalDate.now().plusMonths(-1).toString().substring(0, 7));
            //查出当月要进行考核的
            List<PosReward> posRewardList = iPosRewardService.selectByEndMonth(posReward);
            /*List<PosReward> posRewardList = iPosRewardService.selectRewardByMonth(posReward);*/
            posRewardList.parallelStream().forEach(posReward1 -> {

                if (posReward1.getTotalEndMonth() == null) { //不考核的特殊申请不参与考核扣款
                    return;
                }

                //遍历所选的所有考核月份，有一个月通过考核则跳过考核扣款部分
                PosRewardDetail tempDetail = new PosRewardDetail();
                tempDetail.setPosAgentId(posReward1.getAgentId());
                String[] checkMonth = posReward1.getTotalEndMonth().split(",");
                for (String m : checkMonth) {
                    tempDetail.setProfitPosDate(m.replace("-", ""));
                    PosRewardDetail checkRewardDetail = posRewardSDetailService.getPosRewardDetail(tempDetail);
                    if (new BigDecimal(checkRewardDetail.getPosCurrentCount()).compareTo(posReward1.getGrowAmt().multiply(new BigDecimal("10000"))) >= 0) {
                        LOG.info("考核代理商唯一码：{}，{}月考核交易量达到活动指标，不进行扣减奖励", tempDetail.getPosAgentId(), m);
                        return;
                    }
                }


                //未通过考核则继续下面的计算扣款流程
                LOG.info("考核代理商唯一码：{}，考核未达标，对预发周期得月份按照通用奖励计算进行扣款", tempDetail.getPosAgentId());
                List<String> previewDateList = getMonthBetween(posReward1.getTotalConsMonth(), posReward1.getCreditConsMonth());
                BigDecimal deductAmt = BigDecimal.ZERO;
                PosRewardDetail previewRewardDetail = null;
                for (String previewDate : previewDateList) {
                    tempDetail.setProfitPosDate(previewDate.replaceAll("-", ""));
                    previewRewardDetail = posRewardSDetailService.getPosRewardDetail(tempDetail);
                    if (previewRewardDetail != null) {
                        BigDecimal deductRewardAmt = this.obtainGlobalTemp(previewRewardDetail, previewDate, posRewardTemplates);
                        deductAmt = deductAmt.add(deductRewardAmt);
                        LOG.info("考核代理商唯一码：{}，特殊奖励预发周期月份:{}，考核奖励扣款金额：{}", previewRewardDetail.getPosAgentId(), previewDate, deductRewardAmt);
                    }
                }
                LOG.info("考核代理商唯一码：{}，考核总扣款金额：{}", tempDetail.getPosAgentId(), deductAmt);
                if (deductAmt.compareTo(BigDecimal.ZERO) != 0) {

                    //代理商基本信息
                    Map<String, Object> ps = new HashMap<>();
                    ps.put("agentId", previewRewardDetail.getPosAgentId());
                    ps.put("platformNum", PLATFORM_CODE);
                    Map<String, Object> map = profitAgentService.getAgentWithParentInfo(ps);

                    //记录扣款到其他扣款表中deduction_type=05
                    ProfitDeduction profitDeduction = new ProfitDeduction();
                    profitDeduction.setAgentId(previewRewardDetail.getPosAgentId());
                    profitDeduction.setParentAgentId(previewRewardDetail.getPosAgentId());
                    profitDeduction.setAgentId(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                    profitDeduction.setAgentName(map.get("AG_NAME") == null ? "" : map.get("AG_NAME").toString());
                    profitDeduction.setParentAgentId(map.get("PARENT_AGENT_ID") == null ? "" : map.get("PARENT_AGENT_ID").toString());
                    profitDeduction.setParentAgentName(map.get("PARENT_AGENT_NAME") == null ? "" : map.get("PARENT_AGENT_NAME").toString());
                    profitDeduction.setDeductionDesc("POS考核奖励扣款（系统计算）");
                    profitDeduction.setDeductionType(DeductionType.POS_REWARD_CALCU_DEDUCT.getType());
                    profitDeduction.setSumDeductionAmt(deductAmt);
                    profitDeduction.setAddDeductionAmt(deductAmt);
                    profitDeduction.setMustDeductionAmt(deductAmt);
                    profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                    profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                    profitDeduction.setSourceId("01");//01-本月新增  02-上月未扣足移到本月
                    profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);
                    profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                    profitDeduction.setDeductionStatus("0");
                    profitDeduction.setCreateDateTime(new Date());
                    profitDeductionService.insert(profitDeduction);

                    //此时previewRewardDetail为预发周期结束月的pos奖励明细
                    PosRewardDetail updateDetail = new PosRewardDetail();
                    updateDetail.setId(previewRewardDetail.getId());
                    updateDetail.setPosCheckDeductAmt(deductAmt.abs().toString());
                    posRewardSDetailService.updatePosRewardDetail(updateDetail);
                }
            });
            LOG.info("计算申请特殊奖励的代理商，考核奖励扣款，结束");


            /*LOG.info("POS抱团奖励补款，开始");
            Map<String, Object> map = new HashMap<>();
            map.put("profitMonth", LocalDate.now().plusMonths(-1).toString().substring(0, 7));
            map.put("applyStatus", "1");
            List<PPosHuddleReward> huddleRewards = iPosRewardService.selectPosHuddleRewardByEndMonth(map);//本月需要进行抱团考核的申请
            for (PPosHuddleReward posHuddleReward : huddleRewards) {

                //查询此抱团中所有代理商
                List<PosHuddleRewardDetail> huddleRewardDetails = iPosRewardService.selectByHuddleCode(posHuddleReward.getHuddleCode());

                //遍历所选的所有考核月份，有一个月通过考核则进行考核补款
                String[] checkMonth = posHuddleReward.getTotalEndMonth().split(",");
                boolean db = false;

                //此抱团月总交易量
                for (String m : checkMonth) {
                    BigDecimal totalTranAmt = BigDecimal.ZERO;
                    for (PosHuddleRewardDetail detail : huddleRewardDetails) {
                        PosRewardDetail posRewardDetail = new PosRewardDetail();
                        posRewardDetail.setPosAgentId(detail.getPosAgentId());
                        posRewardDetail.setProfitPosDate(m);
                        PosRewardDetail checkRewardDetail = posRewardSDetailService.getPosRewardDetail(posRewardDetail);
                        totalTranAmt = totalTranAmt.add(new BigDecimal(checkRewardDetail.getPosCurrentCount()));
                    }
                    if (totalTranAmt.compareTo(new BigDecimal(posHuddleReward.getGrowAmt()).multiply(new BigDecimal("10000"))) >= 0) {
                        db = true;
                        LOG.info("抱团：{}，{}月考核交易量达到活动指标，进行pos奖励补发", posHuddleReward.getHuddleCode(), m);
                        break;
                    }
                }


                if (db) {
                    huddleRewardDetails.forEach(detail -> {
                        List<String> previewDateList = getMonthBetween(posHuddleReward.getTotalConsMonth(), posHuddleReward.getCreditConsMonth());
                        BigDecimal deductAmt = BigDecimal.ZERO;
                        PosRewardDetail previewRewardDetail = null;
                        PosRewardDetail tempDetail = new PosRewardDetail();
                        tempDetail.setPosAgentId(detail.getPosAgentId());
                        for (String previewDate : previewDateList) {
                            tempDetail.setProfitPosDate(previewDate.replaceAll("-", ""));
                            previewRewardDetail = posRewardSDetailService.getPosRewardDetail(tempDetail);
                            if (previewRewardDetail != null) {
                                BigDecimal deductRewardAmt = this.obtainGlobalTemp(previewRewardDetail, previewDate, posRewardTemplates);
                                deductAmt = deductAmt.add(deductRewardAmt);
                                LOG.info("考核代理商唯一码：{}，特殊奖励预发周期月份:{}，抱团考核奖励补款金额：{}", previewRewardDetail.getPosAgentId(), previewDate, deductRewardAmt);
                            }
                        }
                        LOG.info("考核代理商唯一码：{}，抱团考核总补款金额：{}", tempDetail.getPosAgentId(), deductAmt);
                        if (deductAmt.compareTo(BigDecimal.ZERO) != 0) {
                            tempDetail.setPosAgentId(detail.getPosAgentId());
                            tempDetail.setProfitPosDate(currentDate);
                            previewRewardDetail = posRewardSDetailService.getPosRewardDetail(tempDetail);
                            previewRewardDetail.setPosReawrdProfit(new BigDecimal(previewRewardDetail.getPosReawrdProfit()).add(deductAmt).toString());
                            posRewardSDetailService.updatePosRewardDetail(previewRewardDetail);
                        }
                    });
                }

            }
            LOG.info("POS抱团奖励补款，结束");*/


            LOG.info("更新基础分润中，POS奖励、考核扣款金额，开始");
            PosRewardDetail queryDetail = new PosRewardDetail();
            queryDetail.setProfitPosDate(currentDate);
            List<PosRewardDetail> queryList = posRewardSDetailService.getPosRewardDetailList(queryDetail, null, null);
            List<PosRewardDetail> updateList = queryList.parallelStream().filter(posRewardDetail -> new BigDecimal(posRewardDetail.getPosCheckDeductAmt()).compareTo(BigDecimal.ZERO) != 0 ||
                    new BigDecimal(posRewardDetail.getPosReawrdProfit()).compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
            updateList.parallelStream().forEach(posRewardDetail -> {
                LOG.info("代理商唯一码：{}，更新基础分润POS奖励信息", posRewardDetail.getPosAgentId());
                String superAgentId = posRewardSDetailService.getSuperAgentId(posRewardDetail.getPosAgentId());
                List<ProfitDetailMonth> profitMonth = profitMonthService.getAgentProfit(posRewardDetail.getPosAgentId(),
                        posRewardDetail.getProfitPosDate(), superAgentId);
                if (profitMonth != null && !profitMonth.isEmpty()) {
                    ProfitDetailMonth detatil = new ProfitDetailMonth();
                    detatil.setId(profitMonth.get(0).getId());
                    detatil.setPosRewardDeductionAmt(new BigDecimal(posRewardDetail.getPosCheckDeductAmt()));
                    detatil.setPosRewardAmt(new BigDecimal(posRewardDetail.getPosReawrdProfit()));
                    profitDetailMonthServiceImpl.updateByPrimaryKeySelective(detatil);
                }
            });
            LOG.info("更新基础分润中，POS奖励、考核扣款金额，结束");
            long khend = System.currentTimeMillis();
            LOG.info("考核处理时间" + (khend - khstart));

            //触发基础分润汇总计算
            profitAmtSumJob.deal();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 查询特殊奖励模板，按照预发周期，计算特殊奖励
     *
     * @param posrewardDetail
     * @param currentDate
     * @param posRewardList
     */
    private void computSpecialStandard(PosRewardDetail posrewardDetail, String currentDate, List<PosReward> posRewardList) {
        posRewardList.forEach(posReward -> {
            List<String> list = getMonthBetween(posReward.getTotalConsMonth(), posReward.getCreditConsMonth());
            for (String date : list) {
                if (Objects.equals(date.replaceAll("-", ""), currentDate)) {
                    BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
                    LOG.info("代理商唯一码：{}，预发周期内，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
                    posrewardDetail.setPosAmt(posAmt.toString());
                    posrewardDetail.setPosStandard(posRewardList.get(0).getRewardScale().toString());
                    posrewardDetail.setPosRemark("TS_Template");
                }
            }
        });
    }

    private void computSpecialHuddle(PosRewardDetail posrewardDetail, String currentDate, List<PPosHuddleReward> posHuddleRewardList) {
        posHuddleRewardList.forEach(posReward -> {
            List<String> list = getMonthBetween(posReward.getTotalConsMonth(), posReward.getCreditConsMonth());
            for (String date : list) {
                if (Objects.equals(date.replaceAll("-", ""), currentDate)) {
                    BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
                    LOG.info("代理商唯一码：{}，预发周期内，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
                    posrewardDetail.setPosAmt(posAmt.toString());
                    posrewardDetail.setPosStandard(posHuddleRewardList.get(0).getRewardScale().toString());
                    posrewardDetail.setPosRemark("TS_Template");
                }
            }
        });
    }

    /**
     * 查询下级商户AG码
     *
     * @return
     */
    private List<String> queryChildLevelByBusNum(String agentId) {
        List<String> list = posRewardSDetailService.queryChildLevelByAgentId(agentId);
        if (list != null && !list.isEmpty()) {
            LOG.info("代理商唯一码：{}，下级代理商唯一码列表：{}", agentId, JSONObject.toJSON(list).toString());
            return list;
        } else {
            LOG.info("代理商唯一码：{}，下级代理商唯一码列表：{}", agentId, "[]");
            return new ArrayList<String>();
        }
    }

    /**
     * 获取通用奖励模板
     *
     * @param currentDate
     */
    private PosRewardTemplate obtainCurrencyRewardTemp(String currentDate, List<PosRewardTemplate> posRewardTemplates) {
        PosRewardTemplate template = null;
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate : posRewardTemplates) {
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for (String activitDate : list) {
                if (Objects.equals(activitDate.replaceAll("-", ""), currentDate)) {
                    template = posRewardTemplate;
                    end = true;
                    break;
                }
            }
            if (end) {
                break;
            }
        }
        return template;
    }

    /**
     * 缓存中获取通用奖励模板
     *
     * @return
     */
    private List<PosRewardTemplate> getPosRewardTemplateList() {
        String tmep = redisTemplate.opsForValue().get("POS_REWARD_TEMP");
        List list = JSONObject.parseObject(tmep, List.class);
        if (list != null && !list.isEmpty()) {
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
            return posRewardTemplateService.getPosRewardTemplateList();
        }
    }

    /**
     * 到达活动奖励月份，获取活动对比月交易量
     *
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
        List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(childAgentList, date, null);
        BigDecimal tranContrastMonthAmt = list != null && !list.isEmpty() ? list.stream().filter(detail1 -> Objects.equals(detail1.getProfitDate(), tranContrastMonth)).map(TransProfitDetail::getPosRewardAmt).reduce(BigDecimal::add).orElseGet(() -> new BigDecimal("0")) : BigDecimal.ZERO;
        BigDecimal childInTransAmt = list != null && !list.isEmpty() ? list.stream().filter(detail1 -> Objects.equals(detail1.getProfitDate(), currentDate)).map(TransProfitDetail::getPosRewardAmt).reduce(BigDecimal::add).orElseGet(() -> new BigDecimal("0")) : BigDecimal.ZERO;
        BigDecimal childCreditTransAmt = list != null && !list.isEmpty() ? list.stream().filter(detail1 -> Objects.equals(detail1.getProfitDate(), currentDate)).map(TransProfitDetail::getPosCreditAmt).reduce(BigDecimal::add).orElseGet(() -> new BigDecimal("0")) : BigDecimal.ZERO;
        BigDecimal creditTranContrastMonthAmt = list != null && !list.isEmpty() ? list.stream().filter(detail1 -> Objects.equals(detail1.getProfitDate(), creditTranContrastMonth)).map(TransProfitDetail::getPosCreditAmt).reduce(BigDecimal::add).orElseGet(() -> new BigDecimal("0")) : BigDecimal.ZERO;

        posrewardDetail.setPosCompareCount(tranContrastMonthAmt.toString());
        posrewardDetail.setPosCompareLoanCount(creditTranContrastMonthAmt.toString());
        posrewardDetail.setPosCurrentCount(new BigDecimal(posrewardDetail.getPosCurrentCount()).add(childInTransAmt).toString());
        posrewardDetail.setPosCurrentLoanCount(new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).add(childCreditTransAmt).toString());
    }

    /**
     * 循环奖励模板
     *
     * @param posrewardDetail
     * @param currentDate
     */
    private void computRewardStandard(PosRewardDetail posrewardDetail, String currentDate, List<PosRewardTemplate> posRewardTemplates) {
        boolean end = false;
        for (PosRewardTemplate posRewardTemplate : posRewardTemplates) {
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for (String activitDate : list) {
                if (Objects.equals(activitDate.replaceAll("-", ""), currentDate)) {
                    BigDecimal proportion = this.obtainRewardStandard(posrewardDetail, posRewardTemplate);
                    if (proportion.compareTo(BigDecimal.ZERO) > 0) {
                        end = true;
                        break;
                    }
                }
            }
            if (end) {
                break;
            }
        }
    }

    /**
     * 获取奖励标准
     *
     * @param posrewardDetail
     * @param posRewardTemplate
     * @return
     */
    private BigDecimal obtainRewardStandard(PosRewardDetail posrewardDetail, PosRewardTemplate posRewardTemplate) {
        BigDecimal tranSumAmt = new BigDecimal(posrewardDetail.getPosCurrentCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareCount()));
        if (tranSumAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && tranSumAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0) {
            BigDecimal posAmt = new BigDecimal(posrewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(posrewardDetail.getPosCompareLoanCount()));
            LOG.info("代理商唯一码：{}，POS奖励交易金额 = 本月贷记交易量- 对比月贷记交易量：{}", posrewardDetail.getPosAgentId(), posAmt);
            posrewardDetail.setPosAmt(posAmt.toString());
            if (posAmt.compareTo(BigDecimal.ZERO) > 0) {
                LOG.info("代理商唯一码：{}，交易总额达标范围：{}万~{}万，奖励比例：{}", posrewardDetail.getPosAgentId(),
                        posRewardTemplate.getTranTotalStart(), posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
                posrewardDetail.setPosStandard(posRewardTemplate.getProportion().toString());
                posrewardDetail.setPosRemark("TY_Template");
                return posRewardTemplate.getProportion();
            }
        } else {
            LOG.info("代理商唯一码：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万", posrewardDetail.getPosAgentId(),
                    tranSumAmt, posRewardTemplate.getTranTotalStart(), posRewardTemplate.getTranTotalEnd());
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
        for (PosRewardTemplate posRewardTemplate : posRewardTemplates) {
            String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
            List<String> list = getMonthBetween(spl[0], spl[1]);
            for (String activitDate : list) {
                if (Objects.equals(activitDate, previewDate)) {
                    BigDecimal deductAmt = this.againComputeReward(posRewardTemplate, previewRewardDetail);
                    deductRewardAmt = deductRewardAmt.add(deductAmt);
                    if (deductAmt.compareTo(BigDecimal.ZERO) > 0) {
                        end = true;
                        break;
                    }
                }
            }
            if (end) {
                break;
            }
        }
        return deductRewardAmt;
    }

    /**
     * 按照通用模板重新计算奖励，然后用特殊奖励-通用奖励=考核扣款
     *
     * @param posRewardTemplate
     */
    private BigDecimal againComputeReward(PosRewardTemplate posRewardTemplate, PosRewardDetail previewRewardDetail) {
        BigDecimal tranAmt = new BigDecimal(previewRewardDetail.getPosCurrentCount()).subtract(new BigDecimal(previewRewardDetail.getPosCompareCount()));
        if (tranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && tranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0) {
            LOG.info("考核代理商ID：{}，交易总额达标范围：{}万~{}万，奖励比例：{}",
                    previewRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(), posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            BigDecimal creditSum = new BigDecimal(previewRewardDetail.getPosCurrentLoanCount()).subtract(new BigDecimal(previewRewardDetail.getPosCompareLoanCount()));
            if (creditSum.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal posRewardAmt = creditSum.multiply(posRewardTemplate.getProportion()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal posReawrdProfit = previewRewardDetail.getPosReawrdProfit() == null ? BigDecimal.ZERO : new BigDecimal(previewRewardDetail.getPosReawrdProfit());
                //预发月分润的特殊奖励-预发月通用模板奖励-预发月下级代理商奖励=考核应扣金额
                return posReawrdProfit.subtract(posRewardAmt).subtract(new BigDecimal(previewRewardDetail.getPosDownReward()));
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            LOG.info("代理商ID：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万",
                    previewRewardDetail.getPosAgentId(), posRewardTemplate.getTranTotalStart(), posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
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
        } catch (Exception e) {
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
