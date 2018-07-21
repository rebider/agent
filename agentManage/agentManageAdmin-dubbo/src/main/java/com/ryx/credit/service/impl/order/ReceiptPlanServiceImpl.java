package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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


    @Override
    public AgentResult saveReceiptPlan(ReceiptPlan receiptPlan){
        AgentResult result = new AgentResult(500, "系统异常", "");
        receiptPlan.setId(idService.genId(TabId.o_receipt_plan));
        Date nowDate = new Date();
        receiptPlan.setcDate(nowDate);
        receiptPlan.setStatus(Status.STATUS_1.status);
        receiptPlan.setVersion(Status.STATUS_1.status);
        int insert = receiptPlanMapper.insert(receiptPlan);
        if(insert==1){
            return AgentResult.ok();
        }
        return result;
    }
}
