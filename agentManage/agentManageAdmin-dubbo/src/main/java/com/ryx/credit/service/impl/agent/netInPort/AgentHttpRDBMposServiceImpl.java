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
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.dao.bank.BankLineNumsMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.bank.BankLineNums;
import com.ryx.credit.pojo.admin.bank.BankLineNumsExample;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private BankLineNumsMapper bankLineNumsMapper;

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
        String accountType = "";
        String customerPid = ""; //身份证
        String userName = "";  //法人姓名
        if( agentColinfo.getCloType().compareTo(BigDecimal.ONE) == 0){ //对公
            accountType = "01";
            customerPid = agent.getAgLegalCernum();
            userName = agent.getAgLegal();
        }else{
            accountType = "00";
            customerPid = agentColinfo.getAgLegalCernum();
            userName = agentColinfo.getCloRealname();
        }
        resultMap.put("accountType",accountType);
        resultMap.put("customerType",accountType);
        resultMap.put("customerPid",customerPid);
        resultMap.put("userName",userName);
        resultMap.put("address",agent.getAgRegAdd());
        resultMap.put("companyNo",agent.getAgBusLic());
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
        Region region = regionMapper.findByRcode(agentColinfo.getBankRegion());
        resultMap.put("code",String.valueOf(region.gettType()));
        resultMap.put("cityid",agentColinfo.getBankRegion());
        resultMap.put("bankcity",region.getrName());
        resultMap.put("bankname",agentColinfo.getCloBank());
        BankLineNums bankLineNums = bankLineNumsMapper.selectByBankName(agentColinfo.getCloBank());
        resultMap.put("bankid",bankLineNums.getBankid());
        resultMap.put("cardName",agentColinfo.getCloRealname());
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
            jsonParams.put("code",paramMap.get("code"));
            jsonParams.put("cityid",paramMap.get("cityid"));
            jsonParams.put("bankcity",paramMap.get("bankcity"));
            jsonParams.put("bankid",paramMap.get("bankid"));
            jsonParams.put("bankname",paramMap.get("bankname"));
            jsonParams.put("cardName",paramMap.get("cardName"));
            jsonParams.put("accountType",paramMap.get("accountType"));
            jsonParams.put("customerType",paramMap.get("customerType"));

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
     * direct 直签标识N String 二代直签--1，一代X--1，机构一代--1，直签 --1 其余--0
     * @return
     */
    private String direct(String busType){
        if(BusType.ZQZF.key.equals(busType) || BusType.YDX.key.equals(busType) || BusType.JGYD.key.equals(busType) || BusType.ZQ.key.equals(busType) )
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
        Agent agent = (Agent)data.get("agent");
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        jsonParams.put("mobile",agentBusInfo.getBusNum());
        jsonParams.put("branchid",agentBusInfo.getBusPlatform().split("_")[0]);
        jsonParams.put("termCount",agentBusInfo.getTerminalsLower());
        jsonParams = commonParam(jsonParams, agentColinfo, agent);
        return jsonParams;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("mobile",data.get("mobile"));
            jsonParams.put("branchid",data.get("branchid"));
            jsonParams.put("termCount",data.get("termCount"));
            //修改参数
            jsonParams.put("cardno",data.get("cardno"));
            jsonParams.put("bankbranchid",data.get("bankbranchid"));
            jsonParams.put("bankbranchname",data.get("bankbranchname"));
            jsonParams.put("customerPid",data.get("customerPid"));
            jsonParams.put("address",data.get("address"));
            jsonParams.put("companyNo",data.get("companyNo"));
            jsonParams.put("userName",data.get("userName"));
            jsonParams.put("agencyName",data.get("agencyName"));
            jsonParams.put("cardidx",data.get("cardidx"));
            jsonParams.put("code",data.get("code"));
            jsonParams.put("cityid",data.get("cityid"));
            jsonParams.put("bankcity",data.get("bankcity"));
            jsonParams.put("bankid",data.get("bankid"));
            jsonParams.put("bankname",data.get("bankname"));
            jsonParams.put("cardName",data.get("cardName"));
            jsonParams.put("accountType",data.get("accountType"));
            jsonParams.put("customerType",data.get("customerType"));

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

    /**
     * 升级和修改 公用参数
     * @param jsonParams
     * @param agentColinfo
     * @param agent
     * @return
     */
    private Map<String,Object> commonParam(Map<String,Object> jsonParams,AgentColinfo agentColinfo,Agent agent){
        jsonParams.put("cardno",agentColinfo.getCloBankAccount());
        jsonParams.put("bankbranchid",agentColinfo.getBranchLineNum());
        jsonParams.put("bankbranchname",agentColinfo.getCloBankBranch());
        String accountType = "";
        String customerPid = ""; //身份证
        String userName = "";  //法人姓名
        if( agentColinfo.getCloType().compareTo(BigDecimal.ONE) == 0){ //对公
            accountType = "01";
            customerPid = agent.getAgLegalCernum();
            userName = agent.getAgLegal();
        }else{
            accountType = "00";
            customerPid = agentColinfo.getAgLegalCernum();
            userName = agentColinfo.getCloRealname();
        }
        jsonParams.put("accountType",accountType);
        jsonParams.put("customerType",accountType);
        jsonParams.put("customerPid",customerPid);
        jsonParams.put("userName",userName);
        jsonParams.put("address",agent.getAgRegAdd());
        jsonParams.put("companyNo",agent.getAgBusLic());
        jsonParams.put("agencyName",agent.getAgName());
        jsonParams.put("cardidx","1");
        Region region = regionMapper.findByRcode(agentColinfo.getBankRegion());
        jsonParams.put("code",String.valueOf(region.gettType()));
        jsonParams.put("cityid",agentColinfo.getBankRegion());
        jsonParams.put("bankcity",region.getrName());
        jsonParams.put("bankname",agentColinfo.getCloBank());
        BankLineNums bankLineNums = bankLineNumsMapper.selectByBankName(agentColinfo.getCloBank());
        jsonParams.put("bankid",bankLineNums.getBankid());
        jsonParams.put("cardName",agentColinfo.getCloRealname());
        return jsonParams;
    }

    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        Map<String,Object> jsonParams = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        jsonParams.put("agencyId",agentBusInfo.getBusNum());
        jsonParams = commonParam(jsonParams, agentColinfo, agent);
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
            jsonParams.put("cardidx",paramMap.get("cardidx"));
            jsonParams.put("code",paramMap.get("code"));
            jsonParams.put("cityid",paramMap.get("cityid"));
            jsonParams.put("bankcity",paramMap.get("bankcity"));
            jsonParams.put("bankid",paramMap.get("bankid"));
            jsonParams.put("bankname",paramMap.get("bankname"));
            jsonParams.put("cardName",paramMap.get("cardName"));
            jsonParams.put("accountType",paramMap.get("accountType"));
            jsonParams.put("customerType",paramMap.get("customerType"));
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
