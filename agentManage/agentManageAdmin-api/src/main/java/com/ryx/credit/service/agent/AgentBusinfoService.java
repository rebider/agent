package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.ResultVO;

import java.util.Map;

/**
 * Created by cx on 2018/5/23.
 */
public interface AgentBusinfoService {
    /**
     * 代理商查询插件数据获取
     * @param par
     * @return
     */
    ResultVO agentBusInfoSelectViewList(Map par, Page page);
}
