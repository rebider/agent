package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.ORefundPriceDiffMapper;
import com.ryx.credit.dao.order.OReturnOrderMapper;
import com.ryx.credit.dao.order.OSupplementMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.agent.PlatFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private OReturnOrderMapper returnOrderMapper;
    @Autowired
    private PlatFormService platFormService;


    @Override
    public List<Agent> queryAgentListByIds(List<String> ids) {
        if(ids==null || ids.size()==0)return new ArrayList<Agent>();
        AgentExample example = new AgentExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status)
                .andAgStatusEqualTo(AgStatus.Approved.name())
                .andIdIn(ids);
        return  agentMapper.selectByExample(example);
    }

    @Override
    public Agent informationQuery(String id) {
        Agent agent = agentMapper.selectByPrimaryKey(id);
        return agent;
    }

    @Override
    public List<AgentColinfo> proceedsQuery(String id) {
        List<AgentColinfo> agentColinfos = agentColinfoMapper.proceedsQuery(id);
        if (null != agentColinfos && agentColinfos.size() > 0) {
            for (AgentColinfo agentColinfo : agentColinfos) {
                agentColinfo.setAttachmentList(accessoryQuery(agentColinfo.getId(), AttachmentRelType.Proceeds.name()));
            }
        }

        return agentColinfos;
    }

    @Override
    public List<Capital> paymentQuery(String id) {
        List<Capital> capitals = capitalMapper.paymentQuery(id);
        if (null != capitals && capitals.size() > 0) {
            for (Capital capital : capitals) {
                capital.setAttachmentList(accessoryQuery(capital.getId(), AttachmentRelType.Capital.name()));
            }
        }
        return capitals;
    }

    @Override
    public List<Capital> capitalQuery(String agentId, String type) {
        CapitalExample example = new CapitalExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status)
                .andCAgentIdEqualTo(agentId)
                .andCTypeEqualTo(type)
                .andCIsinEqualTo(Status.STATUS_0.status);

        example.setOrderByClause(" c_amount desc ");

        return capitalMapper.selectByExample(example);
    }

    @Override
    public List<AgentContract> compactQuery(String id) {
        List<AgentContract> agentContracts = agentContractMapper.compactQuery(id);
        if (null != agentContracts && agentContracts.size() > 0) {
            for (AgentContract agentContract : agentContracts) {
                agentContract.setAttachmentList(accessoryQuery(agentContract.getId(), AttachmentRelType.Contract.name()));
            }
        }
        return agentContracts;
    }

    @Override
    public List<AgentBusInfo> businessQuery(String id) {
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.businessQuery(id);
        //业务没有附件暂时注释  需要时在解开
//        if (null != agentBusInfos && agentBusInfos.size() > 0) {
//            for (AgentBusInfo agentBusInfo : agentBusInfos) {
//                agentBusInfo.setAttachmentList(accessoryQuery(agentBusInfo.getId(), AttachmentRelType.Business.name()));
//            }
//        }
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
            if(null!=platForm){
                agentBusInfo.setBusPlatformType(platForm.getPlatformType());
            }
            agentBusInfo.setAgentColinfoList(agentColinfoMapper.queryBusConinfoList(agentBusInfo.getId()));
        }
        return agentBusInfos;
    }

    @Override
    public List<Attachment> accessoryQuery(String id, String busType) {
        return attachmentMapper.accessoryQuery(id, busType);
    }


    @Override
    public Map<String, Object> queryInfoByProInsId(String proid) {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proid).andStatusEqualTo(Status.STATUS_1.status);
        List<BusActRel>  busr = busActRelMapper.selectByExample(example);
        if(busr.size()==1){
            BusActRel rel = busr.get(0);
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.Agent.name())){
                Agent agent =  agentMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("agent",agent).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.Business.name())){
                AgentBusInfo angetBusInfo = agentBusInfoMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("angetBusInfo",angetBusInfo).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.DC_Agent.name())){
                DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("DateChangeRequest",dateChangeRequest).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.DC_Colinfo.name())){
                DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("DateChangeRequest",dateChangeRequest).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.PkType.name())){
                OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("OSupplement",oSupplement).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.ORDER.name())){
                OOrder order = oOrderMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("OOrder",order).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.COMPENSATE.name())){
                ORefundPriceDiff refundrPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("refundrPriceDiff",refundrPriceDiff).putKeyV("rel",rel);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.refund.name())){
                OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("oReturnOrder",oReturnOrder).putKeyV("rel",rel);
            }
        }
        return null;
    }
}
