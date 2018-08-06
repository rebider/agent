package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排单计划
 * Created by RYX on 2018/7/21.
 */
@Service("receiptPlanService")
public class ReceiptPlanServiceImpl implements ReceiptPlanService {

    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;


    @Override
    public AgentResult saveReceiptPlan(ReceiptPlan receiptPlan) {
        AgentResult result = new AgentResult(500, "系统异常", "");
        receiptPlan.setId(idService.genId(TabId.o_receipt_plan));
        Date nowDate = new Date();
        receiptPlan.setcDate(nowDate);
        receiptPlan.setStatus(Status.STATUS_1.status);
        receiptPlan.setVersion(Status.STATUS_1.status);
        int insert = receiptPlanMapper.insert(receiptPlan);
        if (insert == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public PageInfo getReceiptPlanList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = receiptPlanMapper.getReceipPlanCount(param);
        List<Map<String, Object>> list = receiptPlanMapper.getReceipPlanList(param);
        for (Map<String, Object> maps : list) {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), String.valueOf(maps.get("PRO_TYPE")));
            maps.put("PRO_TYPE",dictByValue.getdItemname());
        }
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }
}
