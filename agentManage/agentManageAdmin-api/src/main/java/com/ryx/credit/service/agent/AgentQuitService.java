package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentQuit;

/**
 * Created by RYX on 2019/1/26.
 */
public interface AgentQuitService {

    /**
     * 退出列表
     * @param agentQuit
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    PageInfo queryAgentQuitList(AgentQuit agentQuit, Page page, String dataRole, Long userId);
}
