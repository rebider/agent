package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.dao.bank.BankLineNumsMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.BankLineNums;
import com.ryx.credit.pojo.admin.bank.BankLineNumsExample;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.dict.DictOptionsService;
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

    private static final String rdbReqUrl = AppConfig.getProperty("rdb_req_url");
    private static Logger log = LoggerFactory.getLogger(AgentNetInNotityServiceImpl.class);
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
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
            Agent agent = (Agent)param.get("agent");
            AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
            if(agentColinfo==null){
                agentColinfo = new AgentColinfo();
            }
            resultMap.put("mobileNo",agentBusInfo.getBusLoginNum().trim());
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
            if(region==null){
                region = new Region();
            }
            resultMap.put("code",String.valueOf(region.gettType()));
            resultMap.put("cityid",agentColinfo.getBankRegion());
            resultMap.put("bankcity",region.getrName());
            resultMap.put("bankname",agentColinfo.getCloBank());
            BankLineNums bankLineNums = bankLineNumsMapper.selectByBankName(agentColinfo.getCloBank());
            if(bankLineNums!=null){
                resultMap.put("bankid",bankLineNums.getBankid());
            }else{
                resultMap.put("bankid","999");
            }
            resultMap.put("cardName",agentColinfo.getCloRealname());
            resultMap.put("channelTopId",agentBusInfo.getFinaceRemitOrgan());
            resultMap.put("invoice",String.valueOf(agentColinfo.getCloInvoice()));
            resultMap.put("tax",String.valueOf(agentColinfo.getCloTaxPoint()));
        } catch (Exception e) {
            log.info("入网组装参数为空，"+e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
            jsonParams.put("channelTopId",paramMap.get("channelTopId"));
            jsonParams.put("invoice",paramMap.get("invoice"));
            jsonParams.put("tax",paramMap.get("tax"));

            String json = JSONObject.toJSONString(jsonParams);
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
            log.info("通知失败:{}",e.getLocalizedMessage());
            throw new Exception("通知失败:"+e.getLocalizedMessage());
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
        Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            agentColinfo = new AgentColinfo();
        }
        jsonParams.put("mobile",agentBusInfo.getBusNum());
        jsonParams.put("branchid",agentBusInfo.getBusPlatform().split("_")[0]);
        jsonParams.put("termCount",agentBusInfo.getTerminalsLower());
        jsonParams = commonParam(jsonParams, agentColinfo, agent, agentBusInfo);
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
            jsonParams.put("channelTopId",data.get("channelTopId"));
            jsonParams.put("invoice",data.get("invoice"));
            jsonParams.put("tax",data.get("tax"));

            String json = JSONObject.toJSONString(jsonParams);
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
            log.info("通知失败:{}",e.getLocalizedMessage());
            throw new Exception("通知失败:"+e.getLocalizedMessage());
        }
    }

    /**
     * 升级和修改 公用参数
     * @param jsonParams
     * @param agentColinfo
     * @param agent
     * @return
     */
    private Map<String,Object> commonParam(Map<String,Object> jsonParams,AgentColinfo agentColinfo,Agent agent,AgentBusInfo agentBusInfo){
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
        if(bankLineNums!=null){
            jsonParams.put("bankid",bankLineNums.getBankid());
        }else{
            jsonParams.put("bankid","999");
        }
        jsonParams.put("cardName",agentColinfo.getCloRealname());
        jsonParams.put("channelTopId",agentBusInfo.getFinaceRemitOrgan());
        jsonParams.put("invoice",String.valueOf(agentColinfo.getCloInvoice()));
        jsonParams.put("tax",String.valueOf(agentColinfo.getCloTaxPoint()));
        return jsonParams;
    }

    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        Map<String,Object> jsonParams = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            agentColinfo = new AgentColinfo();
        }
        jsonParams.put("agencyId",agentBusInfo.getBusNum());
        jsonParams = commonParam(jsonParams, agentColinfo, agent, agentBusInfo);
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
            jsonParams.put("channelTopId",paramMap.get("channelTopId"));
            jsonParams.put("invoice",paramMap.get("invoice"));
            jsonParams.put("tax",paramMap.get("tax"));

            String json = JSONObject.toJSONString(jsonParams);
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
            log.info("通知失败:{}",e.getLocalizedMessage());
            throw new Exception("通知失败:"+e.getLocalizedMessage());
        }

    }


    /**
     * RDB升级直签校验
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult agencyLevelCheck(Map<String, Object> paramMap)throws Exception{

        AgentVo agentVo = (AgentVo) paramMap.get("agentVo");
        String rdbUpSingUrl =  AppConfig.getProperty("rdbpos_up_sing_url");

        Agent agent = agentVo.getAgent();
        AgentBusInfo agentBusInfo = agentVo.getBusInfoVoList().get(0);
        AgentColinfo agentColinfo = agentVo.getColinfoVoList().get(0);
        Map<String,Object> jsonParams = new HashMap<String, Object>();
        jsonParams = commonParam(jsonParams, agentColinfo, agent, agentBusInfo);

        AgentBusInfo agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        Map<String, Object> requMap = new HashMap<String, Object>();
        if(null!=agentParent){
            requMap.put("parentAgencyId",agentParent.getBusNum());
        }else{
            requMap.put("parentAgencyId","");
        }
        Dict dict = dictOptionsService.findDictByName(DictGroup.RDBPOS.name(), DictGroup.RDB_POS_LOWER.name(), agentBusInfo.getBusType());//直签终端下限数
        requMap.put("termCount",dict.getdItemvalue());//直签终端下限数
        requMap.put("channelTopId",agentBusInfo.getBusNum());
        requMap.put("mobile",agentBusInfo.getBusLoginNum().trim());
        requMap.put("branchid",agentBusInfo.getBusPlatform().split("_")[0]);
        requMap.put("cardno",jsonParams.get("cardno"));
        requMap.put("bankbranchid",jsonParams.get("bankbranchid"));
        requMap.put("bankbranchname",jsonParams.get("bankbranchname"));
        requMap.put("customerPid",jsonParams.get("customerPid"));
        requMap.put("address",jsonParams.get("address"));
        requMap.put("companyNo",jsonParams.get("companyNo"));
        requMap.put("userName",jsonParams.get("userName"));
        requMap.put("agencyName",jsonParams.get("agencyName"));
        requMap.put("cardidx",jsonParams.get("cardidx"));
        requMap.put("code",jsonParams.get("code"));
        requMap.put("bankid",jsonParams.get("bankid"));
        requMap.put("bankname",jsonParams.get("bankname"));
        requMap.put("cityid",jsonParams.get("cityid"));
        requMap.put("bankcity",jsonParams.get("bankcity"));
        requMap.put("cardName",jsonParams.get("cardName"));
        requMap.put("accountType",jsonParams.get("accountType"));
        requMap.put("customerType",jsonParams.get("customerType"));
        requMap.put("invoice",jsonParams.get("invoice"));
        requMap.put("tax",jsonParams.get("tax"));

        // 封装参数，发送请求，解析参数，返回结果。
        try {
            String json = JsonUtil.objectToJson(requMap);
            log.info("------------------------------------请求瑞大宝升级直签参数"+json);
            String httpResult = HttpClientUtil.doPostJsonWithException(rdbUpSingUrl, json);
            log.info("------------------------------------瑞大宝升级直签返回参数"+httpResult);
            if(StringUtils.isNotBlank(httpResult)) {
                JSONObject respJson = JSONObject.parseObject(httpResult);
                if (null != respJson.getString("code") && "0000".equals(respJson.getString("code")) && null != respJson.getBoolean("success") && respJson.getBoolean("success")) {
                    //升级直签成功
                    return AgentResult.ok(respJson.getString("msg"));
                } else if (null != respJson.getString("code") && "9999".equals(respJson.getString("code")) && null != respJson.getBoolean("success") && !respJson.getBoolean("success")) {
                    //升级直签失败，返回相应数据
                    return AgentResult.fail(respJson.getString("msg"));
                } else {
                    if(null != respJson.getString("msg")) {
                        throw new Exception(respJson.getString("msg"));
                    }
                    throw new Exception("请求瑞大宝升级直签接口成功，返回值异常！");
                }
            }else{
                throw new Exception("请求瑞大宝升级直签接口失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
