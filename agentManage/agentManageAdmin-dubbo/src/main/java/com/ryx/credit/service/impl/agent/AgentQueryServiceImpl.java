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
public class AgentQueryServiceImpl implements AgentQueryService {
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

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public Agent informationQuery(String id) {
        return agentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AgentColinfo> proceedsQuery(String id) {
        List<AgentColinfo> agentColinfos = agentColinfoMapper.proceedsQuery(id);
        if (null != agentColinfos && agentColinfos.size() > 0) {
            for (AgentColinfo agentColinfo : agentColinfos) {
                agentColinfo.setAttachmentList(accessoryQuery(agentColinfo.getId(), "Proceeds"));
            }
        }

        return agentColinfos;
    }

    @Override
    public List<Capital> paymentQuery(String id) {
        List<Capital> capitals = capitalMapper.paymentQuery(id);
        if (null != capitals && capitals.size() > 0) {
            for (Capital capital : capitals) {
                capital.setAttachmentList(accessoryQuery(capital.getId(), "Capital"));
            }
        }
        return capitals;
    }

    @Override
    public List<AgentContract> compactQuery(String id) {
        List<AgentContract> agentContracts = agentContractMapper.compactQuery(id);
        if (null != agentContracts && agentContracts.size() > 0) {
            for (AgentContract agentContract : agentContracts) {
                agentContract.setAttachmentList(accessoryQuery(agentContract.getId(), "Contract"));
            }
        }
        return agentContracts;
    }

    @Override
    public List<AgentBusInfo> businessQuery(String id) {
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.businessQuery(id);
        if (null != agentBusInfos && agentBusInfos.size() > 0) {
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                agentBusInfo.setAttachmentList(accessoryQuery(agentBusInfo.getId(), "Business"));
            }
        }
        return agentBusInfos;
    }

    @Override
    public List<Attachment> accessoryQuery(String id, String busType) {
        return attachmentMapper.accessoryQuery(id, busType);
    }
}
