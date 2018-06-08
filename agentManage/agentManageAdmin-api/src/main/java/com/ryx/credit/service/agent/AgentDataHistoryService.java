package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/8 10:38
 */
public interface AgentDataHistoryService {

    AgentResult saveDataHistory(Object object,String dataType);
}
