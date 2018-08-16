package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 代理商分润不足，扣减了担保代理商的分润，是否需要通知上层被扣减担保代理商是谁，扣减分润是多少？
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        LOG.info("机具分润扣款：{}", map);
        if (map == null) {
            throw new Exception("机具分润扣款参数为空");
        }

        if (map.get("agentId") == null || map.get("paltformNo") == null
                || map.get("deductDate") == null || map.get("agentProfitAmt") == null) {
            throw new Exception("机具分润扣款参数为空");
        }
        String agentId = map.get("agentId").toString();
        String paltformNo = map.get("paltformNo").toString();
        String deductDate = map.get("deductDate").toString();
        BigDecimal agentProfitAmt = new BigDecimal(map.get("agentProfitAmt").toString());

        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentId);
        profitDeduction.setAgentPid(paltformNo);
        profitDeduction.setDeductionDate(deductDate);
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        //应扣总额
        BigDecimal mustDeductionAmtSum = BigDecimal.ZERO;
        //实扣总额
        BigDecimal actualDeductionAmtSum = BigDecimal.ZERO;
        if (list != null && !list.isEmpty()) {
            for (ProfitDeduction profitDeductionList : list) {
                if(Objects.equals(DeductionStatus.YES_WITHHOLD.getStatus(),profitDeductionList.getStagingStatus())){
                    continue;
                } else if(Objects.equals(DeductionStatus.REVIEWING.getStatus(),profitDeductionList.getStagingStatus())){
                    mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getSumDeductionAmt());
                    profitDeductionList.setMustDeductionAmt(profitDeductionList.getSumDeductionAmt());
                } else {
                    mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                }
                LOG.info("机具分润扣款：代理商编号：{}，机具类型：{}，代理商分润：{}", agentId, paltformNo, agentProfitAmt.toString());
                LOG.info("扣款信息：代理商编号：{}，扣款总额：{}，应扣：{}", profitDeductionList.getAgentId(), profitDeductionList.getSumDeductionAmt(), profitDeductionList.getMustDeductionAmt());
                mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                if (agentProfitAmt.compareTo(profitDeductionList.getMustDeductionAmt()) >= 0) {
                    agentProfitAmt = agentProfitAmt.subtract(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    actualDeductionAmtSum = actualDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                    this.updateDeductionInfo(profitDeductionList);
                } else {
                    LOG.info("机具分润扣款：代理商编号：{}，代理商分润不足，查找担保代理商分润进行扣款", agentId);
                    //未扣足部分
                    BigDecimal notDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(agentProfitAmt);
                    //代理商月分润已扣完。
                    agentProfitAmt = BigDecimal.ZERO;
                    //从担保代理商分润进行扣款
                    ProfitDetailMonth profitMonth = profitMonthService.getAgentProfit(profitDeductionList.getParentAgentId(), deductDate);
                    BigDecimal parentAgentProfitAmt = BigDecimal.ZERO ;
                    if(profitMonth != null ){
                        LOG.info("机具分润扣款：代理商编号：{}，上级代理商ID：{}，代理商分润：{}，担保代理商分润：{}",
                                agentId, profitMonth.getAgentId(), agentProfitAmt.toString(), profitMonth.getRealProfitAmt());
                        BigDecimal realProfitAmt = profitMonth.getRealProfitAmt() == null ? BigDecimal.ZERO : profitMonth.getRealProfitAmt();
                        if(realProfitAmt.compareTo(BigDecimal.ZERO) > 0){
                            parentAgentProfitAmt = parentAgentProfitAmt.add(profitMonth.getRealProfitAmt());//实发分润
                        } else {
                            parentAgentProfitAmt = parentAgentProfitAmt.add(profitMonth.getProfitSumAmt());//分润汇总
                        }
                    }
                    if (parentAgentProfitAmt.compareTo(notDeductionAmt) >= 0) {
                        parentAgentProfitAmt = parentAgentProfitAmt.subtract(notDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                        actualDeductionAmtSum = actualDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                    } else {
                        LOG.info("机具分润扣款：代理商编号：{}，担保代理商分润不足", agentId);
                        //担保代理商月分润不足
                        BigDecimal parentProfitNotDeductionAmt = notDeductionAmt.subtract(parentAgentProfitAmt);
                        //担保代理商月分润已扣完。
                        parentAgentProfitAmt = BigDecimal.ZERO;
                        profitDeductionList.setNotDeductionAmt(parentProfitNotDeductionAmt);
                        BigDecimal actualDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(parentProfitNotDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
                        actualDeductionAmtSum = actualDeductionAmtSum.add(actualDeductionAmt);
                    }
                    if(profitMonth != null){
                        this.updateProfitMonth(profitMonth, parentAgentProfitAmt);
                    }
                    this.updateDeductionInfo(profitDeductionList);
                }
            }
        }
        LOG.info("机具扣款结束。代理商剩余分润：{}，代理商本月应扣总额：{}，代理商本月实扣总额：{}", agentProfitAmt, mustDeductionAmtSum, actualDeductionAmtSum);
        map.put("mustDeductionAmtSum", mustDeductionAmtSum);
        map.put("actualDeductionAmtSum", actualDeductionAmtSum);
        return map;
    }

    /**
     * 担保代理商扣减分润
     * @param profitDeductionList
     * @param profitMonth
     * @param notDeductionAmt
     */
    private Map<String, Object> deductParentAgentProfit(ProfitDeduction profitDeductionList, ProfitDetailMonth profitMonth, BigDecimal notDeductionAmt) {
        BigDecimal realProfitAmt = profitMonth.getRealProfitAmt() == null ? BigDecimal.ZERO : profitMonth.getRealProfitAmt();
        if(realProfitAmt.compareTo(BigDecimal.ZERO) > 0){
            //实发分润，计算
            BigDecimal jjDudecutAmt = this.computeProfit(profitDeductionList, realProfitAmt, notDeductionAmt);
            //更新已经计算的代理商分润
            this.updateProfitMonth(profitMonth, jjDudecutAmt, profitDeductionList.getDeductionDesc());
            return null;
        } else {
            //分润汇总，计算，返回到大流程中更新代理商分润
            BigDecimal profitSumAmt = profitMonth.getProfitSumAmt() == null ? BigDecimal.ZERO : profitMonth.getProfitSumAmt();
            BigDecimal jjDudecutAmt = this.computeProfit(profitDeductionList, profitSumAmt, notDeductionAmt);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("agentPid", profitMonth.getAgentPid());
            map.put("jjDudecutAmt", jjDudecutAmt);
            return map;
        }
    }

    private BigDecimal computeProfit(ProfitDeduction profitDeductionList, BigDecimal profitAmt, BigDecimal notDeductionAmt) {
        if (profitAmt.compareTo(notDeductionAmt) >= 0) {
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
            return notDeductionAmt;
        } else {
            LOG.info("机具分润扣款：代理商编号：{}，担保代理商分润不足", profitDeductionList.getParentAgentPid());
            //担保代理商月分润不足
            BigDecimal parentProfitNotDeductionAmt = notDeductionAmt.subtract(profitAmt);
            //担保代理商月分润已扣完。
            profitDeductionList.setNotDeductionAmt(parentProfitNotDeductionAmt);
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt().subtract(parentProfitNotDeductionAmt));
            return profitAmt;
        }
    }


    /**
     * 1、更新扣款表状态与实扣金额
     * 2、新增扣款明细
     * @param profitDeductionList
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    private void updateDeductionInfo(ProfitDeduction profitDeductionList) throws Exception {
        try {
            if(Objects.equals(DeductionStatus.REVIEWING.getStatus(),profitDeductionList.getStagingStatus())){
                profitDeductionList.setRemark("系统自动扣款日期已到，审批中的扣款按照应扣款总额进行扣款，调整的扣款金额，将不会累计到下月分期中");
            if(Objects.equals(DeductionStatus.REVIEWING.getStatus(), profitDeductionList.getStagingStatus())){
                profitDeductionList.setRemark("系统自动扣款日期已到，审批中的扣款将按照扣款总额进行扣款，调整的扣款金额将被重置。");
                //减掉申请调整的扣款分期任务
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
            ProfitDeducttionDetail detailList = profitDeducttionDetailService.getProfitDeducttionDetail(profitDeductionList);
            if (detailList == null) {
                profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }

    /**
     * 扣减上级担保代理商实际分润。
     * @param profitMonth
     * @param parentAgentProfitAmt
     */
    private void updateProfitMonth(ProfitDetailMonth profitMonth, BigDecimal parentAgentProfitAmt){
        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
        profitDetailMonth.setId(profitMonth.getId());
        profitDetailMonth.setRealProfitAmt(parentAgentProfitAmt);
        profitDetailMonthServiceImpl.updateByPrimaryKeySelective(profitDetailMonth);
    }
}
