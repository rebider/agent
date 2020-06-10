package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;

import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2020/6/3 15:13
 * @Description:业务冻结
 */
public interface AgentBusinfoFreezeService {

    PageInfo abfreezeList(Page page, Map map);

    public AgentResult AgentBusinfoFreeze(AgentFreeze agentFreeze, String userId);
}