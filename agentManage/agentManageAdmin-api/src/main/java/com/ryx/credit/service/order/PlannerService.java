package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptPro;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/20.
 */
public interface PlannerService {

    PageInfo queryPlannerList(OReceiptOrder receiptOrder, OReceiptPro receiptPro, Page page,Map map);

    AgentResult savePlanner(ReceiptPlan receiptPlan,String receiptProId,String activityId) throws Exception;

    List<Map<String, Object>> queryOrderReceiptPlanInfo(Map<String, String> params) throws ProcessException;

    AgentResult batchPlanner(List<ReceiptPlan> receiptPlanList,String userId) throws Exception;
}
