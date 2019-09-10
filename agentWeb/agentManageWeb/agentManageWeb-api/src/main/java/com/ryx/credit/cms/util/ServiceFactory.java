package com.ryx.credit.cms.util;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by cx on 2018/5/29.
 */
public class ServiceFactory {

    public static ApaycompService apaycompService;
    public static BusinessPlatformService businessPlatformService;
    public static DictOptionsService dictOptionsService;
    public static RegionService regionService;
    public static DepartmentService departmentService;
    public static AgentService agentService;
    public static RedisService redisService;
    public static AgentBusinfoService agentBusinfoService;
    public static PosRegionService posRegionService;
    public static IUserService iUserService;
    public static JSONObject dictGroup=null;
    public static JSONObject orderDictGroup=null;

    @Autowired
    private ApaycompService p_apaycompService;
    @Autowired
    private BusinessPlatformService p_businessPlatformService;
    @Autowired
    private DictOptionsService p_pdictOptionsService;
    @Autowired
    private RegionService p_regionService;
    @Autowired
    private DepartmentService p_departmentService;
    @Autowired
    private AgentService p_agentService;
    @Autowired
    private RedisService p_redisService;
    @Autowired
    private AgentBusinfoService p_agentBusinfoService;
    @Autowired
    private PosRegionService p_posRegionService;
    @Autowired
    private IUserService p_iUserService;

    @PostConstruct
    public void init(){
        apaycompService = p_apaycompService;
        businessPlatformService = p_businessPlatformService;
        dictOptionsService=p_pdictOptionsService;
        regionService = p_regionService;
        departmentService = p_departmentService;
        agentService = p_agentService;
        redisService= p_redisService;
        agentBusinfoService = p_agentBusinfoService;
        posRegionService = p_posRegionService;
        iUserService = p_iUserService;
    }


}
