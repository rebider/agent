package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
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

/**
 * @author yangmx
 * @desc 机具分润扣款
 */
@Service("profitToolsDeductServiceImpl")
public class ProfitToolsDeductServiceImpl implements DeductService {

    private static Logger LOG = LoggerFactory.getLogger(ProfitToolsDeductServiceImpl.class);
    @Autowired
    private ToolsDeductService toolsDeductService;
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;
    @Autowired
    private ProfitMonthService profitMonthService;

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
                BigDecimal parentAgentProfitAmt = profitMonthService.getAgentProfit(profitDeductionList.getParentAgentId(), deductDate);
                LOG.info("机具分润扣款：代理商编号：{}，机具类型：{}，代理商分润：{}，担保代理商分润：{}",
                        agentId, paltformNo, agentProfitAmt.toString(), parentAgentProfitAmt.toString());
                LOG.info("扣款信息：代理商编号：{}，扣款总额：{}，应扣：{}", profitDeductionList.getAgentId(), profitDeductionList.getSumDeductionAmt(), profitDeductionList.getMustDeductionAmt());
                mustDeductionAmtSum = mustDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                if (agentProfitAmt.compareTo(profitDeductionList.getMustDeductionAmt()) >= 0) {
                    agentProfitAmt = agentProfitAmt.subtract(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                    profitDeductionList.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    this.updateDeductionInfo(profitDeductionList);
                    actualDeductionAmtSum = actualDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                } else {
                    //未扣足部分
                    BigDecimal notDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(agentProfitAmt);
                    //代理商月分润已扣完。
                    agentProfitAmt = BigDecimal.ZERO;
                    //从担保代理商分润进行扣款
                    if (parentAgentProfitAmt.compareTo(notDeductionAmt) >= 0) {
                        parentAgentProfitAmt = parentAgentProfitAmt.subtract(notDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(profitDeductionList.getMustDeductionAmt());
                        actualDeductionAmtSum = actualDeductionAmtSum.add(profitDeductionList.getMustDeductionAmt());
                    } else {
                        //担保代理商月分润不足
                        BigDecimal parentProfitNotDeductionAmt = notDeductionAmt.subtract(parentAgentProfitAmt);
                        //担保代理商月分润已扣完。
                        parentAgentProfitAmt = BigDecimal.ZERO;
                        profitDeductionList.setNotDeductionAmt(parentProfitNotDeductionAmt);
                        BigDecimal actualDeductionAmt = profitDeductionList.getMustDeductionAmt().subtract(parentProfitNotDeductionAmt);
                        profitDeductionList.setActualDeductionAmt(actualDeductionAmt);
                        actualDeductionAmtSum = actualDeductionAmtSum.add(actualDeductionAmt);
                    }
                    profitDeductionList.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
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
     * 1、更新扣款表状态与实扣金额
     * 2、新增扣款明细
     * @param profitDeductionList
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    private void updateDeductionInfo(ProfitDeduction profitDeductionList) throws Exception {
        try {
            ProfitDeducttionDetail detailList = profitDeducttionDetailService.getProfitDeducttionDetail(profitDeductionList);
            if (detailList == null) {
                profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);
            }
            profitDeductionService.updateProfitDeduction(profitDeductionList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }
}
