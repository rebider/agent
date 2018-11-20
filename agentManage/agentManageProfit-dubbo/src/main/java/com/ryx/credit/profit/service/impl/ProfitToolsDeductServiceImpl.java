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

    /**
     * 代理商分润不足，扣减了担保代理商的分润
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        LOG.info("机具分润扣款请求参数：{}", map);
        if (map == null || map.get("agentPid") == null || map.get("paltformNo") == null
                || map.get("agentProfitAmt") == null) {
            throw new Exception("机具分润扣款参数为空");
        }
        String agentPid = map.get("agentPid").toString();
        String computType = map.get("computType").toString();
        if(map.get("hbList") == null){
            LOG.info("第一轮机具扣款（代理商本身扣，补足部分担保代理商扣），代理商编号：{}", agentPid);
            return this.fristRound(map, agentPid, computType);
        } else {
            LOG.info("第二轮机具扣款（未扣足部分从和并商户基础分润扣）：代理商编号：{}", agentPid);
            return this.secondRound(agentPid, computType, map);
        }
    }

    /**
     * 1、代理商基础分润充足，优先扣除自己的分润，能扣多少算多少
     * 2、分润不足，不存在担保代理商，则记录未扣足部分机具款
     * 3、有担保代理商，不足部分从担保代理商list的基础分润一条条的扣
     */
    private Map<String, Object> fristRound(Map<String, Object> map, String agentPid, String computType) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentPid);
        profitDeduction.setDeductionDesc(map.get("paltformNo").toString());
        profitDeduction.setDeductionDate(map.get("deductDate").toString());
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        //应扣总额
        BigDecimal mustDeductionAmtSum = BigDecimal.ZERO;
        //实扣总额
        BigDecimal actualDeductionAmtSum = BigDecimal.ZERO;
        //基础分润
        BigDecimal basicsProfitAmt = new BigDecimal(map.get("agentProfitAmt").toString());
        if (list != null && !list.isEmpty()) {
            for (ProfitDeduction profitDeductionList : list) {
                mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                if(profitDeductionList.getMustDeductionAmt().compareTo(profitDeductionList.getActualDeductionAmt()) == 0){
                    continue;
                }
                BigDecimal mustAmt = null;
                if(profitDeductionList.getActualDeductionAmt().compareTo(BigDecimal.ZERO) > 0){
                    mustAmt = profitDeductionList.getMustDeductionAmt().subtract(profitDeductionList.getActualDeductionAmt());
                } else {
                    mustAmt = profitDeductionList.getMustDeductionAmt();
                }
                LOG.info("机具扣款流水号：{}，代理商编号：{}，应扣金额：{}", profitDeductionList.getSourceId(), agentPid, mustAmt);

                if (basicsProfitAmt.compareTo(mustAmt) >= 0) {
                    LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商基础分润充足，应扣款：{}", profitDeductionList.getSourceId(),
                            agentPid, mustAmt);
                    basicsProfitAmt = basicsProfitAmt.subtract(mustAmt);
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(mustAmt));
                    if(profitDeductionList.getNotDeductionAmt().compareTo(BigDecimal.ZERO) > 0 ){
                        profitDeductionList.setNotDeductionAmt(profitDeductionList.getNotDeductionAmt().subtract(mustAmt));
                    }
                    actualDeductionAmtSum = actualDeductionAmtSum.add(mustAmt);
                    try {
                        this.updateDeductionInfo(profitDeductionList, computType);
                    } catch (Exception e){}
                } else {
                    Map<String, Object> newMap = null;
                    if(profitDeductionList.getParentAgentPid() == null){
                        LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商基础分润不足，没有担保代理商。", profitDeductionList.getSourceId(), profitDeductionList.getAgentId());
                        newMap = this.notDeductionAmt(profitDeductionList, basicsProfitAmt, computType, mustAmt);
                    } else {
                        LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商基础分润不足，从担保代理商分润进行扣款", profitDeductionList.getSourceId(), agentPid);
                        newMap = this.deduceParentAgent(profitDeductionList, basicsProfitAmt, computType, mustAmt);
                    }
                    actualDeductionAmtSum = actualDeductionAmtSum.add(new BigDecimal(newMap.get("actualDeductionAmt").toString()));
                    basicsProfitAmt = new BigDecimal(newMap.get("basicsProfitAmt").toString());
                }
                LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商剩余的基础分润：{}", profitDeductionList.getSourceId(), agentPid, basicsProfitAmt);
            }
        }
        map.put("mustDeductionAmtSum", mustDeductionAmtSum);
        map.put("actualDeductionAmtSum", actualDeductionAmtSum);
        LOG.info("机具扣款结束。代理商本月应扣总额：{}，代理商本月实扣总额：{}", mustDeductionAmtSum, actualDeductionAmtSum);
        LOG.info("机具扣款结束。响应参数：{}", JSONObject.toJSON(map));
        return map;
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
     * 没有担保代理商，分润剩多少口多少。
     */
    private Map<String, Object> notDeductionAmt(ProfitDeduction profitDeductionList, BigDecimal basicsProfitAmt, String computType, BigDecimal mustAmt){
        Map<String, Object> newMap = new HashMap<String, Object>();
        BigDecimal notDeductionAmt = mustAmt.subtract(basicsProfitAmt);
        profitDeductionList.setNotDeductionAmt(notDeductionAmt);
        BigDecimal actualDeductionAmt = profitDeductionList.getActualDeductionAmt().add(basicsProfitAmt);
        profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
        newMap.put("actualDeductionAmt", actualDeductionAmt);
        newMap.put("basicsProfitAmt", BigDecimal.ZERO);
        newMap.put("mustDeductionAmtSum", basicsProfitAmt);
        try {
            this.updateDeductionInfo(profitDeductionList, computType);
        } catch (Exception e){}
        return newMap;
    }

    /**
     * 先扣掉代理商还剩的分润，然后在从担保代理商扣
     */
    private Map<String, Object> deduceParentAgent(ProfitDeduction profitDeduction, BigDecimal basicsProfitAmt, String computType, BigDecimal mustAmt){
        List<ProfitDetailMonth> profitMonth = profitMonthService.getAgentProfit(profitDeduction.getParentAgentId(),
                profitDeduction.getDeductionDate().replaceAll("-", ""), null);
        if(profitMonth != null && !profitMonth.isEmpty()){
            Map<String, Object> newMap = new HashMap<String, Object>();
            BigDecimal notDeductionAmt = mustAmt.subtract(basicsProfitAmt);//未扣足的部分
            newMap.put("basicsProfitAmt", BigDecimal.ZERO);
            LOG.info("应扣金额：{}，基础分润扣款：{}，扣减后应扣：{}：", mustAmt, basicsProfitAmt,notDeductionAmt);
            BigDecimal actualDeductionAmt = basicsProfitAmt; //已扣的部分
            profitDeduction.setActualDeductionAmt(profitDeduction.getActualDeductionAmt().add(basicsProfitAmt));//截止目前实扣
            for (ProfitDetailMonth detail : profitMonth){
                BigDecimal parentBasicsProfitAmt = detail.getBasicsProfitAmt() == null ? BigDecimal.ZERO : detail.getBasicsProfitAmt();
                if(notDeductionAmt.compareTo(BigDecimal.ZERO) == 0){//应扣的机具款为0，已经扣完
                    break;
                } else if(parentBasicsProfitAmt.compareTo(BigDecimal.ZERO) <= 0 ){//担保代理商分润为0，循环下个基础分润进行扣款
                    continue;
                } else {
                    Map<String, Object> respMap = this.deductParentAgentProfit(profitDeduction, detail, computType, notDeductionAmt);
                    actualDeductionAmt = actualDeductionAmt.add(new BigDecimal(respMap.get("actualDeductionAmt").toString()));
                    notDeductionAmt = notDeductionAmt.subtract(new BigDecimal(respMap.get("deductionAmt").toString()));
                    LOG.info("该笔机具款目前，已实扣金额：{}，还应扣金额：{}", actualDeductionAmt, notDeductionAmt);
                }
            }
            if(notDeductionAmt.compareTo(BigDecimal.ZERO) > 0){
                LOG.info("该笔机具款目前，应扣金额还剩：{}元未扣足", notDeductionAmt);
            }
            profitDeduction.setNotDeductionAmt(notDeductionAmt);
            try {
                this.updateDeductionInfo(profitDeduction, computType);
            } catch (Exception e){}
            newMap.put("actualDeductionAmt", actualDeductionAmt);
            newMap.put("mustDeductionAmtSum", mustAmt.subtract(notDeductionAmt));
            return newMap;
        } else {
            LOG.info("机具扣款流水号：{}，代理商编号：{}，未查询到担保代理商月分润信息", profitDeduction.getSourceId(), profitDeduction.getAgentId());
            return this.notDeductionAmt(profitDeduction, basicsProfitAmt, computType, mustAmt);
        }
    }

    /**
     * 担保代理商扣减基础分润，增加担保代理商的应扣与实扣
     * @param profitDeductionList
     * @param profitMonth
     */
    private Map<String, Object> deductParentAgentProfit(ProfitDeduction profitDeductionList, ProfitDetailMonth profitMonth,
                                                        String computType, BigDecimal mustNotDeductionAmt) {
        BigDecimal parentBasicsProfitAmt = profitMonth.getBasicsProfitAmt();
        LOG.info("机具扣款流水号：{}，担保代理商：{}，担保代理商基础分润：{}，应扣金额：{}",
                profitDeductionList.getSourceId(),  profitDeductionList.getParentAgentId(), parentBasicsProfitAmt, mustNotDeductionAmt);
        Map<String, Object> newMap = new HashMap<String, Object>();
        ProfitDetailMonth profitDetailMonth = null;
        if (parentBasicsProfitAmt.compareTo(mustNotDeductionAmt) >= 0) {
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(mustNotDeductionAmt));
            profitDetailMonth = this.updateProfitMonth(profitMonth, mustNotDeductionAmt, profitDeductionList.getDeductionDesc());
            newMap.put("deductionAmt", mustNotDeductionAmt);
            newMap.put("actualDeductionAmt", mustNotDeductionAmt);

        } else if(mustNotDeductionAmt.compareTo(parentBasicsProfitAmt) >= 0){
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(parentBasicsProfitAmt));
            profitDetailMonth = this.updateProfitMonth(profitMonth, parentBasicsProfitAmt, profitDeductionList.getDeductionDesc());
            newMap.put("deductionAmt", parentBasicsProfitAmt);
            newMap.put("actualDeductionAmt", parentBasicsProfitAmt);
        }
        try {
            this.insertDeductionInfo(profitDeductionList, profitDetailMonth, computType, newMap);
        } catch (Exception e){}
        return newMap;
    }

    /**
     * 扣减上级担保代理商实际分润。
     * @param profitDetailMonth
     * @param dudecutAmt
     * @param paltformNo
     */
    private ProfitDetailMonth updateProfitMonth(ProfitDetailMonth profitDetailMonth, BigDecimal dudecutAmt, String paltformNo){
        profitDetailMonth.setBasicsProfitAmt(profitDetailMonth.getBasicsProfitAmt().subtract(dudecutAmt));
        BigDecimal sum = profitDetailMonth.getProfitSumAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getProfitSumAmt();
        profitDetailMonth.setProfitSumAmt(sum.subtract(dudecutAmt));
        profitDetailMonth.setBusPlatForm(paltformNo);
        if(Objects.equals("100003", paltformNo)){
            BigDecimal posMuust = profitDetailMonth.getPosDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgMustDeductionAmt();
            BigDecimal posReal = profitDetailMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgRealDeductionAmt();
            profitDetailMonth.setPosDgMustDeductionAmt(posMuust.add(dudecutAmt));
            profitDetailMonth.setPosDgRealDeductionAmt(posReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，POS机具，累加前应扣：{}，累加前实扣：{}，扣款金额：{}",
                    profitDetailMonth.getId(),profitDetailMonth.getAgentId(), posMuust, posReal, dudecutAmt);
        } else if(Objects.equals("100002", paltformNo)){
            BigDecimal zposMuust = profitDetailMonth.getZposDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposDgMustDeductionAmt();
            BigDecimal zposReal = profitDetailMonth.getZposTdRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposTdRealDeductionAmt();
            profitDetailMonth.setZposDgMustDeductionAmt(zposMuust.add(dudecutAmt));
            profitDetailMonth.setZposTdRealDeductionAmt(zposReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，ZPOS机具，累加前应扣：{}，累加前实扣：{}，扣款金额：{}",
                    profitDetailMonth.getId(),profitDetailMonth.getAgentId(), zposMuust, zposReal, dudecutAmt);
        }else if(Objects.equals("5000", paltformNo)){
            BigDecimal rhbMuust = profitDetailMonth.getRhbDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgMustDeductionAmt();
            BigDecimal rhbReal = profitDetailMonth.getRhbDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgRealDeductionAmt();
            profitDetailMonth.setRhbDgMustDeductionAmt(rhbMuust.add(dudecutAmt));
            profitDetailMonth.setRhbDgRealDeductionAmt(rhbReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，瑞和宝机具，累加前应扣：{}，累加前实扣：{}，扣款金额：{}",
                    profitDetailMonth.getId(),profitDetailMonth.getAgentId(), rhbMuust, rhbReal, dudecutAmt);
        }
        return profitDetailMonth;
    }

    /**
     * 如果发生机具款扣款日期已到，还存在审批中的调整申请，正常扣款，后续审批拒绝或者人为处理
     * 1、更新扣款表状态与实扣金额
     * 2、新增扣款明细
     * @param profitDeductionList
     * @throws Exception
     */
    private void insertDeductionInfo(ProfitDeduction profitDeductionList, ProfitDetailMonth profitDetailMonth,
                                     String computType, Map<String, Object> newMap) throws Exception {
        try {
            if(Objects.equals(computType, "1")){
                if(profitDetailMonth != null){
                    ProfitDeduction danbaoDeduct = new ProfitDeduction();
                    danbaoDeduct.setAgentId(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setAgentPid(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                    danbaoDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                    danbaoDeduct.setDeductionType(DeductionType.MACHINE.getType());
                    danbaoDeduct.setActualDeductionAmt(new BigDecimal(newMap.get("deductionAmt").toString()));
                    danbaoDeduct.setMustDeductionAmt(new BigDecimal(newMap.get("deductionAmt").toString()));
                    danbaoDeduct.setRemark("为担保代理商："+profitDeductionList.getAgentPid()+"，补扣机具款");
                    danbaoDeduct.setId(profitDeductionList.getId());
                    profitDeducttionDetailService.insertDeducttionDetail(danbaoDeduct);
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
                    detatil.setProfitSumAmt(profitDetailMonth.getProfitSumAmt());
                    profitDetailMonthServiceImpl.updateByPrimaryKeySelective(detatil);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }

    /**
     * 第二轮，未扣足的部分，从合并代理商扣款(list列表)
     * 返回请求代理商的实扣扣款金额、代理商扣款list列表
     * 合并代理商list列表，在基础分润扣掉没后，删除list列表
     */
    private Map<String, Object> secondRound(String agentPid, String computType, Map<String, Object> map) {
        BigDecimal actualDeductionAmtSum =  BigDecimal.ZERO;
        List<Map<String, Object>> mergeAgentList= (List<Map<String, Object>>)map.get("hbList");
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentPid(agentPid);
        profitDeduction.setDeductionDesc(map.get("paltformNo").toString());
        profitDeduction.setDeductionDate(map.get("deductDate").toString());
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if (list != null && !list.isEmpty()) {
            for (ProfitDeduction profitDeductionList : list){
                if(profitDeductionList.getNotDeductionAmt().compareTo(BigDecimal.ZERO) == 0){
                    continue;
                }
                Iterator<Map<String, Object>> iter = mergeAgentList.iterator();
                while (iter.hasNext()) {
                    Map<String, Object> mergeMap = iter.next();
                    BigDecimal basicAmt = new BigDecimal(mergeMap.get("basicAmt").toString());
                    BigDecimal notDeductionAmt = profitDeductionList.getNotDeductionAmt();
                    LOG.info("机具扣款代理商唯一码：{}，未扣足金额：{}，合并代理商基础分润主键：{}，合并代理商基础分润：{}",
                            agentPid, notDeductionAmt, mergeMap.get("id"), basicAmt);
                    if(basicAmt.compareTo(notDeductionAmt) >= 0){
                        basicAmt = basicAmt.subtract(notDeductionAmt);
                        profitDeductionList.setNotDeductionAmt(BigDecimal.ZERO);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(notDeductionAmt));
                        mergeMap.put("basicAmt", basicAmt);
                        actualDeductionAmtSum = actualDeductionAmtSum.add(notDeductionAmt);
                        try {
                            this.mergeAgentUpdate(mergeMap, profitDeductionList, computType, notDeductionAmt, notDeductionAmt);
                        } catch (Exception e) {}
                        LOG.info("该合并商户基础分润充足，合并代理商基础分润主键：{}，已扣机具金额：{}，合并代理商剩余基础分润：{}",
                                mergeMap.get("id"), notDeductionAmt, basicAmt);
                        if(basicAmt.compareTo(BigDecimal.ZERO) == 0){
                            iter.remove();
                        }
                        break;
                    } else {
                        BigDecimal surNotDeductionAmt = notDeductionAmt.subtract(basicAmt);
                        profitDeductionList.setNotDeductionAmt(surNotDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(basicAmt));
                        actualDeductionAmtSum = actualDeductionAmtSum.add(basicAmt);
                        mergeMap.put("basicAmt", BigDecimal.ZERO);
                        try {
                            this.mergeAgentUpdate(mergeMap, profitDeductionList, computType, basicAmt, notDeductionAmt);
                        } catch (Exception e) {}
                        LOG.info("该合并商户基础分润不足，合并代理商基础分润主键：{}，已扣机具金额：{}，剩余基础分润：{}",
                                mergeMap.get("id"), basicAmt, mergeMap.get("basicAmt"));
                        iter.remove();
                    }
                }
            }
        }
        map.put("actualDeductionAmtSum", actualDeductionAmtSum);
        map.put("hbList", mergeAgentList);
        return map;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    private void mergeAgentUpdate(Map<String, Object> map, ProfitDeduction profitDeductionList,
                                  String computType, BigDecimal deductAmt, BigDecimal shouldDeductAmt) throws Exception{
        try {
            ProfitDetailMonth profitMonth = profitMonthService.getProfitDetailMonth((String)map.get("id"));
            profitMonth.setBasicsProfitAmt(new BigDecimal(map.get("basicAmt").toString()));
            profitMonth.setProfitSumAmt(new BigDecimal(map.get("basicAmt").toString()));
            BigDecimal otherSupplyAmt = profitMonth.getOtherSupplyAmt() == null ? BigDecimal.ZERO : profitMonth.getOtherSupplyAmt();
            profitMonth.setOtherSupplyAmt(otherSupplyAmt.add(deductAmt));
            profitDetailMonthServiceImpl.updateByPrimaryKeySelective(profitMonth);
            if(Objects.equals(computType, "1")) {
                ProfitDeduction mergeDeduct = new ProfitDeduction();
                mergeDeduct.setAgentId(profitMonth.getAgentPid());
                mergeDeduct.setAgentPid(profitMonth.getAgentPid());
                mergeDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                mergeDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                mergeDeduct.setDeductionType(DeductionType.MACHINE.getType());
                mergeDeduct.setActualDeductionAmt(deductAmt);
                mergeDeduct.setMustDeductionAmt(shouldDeductAmt);
                mergeDeduct.setNotDeductionAmt(shouldDeductAmt.subtract(deductAmt));
                mergeDeduct.setRemark("为代理商："+profitDeductionList.getAgentPid()+"补扣机具款，扣款金额累计在分润明细的其他扣款中");
                mergeDeduct.setId(profitDeductionList.getId());
                profitDeducttionDetailService.insertDeducttionDetail(mergeDeduct);
                profitDeductionService.updateProfitDeduction(profitDeductionList);
                profitDeductionList.setActualDeductionAmt(deductAmt);
                profitDeductionList.setMustDeductionAmt(shouldDeductAmt);
                profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);
            }
        } catch (Exception e){
            e.printStackTrace();e.printStackTrace();
            throw new Exception("机具扣款失败。");
        }
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

    public static void main(String[] args) {
        BigDecimal ss = new BigDecimal("1000");
        dd(ss);
        System.out.println(ss);
    }

    public static void dd(BigDecimal amt){
        for (int i = 0; i < 10; i++) {
            amt = amt.add(new BigDecimal(i));
        }
        System.out.println(amt);
    }
}