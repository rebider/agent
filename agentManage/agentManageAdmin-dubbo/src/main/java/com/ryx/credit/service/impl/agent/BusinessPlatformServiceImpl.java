package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务平台管理
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:26
 */
@Service("businessPlatformService")
public class BusinessPlatformServiceImpl implements BusinessPlatformService {

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;


    @Override
    public PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page) {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("agStatus",AgStatus.Approved.name());
        if(!StringUtils.isBlank(agent.getAgName())){
            reqMap.put("agName",agent.getAgName());
        }
        if(!StringUtils.isBlank(agent.getAgUniqNum())){
            reqMap.put("agUniqNum",agent.getAgUniqNum());
        }
        if(!StringUtils.isBlank(agentBusInfo.getBusNum())){
            reqMap.put("busNum",agentBusInfo.getBusNum());
        }
        if(!StringUtils.isBlank(agentBusInfo.getBusPlatform())){
            reqMap.put("busPlatform",agentBusInfo.getBusPlatform());
        }
        if(agentBusInfo.getCloReviewStatus()!=null){
            reqMap.put("cloReviewStatus",agentBusInfo.getCloReviewStatus());
        }

        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }

    /**
     * 根据代理商唯一编号检索
     * @param agent
     * @return
     */
    @Override
    public Agent verifyAgent(Agent agent){
        if(StringUtils.isBlank(agent.getAgUniqNum())){
            return null;
        }
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andAgUniqNumEqualTo(agent.getAgUniqNum());
        criteria.andAgStatusEqualTo(AgStatus.Approved.name());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(example);
        if(agents.size()!=1){
            return null;
        }
        return agents.get(0);
    }

    @Override
    public AgentBusInfo findById(String id){
        AgentBusInfo agentBusInfo = null;
        if(StringUtils.isBlank(id)){
            return agentBusInfo;
        }else{
            agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(id);
        }
        return agentBusInfo;
    }

    @Override
    public int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo){
        if(StringUtils.isBlank(agentBusInfo.getId())){
            return 0;
        }
        AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
        agentBusInfo.setVersion(agbus.getVersion());
        int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
        return i;
    }

    @Override
    public List<PlatForm> queryAblePlatForm() {
        PlatFormExample example = new PlatFormExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
        return platFormMapper.selectByExample(example);
    }

    /**
     * 平台业务保存
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult saveBusinessPlatform(AgentVo agentVo) throws Exception{
        try {

            Agent agent = agentVo.getAgent();
            agent.setId(agentVo.getAgentId());
            //先查询业务是否已添加 有个添加过 全部返回
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setAgentId(agent.getId());
                Boolean busPlatExist = findBusPlatExist(item);
                if(busPlatExist){
                    return new AgentResult(500,"业务已添加,请勿重复添加","");
                }
            }
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                agentContractService.insertAgentContract(item, item.getContractTableFile());
            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                item.setcAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                AgentResult result = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser());
                if(!result.isOK()){
                    throw new ProcessException("缴纳款项信息录入失败");
                }
            }
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                agentColinfoService.agentColinfoInsert(item,item.getColinfoTableFile());
            }
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                agentBusinfoService.agentBusInfoInsert(item);
            }
            return AgentResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new ProcessException("业务平台信息录入失败");
        }
    }

    /**
     * 查询代理上次业务是否添加过
     * @param agentBusInfo
     * @return
     */
    private Boolean findBusPlatExist(AgentBusInfo agentBusInfo){
        AgentBusInfoExample example = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentBusInfo.getAgentId());
        criteria.andBusPlatformEqualTo(agentBusInfo.getBusPlatform());
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(example);
        if(null==agentBusInfos){
            return true;
        }
        if(agentBusInfos.size()==0){
            return false;
        }
        return true;
    }

}
