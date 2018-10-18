package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.tools.jar.Main;

import java.math.BigDecimal;
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
    private ProfitStagingDetailMapper profitStagingDetailMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;

    /**
     * 代理商分润不足，扣减了担保代理商的分润，是否需要通知上层被扣减担保代理商是谁，扣减分润是多少？
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        LOG.info("机具分润扣款：{}", map);
        if (map == null || map.get("agentPid") == null || map.get("paltformNo") == null
                || map.get("agentProfitAmt") == null) {
            throw new Exception("机具分润扣款参数为空");
        }
        map.put("deductDate", deductDate);
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
     * 第一轮，代理商扣不足，从担保代理商扣
     * 1、代理商基础分润充足，足额扣掉自己本月所有机具分期订单，则返回给大流程，应扣，实扣，大流程中更新基础分润等信息
     * 2、代理商基础分润不足，不能扣掉本月自己的所有机具分期订单，则能扣多少算多少。
     * 3、不足的部分，在担保代理商的基础分润进行扣款，如果担保代理商分润没有初始化基础分润，在进行初始化
     */
    private Map<String, Object> fristRound(Map<String, Object> map, String agentPid, String computType) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentPid(agentPid);
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
        List<Map<String, Object>> respList = new ArrayList<Map<String, Object>>();
        if (list != null && !list.isEmpty()) {
            LOG.info("机具分润扣款：代理商编号：{}，机具类型：{}，代理商基础分润：{}",
                    agentPid, map.get("paltformNo").toString(), map.get("agentProfitAmt").toString());
            for (ProfitDeduction profitDeductionList : list) {
                LOG.info("机具扣款流水号：{}，代理商编号：{}，应扣金额：{}", profitDeductionList.getSourceId(), agentPid, profitDeductionList.getMustDeductionAmt());
                if(Objects.equals(DeductionStatus.YES_WITHHOLD.getStatus(),profitDeductionList.getStagingStatus())){
                    continue;
                } else if(Objects.equals(DeductionStatus.REVIEWING.getStatus(),profitDeductionList.getStagingStatus())){
                    mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getSumDeductionAmt());
                    profitDeductionList.setMustDeductionAmt(profitDeductionList.getSumDeductionAmt());
                } else {
                    mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                }
                if (basicsProfitAmt.compareTo(profitDeductionList.getMustDeductionAmt()) >= 0) {
                    LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商基础分润充足，直接扣款：{}", profitDeductionList.getSourceId(),
                            agentPid, profitDeductionList.getMustDeductionAmt());
                    basicsProfitAmt = basicsProfitAmt.subtract(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    actualDeductionAmtSum = actualDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                    try {
                        this.updateDeductionInfo(profitDeductionList, null, computType);
                    } catch (Exception e){}
                } else {
                    LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商分润不足，查找担保代理商分润进行扣款", profitDeductionList.getSourceId(), agentPid);
                    Map<String, Object> newMap = this.deduceParentAgent(profitDeductionList, basicsProfitAmt, computType);
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

    /**
     * 基础分润不足部分，从担保代理商进行扣款
     */
    private Map<String, Object> deduceParentAgent(ProfitDeduction profitDeductionList, BigDecimal basicsProfitAmt, String computType) {
        if(profitDeductionList.getParentAgentPid() == null){
            LOG.info("机具扣款流水号：{}，代理商编号：{}，没有担保代理商。", profitDeductionList.getSourceId(), profitDeductionList.getAgentId());
            return this.notDeductionAmt(profitDeductionList, basicsProfitAmt, computType);
        }
        String danbaoParentAgentId = this.obtainParentAgentId(profitDeductionList.getParentAgentId(), profitDeductionList.getDeductionDesc());
        ProfitDetailMonth profitMonth = profitMonthService.getAgentProfit(profitDeductionList.getParentAgentId(),
                profitDeductionList.getDeductionDate().replaceAll("-",""), danbaoParentAgentId);
        if(profitMonth == null){
            LOG.info("机具扣款流水号：{}，代理商编号：{}，未查询到担保代理商月分润信息", profitDeductionList.getSourceId(), profitDeductionList.getAgentId());
            return this.notDeductionAmt(profitDeductionList, basicsProfitAmt, computType);
        }
        Map<String, Object> respMap = this.deductParentAgentProfit(profitDeductionList, profitMonth, computType, danbaoParentAgentId, basicsProfitAmt);
        return respMap ;
    }

    private Map<String, Object> notDeductionAmt(ProfitDeduction profitDeductionList, BigDecimal basicsProfitAmt, String computType){
        Map<String, Object> newMap = new HashMap<String, Object>();
        BigDecimal notDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(basicsProfitAmt);
        profitDeductionList.setNotDeductionAmt(notDeductionAmt);
        BigDecimal actualDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(notDeductionAmt);
        profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
        newMap.put("actualDeductionAmt", actualDeductionAmt);
        newMap.put("basicsProfitAmt", BigDecimal.ZERO);
        try {
            this.updateDeductionInfo(profitDeductionList, null, computType);
        } catch (Exception e){}
        return newMap;
    }

    /**
     * 根据代理商查找上级代理商ID
     */
    private String obtainParentAgentId(String parentAgentId , String platfromNo){
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), platfromNo, parentAgentId);
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            LOG.info("担保代理商ID：{}，上级代理商ID：{}", parentAgentId, agentBusInfo.get(0).getAgentId());
            return agentBusInfo.get(0).getAgentId();
        }
        return null;
    }

    /**
     * 担保代理商扣减基础分润，增加担保代理商的应扣与实扣
     * @param profitDeductionList
     * @param profitMonth
     */
    private Map<String, Object> deductParentAgentProfit(ProfitDeduction profitDeductionList, ProfitDetailMonth profitMonth,
                                                        String computType, String danbaoParentAgentId, BigDecimal basicsProfitAmt) {
        BigDecimal parentBasicsProfitAmt = profitMonth.getBasicsProfitAmt() == null ? BigDecimal.ZERO : profitMonth.getBasicsProfitAmt();
        if(parentBasicsProfitAmt.compareTo(BigDecimal.ZERO) == 0){
            //初始化担保代理商基础分润
//            Map<String, Object> danbaoMap = profitMonthService.getDbProfitAmt(profitDeductionList.getAgentId(), danbaoParentAgentId, computType);
//            if(danbaoMap == null){
//                return this.notDeductionAmt(profitDeductionList, basicsProfitAmt, computType);
//            } else {
//                parentBasicsProfitAmt = new BigDecimal(danbaoMap.get("basicAmt").toString());
//            }
        }
        LOG.info("机具扣款流水号：{}，代理商编号：{}，代理商剩余基础分润：{}，担保代理商：{}，担保代理商基础分润：{}",
                profitDeductionList.getSourceId(), profitDeductionList.getAgentId(), basicsProfitAmt, profitDeductionList.getParentAgentId(), parentBasicsProfitAmt);

        BigDecimal notDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(basicsProfitAmt);//代理商本身基础分润扣减后，未扣足部分
        ProfitDetailMonth profitDetailMonth = null;
        if (parentBasicsProfitAmt.compareTo(notDeductionAmt) >= 0) {
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
            profitDetailMonth = this.updateProfitMonth(profitMonth, notDeductionAmt, profitDeductionList.getDeductionDesc());
        } else if(parentBasicsProfitAmt.compareTo(BigDecimal.ZERO) <= 0){
            profitDeductionList.setNotDeductionAmt(notDeductionAmt);
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt().subtract(notDeductionAmt));
        } else if(notDeductionAmt.compareTo(parentBasicsProfitAmt) >= 0){
            BigDecimal deductAmt = notDeductionAmt.subtract(parentBasicsProfitAmt);
            profitDeductionList.setNotDeductionAmt(deductAmt);
            BigDecimal actualDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(deductAmt);
            profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
            profitDetailMonth = this.updateProfitMonth(profitMonth, parentBasicsProfitAmt, profitDeductionList.getDeductionDesc());
        }
        try {
            this.updateDeductionInfo(profitDeductionList, profitDetailMonth, computType);
        } catch (Exception e){}
        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("actualDeductionAmt", profitDeductionList.getActualDeductionAmt());
        newMap.put("basicsProfitAmt", BigDecimal.ZERO);
        return newMap;
    }

    /**
     * 扣减上级担保代理商实际分润。
     * @param profitDetailMonth
     * @param jjDudecutAmt
     * @param paltformNo
     */
    private ProfitDetailMonth updateProfitMonth(ProfitDetailMonth profitDetailMonth, BigDecimal jjDudecutAmt, String paltformNo){
        LOG.info("代理商编号：{}，担保代理商扣减基础分润金额：{}，机具类型：{}", profitDetailMonth.getAgentPid(), jjDudecutAmt, paltformNo);
        profitDetailMonth.setBasicsProfitAmt(profitDetailMonth.getBasicsProfitAmt().subtract(jjDudecutAmt));
        profitDetailMonth.setProfitSumAmt(profitDetailMonth.getProfitSumAmt().subtract(jjDudecutAmt));
        profitDetailMonth.setBusPlatForm(paltformNo);
        if(Objects.equals("100003", paltformNo)){//POS
            profitDetailMonth.setPosDgMustDeductionAmt(profitDetailMonth.getPosDgMustDeductionAmt().add(jjDudecutAmt));
            profitDetailMonth.setPosDgRealDeductionAmt(profitDetailMonth.getPosDgRealDeductionAmt().add(jjDudecutAmt));
        } else if(Objects.equals("100002", paltformNo)){//ZPOS
            profitDetailMonth.setZposDgMustDeductionAmt(profitDetailMonth.getZposDgMustDeductionAmt().add(jjDudecutAmt));
            profitDetailMonth.setZposTdRealDeductionAmt(profitDetailMonth.getZposTdRealDeductionAmt().add(jjDudecutAmt));
        }else if(Objects.equals("5000", paltformNo)){//瑞和宝
            profitDetailMonth.setRhbDgMustDeductionAmt(profitDetailMonth.getRhbDgMustDeductionAmt().add(jjDudecutAmt));
            profitDetailMonth.setRhbDgRealDeductionAmt(profitDetailMonth.getRhbDgRealDeductionAmt().add(jjDudecutAmt));
        }
        return profitDetailMonth;
    }

    /**
     * 1、更新扣款表状态与实扣金额
     * 2、新增扣款明细
     * @param profitDeductionList
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    private void updateDeductionInfo(ProfitDeduction profitDeductionList, ProfitDetailMonth profitDetailMonth, String computType) throws Exception {
        try {
            if(Objects.equals(computType, "1")){
                if(Objects.equals(DeductionStatus.REVIEWING.getStatus(), profitDeductionList.getStagingStatus())){
                    profitDeductionList.setRemark("系统自动扣款日期已到，审批中的扣款将按照扣款总额进行扣款，调整的扣款金额将被重置。");
                    ProfitStagingDetailExample profitStagingDetailExample = new ProfitStagingDetailExample();
                    ProfitStagingDetailExample.Criteria criteria = profitStagingDetailExample.createCriteria();
                    criteria.andStagIdEqualTo(profitDeductionList.getId());
                    criteria.andSourceIdEqualTo(profitDeductionList.getSourceId());
                    List<ProfitStagingDetail> detail = profitStagingDetailMapper.selectByExample(profitStagingDetailExample);
                    if(detail != null && !detail.isEmpty()){
                        ProfitStagingDetail profitStagingDetail = detail.get(0);
                        profitStagingDetail.setMustAmt(BigDecimal.ZERO);
                        profitStagingDetail.setRealAmt(BigDecimal.ZERO);
                        profitStagingDetailMapper.updateByPrimaryKeySelective(profitStagingDetail);
                    }
                }
                profitDeductionList.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                profitDeductionService.updateProfitDeduction(profitDeductionList);
                profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);
                if(profitDetailMonth != null){
                    ProfitDeduction danbaoDeduct = new ProfitDeduction();
                    danbaoDeduct.setAgentId(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setAgentPid(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                    danbaoDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                    danbaoDeduct.setDeductionType("02");
                    danbaoDeduct.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt());
                    danbaoDeduct.setMustDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    danbaoDeduct.setRemark("担保代理商为下级代理商："+profitDeductionList.getAgentPid()+"补扣机具未扣足部分机具款");
                    danbaoDeduct.setId(profitDeductionList.getId());
                    profitDeducttionDetailService.insertDeducttionDetail(danbaoDeduct);
                }
            }
            if(profitDetailMonth != null){
                profitDetailMonthServiceImpl.updateByPrimaryKeySelective(profitDetailMonth);
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
        profitDeduction.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if (list != null && !list.isEmpty()) {
            for (ProfitDeduction profitDeductionList : list){
                if(profitDeductionList.getNotDeductionAmt().compareTo(BigDecimal.ZERO) == 0){
                    continue;
                }
                Iterator<Map<String, Object>> iter = mergeAgentList.iterator();
                while (iter.hasNext()) {
                    Map<String, Object> mergeMap = iter.next();
                    LOG.info("机具扣款代理商编号：{}，未扣款金额：{}，合并代理商编号主键：{}，基础分润：{}",
                            agentPid, profitDeductionList.getNotDeductionAmt(), mergeMap.get("id"), mergeMap.get("basicAmt"));
                    BigDecimal basicAmt = new BigDecimal(mergeMap.get("basicAmt").toString());
                    BigDecimal notDeductionAmt = profitDeductionList.getNotDeductionAmt();
                    if(basicAmt.compareTo(notDeductionAmt) >= 0){
                        basicAmt = basicAmt.subtract(notDeductionAmt);
                        profitDeductionList.setNotDeductionAmt(BigDecimal.ZERO);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(notDeductionAmt));
                        mergeMap.put("basicAmt", basicAmt);
                        actualDeductionAmtSum = actualDeductionAmtSum.add(notDeductionAmt);
                        try {
                            this.mergeAgentUpdate(mergeMap, profitDeductionList, computType, notDeductionAmt, notDeductionAmt);
                        } catch (Exception e) {}
                        LOG.info("该合并商户基础分润充足，合并代理商编号主键：{}，可扣金额：{}，剩余基础分润：{}",
                                mergeMap.get("id"), notDeductionAmt, mergeMap.get("basicAmt"));
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
                        LOG.info("该合并商户基础分润不足，合并代理商编号主键：{}，可扣金额：{}，剩余基础分润：{}",
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
            if(Objects.equals(computType, "1")) {//真实计算
                ProfitDeduction mergeDeduct = new ProfitDeduction();
                mergeDeduct.setAgentId(profitMonth.getAgentPid());
                mergeDeduct.setAgentPid(profitMonth.getAgentPid());
                mergeDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                mergeDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                mergeDeduct.setDeductionType(DeductionType.MACHINE.getType());
                mergeDeduct.setActualDeductionAmt(deductAmt);
                mergeDeduct.setMustDeductionAmt(shouldDeductAmt);
                mergeDeduct.setNotDeductionAmt(shouldDeductAmt.subtract(deductAmt));
                mergeDeduct.setRemark("为代理商ID："+profitDeductionList.getAgentPid()+"补扣机具未扣足部分机具款，扣款金额累计在分润明细的其他扣款中");
                mergeDeduct.setId(profitDeductionList.getId());
                profitDeducttionDetailService.insertDeducttionDetail(mergeDeduct);
                profitDeductionService.updateProfitDeduction(profitDeductionList);
                ProfitDeduction deduct = profitDeductionList;
                deduct.setActualDeductionAmt(deductAmt);
                deduct.setMustDeductionAmt(shouldDeductAmt);
                profitDeducttionDetailService.insertDeducttionDetail(deduct);
            }
        } catch (Exception e){
            e.printStackTrace();e.printStackTrace();
            throw new Exception("机具扣款失败。");
        }
    }
}