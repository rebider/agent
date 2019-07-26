package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
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
@Service("agentHttpRHBPosServiceImpl")
public class AgentHttpRHBPosServiceImpl implements AgentNetInHttpService {

    private static Logger log = LoggerFactory.getLogger(AgentHttpRHBPosServiceImpl.class);

    private static final String rhbReqUrl = AppConfig.getProperty("rhb_req_url");
    private static final String rhb3desKey = AppConfig.getProperty("rhb_3des_Key");
    private static final String rhb3desIv = AppConfig.getProperty("rhb_3des_iv");

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentMapper agentMapper;

    /**
     * 入网组装参数
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {

        Map<String, Object> resultMap = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        PlatForm platForm = (PlatForm)param.get("platForm");

        resultMap.put("application","AgencyNetwork");
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        resultMap.put("agCode",agent.getId());
        resultMap.put("paymentAgency",agentBusInfo.getFinaceRemitOrgan());//出款机构
        resultMap.put("orgId",agentBusInfo.getId());
        resultMap.put("orgName",agent.getAgName());
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
        resultMap.put("orgType",OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        resultMap.put("alwaysProfit","00");//该机构是否参与实时分润
        resultMap.put("brandName",platForm.getPlatformName());//平台名称
        //收款账户新
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            log.info("收款账户为空:{},{}",agent.getId(), agentBusInfo.getId());
            agentColinfo = new AgentColinfo();
        }
        //00:对公账户，01：对私账户
        resultMap.put("acctType",agentColinfo.getCloType()==null?"":agentColinfo.getCloType().compareTo(BigDecimal.ONE)==0?"00":"01");
        resultMap.put("credName",agent.getAgLegal());//法人姓名
        resultMap.put("credNo",agent.getAgLegalCernum());//法人身份证
        resultMap.put("credPhone",agent.getAgLegalMobile());//法人手机号
        resultMap.put("dutyName",agent.getAgHead());//负责人
        resultMap.put("dutyPhone",agent.getAgHeadMobile());//负责人手机号
        resultMap.put("businesslicense",agent.getAgBusLic());//营业执照
        resultMap.put("companyNature",agent.getAgNature());//公司性质
        resultMap.put("bankCardName",agentColinfo.getCloRealname());//结算户名
        resultMap.put("bankCard",agentColinfo.getCloBankAccount());//结算卡号
        resultMap.put("bankCardCredNo",agentColinfo.getAgLegalCernum());//结算卡户主身份证
        resultMap.put("openBank",agentColinfo.getCloBankCode());//收款开户总行 银行代码
        resultMap.put("openBankChild",agentColinfo.getBranchLineNum());//收款开户支行 联号
        resultMap.put("openBankChildName",agentColinfo.getCloBankBranch());//收款开户支行
        resultMap.put("isBill",agentColinfo.getCloInvoice());//是否开具分润发票
        resultMap.put("taxPoint",agentColinfo.getCloTaxPoint());//税点
        resultMap.put("agencyMobileNo",agentBusInfo.getBusLoginNum());//代理商登录管理app手机号
        return resultMap;
    }



    @Override
    public AgentResult httpRequestNetIn(Map<String,Object> paramMap)throws Exception{
        try {

            String json = JsonUtil.objectToJson(paramMap);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞花宝入网请求参数：{}",json);
            log.info("通知瑞花宝入网请求参数加密：{}",reqParamEncrypt);
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞大宝入网返回参数：{}",reqParamDecrypt);
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网通知瑞花宝失败报警");
                throw new Exception(reqParamDecrypt);
            }
        }catch (Exception e){
            AppConfig.sendEmails("通知瑞花宝请求超时："+ MailUtil.printStackTrace(e), "入网通知瑞花宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }



    /**
     * 升级组装参数
     * @param param
     * @return
     */
    @Override
    public Map agencyLevelUpdateChangeData(Map param) {
        Map<String, Object> resultMap = new HashMap<>();
        PlatForm platForm = (PlatForm)param.get("platForm");
        String busId = String.valueOf(param.get("agentBusinfoId"));
        AgentBusInfo agentBusInfo = agentBusinfoService.getById(busId);
        Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());

        resultMap.put("application","AgencyPromotion");
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        resultMap.put("agCode",agent.getId());
        resultMap.put("paymentAgency",agentBusInfo.getFinaceRemitOrgan());//出款机构
        resultMap.put("orgId",agentBusInfo.getId());
        resultMap.put("orgName",agent.getAgName());
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
        resultMap.put("orgType",OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        resultMap.put("alwaysProfit","00");//该机构是否参与实时分润
        resultMap.put("brandName",platForm.getPlatformName());//平台名称
        //收款账户新
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            log.info("收款账户为空:{},{}",agent.getId(), agentBusInfo.getId());
            agentColinfo = new AgentColinfo();
        }
        resultMap.put("agentId",agentBusInfo.getBusNum());//业务平台编号RHB

