package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.OActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/13.
 * 订单活动
 */
public interface OrderActivityService {

    PageInfo activityList(OActivity activity, Page page);

    ResultVO saveActivity(OActivity activity) throws MessageException;

    AgentResult updateActivity(OActivity activity);

    OActivity findById(String id);

    AgentResult deleteById(String id);

    List<OActivity> allActivity();

    List<OActivity> productActivity(String product,String angetId);

    Map selectTermMachine(String platformNum);
}
