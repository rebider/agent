package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentPlatFormSynMapper;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import com.ryx.credit.common.util.PageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步通知 pos/手刷开户
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 11:33
 */
@Service("agentNotifyService")
public class AgentNotifyServiceImpl implements AgentNotifyService {

    private static Logger log = LoggerFactory.getLogger(AgentNotifyServiceImpl.class);
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;


    @Override
    public void asynNotifyPlatform(String busId) {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                notifyPlatform(busId);
            }
        });
    }

    @Override
    public PageInfo queryList(Page page,AgentPlatFormSyn agentPlatFormSyn) {
        Map<String, Object> map = new HashMap<>();
        if(null!=agentPlatFormSyn.getAgentId()){
            map.put("agentId",agentPlatFormSyn.getAgentId());
        }
        List<Map<String, Object>> list = agentPlatFormSynMapper.queryList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(agentPlatFormSynMapper.queryCount(map));
        return pageInfo;
    }

    private void notifyPlatform(String busId) {
        if (StringUtils.isBlank(busId)) {
            log.info("notifyPlatform业务ID为空");
            return;
        }
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        if (agentBusInfo == null) {
            log.info("notifyPlatform记录不存在");
            return;
        }
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if (StringUtils.isNotBlank(agentBusInfo.getBusParent())) {
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        List<String> regionList = getParent(agentBusInfo.getBusRegion());
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        if (regionList == null) {
            if (regionList.size() == 3) {
                agentNotifyVo.setProvince(regionList.get(0));
                agentNotifyVo.setCity(regionList.get(1));
                agentNotifyVo.setCityArea(regionList.get(2));
            }
            if (regionList.size() == 2) {
                agentNotifyVo.setProvince(regionList.get(0));
                agentNotifyVo.setCity(regionList.get(1));
            }
            if (regionList.size() == 1) {
                agentNotifyVo.setProvince(regionList.get(0));
            }
        }
        agentNotifyVo.setUniqueId(agent.getAgUniqNum());
        agentNotifyVo.setOrgName(agent.getAgName());
        if (null != agentParent) {
            agentNotifyVo.setSupDorgId(agentParent.getBusNum());
        }

        try {
            AgentResult result = httpRequest(agentNotifyVo);

        } catch (Exception e) {
            log.info("");

        }

    }

    /**
     * 递归获取层级
     *
     * @param busRegion
     * @return
     */
    private List<String> getParent(String busRegion) {
        List<String> resultList = new ArrayList<>();
        Region region = queryParent(busRegion);
        resultList.add(0, region.getrCode());
        while (true) {
            if (!region.getpCode().equals("0")) {
                region = queryParent(region.getpCode());
                resultList.add(0, region.getrCode());
            } else {
                break;
            }
        }
        return resultList;
    }

    private Region queryParent(String busRegion) {
        RegionExample example = new RegionExample();
        RegionExample.Criteria criteria = example.createCriteria();
        criteria.andRCodeEqualTo(busRegion);
        List<Region> regions = regionMapper.selectByExample(example);
        return regions.get(0);
    }

    private AgentResult httpRequest(AgentNotifyVo agentNotifyVo) throws Exception {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("uniqueId", agentNotifyVo.getUniqueId());
            param.put("useOrgan", "");  //885:自营，886：代理商，A00：机构，887：手刷
            param.put("orgName", agentNotifyVo.getOrgName());
            if (StringUtils.isNotBlank(agentNotifyVo.getProvince()))
                param.put("province", agentNotifyVo.getProvince());
            if (StringUtils.isNotBlank(agentNotifyVo.getCity()))
                param.put("city", agentNotifyVo.getCity());
            if (StringUtils.isNotBlank(agentNotifyVo.getCity()))
                param.put("cityArea", agentNotifyVo.getCity());
            param.put("orgType", agentNotifyVo.getOrgType());
            param.put("supDorgId", agentNotifyVo.getSupDorgId());

            String httpResult = HttpClientUtil.doPost("url", param);
            if (httpResult.contains("orgId")) {
                return AgentResult.ok(httpResult);
            } else {
                log.info("http请求超时返回错误:{}", httpResult);
                throw new Exception("http请求超时");
            }
        } catch (Exception e) {
            log.info("http请求超时:{}", e.getMessage());
            throw new Exception("http请求超时");
        }
    }
}
