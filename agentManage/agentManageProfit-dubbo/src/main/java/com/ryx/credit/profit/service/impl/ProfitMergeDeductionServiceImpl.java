package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.IProfitMergeDeductionService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfitMergeDeductionServiceImpl implements IProfitMergeDeductionService {

    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private IdService idService;

    public Map ProfitMergeDeduction(Map<String, String> params) {
        Map<String,Object> map = new HashMap();
        try{
            String  agentName = params.get("AGENT_NAME");
            String   agentId  =  params.get("AGENT_ID");
            String parentAgentId =  params.get("PARENT_AGENT_ID");
            String parentAgentName = params.get("PARENT_AGENT_NAME");
            String rplaceAgentId = params.get("RPLACE_AGENT_ID");
            String rplaceAgentName=params.get("RPLACE_AGENT_NAME");
            String supplyAmt =params.get("SUPPLY_AMT");
            String remark = params.get("REMARK");
            ProfitDeduction deduction = new ProfitDeduction();
            deduction.setAgentName(agentName);
            deduction.setAgentId(agentId);
            deduction.setParentAgentId(parentAgentId);
            deduction.setParentAgentName(parentAgentName);
            deduction.setRrplaceAgentId(rplaceAgentId);
            deduction.setRrplaceAgentName(rplaceAgentName);
            BigDecimal amt =  new BigDecimal(supplyAmt.toString());
            deduction.setAddDeductionAmt(amt);
            deduction.setSumDeductionAmt(amt);
            deduction.setMustDeductionAmt(amt);
            deduction.setRemark(remark);
            deduction.setDeductionType("06");
            int rusult = insert(deduction);
            //00 成功  99 失败
            if (1==rusult) {
                map.put("rusult_code","00");
                map.put("rusult_msg","成功");
            }else{
                map.put("rusult_code","99");
                map.put("rusult_msg","失败");
            }
            return map;
        }catch (Exception e){
            map.put("rusult_code","99");
            map.put("rusult_msg","失败");
            return map;
        }
    }

    @Override
    public int insert(ProfitDeduction deduction) {
        if (StringUtils.isBlank(deduction.getId())) {
            deduction.setId(idService.genId(TabId.P_DEDUCTION));
        }
        return profitDeductionMapper.insertSelective(deduction);
    }
}
