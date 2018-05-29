package com.ryx.credit.service.impl.agent;

import com.alibaba.druid.sql.visitor.functions.If;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        Agent agent = agentMapper.selectByPrimaryKey(id);
        List<Map<String,Object>> agDocProList=null;
        List<Map<String,Object>> agDocDistrictList=null;
        if(null!=agent.getAgDocPro()){
            agDocProList = agentMapper.queryDeptName(agent.getAgDocPro());
        }
        if(null!=agent.getAgDocDistrict()){
            agDocDistrictList  = agentMapper.queryDeptNameDis(agent.getAgDocDistrict());
        }
        if(null!=agDocProList&&agDocProList.size()>0){
            Map<String, Object>  agDocProMap= agDocProList.get(0);
            agent.setAgDocPro((String)agDocProMap.get("NAME"));
        }if(null!=agDocDistrictList&&agDocDistrictList.size()>0){
            Map<String, Object> agDocDistrictMap = agDocDistrictList.get(0);
            agent.setAgDocDistrict((String)agDocDistrictMap.get("NAME"));
        }
        return agent;
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
                Map<String,Object> busParents = agentBusInfoMapper.queryAgentName(agentBusInfo.getBusParent());
                Map<String,Object> busRiskParents = agentBusInfoMapper.queryAgentName(agentBusInfo.getBusRiskParent());
                Map<String,Object> busActivationParents = agentBusInfoMapper.queryAgentName(agentBusInfo.getBusActivationParent());
                agentBusInfo.setBusParent((String)busParents.get("AG_NAME"));
                agentBusInfo.setBusRiskParent((String)busParents.get("AG_NAME"));
                agentBusInfo.setBusActivationParent((String)busParents.get("AG_NAME"));
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
