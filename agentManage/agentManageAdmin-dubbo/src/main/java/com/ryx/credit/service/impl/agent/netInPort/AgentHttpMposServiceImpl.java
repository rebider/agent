package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 17:17
 * @Param
 * @return
 **/
@Service("agentHttpMposServiceImpl")
public class AgentHttpMposServiceImpl implements AgentNetInHttpService {

    private static Logger log = LoggerFactory.getLogger(AgentHttpMposServiceImpl.class);
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {

        Map<String, Object> resultMap = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");

        PayComp payComp = apaycompService.selectById(agentBusInfo.getCloPayCompany());
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        agentColinfo.setAccountId(agentBusInfo.getCloPayCompany());
        agentColinfo.setAccountName(payComp.getComName());

        resultMap.put("colinfoMessage",agentColinfo);
        resultMap.put("uniqueId",agentBusInfo.getAgentId());
        resultMap.put("orgId",agentBusInfo.getBusNum());
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        resultMap.put("orgName",agent.getAgName());
        resultMap.put("busPlatform",agentBusInfo.getBusPlatform());
        resultMap.put("agHeadMobile",agent.getAgHeadMobile());
        resultMap.put("baseMessage",agent);
        resultMap.put("busMessage",agentBusInfo);


        if(agent.getAgRegArea()!=null){
            List<String> regionList = agentNetInNotityService.getParent(agent.getAgRegArea());
            if(regionList!=null){
                if(regionList.size()==3){
                    resultMap.put("province",regionList.get(0));
                    resultMap.put("city",regionList.get(1));
                    resultMap.put("cityArea",regionList.get(2));
                }else if(regionList.size()==2){
                    resultMap.put("province",regionList.get(0));
                    resultMap.put("city",regionList.get(1));
                }else if(regionList.size()==1){
                    resultMap.put("province",regionList.get(0));
                }
            }
        }
        AgentBusInfo agentParent = null;
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
        resultMap.put("orgType",dictByValue.getdItemname().contains(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());


        return null;
    }


    @Override
    public AgentResult httpRequestNetIn(Map<String,Object> paramMap)throws Exception{
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("uniqueId",paramMap.get("uniqueId"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("orgId"))))
                jsonParams.put("orgId",paramMap.get("orgId"));
            jsonParams.put("useOrgan",paramMap.get("useOrgan")); //使用范围
            jsonParams.put("orgName",paramMap.get("orgName"));
            jsonParams.put("busPlatform",paramMap.get("busPlatform"));
            jsonParams.put("agHeadMobile",paramMap.get("agHeadMobile"));
            jsonParams.put("baseMessage",paramMap.get("baseMessage"));
            jsonParams.put("busMessage",paramMap.get("busMessage"));
            jsonParams.put("colinfoMessage",paramMap.get("colinfoMessage"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("province"))))
                jsonParams.put("province",paramMap.get("province"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("city"))))
                jsonParams.put("city",paramMap.get("city"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("cityArea"))))
                jsonParams.put("cityArea",paramMap.get("cityArea"));
            jsonParams.put("orgType",paramMap.get("orgType"));
            if(paramMap.get("orgType").equals(OrgType.STR.getValue()))
                jsonParams.put("supDorgId",paramMap.get("supDorgId"));

            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知手刷请求参数：{}",json);

            //发送请求
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("agent_mpos_notify_url"), json);

            log.info("通知手刷返回参数：{}",httpResult);
            if (httpResult.contains("data") && httpResult.contains("orgId")){
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                log.info(dataObj.toJSONString());
                if(com.ryx.credit.commons.utils.StringUtils.isBlank(respXMLObj.get("data").toString())){
                    AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                }
                return AgentResult.ok(dataObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                log.info("http请求超时返回错误:{}",httpResult);
                throw new Exception("http返回有误");
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知手刷请求超时："+ MailUtil.printStackTrace(e), "入网通知手刷失败报警");
            log.info("http请求超时:{}",e.getMessage());
            throw new Exception("http请求超时");
        }
    }
}
