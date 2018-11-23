package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yangmx
 * @desc 机具分润扣款
 */
@Service("profitToolsDeductServiceImpl")
public class ProfitToolsDeductServiceImpl implements DeductService {

    private static Logger LOG = LoggerFactory.getLogger(ProfitToolsDeductServiceImpl.class);
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    private static final String RHB = "5000";
    private static final String POS = "100003";
    private static final String ZPOS = "100002";

    /**
     * 代理商分润不足，扣减了担保代理商的分润
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        LOG.info("机具分润扣款请求参数：{}", map);
        String agentPid = map.get("agentPid").toString();
        String computType = map.get("computType").toString();
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentPid);
        profitDeduction.setDeductionDate(map.get("deductDate").toString());
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if(list != null && !list.isEmpty()){
            if(Objects.equals("1", map.get("rotation"))){
                LOG.info("第一轮机具扣款，正常扣款，代理商编号：{}", agentPid);
                return this.fristRound(map, agentPid, computType, list);
            } else if(Objects.equals("2", map.get("rotation"))){
                LOG.info("第二轮机具扣款（合并商户基础分润扣款）：代理商编号：{}", agentPid);
                return this.secondRound(agentPid, map, list, computType);
            } else {
                map.put("agentProfitAmt", BigDecimal.ZERO);
                LOG.info("第三轮机具扣款（担保代理商扣款）：代理商编号：{}", agentPid);
                this.fristRound(map, agentPid, computType, list);
                return map;
            }
        }
        return map;
    }

    /**
     * 1、代理商基础分润充足，优先扣除自己的分润，能扣多少算多少
     * 2、分润不足，不存在担保代理商，则记录未扣足部分机具款
     * 3、有担保代理商，不足部分从担保代理商list的基础分润一条条的扣
     */
    private Map<String, Object> fristRound(Map<String, Object> map, String agentPid, String computType, List<ProfitDeduction> list) {
        BigDecimal profitSumAmt = new BigDecimal(map.get("agentProfitAmt").toString());
        Optional<BigDecimal> posOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(POS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add);
        if(posOptional != null && posOptional.isPresent()) {
            map.put("PosDgMustDeductionAmt", posOptional.get());
        }
        Optional<BigDecimal> rhbOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(RHB, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add);
        if(rhbOptional != null && rhbOptional.isPresent()) {
            map.put("RhbDgMustDeductionAmt", rhbOptional.get());
        }
        Optional<BigDecimal> ZposOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(ZPOS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add);
        if(ZposOptional != null && ZposOptional.isPresent()) {
            map.put("ZposDgMustDeductionAmt", ZposOptional.get());
        }
        for (ProfitDeduction deduction : list) {
            if(deduction.getMustDeductionAmt().compareTo(deduction.getActualDeductionAmt()) == 0){
                continue;
            }
            BigDecimal mustAmt = null;
            if(deduction.getActualDeductionAmt().compareTo(BigDecimal.ZERO) > 0){
                mustAmt = deduction.getMustDeductionAmt().subtract(deduction.getActualDeductionAmt());
            } else {
                mustAmt = deduction.getMustDeductionAmt();
            }
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，应扣金额：{}，分润汇总剩余：{}", deduction.getSourceId(), agentPid, mustAmt, profitSumAmt);

            if(profitSumAmt.compareTo(BigDecimal.ZERO) > 0){
                this.basicsProfitDeductAmt(deduction, mustAmt, profitSumAmt, map);
                profitSumAmt = (BigDecimal)map.get("profitSumAmt");
                LOG.info("机具扣款流水号：{}，此条机具扣款完毕。应扣：{}，实扣：{}，未扣足：{}，分润汇总剩余：{}", deduction.getSourceId(),
                        deduction.getMustDeductionAmt(), deduction.getActualDeductionAmt(), deduction.getNotDeductionAmt(), profitSumAmt);
            } else {
                this.deduceParentAgent(deduction, mustAmt, computType);
                LOG.info("机具扣款流水号：{}，此条机具扣款完毕。应扣：{}，实扣：{}，未扣足：{}", deduction.getSourceId(),
                        deduction.getMustDeductionAmt(), deduction.getActualDeductionAmt(), deduction.getNotDeductionAmt());
            }
        }
        Optional<BigDecimal> posRealOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(POS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add);
        if(posRealOptional != null && posRealOptional.isPresent()) {
            map.put("PosDgRealDeductionAmt", posRealOptional.get());
        }
        Optional<BigDecimal> rhbRealOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(RHB, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add);
        if(rhbRealOptional != null && rhbRealOptional.isPresent()) {
            map.put("RhbDgRealDeductionAmt", rhbRealOptional.get());
        }
        Optional<BigDecimal> ZposRealOptional = list.parallelStream().filter(profitDeduction1 -> profitDeduction1 != null && Objects.equals(ZPOS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add);
        if(ZposRealOptional != null && ZposRealOptional.isPresent()) {
            map.put("ZposTdRealDeductionAmt", ZposRealOptional.get());
        }
        map.put("agentProfitAmt", profitSumAmt);
        LOG.info("机具分润扣款响应参数：{}", map);
        return map;
    }

    private void basicsProfitDeductAmt(ProfitDeduction deduction, BigDecimal mustAmt, BigDecimal basicsProfitAmt, Map<String, Object> map) {
        if (basicsProfitAmt.compareTo(mustAmt) >= 0) {
            deduction.setActualDeductionAmt(deduction.getActualDeductionAmt().add(mustAmt));
            if(deduction.getNotDeductionAmt().compareTo(BigDecimal.ZERO) > 0 ){
                deduction.setNotDeductionAmt(deduction.getNotDeductionAmt().subtract(mustAmt));
            }
            basicsProfitAmt = basicsProfitAmt.subtract(mustAmt);
            map.put("profitSumAmt", basicsProfitAmt);
            try {
                this.updateDeductionInfo(deduction, map.get("computType").toString());
            } catch (Exception e){e.printStackTrace();}
        } else {
            BigDecimal notDeductionAmt = mustAmt.subtract(basicsProfitAmt);
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润不足，先扣减剩余的分润：{}，不足的部分从担保代理商扣减：{}",
                    deduction.getSourceId(), deduction.getAgentId(), basicsProfitAmt, notDeductionAmt);
            map.put("profitSumAmt", BigDecimal.ZERO);
            deduction.setActualDeductionAmt(deduction.getActualDeductionAmt().add(basicsProfitAmt));
            if(deduction.getParentAgentPid() == null){
                LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润已扣完，且没有担保代理商。", deduction.getSourceId(), deduction.getAgentId());
                try {
                    deduction.setNotDeductionAmt(notDeductionAmt);
                    this.updateDeductionInfo(deduction, map.get("computType").toString());
                } catch (Exception e){e.printStackTrace();}
            } else {
                this.deduceParentAgent(deduction, notDeductionAmt, map.get("computType").toString());
            }
        }
    }

