package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.service.agent.BusinessPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page) {

        Map<String, Object> reqMap = new HashMap<>();
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }
}