        //00:对公账户，01：对私账户
        resultMap.put("acctType",agentColinfo.getCloType().compareTo(BigDecimal.ONE)==0?"00":"01");
        resultMap.put("credName",agent.getAgLegal());//法人姓名
        resultMap.put("credNo",agent.getAgLegalCernum());//法人身份证
        resultMap.put("credPhone",agent.getAgLegalMobile());//法人手机号
        resultMap.put("dutyName",agent.getAgHead());//负责人
        resultMap.put("dutyPhone",agent.getAgHeadMobile());//负责人手机号
        resultMap.put("businesslicense",agent.getAgBusLic());//营业执照
        resultMap.put("companyNature",agent.getAgNature());//公司性质
        resultMap.put("bankCardName",agentColinfo.getCloRealname());//结算户名
        resultMap.put("bankCard",agentColinfo.getCloBankAccount());//结算卡号
        resultMap.put("bankCardCredNo",agentColinfo.getAgLegalCernum());//结算卡户主身份证
        resultMap.put("openBank",agentColinfo.getCloBankCode());//收款开户总行 银行代码
        resultMap.put("openBankChild",agentColinfo.getBranchLineNum());//收款开户支行 联号
        resultMap.put("openBankChildName",agentColinfo.getCloBankBranch());//收款开户支行
        resultMap.put("isBill",agentColinfo.getCloInvoice());//是否开具分润发票
        resultMap.put("taxPoint",agentColinfo.getCloTaxPoint());//税点
        resultMap.put("agencyMobileNo",agentBusInfo.getBusLoginNum());//代理商登录管理app手机号
        return resultMap;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        try {
            String json = JsonUtil.objectToJson(data);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞花宝入网升级请求参数：{}",json);
            log.info("通知瑞花宝入网升级请求参数加密：{}",reqParamEncrypt);
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞大宝入网升级返回参数：{}",reqParamDecrypt);
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网升级通知瑞花宝失败报警");
                throw new Exception(reqParamDecrypt);
            }
        }catch (Exception e){
            AppConfig.sendEmails("通知瑞花宝请求超时："+ MailUtil.printStackTrace(e), "入网升级通知瑞花宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }


    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        AgentBusInfo agentBusInfo = (AgentBusInfo)param.get("agentBusInfo");
        Agent agent = (Agent)param.get("agent");
        PlatForm platForm = (PlatForm)param.get("platForm");

        resultMap.put("application","AgencyUpdateInfo");
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        resultMap.put("paymentAgency",agentBusInfo.getFinaceRemitOrgan());//出款机构
        resultMap.put("orgId",agentBusInfo.getId());
        resultMap.put("orgName",agent.getAgName());
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
        resultMap.put("orgType",OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        resultMap.put("alwaysProfit","00");//该机构是否参与实时分润
        resultMap.put("brandName",platForm.getPlatformName());//平台名称
        //收款账户新
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            log.info("收款账户为空:{},{}",agent.getId(), agentBusInfo.getId());
            agentColinfo = new AgentColinfo();
        }
        resultMap.put("agentId",agentBusInfo.getBusNum());

        //00:对公账户，01：对私账户
        resultMap.put("acctType",agentColinfo.getCloType().compareTo(BigDecimal.ONE)==0?"00":"01");
        resultMap.put("credName",agent.getAgLegal());//法人姓名
        resultMap.put("credNo",agent.getAgLegalCernum());//法人身份证
        resultMap.put("credPhone",agent.getAgLegalMobile());//法人手机号
        resultMap.put("dutyName",agent.getAgHead());//负责人
        resultMap.put("dutyPhone",agent.getAgHeadMobile());//负责人手机号
        resultMap.put("businesslicense",agent.getAgBusLic());//营业执照
        resultMap.put("companyNature",agent.getAgNature());//公司性质
        resultMap.put("bankCardName",agentColinfo.getCloRealname());//结算户名
        resultMap.put("bankCard",agentColinfo.getCloBankAccount());//结算卡号
        resultMap.put("bankCardCredNo",agentColinfo.getAgLegalCernum());//结算卡户主身份证
        resultMap.put("openBank",agentColinfo.getCloBankCode());//收款开户总行 银行代码
        resultMap.put("openBankChild",agentColinfo.getBranchLineNum());//收款开户支行 联号
        resultMap.put("openBankChildName",agentColinfo.getCloBankBranch());//收款开户支行
        resultMap.put("isBill",agentColinfo.getCloInvoice());//是否开具分润发票
        resultMap.put("taxPoint",agentColinfo.getCloTaxPoint());//税点
        resultMap.put("agencyMobileNo",agentBusInfo.getBusLoginNum());//代理商登录管理app手机号
        return resultMap;
    }

    @Override
    public AgentResult httpRequestNetInUpdate(Map<String, Object> paramMap) throws Exception {

        try {
            String json = JsonUtil.objectToJson(paramMap);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞花宝入网修改请求参数：{}",json);
            log.info("通知瑞花宝入网修改请求参数加密：{}",reqParamEncrypt);
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            log.info("通知瑞大宝入网修改返回参数：{}",reqParamDecrypt);
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网修改通知瑞花宝失败报警");
                throw new Exception(reqParamDecrypt);
            }
        }catch (Exception e){
            AppConfig.sendEmails("通知瑞花宝请求超时："+ MailUtil.printStackTrace(e), "入网修改通知瑞花宝失败报警");
            log.info("http请求超时:{}",e.getLocalizedMessage());
            throw new Exception("http请求超时:"+e.getLocalizedMessage());
        }
    }


}

