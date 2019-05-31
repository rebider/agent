package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusType;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 17:17
 * @Param
 * @return
 **/
@Service("agentHttpRDBMposServiceImpl")
public class AgentHttpRDBMposServiceImpl implements AgentNetInHttpService{

    private static Logger log = LoggerFactory.getLogger(AgentNetInNotityServiceImpl.class);

    private static final String rdbReqUrl = AppConfig.getProperty("rdb_req_url");
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;


    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());

        resultMap.put("mobileNo",agentBusInfo.getBusLoginNum());
        resultMap.put("branchid",agentBusInfo.getBusPlatform());
        resultMap.put("direct",direct(agentBusInfo.getBusType()));
        resultMap.put("cardno",agentColinfo.getCloBankAccount());
        resultMap.put("termCount",agentBusInfo.getTerminalsLower());
        resultMap.put("bankbranchid",agentColinfo.getBranchLineNum());
        resultMap.put("bankbranchname",agentColinfo.getCloBankBranch());
        resultMap.put("customerPid",agent.getAgLegalCernum());
        resultMap.put("address",agent.getAgRegAdd());
        resultMap.put("companyNo",agent.getAgBusLic());
        resultMap.put("userName",agent.getAgLegal());
        resultMap.put("agencyName",agent.getAgName());
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            AgentBusInfo agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
            if(null!=agentParent){
                resultMap.put("parentAgencyId",agentParent.getBusNum());
            }else{
                resultMap.put("parentAgencyId","");
            }
        }
        resultMap.put("agCode",agent.getId());
        resultMap.put("directLabel",directLabel(agentBusInfo.getBusType()));
        return resultMap;
    }

    @Override
    public AgentResult httpRequestNetIn(Map<String, Object> paramMap) throws Exception {

        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("mobileNo",paramMap.get("mobileNo"));
            jsonParams.put("branchid",paramMap.get("branchid"));
            jsonParams.put("direct",paramMap.get("direct"));
            jsonParams.put("cardno",paramMap.get("cardno"));
            jsonParams.put("termCount",paramMap.get("termCount"));
            jsonParams.put("bankbranchid",paramMap.get("bankbranchid"));
            jsonParams.put("bankbranchname",paramMap.get("bankbranchname"));
            jsonParams.put("customerPid",paramMap.get("customerPid"));
            jsonParams.put("address",paramMap.get("address"));
            jsonParams.put("companyNo",paramMap.get("companyNo"));
            jsonParams.put("userName",paramMap.get("userName"));
            jsonParams.put("agencyName",paramMap.get("agencyName"));
            jsonParams.put("parentAgencyId",paramMap.get("parentAgencyId"));
            jsonParams.put("agCode",paramMap.get("agCode"));
            jsonParams.put("directLabel",paramMap.get("directLabel"));
            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知瑞大宝入网请求参数：{}",json);
            //发送请求
            String httpResult = HttpClientUtil.doPostJson(rdbReqUrl+"agency/agencyNetIn", json);
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            log.info("通知瑞大宝入网返回参数：{}",httpResult);
            if (respXMLObj.getString("code").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网通知瑞大宝失败报警");
                throw new Exception(httpResult);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知瑞大宝请求超时："+ MailUtil.printStackTrace(e), "入网通知瑞大宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }

    /**
     * direct 直签标识N String 二代直签--1，一代X--1，机构一代--1，其余--0
     * @return
     */
    private String direct(String busType){
        if(BusType.ZQZF.key.equals(busType) || BusType.YDX.key.equals(busType) || BusType.JGYD.key.equals(busType) )
        return "1";
        return "0";
    }

    /**
     * 二代直签--1，一代X--2，机构一代--3  其余传空
     * @param busType
     * @return
     */
    private String directLabel(String busType){
        if(BusType.ZQZF.key.equals(busType)){
            return BusType.ZQZF.key;
        }
        if(BusType.YDX.key.equals(busType)){
            return "2";
        }
        if(BusType.JGYD.key.equals(busType)){
            return BusType.JGYD.key;
        }
        return "";
    }


    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        Map<String,Object> jsonParams = new HashMap<>();
        String busId = String.valueOf(data.get("agentBusinfoId"));
        AgentBusInfo agentBusInfo = agentBusinfoService.getById(busId);
        jsonParams.put("agencyId",agentBusInfo.getBusNum());
        jsonParams.put("termCount","2");

        return jsonParams;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("agencyId",data.get("agencyId"));
            jsonParams.put("termCount",data.get("termCount"));
            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知瑞大宝升级请求参数：{}",json);
            //发送请求
            String httpResult = HttpClientUtil.doPostJson(rdbReqUrl+"agency/setRztAgencyDirect", json);
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            log.info("通知瑞大宝升级返回参数：{}",httpResult);
            if (respXMLObj.getString("code").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "升级通知瑞大宝失败报警");
                throw new Exception(httpResult);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("升级通知瑞大宝请求超时："+ MailUtil.printStackTrace(e), "升级通知瑞大宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }

    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        Map<String,Object> jsonParams = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        jsonParams.put("agencyId",agentBusInfo.getBusNum());
        jsonParams.put("cardno",agentColinfo.getCloBankAccount());
        jsonParams.put("bankbranchid",agentColinfo.getBranchLineNum());
        jsonParams.put("bankbranchname",agentColinfo.getCloBankBranch());
        jsonParams.put("customerPid",agent.getAgLegalCernum());
        jsonParams.put("address",agent.getAgRegAdd());
        jsonParams.put("companyNo",agent.getAgBusLic());
        jsonParams.put("userName",agent.getAgLegal());
        jsonParams.put("agencyName",agent.getAgName());
        return jsonParams;
    }

    @Override
    public AgentResult httpRequestNetInUpdate(Map<String, Object> paramMap) throws Exception {
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("agencyId",paramMap.get("agencyId"));
            jsonParams.put("cardno",paramMap.get("cardno"));
            jsonParams.put("bankbranchid",paramMap.get("bankbranchid"));
            jsonParams.put("bankbranchname",paramMap.get("bankbranchname"));
            jsonParams.put("customerPid",paramMap.get("customerPid"));
            jsonParams.put("address",paramMap.get("address"));
            jsonParams.put("companyNo",paramMap.get("companyNo"));
            jsonParams.put("userName",paramMap.get("userName"));
            jsonParams.put("agencyName",paramMap.get("agencyName"));
            jsonParams.put("cardidx","1");
            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知瑞大宝入网修改请求参数：{}",json);
            //发送请求
            String httpResult = HttpClientUtil.doPostJson(rdbReqUrl+"agency/setAgencyNetIn", json);
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            log.info("通知瑞大宝入网修改返回参数：{}",httpResult);
            if (respXMLObj.getString("code").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网修改通知瑞大宝失败报警");
                throw new Exception(httpResult);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知瑞大宝请求超时："+ MailUtil.printStackTrace(e), "入网修改通知瑞大宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }

    }

    @Override
    public AgentResult queryTermCount(String agencyId)throws Exception{
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("agencyId",agencyId);
            String json = JsonUtil.objectToJson(jsonParams);
            log.info("查询终端下限数量请求参数：{}",json);
            //发送请求
            String httpResult = HttpClientUtil.doPostJson(rdbReqUrl+"agency/getTermCount", json);
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            log.info("查询终端下限数量返回参数：{}",httpResult);
            if (respXMLObj.getString("code").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "升级通知瑞大宝失败报警");
                throw new Exception(httpResult);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("升级通知瑞大宝请求超时："+ MailUtil.printStackTrace(e), "升级通知瑞大宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }
}
