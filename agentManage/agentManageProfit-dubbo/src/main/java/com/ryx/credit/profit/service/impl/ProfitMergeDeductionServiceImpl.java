package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.IProfitMergeDeductionService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProfitMergeDeductionServiceImpl implements IProfitMergeDeductionService {

    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private IdService idService;

    public Map ProfitMergeDeduction(Map<String,Object> params) {
        String deductionType = (String) params.get("DEDUCTION_TYPE");
        Map<String, Object> map = new HashMap();
        if ("06".equals(deductionType)) {

            try {
                String agentName = (String) params.get("AGENT_NAME");
                String agentId = (String) params.get("AGENT_ID");
                String parentAgentId = (String) params.get("PARENT_AGENT_ID");
                String parentAgentName = (String) params.get("PARENT_AGENT_NAME");
                String rplaceAgentId = (String) params.get("RPLACE_AGENT_ID");
                String rplaceAgentName = (String) params.get("RPLACE_AGENT_NAME");

                List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("DETAILS");


                for (Map<String, Object> detailIdMap : list) {
                    ProfitDeduction deduction = new ProfitDeduction();
                    deduction.setAgentName(agentName);
                    deduction.setAgentId(agentId);
                    deduction.setParentAgentId(parentAgentId);
                    deduction.setParentAgentName(parentAgentName);
                    deduction.setRrplaceAgentId(rplaceAgentId);
                    deduction.setRrplaceAgentName(rplaceAgentName);
                    deduction.setDeductionType(deductionType);


                    String remark = (String) detailIdMap.get("DETAIN_NAME");
                    Date cDate = (Date) detailIdMap.get("C_DATE");
                    String id = (String) detailIdMap.get("ID");
                    BigDecimal supplyAmt = (BigDecimal) detailIdMap.get("PAY_AMOUNT");
                    String detainCode = (String) detailIdMap.get("DETAIN_CODE");


                    deduction.setRemark(remark);
                    deduction.setCreateDateTime(cDate);
                    deduction.setDetailId(id);
                    deduction.setAddDeductionAmt(supplyAmt);
                    deduction.setSumDeductionAmt(supplyAmt);
                    deduction.setMustDeductionAmt(supplyAmt);
                    deduction.setSourceId(detainCode);



                    deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                    deduction.setDeductionStatus("0");
                    Calendar cale = null;
                    cale = Calendar.getInstance();
                    int year = cale.get(Calendar.YEAR);
                    int month = cale.get(Calendar.MONTH) + 1;
                    String yearMonth = month >= 10 ? (year + "" + month) : (year + "0" + month);
                    deduction.setDeductionDate(yearMonth);

                    insert(deduction);
                }


           /* Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            Date date = calendar.getTime();
            String dateStr = new SimpleDateFormat("yyyyMM").format(date);*/
                //00 成功  99 失败
                map.put("rusult_code", "00");
                map.put("rusult_msg", "成功");
                return map;
            } catch (Exception e) {
                map.put("rusult_code", "99");
                map.put("rusult_msg", "失败");
                return map;
            }
        }
        map.put("rusult_code", "99");
        map.put("rusult_msg", "失败");
        return map;
    }

    @Override
    public int insert(ProfitDeduction deduction) {
        if (StringUtils.isBlank(deduction.getId())) {
            deduction.setId(idService.genId(TabId.P_DEDUCTION));
        }
        return profitDeductionMapper.insertSelective(deduction);
    }
}
