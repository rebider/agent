package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;

/**
 * Created by lhl on 2019/4/24.
 */
public interface OLogisticsDetailHService {

    AgentResult transferHistoryData();

    AgentResult queryLogisticsHistoryOrder();

    AgentResult clearLogisticsDetailOrder();
}
