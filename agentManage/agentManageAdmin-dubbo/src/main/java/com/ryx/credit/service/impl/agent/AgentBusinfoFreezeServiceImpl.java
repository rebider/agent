package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentBusinfoFreezeMapper;
import com.ryx.credit.service.agent.AgentBusinfoFreezeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2020/6/3 15:14
 * @Description:
 */
@Service("agentBusinfoFreezeService")
public class AgentBusinfoFreezeServiceImpl implements AgentBusinfoFreezeService {

    private static Logger logger = LoggerFactory.getLogger(AgentBusinfoFreezeServiceImpl.class);

    @Autowired
    private AgentBusinfoFreezeMapper agentBusinfoFreezeMapper;
    @Override
    public PageInfo abfreezeList(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusinfoFreezeMapper.queryAbfreezeListView(map,page));
        pageInfo.setTotal(agentBusinfoFreezeMapper.queryAbfreezeListCount(map));
        return pageInfo;
    }
}