    private void updateDeductionInfo(ProfitDeduction profitDeductionList, String computType) throws Exception {
        try {
            if(Objects.equals(computType, "1")){
                if(profitDeductionList != null){
                    ProfitDeduction update = new ProfitDeduction();
                    update.setId(profitDeductionList.getId());
                    update.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    update.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt());
                    update.setNotDeductionAmt(profitDeductionList.getNotDeductionAmt());
                    profitDeductionService.updateProfitDeduction(update);
                    profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }

    /**
     * 先扣掉代理商还剩的分润，然后在从担保代理商扣
     */
    private void deduceParentAgent(ProfitDeduction profitDeduction, BigDecimal mustAmt, String computType){
        LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润不足，担保代理商需代扣金额：{}",
                profitDeduction.getSourceId(), profitDeduction.getAgentId(), mustAmt);
        List<ProfitDetailMonth> profitMonth = profitMonthService.getAgentProfit(profitDeduction.getParentAgentId(),
                profitDeduction.getDeductionDate().replaceAll("-", ""), null);
        if(profitMonth != null && !profitMonth.isEmpty()){
            for (ProfitDetailMonth detail : profitMonth){
                BigDecimal parentBasicsProfitAmt = detail.getBasicsProfitAmt() == null ? BigDecimal.ZERO : detail.getBasicsProfitAmt();
                if(parentBasicsProfitAmt.compareTo(BigDecimal.ZERO) <= 0 ){
                    continue;
                } else {
                    BigDecimal deductionAmt = this.deductParentAgentProfit(profitDeduction, detail, computType, mustAmt);
                    mustAmt = mustAmt.subtract(deductionAmt);
                    LOG.info("机具扣款流水号：{}，代理商唯一码：{}，该笔机具款目前，应扣金额还剩：{}元未扣足",
                            profitDeduction.getSourceId(), profitDeduction.getAgentId(), mustAmt);
                }
            }
            try {
                if(Objects.equals(computType, "1")){
                    ProfitDeduction update = new ProfitDeduction();
                    update.setId(profitDeduction.getId());
                    update.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    update.setActualDeductionAmt(profitDeduction.getActualDeductionAmt());
                    update.setNotDeductionAmt(mustAmt);
                    profitDeductionService.updateProfitDeduction(update);
                }
            } catch (Exception e){e.printStackTrace();}
        } else {
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，未查询到担保代理商月分润信息，扣除基础分润金额：{}，未扣足金额：{}",
                    profitDeduction.getSourceId(), profitDeduction.getAgentId(), profitDeduction.getActualDeductionAmt(), mustAmt);
            try {
                profitDeduction.setNotDeductionAmt(mustAmt);
                this.updateDeductionInfo(profitDeduction, computType);
            } catch (Exception e){e.printStackTrace();}
        }
    }

