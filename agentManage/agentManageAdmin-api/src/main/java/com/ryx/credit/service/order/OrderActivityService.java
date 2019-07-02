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

    /**
     * 活动复制
     * @param activityId
     * @return
     */
    AgentResult activityCopy(String activityId);

    List<OActivity> allActivity();

    List<OActivity> productActivity(String product,String angetId,String orderAgentBusifo,String oldActivityId);

    Map selectTermMachine(String platformNum) throws MessageException;

    List<Map<String,String>> planChoiseProComAndModel(String productId,String orderId);

    /**
     * 从业务平台获取sn信息并匹配本地相关活动
     * @param snStart
     * @param snEnd
     * @param count
     * @param proModel POS 手刷
     * @return
     */
    AgentResult querySnInfoFromBusSystem(String snStart,String snEnd,String count,String proModel)throws MessageException;

    void saveActivityVisible(String activityId,String visible,String[] agentIds,String userId)throws MessageException;

    List<Map<String,String>> selectConfigured(String activityId);
}
