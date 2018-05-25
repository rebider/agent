package com.ryx.credit.service.impl.agent;

import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("agentQueryService")
public class AgentQueryServiceImpl implements AgentQueryService{
    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private AgentColinfoMapper agentColinfoMapper;

    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private AgentContractMapper agentContractMapper;

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;

    @Override
    public Agent informationQuery(String id) {
        return agentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AgentColinfo> proceedsQuery(String id) {
        return agentColinfoMapper.proceedsQuery(id);
    }

    @Override
    public List<Capital> paymentQuery(String id) {
        return capitalMapper.paymentQuery(id);
    }

    @Override
    public List<AgentContract> compactQuery(String id) {
        return agentContractMapper.compactQuery(id);
    }

    @Override
    public List<AgentBusInfo> businessQuery(String id) {
        return agentBusInfoMapper.businessQuery(id);
    }
}
