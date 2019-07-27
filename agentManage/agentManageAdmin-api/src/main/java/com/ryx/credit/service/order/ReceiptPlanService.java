package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/21.
 * 已排单查询服务接口
 */
public interface ReceiptPlanService {

    AgentResult saveReceiptPlan(ReceiptPlan receiptPlan);

    /**
     * 已排单查询
     * @return
     */
    PageInfo getReceiptPlanList(Map<String, Object> param, PageInfo pageInfo, Boolean isPlan);

    AgentResult revocationPlanner(String planNum, String orderId, String user) throws Exception;
}
