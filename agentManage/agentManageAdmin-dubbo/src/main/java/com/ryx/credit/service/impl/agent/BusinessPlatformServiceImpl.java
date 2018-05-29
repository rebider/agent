package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.BusinessPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page) {

        Map<String, Object> reqMap = new HashMap<>();
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
        criteria.andStatusEqualTo(AgStatus.Approved.status);
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


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult saveBusinessPlatform(AgentVo agentVo) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AgentResult();
    }
}
