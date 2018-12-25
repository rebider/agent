package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.bank.DPosRegionExample;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.agent.PlatFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private DPosRegionMapper dPosRegionMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AssProtoColMapper assProtoColMapper;
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;


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
                .andCIsinEqualTo(Status.STATUS_0.status)
        .andCloReviewStatusEqualTo(AgStatus.Approved.status);

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
            List<Map<String, Object>> maps = assProtoColMapper.selectByBusInfoId(agentBusInfo.getId());
            if(null==maps){
                continue;
            }else if(maps.size()==0){
                continue;
            }else{
                agentBusInfo.setAssProtocolMap(maps.get(0));
            }
        }
        return agentBusInfos;
    }

    @Override
    public List<AgentBusInfo> businessQuery(String agentId,String isZpos) {

        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
        criteria.andAgentIdEqualTo(agentId);
        if(StringUtils.isBlank(isZpos)){
            criteria.andBusPlatformNotEqualTo(Platform.ZPOS.getValue());
        }else if(isZpos.equals("true")){
            criteria.andBusPlatformEqualTo(Platform.ZPOS.getValue());
        }
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
            if(null!=platForm){
                agentBusInfo.setBusPlatformType(platForm.getPlatformType());
            }
            agentBusInfo.setAgentColinfoList(agentColinfoMapper.queryBusConinfoList(agentBusInfo.getId()));
            List<Map<String, Object>> maps = assProtoColMapper.selectByBusInfoId(agentBusInfo.getId());
            if(null==maps){
                continue;
            }else if(maps.size()==0){
                continue;
            }else{
                agentBusInfo.setAssProtocolMap(maps.get(0));
            }
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
                return FastMap.fastSuccessMap().putKeyV("agent",agent).putKeyV("rel",rel)
                        .putKeyV("agentId",agent.getId()).putKeyV("agName",agent.getAgName());
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.Business.name())){
                AgentBusInfo angetBusInfo = agentBusInfoMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,angetBusInfo.getAgentId());
                return FastMap.fastSuccessMap().putKeyV("angetBusInfo",angetBusInfo).putKeyV("rel",rel)
                        .putKeyV("agentId",angetBusInfo.getAgentId()).putKeyV("agName",agName);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.DC_Agent.name())){
                DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,dateChangeRequest.getDataId());
                return FastMap.fastSuccessMap().putKeyV("DateChangeRequest",dateChangeRequest).putKeyV("rel",rel)
                        .putKeyV("agentId",dateChangeRequest.getDataId()).putKeyV("agName",agName);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.DC_Colinfo.name())){
                DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,dateChangeRequest.getDataId());
                return FastMap.fastSuccessMap().putKeyV("DateChangeRequest",dateChangeRequest).putKeyV("rel",rel)
                        .putKeyV("agentId",dateChangeRequest.getDataId()).putKeyV("agName",agName);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.PkType.name())){
                OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,oSupplement.getAgentId());
                return FastMap.fastSuccessMap().putKeyV("OSupplement",oSupplement).putKeyV("rel",rel)
                        .putKeyV("agentId",oSupplement.getAgentId()).putKeyV("agName",agName);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.ORDER.name())){
                OOrder order = oOrderMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,order.getAgentId());
                return FastMap.fastSuccessMap().putKeyV("OOrder",order).putKeyV("rel",rel)
                        .putKeyV("agentId",order.getAgentId()).putKeyV("agName",agName);
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.COMPENSATE.name())){
                ORefundPriceDiff refundrPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(rel.getBusId());
                return FastMap.fastSuccessMap().putKeyV("refundrPriceDiff",refundrPriceDiff).putKeyV("rel",rel)
                        .putKeyV("agentId","").putKeyV("agName","");
            }
            if(StringUtils.isNotBlank(rel.getBusType()) && rel.getBusType().equals(BusActRelBusType.refund.name())){
                OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(rel.getBusId());
                String agName = redisService.hGet(RedisCachKey.AGENTINFO.code,oReturnOrder.getAgentId());
                return FastMap.fastSuccessMap().putKeyV("oReturnOrder",oReturnOrder).putKeyV("rel",rel)
                        .putKeyV("agentId",oReturnOrder.getAgentId()).putKeyV("agName",agName);
            }
            return FastMap.fastSuccessMap().putKeyV("rel",rel);
        }
        return null;
    }


    @Override
    public String dPosRegionNameFromDposIds(String... ids) {
        try {
            if(ids==null || ids.length==0)
                return "";
            StringBuffer sb = new StringBuffer();
            List list = redisService.multiGet(RedisCachKey.DPOSREGION.code,ids);
            for (int i=0;i<list.size();i++) {
                sb.append(list.get(i)).append((i!=list.size()-1)?",":"");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public String dRegionNameFromIds(String... ids){
        try {
            if(ids==null || ids.length==0)
                return "";
            StringBuffer sb = new StringBuffer();
            List list = redisService.multiGet(RedisCachKey.DREGIONS.code,ids);
            for (int i=0;i<list.size();i++) {
                sb.append(list.get(i)).append((i!=list.size()-1)?",":"");
            }
            return sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public String getAgentNameByBusId(String busId) {
        try {
            return redisService.hGet(RedisCachKey.AGENT_BUSINFO.code+busId,"AG_NAME");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void loadCach() {

        new Thread(() -> {

            //agent
            List<Map<String,Object>>  list = agentBusInfoMapper.queryAgentNameByBusId();
            Map<String,String> agentBusInfoName = new HashMap();
            for (Map<String, Object> stringObjectMap : list) {
                try {
                    redisService.hSet(RedisCachKey.AGENT_BUSINFO.code+stringObjectMap.get("ID"),stringObjectMap);
                    agentBusInfoName.put(stringObjectMap.get("AGENT_ID")+"",stringObjectMap.get("AG_NAME")+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("代理商业务集合信息放入redis");
            try {
                redisService.hSet(RedisCachKey.AGENTINFO.code,agentBusInfoName);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if(!redisService.haveKey(RedisCachKey.DPOSREGION.code)) {
                //DPosRegion
                DPosRegionExample dPosRegionExample = new DPosRegionExample();
                List<DPosRegion> dPosRegions = dPosRegionMapper.selectByExample(dPosRegionExample);
                Map<String, String> dPosRegionName = new HashMap();
                for (DPosRegion dPosRegion : dPosRegions) {
                    dPosRegionName.put(dPosRegion.getCode(), dPosRegion.getName());
                }
                logger.info("POSREGIONS 放入redis");
                try {
                    redisService.hSet(RedisCachKey.DPOSREGION.code, dPosRegionName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(!redisService.haveKey(RedisCachKey.DREGIONS.code)) {
                List<Region> regions = regionMapper.selectAll();
                Map<String, String> regionsName = new HashMap();
                for (Region region : regions) {
                    regionsName.put(region.getrCode(), region.getrName());
                }
                try {
                    logger.info("REGION 放入redis");
                    redisService.hSet(RedisCachKey.DREGIONS.code, regionsName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
