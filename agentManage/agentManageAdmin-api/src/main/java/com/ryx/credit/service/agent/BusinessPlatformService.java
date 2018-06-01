package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.util.List;

/**
 * 业务平台管理
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:27
 */
public interface BusinessPlatformService {

    PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page);

    Agent verifyAgent(Agent agent);

    AgentBusInfo findById(String id);

    int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo);

    List<PlatForm> queryAblePlatForm();

    AgentResult saveBusinessPlatform(AgentVo agentVo) throws Exception;
}
