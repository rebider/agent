package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.Constants;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.util.*;
import org.apache.commons.codec.binary.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.Base64;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 17:17
 * @Param
 * @return
 **/
@Service("agentHttpSsPosServiceImpl")
public class AgentHttpSsPosServiceImpl implements AgentNetInHttpService  {

    private static Logger log = LoggerFactory.getLogger(AgentHttpPosServiceImpl.class);
    @Autowired
    private DPosRegionMapper posRegionMapper;
    @Autowired
    private PosRegionService posRegionService;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;

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

        if(StringUtils.isNotBlank(agentBusInfo.getBusRegion())) {
            String[] split = null;
            String[] busRegion = agentBusInfo.getBusRegion().split(",");
            Boolean flag = false;
            for(int i=0;i<busRegion.length;i++){
                if(busRegion[i].equals("0")){
                    flag = true;
                    break;
                }
            }
            if(flag){
                Set<String> dPosRegions = posRegionMapper.queryNationwide();
                split = dPosRegions.toArray(new String[]{});
            }else{
                Set<String> dPosRegions = posRegionService.queryCityByCode(agentBusInfo.getBusRegion());
                split = dPosRegions.toArray(new String[]{});
            }
            resultMap.put("busiAreas",split);
        }
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
        resultMap.put("debitTop",agentBusInfo.getDebitCapping());
        resultMap.put("ckDebitRate",agentBusInfo.getDebitAppearRate());
        resultMap.put("lowDebitRate",agentBusInfo.getDebitRateLower());
        resultMap.put("hasS0",agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        resultMap.put("orgName",agent.getAgName());
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        if(StringUtils.isNotBlank(platForm.getPosanameprefix())){
            resultMap.put("activityType",platForm.getPosanameprefix()); //前缀
        }
        resultMap.put("uniqueId",agentBusInfo.getId());
        resultMap.put("orgId",agentBusInfo.getBusNum());
        resultMap.put("busiType",platForm.getPosbusitype());
        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
        resultMap.put("orgType",dictByValue.getdItemname().contains(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
        resultMap.put("loginName",agentBusInfo.getBusLoginNum());
        AgentBusInfo agentParent = null;
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(agent.getId());
        resultMap.put("alwaysProfit","01");//该机构是否参与实时分润
        resultMap.put("agentId",agentBusInfo.getOrganNum());//机构ID
        resultMap.put("agentName","");//机构编号
        resultMap.put("credName",agent.getAgLegal());//法人姓名
        resultMap.put("credNo",agent.getAgLegalCernum());
        resultMap.put("bankCardName",agentColinfo.getCloRealname());//结算户名
        resultMap.put("bankCard",agentColinfo.getCloBankAccount());//结算卡号
        resultMap.put("openBank",agentColinfo.getCloBank());//收款开户总行
        resultMap.put("openBankChild",agentColinfo.getCloBankBranch());//收款开户支行
        resultMap.put("isBill",agentColinfo.getCloInvoice());//是否开具分润发票
        resultMap.put("taxPoint",agentColinfo.getCloTaxPoint());//税点
        return resultMap;
    }



    @Override
    public AgentResult httpRequestNetIn(Map<String,Object> paramMap)throws Exception{
        try {

            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG001"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("uniqueId",paramMap.get("uniqueId"));
            data.put("activityType",paramMap.get("activityType"));
            data.put("useOrgan",paramMap.get("useOrgan")); //使用范围
            data.put("orgName",paramMap.get("orgName"));
            data.put("busiAreas",paramMap.get("busiAreas"));
            data.put("hasS0",paramMap.get("hasS0"));
            data.put("busiType",paramMap.get("busiType"));
            data.put("debitTop",paramMap.get("debitTop"));
            data.put("ckDebitRate",paramMap.get("ckDebitRate"));
            data.put("lowDebitRate",paramMap.get("lowDebitRate"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("orgId")))){
                data.put("orgId",paramMap.get("orgId"));
            }
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("loginName")))){
                data.put("loginName",paramMap.get("loginName"));
            }
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("province"))))
                data.put("province",paramMap.get("province"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("city"))))
                data.put("city",paramMap.get("city"));
            if(StringUtils.isNotBlank(String.valueOf(paramMap.get("cityArea"))))
                data.put("cityArea",paramMap.get("cityArea"));
            data.put("orgType",paramMap.get("orgType"));
            if(paramMap.get("orgType").equals(OrgType.STR.getValue()))
                data.put("supDorgId",paramMap.get("supDorgId"));

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(org.apache.commons.codec.binary.Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, com.ryx.credit.util.Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, com.ryx.credit.util.Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            log.info("通知pos请求参数:{}",data);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                log.info("请求异常======" + httpResult);
                AppConfig.sendEmails("http请求异常", "入网通知POS失败报警");
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, com.ryx.credit.util.Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, com.ryx.credit.util.Constants.publicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    if (respXML.contains("data") && respXML.contains("orgId")){
                        JSONObject respXMLObj = JSONObject.parseObject(respXML);
                        JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                        if(com.ryx.credit.commons.utils.StringUtils.isBlank(String.valueOf(respXMLObj.get("data"))) || "null".equals(String.valueOf(respXMLObj.get("data")))){
                            AppConfig.sendEmails(respXML, "入网通知POS失败报警");
                        }
                        log.info(dataObj.toJSONString());
                        return AgentResult.ok(dataObj);
                    }else if (respXML.contains("respMsg")){
                        JSONObject respXMLObj = JSONObject.parseObject(respXML);
                        AppConfig.sendEmails(respXML, "入网通知POS失败报警:"+respXMLObj.get("respMsg"));
                        log.info("http请求超时返回错误:{}",respXML);
                        AgentResult ag = AgentResult.fail(respXML);
                        ag.setData(respXMLObj);
                        return ag;
                    }else{
                        AppConfig.sendEmails(respXML, "入网通知POS失败报警:"+respXML);
                        log.info("http请求超时返回错误:{}",respXML);
                        AgentResult ag = AgentResult.fail(respXML);
                        ag.setData(respXML);
                        return ag;
                    }
                }
                return new AgentResult(500,"http请求异常",respXML);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("http请求超时:"+ MailUtil.printStackTrace(e), "入网通知POS失败报警");
            log.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }

    /**
     * 升级组装参数
     * @param data
     * @return
     */
    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        Map data_res = new HashMap();
        try {
            data_res.put("agencyLevelUpdateChangeData","agencyLevelUpdateChangeData");
            Object agentBusinfo = data.get("agentBusinfoId");
            AgentBusInfo agentBusInfo = agentBusinfoService.getById(agentBusinfo+"");
            data_res.put("orgId", agentBusInfo.getBusNum());
            //普通二代 升级为 直签
            data_res.put("operType", "2");
            //查询代理商的上级代理
            AgentBusInfo parent = agentBusinfoService.getById(agentBusInfo.getBusParent());
            if(parent!=null) {
                data_res.put("supOrgId", parent.getBusNum());
            }
        } catch (Exception e) {
            e.printStackTrace();
            data_res.put("msg",e.getLocalizedMessage());
        }
        return data_res;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        if(data==null){
            log.info("POS业务升级代理接口数据为空");
        }
        try{
            JSONObject reqdata = new JSONObject();
            //请求方法
            if(data.get("orgId")==null || StringUtils.isEmpty(data.get("orgId")+""))throw new MessageException("Pos系统机构编码");
            reqdata.put("orgId",data.get("orgId"));
            if(data.get("operType")==null || StringUtils.isEmpty(data.get("operType")+""))throw new MessageException("迁移类型");
            reqdata.put("operType",data.get("operType")); //使用范围
            if(data.get("supOrgId")==null || StringUtils.isEmpty(data.get("supOrgId")+""))throw new MessageException("迁移上级");
            reqdata.put("supOrgId",data.get("supOrgId"));

            JSONObject res = request(reqdata,"ORG004");

            String respCode = res.getString("respCode");
            String respMsg =  res.getString("respMsg");
            if(StringUtils.isNotEmpty(respCode) && respCode.equals("000000")){
                JSONObject rep_data =  res.getJSONObject("data");
                return AgentResult.ok(res);
            }else{
                AppConfig.sendEmails(res.toJSONString(), "升级通知POS失败报警");
                AgentResult ag = AgentResult.fail(respMsg);
                ag.setData(res);
                return ag;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            log.info("http请求超时:{}",e.getMessage());
            AppConfig.sendEmails("http请求超时:"+ MailUtil.printStackTrace(e), "升级通知POS失败报警");
            throw new Exception("http请求超时");
        }
    }

    public JSONObject request(Map data,String url)throws Exception {
        try {

            log.info("request请求参数:{}",data);
            String cooperator = com.ryx.credit.util.Constants.cooperator;

            String charset = "UTF-8"; // 字符集
            String tranCode = url; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间



            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(org.apache.commons.codec.binary.Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, com.ryx.credit.util.Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, com.ryx.credit.util.Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);

            // 请求报文加密结束
            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);
            log.info("通知pos请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                System.out.println("请求异常======" + httpResult);
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, com.ryx.credit.util.Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, com.ryx.credit.util.Constants.publicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                    throw new MessageException("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    return respXMLObj;
                }
            }
        }catch (Exception e){
            throw e;
        }
    }
}
