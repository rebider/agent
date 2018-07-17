package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OActivity;

/**
 * Created by RYX on 2018/7/13.
 * 订单活动
 */
public interface OrderActivityService {

    PageInfo activityList(OActivity activity, Page page);

    AgentResult saveActivity(OActivity activity);

    AgentResult updateActivity(OActivity activity);

    OActivity findById(String id);

    AgentResult deleteById(String id);
}
