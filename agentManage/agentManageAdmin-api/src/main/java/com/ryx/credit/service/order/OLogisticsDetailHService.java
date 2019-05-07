package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;

/**
 * Created by lhl on 2019/4/24.
 */
public interface OLogisticsDetailHService {

    AgentResult transferHistoryData();


    AgentResult clearLogisticsDetailOrder(OLogisticsDetail detail)throws Exception;
}