    /**
     * 担保代理商扣减基础分润，增加担保代理商的应扣与实扣
     * @param profitDeductionList
     * @param profitMonth
     */
    private BigDecimal deductParentAgentProfit(ProfitDeduction profitDeductionList, ProfitDetailMonth profitMonth, String computType, BigDecimal mustNotDeductionAmt) {
        BigDecimal parentBasicsProfitAmt = profitMonth.getBasicsProfitAmt();
        BigDecimal deductionAmt = BigDecimal.ZERO;
        if (parentBasicsProfitAmt.compareTo(mustNotDeductionAmt) >= 0) {
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，担保代理商：{}，担保代理商基础分润：{}，代扣金额：{}，担保代理剩余分润：{}", profitDeductionList.getSourceId(),
                    profitDeductionList.getAgentId(), profitDeductionList.getParentAgentId(), parentBasicsProfitAmt, mustNotDeductionAmt, parentBasicsProfitAmt.subtract(mustNotDeductionAmt));
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(mustNotDeductionAmt));
            this.updateProfitMonth(profitMonth, mustNotDeductionAmt, profitDeductionList.getDeductionDesc());
            deductionAmt = mustNotDeductionAmt;
            profitMonth.setBasicsProfitAmt(parentBasicsProfitAmt.subtract(mustNotDeductionAmt));
        } else if(mustNotDeductionAmt.compareTo(parentBasicsProfitAmt) >= 0){
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，担保代理商：{}，担保代理商基础分润：{}，代扣金额：{}，担保代理剩余分润：{}", profitDeductionList.getSourceId(),
                    profitDeductionList.getAgentId(), profitDeductionList.getParentAgentId(), parentBasicsProfitAmt, parentBasicsProfitAmt, BigDecimal.ZERO);
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(parentBasicsProfitAmt));
            this.updateProfitMonth(profitMonth, parentBasicsProfitAmt, profitDeductionList.getDeductionDesc());
            profitMonth.setBasicsProfitAmt(BigDecimal.ZERO);
            deductionAmt = parentBasicsProfitAmt;
        }
        try {
            this.insertDeductionInfo(profitDeductionList, profitMonth, computType, deductionAmt);
        } catch (Exception e){e.printStackTrace();}
        return deductionAmt;
    }

    /**
     * 核算担保代理的各款项扣款累计
     * @param profitDetailMonth
     * @param dudecutAmt
     * @param paltformNo
     */
    private ProfitDetailMonth updateProfitMonth(ProfitDetailMonth profitDetailMonth, BigDecimal dudecutAmt, String paltformNo){
        if(Objects.equals(POS, paltformNo)){
            BigDecimal posMuust = profitDetailMonth.getPosDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgMustDeductionAmt();
            BigDecimal posReal = profitDetailMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgRealDeductionAmt();
            profitDetailMonth.setPosDgMustDeductionAmt(posMuust.add(dudecutAmt));
            profitDetailMonth.setPosDgRealDeductionAmt(posReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，POS机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), posMuust, posReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，POS机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getPosDgMustDeductionAmt(), profitDetailMonth.getPosDgRealDeductionAmt());
        } else if(Objects.equals(ZPOS, paltformNo)){
            BigDecimal zposMuust = profitDetailMonth.getZposDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposDgMustDeductionAmt();
            BigDecimal zposReal = profitDetailMonth.getZposTdRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposTdRealDeductionAmt();
            profitDetailMonth.setZposDgMustDeductionAmt(zposMuust.add(dudecutAmt));
            profitDetailMonth.setZposTdRealDeductionAmt(zposReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，ZPOS机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), zposMuust, zposReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，ZPOS机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getZposDgMustDeductionAmt(), profitDetailMonth.getZposTdRealDeductionAmt());
        }else if(Objects.equals(RHB, paltformNo)){
            BigDecimal rhbMuust = profitDetailMonth.getRhbDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgMustDeductionAmt();
            BigDecimal rhbReal = profitDetailMonth.getRhbDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgRealDeductionAmt();
            profitDetailMonth.setRhbDgMustDeductionAmt(rhbMuust.add(dudecutAmt));
            profitDetailMonth.setRhbDgRealDeductionAmt(rhbReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，瑞和宝机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), rhbMuust, rhbReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，瑞和宝机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getRhbDgMustDeductionAmt(), profitDetailMonth.getRhbDgRealDeductionAmt());
        }
        return profitDetailMonth;
    }

    private void insertDeductionInfo(ProfitDeduction profitDeductionList, ProfitDetailMonth profitDetailMonth,
                                     String computType, BigDecimal deductionAmt) throws Exception {
        try {
            if(Objects.equals(computType, "1")){
                if(profitDetailMonth != null){
                    ProfitDeduction danbaoDeduct = new ProfitDeduction();
                    danbaoDeduct.setAgentId(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setAgentPid(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                    danbaoDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                    danbaoDeduct.setDeductionType(DeductionType.MACHINE.getType());
                    danbaoDeduct.setActualDeductionAmt(deductionAmt);
                    danbaoDeduct.setMustDeductionAmt(deductionAmt);
                    danbaoDeduct.setRemark("担保代理商代扣,扣款明细:"+profitDeductionList.getSourceId());
                    danbaoDeduct.setUserId(profitDeductionList.getAgentId());
                    danbaoDeduct.setId(profitDeductionList.getId());
                    profitDeducttionDetailService.insertDeducttionDetail(danbaoDeduct);
                }
            }

            if(profitDetailMonth != null){
                ProfitDetailMonth detatil = new ProfitDetailMonth();
                detatil.setId(profitDetailMonth.getId());
                detatil.setPosDgMustDeductionAmt(profitDetailMonth.getPosDgMustDeductionAmt());
                detatil.setPosDgRealDeductionAmt(profitDetailMonth.getPosDgRealDeductionAmt());
                detatil.setZposDgMustDeductionAmt(profitDetailMonth.getZposDgMustDeductionAmt());
                detatil.setZposTdRealDeductionAmt(profitDetailMonth.getZposTdRealDeductionAmt());
                detatil.setRhbDgMustDeductionAmt(profitDetailMonth.getRhbDgMustDeductionAmt());
                detatil.setRhbDgRealDeductionAmt(profitDetailMonth.getRhbDgRealDeductionAmt());
                detatil.setBasicsProfitAmt(profitDetailMonth.getBasicsProfitAmt());
                profitDetailMonthServiceImpl.updateByPrimaryKeySelective(detatil);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }

    /**
     * 扣除合并代理商的分润，合并代理商的分润被扣完，则将该合并代理从list列表里删除
     * 代理商代扣的机具款，记录在合并代理商对应的机具实扣列中
     * @param agentPid
     * @param map
     * @return
     */
    private Map<String, Object> secondRound(String agentPid, Map<String, Object> map, List<ProfitDeduction> list, String computType) {
        List<Map<String, Object>> mergeAgentList= (List<Map<String, Object>>)map.get("hbList");
        for (ProfitDeduction profitDeductionList : list){
            if(profitDeductionList.getNotDeductionAmt().compareTo(BigDecimal.ZERO) == 0){
                continue;
            }
            Iterator<Map<String, Object>> iter = mergeAgentList.iterator();
            while (iter.hasNext()) {
                Map<String, Object> mergeMap = iter.next();
                ProfitDetailMonth profitMonth = profitMonthService.getProfitDetailMonth((String)mergeMap.get("id"));
                if(profitMonth == null){
                    continue;
                }
                BigDecimal basicAmt = new BigDecimal(mergeMap.get("basicAmt").toString());
                BigDecimal notDeductionAmt = profitDeductionList.getNotDeductionAmt();
                LOG.info("机具扣款代理商唯一码：{}，应扣：{}，实扣：{}，未扣足金额：{}",
                        agentPid, profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt() ,notDeductionAmt);
                LOG.info("合并代理商唯一码：{}，合并代理商基础分润：{}",profitMonth.getAgentId(), basicAmt);
                BigDecimal deductAmt = null;
                ProfitDeduction insert = new ProfitDeduction();
                if(basicAmt.compareTo(notDeductionAmt) >= 0){
                    basicAmt = basicAmt.subtract(notDeductionAmt);
                    deductAmt = notDeductionAmt;
                    mergeMap.put("basicAmt", basicAmt);
                    insert.setMustDeductionAmt(notDeductionAmt);
                    insert.setActualDeductionAmt(notDeductionAmt);
                    profitDeductionList.setNotDeductionAmt(BigDecimal.ZERO);
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(notDeductionAmt));
                    LOG.info("合并代理商唯一码：{}，已扣金额：{}，合并代理商剩余基础分润：{}",
                            profitMonth.getAgentId(), notDeductionAmt, basicAmt);
                } else {
                    LOG.info("基础分润不足，合并代理商唯一码：{}，已扣金额：{}，合并代理商剩余基础分润：{}",
                            profitMonth.getAgentId(), basicAmt, BigDecimal.ZERO);
                    BigDecimal surNotDeductionAmt = notDeductionAmt.subtract(basicAmt);
                    insert.setMustDeductionAmt(notDeductionAmt);
                    insert.setActualDeductionAmt(basicAmt);
                    insert.setNotDeductionAmt(surNotDeductionAmt);
                    profitDeductionList.setNotDeductionAmt(surNotDeductionAmt);
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(basicAmt));
                    deductAmt = basicAmt;
                    mergeMap.put("basicAmt", BigDecimal.ZERO);
                }

                try {
                    if(Objects.equals(computType, "1")){
                        insert.setAgentPid(profitMonth.getAgentId());
                        insert.setAgentId(profitMonth.getAgentId());
                        insert.setAgentName(profitMonth.getAgentName());
                        insert.setDeductionType(DeductionType.MACHINE.getType());
                        insert.setDeductionDesc(profitDeductionList.getDeductionDesc());
                        insert.setDeductionDate(profitDeductionList.getDeductionDate());
                        insert.setId(profitDeductionList.getId());
                        insert.setRemark("合并代理商代扣，扣款明细："+profitDeductionList.getSourceId());
                        insert.setUserId(profitDeductionList.getAgentId());
                        profitDeducttionDetailService.insertDeducttionDetail(insert);
                    }

                    ProfitDetailMonth update = new ProfitDetailMonth();
                    update.setId((String)mergeMap.get("id"));
                    if(Objects.equals(POS, profitDeductionList.getDeductionDesc())){
                        BigDecimal posDgRealDeductionAmt = profitMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getZposTdRealDeductionAmt();
                        update.setPosDgRealDeductionAmt(posDgRealDeductionAmt.add(deductAmt));
                    } else if(Objects.equals(ZPOS, profitDeductionList.getDeductionDesc())){
                        BigDecimal zposTdRealDeductionAmt = profitMonth.getZposTdRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getZposTdRealDeductionAmt();
                        update.setZposTdRealDeductionAmt(zposTdRealDeductionAmt.add(deductAmt));
                    }else if(Objects.equals(RHB, profitDeductionList.getDeductionDesc())){
                        BigDecimal rhbDgRealDeductionAmt = profitMonth.getRhbDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getZposTdRealDeductionAmt();
                        update.setRhbDgRealDeductionAmt(rhbDgRealDeductionAmt.add(deductAmt));
                    }
                    profitMonthService.updateByPrimaryKeySelective(update);

                    if(Objects.equals(computType, "1")){
                        ProfitDeduction updateDeduct = new ProfitDeduction();
                        updateDeduct.setId(profitDeductionList.getId());
                        updateDeduct.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                        updateDeduct.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt());
                        updateDeduct.setNotDeductionAmt(profitDeductionList.getNotDeductionAmt());
                        profitDeductionService.updateProfitDeduction(updateDeduct);
                    }
                } catch (Exception e) {e.printStackTrace();}

                if(profitDeductionList.getMustDeductionAmt().compareTo(profitDeductionList.getActualDeductionAmt()) == 0){
                    LOG.info("代理商唯一码：{}，订单号：{}，该笔机具款实扣金额已经扣完，循环下一条机具扣款。应扣：{}，实扣：{}，未扣足：{}",
                            profitDeductionList.getAgentId(), profitDeductionList.getSourceId(),
                            profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt(), profitDeductionList.getNotDeductionAmt());
                    break;
                } else {
                    LOG.info("此合并代理商分润已扣完。合并代理商分润主键：{}，继续扣下个合并代理商扣款。应扣：{}，实扣：{}，未扣足：{}",
                            mergeMap.get("id"),profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt(), profitDeductionList.getNotDeductionAmt());
                }
            }
        }
        map.put("hbList", mergeAgentList);
        LOG.info("机具分润扣款响应参数：{}", map);
        return map;
    }


    /**
     * 终审后，查询机具未扣款订单，通知订单系统，未扣款订单与金额
     */
    @Override
    public void otherOperate(){
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setDeductionDate(deductDate);
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if(list != null && !list.isEmpty()){
            List<Map<String, Object>> noticeList = new ArrayList<Map<String, Object>>(list.size());
            for (ProfitDeduction deduction : list){
                Map<String, Object> map = new HashMap<String, Object>(5);
                ProfitDeducttionDetail detail = profitDeducttionDetailService.getProfitDeducttionDetail(deduction);
                if(detail == null){
                    map.put("deductTime", "");//扣款时间
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    String updateTime = sdf.format(detail.getCreateDateTime());
                    map.put("deductTime", updateTime);//最后扣款时间
                }
                BigDecimal mustDeductionAmtSum = deduction.getMustDeductionAmt() == null ? BigDecimal.ZERO : deduction.getMustDeductionAmt();
                map.put("mustDeductionAmtSum", mustDeductionAmtSum.toString());//应扣
                BigDecimal actualDeductionAmtSum = deduction.getActualDeductionAmt() == null ? BigDecimal.ZERO : deduction.getActualDeductionAmt();
                map.put("actualDeductionAmtSum", actualDeductionAmtSum.toString());//实扣
                map.put("notDeductionAmt", mustDeductionAmtSum.subtract(actualDeductionAmtSum).toString());//未扣足
                map.put("detailId", deduction.getSourceId());//订单号
                map.put("srcId", deduction.getId());//分润系统扣款ID
                noticeList.add(map);
            }
            LOG.info("系统已经终审，通知订单系统，机具款变更清算状态，通知数据：{}",JSONObject.toJSON(noticeList));
            iPaymentDetailService.uploadStatus(noticeList, PaySign.JQ.code);
        }
    }

    @Override
    public void clearDetail(){

    }
}