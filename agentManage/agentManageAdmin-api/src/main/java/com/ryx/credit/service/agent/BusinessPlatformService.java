package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;

/**
 * 业务平台管理
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:27
 */
public interface BusinessPlatformService {

    PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page);
}
