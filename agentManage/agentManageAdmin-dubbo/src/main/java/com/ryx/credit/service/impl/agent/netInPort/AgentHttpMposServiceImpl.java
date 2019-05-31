package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
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
    @Autowired
    private AgentBusinfoService agentBusinfoService;

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
        resultMap.put("orgType",OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        return resultMap;
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
                throw new Exception(httpResult);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知手刷请求超时："+ MailUtil.printStackTrace(e), "入网通知手刷失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时"+e.getLocalizedMessage());
        }
    }

    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        //待请求参数
        FastMap fastMap = FastMap.fastMap("agencyLevelUpdateChangeData","agencyLevelUpdateChangeData");
        try {
            Object agentBusinfo = data.get("agentBusinfoId");
            AgentBusInfo agentBusInfo = agentBusinfoService.getById(agentBusinfo+"");
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfo.getBusParent())) {
                AgentBusInfo parent = agentBusinfoService.getById(agentBusInfo.getBusParent());
                fastMap.putKeyV("newparentAgencyId",parent.getBusNum());
            }
            //查询代理商的上级代理
            fastMap.putKeyV("agencyId",agentBusInfo.getBusNum());
            fastMap.putKeyV("type","0");
            fastMap.putKeyV("createName",data.get("processingId"));
        } catch (Exception e) {
            e.printStackTrace();
            fastMap.putKeyV("msg",e.getLocalizedMessage());
        }
        return fastMap;
    }

    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        log.info("通知手刷请求参数 代理商升级：{}",data);
        try {

            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("agencyId",data.get("agencyId"));

            if(null!=data.get("oldparentAgencyId"))
                jsonParams.put("oldparentAgencyId",data.get("oldparentAgencyId")); //使用范围
            jsonParams.put("newparentAgencyId",data.get("newparentAgencyId"));
            jsonParams.put("type",data.get("type"));
            jsonParams.put("createName",data.get("createName"));

            JSONObject respXMLObj = request(jsonParams,AppConfig.getProperty("agencyChange_mpos_notify_url"));

            String respCode = respXMLObj.getString("respCode");
            String respMsg = respXMLObj.getString("respMsg");

            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(respCode) && respCode.equals("000000")){
                JSONObject repdata =  respXMLObj.getJSONObject("data");
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(respXMLObj.toJSONString(), "升级通知手刷失败报警");
                AgentResult ag = AgentResult.fail(respMsg);
                ag.setData(respXMLObj);
                return ag;
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            AppConfig.sendEmails("http请求超时:"+ MailUtil.printStackTrace(e), "升级通知手刷失败报警");
            throw e;
        }
    }

    public JSONObject request(Map data,String url) throws Exception {
        String json = JsonUtil.objectToJson(data);
        log.info("升级通知手刷请求参数：{}",json);
        String httpResult = HttpClientUtil.doPostJson(url, json);
        log.info("升级通知手刷返回参数：{}",httpResult);
        JSONObject respXMLObj = JSONObject.parseObject(httpResult);
        return respXMLObj;
    }

    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        return packageParam(param);
    }

    @Override
    public AgentResult httpRequestNetInUpdate(Map<String, Object> paramMap) throws Exception {
        return httpRequestNetIn(paramMap);
    }

    @Override
    public AgentResult queryTermCount(String agencyId) throws Exception {
        return null;
    }


}
