package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;

/**
 * Created by RYX on 2018/7/21.
 */
public interface ReceiptPlanService {

    AgentResult saveReceiptPlan(ReceiptPlan receiptPlan);

}
