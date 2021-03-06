package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.dao.CBranchInnerMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.dao.order.OrgPlatformMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OrgPlatform;
import com.ryx.credit.pojo.admin.order.OrgPlatformExample;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.util.Constants;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 17:17
 * @Param
 * @return
 **/
@Service("agentHttpPosServiceImpl")
public class AgentHttpPosServiceImpl implements AgentNetInHttpService {

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
    @Autowired
    private CBranchInnerMapper branchInnerMapper;
    @Autowired
    private OrgPlatformMapper orgPlatformMapper;

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
        resultMap.put("lowCreditRate",agentBusInfo.getCreditRateFloor());
        resultMap.put("ceilingCreditRate",agentBusInfo.getCreditRateCeiling());
        resultMap.put("topDebitRate",agentBusInfo.getDebitRateCapping());
        resultMap.put("debitLow",agentBusInfo.getDebitCappingLower());

        resultMap.put("hasS0",agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        resultMap.put("orgName",agent.getAgName());
        resultMap.put("useOrgan",agentBusInfo.getBusUseOrgan()); //使用范围
        if(StringUtils.isNotBlank(platForm.getPosanameprefix())){
            resultMap.put("activityType",platForm.getPosanameprefix()); //前缀
        }
        resultMap.put("uniqueId",agentBusInfo.getId());
        resultMap.put("orgId",agentBusInfo.getBusNum());
        resultMap.put("busiType",platForm.getPosbusitype());
        resultMap.put("orgType",OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        resultMap.put("loginName",agentBusInfo.getBusLoginNum());
        AgentBusInfo agentParent = null;
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        if(null!=agentParent){
            resultMap.put("supDorgId",agentParent.getBusNum());
        }
        //收款账户新
        AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
        if(agentColinfo==null){
            log.info("收款账户为空:{},{}",agent.getId(), agentBusInfo.getId());
            agentColinfo = new AgentColinfo();
        }
        //机构信息
        Organization organization = organizationMapper.selectByPrimaryKey(agentBusInfo.getOrganNum());
        if(organization==null){
            log.info("机构信息为空:{},{}",agent.getId(), agentBusInfo.getId());
            organization = new Organization();
        }
        //组装参数
        //组装参数
        resultMap.put("brand",platForm.getBusplatform()); //品牌编号
        resultMap.put("brandName",platForm.getPlatformName());//平台名称
        resultMap.put("alwaysProfit","00");//该机构是否参与实时分润
        resultMap.put("agentId",organization.getOrgId());//机构ID
        resultMap.put("finaceRemitOrgan",agentBusInfo.getFinaceRemitOrgan());//财务出款机构
        resultMap.put("agentName",organization.getOrgName());//机构编号
        resultMap.put("credName",agent.getAgLegal());//法人姓名
        resultMap.put("credNo",agent.getAgLegalCernum());//法人身份证
        resultMap.put("credPhone",agent.getAgLegalMobile());//法人手机号

        resultMap.put("bankCardType",agentColinfo.getCloType());//1对公2对私
        resultMap.put("bankCardName",agentColinfo.getCloRealname());//结算户名
        resultMap.put("bankCardCredNo",agentColinfo.getAgLegalCernum());//结算卡户主身份证
        resultMap.put("bankCard",agentColinfo.getCloBankAccount());//结算卡号
        resultMap.put("openBank",agentColinfo.getCloBankCode());//收款开户总行 银行代码
        resultMap.put("openBankChild",agentColinfo.getBranchLineNum());//收款开户支行 联号
        resultMap.put("openBankName",agentColinfo.getCloBank());//收款开户总行
        resultMap.put("openBankChildName",agentColinfo.getCloBankBranch());//收款开户支行

        resultMap.put("isBill",agentColinfo.getCloInvoice());//是否开具分润发票
        resultMap.put("taxPoint",agentColinfo.getCloTaxPoint());//税点
        resultMap.put("agCode",agentBusInfo.getAgentId());//AG码
        //激活返现的业务id和编码
        if (StringUtils.isNotBlank(agentBusInfo.getBusActivationParent())){
            AgentBusInfo actBudinfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusActivationParent());
            resultMap.put("actBusId",agentBusInfo.getBusActivationParent());
            resultMap.put("actBusNum",actBudinfo.getBusNum());
            resultMap.put("reOrgId",actBudinfo.getBusNum());
        }
        //查询相关联的大区经理账号
        List accList = branchInnerMapper.selectInnerLogin(FastMap.fastMap("status", Status.STATUS_1.status).putKeyV("busId", agentBusInfo.getId()));
        List delList = branchInnerMapper.selectInnerLogin(FastMap.fastMap("status", Status.STATUS_2.status).putKeyV("busId", agentBusInfo.getId()));
        resultMap.put("managerAccount", String.join(",", accList));
        resultMap.put("delManagerAccount", String.join(",", delList));

        OrgPlatformExample orgPlatformExample = new OrgPlatformExample();
        OrgPlatformExample.Criteria criteria = orgPlatformExample.createCriteria();
        criteria.andOrgIdEqualTo(organization.getOrgId());
        criteria.andPlatNumEqualTo(agentBusInfo.getBusPlatform());
        List<OrgPlatform> orgPlatforms = orgPlatformMapper.selectByExample(orgPlatformExample);
        OrgPlatform orgPlatform = new OrgPlatform();
        if(orgPlatforms.size()!=0){
            orgPlatform = orgPlatforms.get(0);
        }
        if(organization.getAgentId().equals(agent.getId()) && StringUtils.isBlank(orgPlatform.getPlatCode())){
            resultMap.put("IsOper","1");
        }else{
            resultMap.put("IsOper","0");
            resultMap.put("operOrgId",orgPlatform.getPlatCode());
        }


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
            data.put("lowCreditRate",paramMap.get("lowCreditRate"));
            data.put("ceilingCreditRate",paramMap.get("ceilingCreditRate"));
            data.put("topDebitRate",paramMap.get("topDebitRate"));
            data.put("debitLow",paramMap.get("debitLow"));

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

            //组装参数
            data.put("brand",paramMap.get("brand")); //品牌编号
            data.put("brandName",paramMap.get("brandName"));//平台名称
            data.put("alwaysProfit",paramMap.get("alwaysProfit"));//该机构是否参与实时分润
            data.put("finaceRemitOrgan",paramMap.get("finaceRemitOrgan"));//财务出款机构
            data.put("agentId",paramMap.get("agentId"));//机构ID
            data.put("agentName",paramMap.get("agentName"));//机构编号
            data.put("credName",paramMap.get("credName"));//法人姓名
            data.put("credNo",paramMap.get("credNo"));//法人身份证
            data.put("credPhone",paramMap.get("credPhone"));//法人手机号

            data.put("bankCardType",paramMap.get("bankCardType"));//1对公2对私
            data.put("bankCardName",paramMap.get("bankCardName"));//结算户名
            data.put("bankCardCredNo",paramMap.get("bankCardCredNo"));//收款开户支行 户主姓名
            data.put("bankCard",paramMap.get("bankCard"));//结算卡号
            data.put("openBank",paramMap.get("openBank"));//收款开户总行 行号
            data.put("openBankChild",paramMap.get("openBankChild"));//收款开户支行 联号
            data.put("openBankName",paramMap.get("openBankName"));//收款开户总行 名称
            data.put("openBankChildName",paramMap.get("openBankChildName"));//收款开户支行 名称

            data.put("isBill",paramMap.get("isBill"));//是否开具分润发票
            data.put("taxPoint",paramMap.get("taxPoint"));//税点
            data.put("agCode",paramMap.get("agCode"));//AG码
            data.put("actBusId",paramMap.get("actBusId"));//激活返现的业务id
            data.put("actBusNum",paramMap.get("actBusNum"));//激活返现的编码
            data.put("reOrgId",paramMap.get("reOrgId"));//返现机构编码
            //关联账号增加关系
            if (null != paramMap.get("managerAccount"))
                data.put("managerAccount",paramMap.get("managerAccount"));
            if (null != paramMap.get("delManagerAccount"))
                data.put("delManagerAccount",paramMap.get("delManagerAccount"));
            data.put("IsOper",paramMap.get("IsOper"));
            data.put("operOrgId",paramMap.get("operOrgId"));

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
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
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    if (respXML.contains("data") && respXML.contains("orgId")){
                        JSONObject respXMLObj = JSONObject.parseObject(respXML);
                        if(com.ryx.credit.commons.utils.StringUtils.isBlank(String.valueOf(respXMLObj.get("data"))) || "null".equals(String.valueOf(respXMLObj.get("data")))){
                            AppConfig.sendEmails(respXML, "入网通知POS失败报警");
                            AgentResult ag = AgentResult.fail(respXML);
                            ag.setData(respXMLObj);
                            return ag;
                        }
                        return AgentResult.ok(respXMLObj);
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
            AppConfig.sendEmails("通知失败:"+ MailUtil.printStackTrace(e), "入网通知POS失败报警");
            log.info("通知失败:{}",e.getMessage());
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
            log.info("通知失败:{}",e.getMessage());
            AppConfig.sendEmails("通知失败:"+ MailUtil.printStackTrace(e), "升级通知POS失败报警");
            throw new Exception("通知失败");
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
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);

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
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
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

    @Override
    public Map<String, Object> packageParamUpdate(Map<String, Object> param) {
        return packageParam(param);
    }

    @Override
    public AgentResult httpRequestNetInUpdate(Map<String, Object> paramMap) throws Exception {
        return httpRequestNetIn(paramMap);
    }

    /**
     * POS升级直签校验
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult agencyLevelCheck(Map<String, Object> paramMap)throws Exception{
        try {
            AgentVo agentVo = (AgentVo) paramMap.get("agentVo");
            if (null == agentVo || null == agentVo.getBusInfoVoList()) throw new Exception("信息不完整，请补全业务信息，如有疑问请联系管理员");
            AgentBusInfo agentBusInfo = agentVo.getBusInfoVoList().get(0);

            //查询上级编码，和活动首字母
            Map<String, Object> upSingCheckMap = agentBusInfoMapper.selectByIdForPosUpSingCheck(agentBusInfo.getBusParent());
            if (null == upSingCheckMap.get("BUSNUM")) throw new Exception("上级代理商信息不存在，请联系管理员！！！");

            //查询是否升级完成，升级完成之后不允许再次升级
            /*if (null == agentBusInfo.getBusNum()) throw new Exception("请填写业务平台编号！！！");
            if (null == agentBusInfo.getBusPlatform()) throw new Exception("业务平台数据异常！！！");
            int i = agentBusInfoMapper.selectByBusNum(FastMap.fastMap("busNum",agentBusInfo.getBusNum()).putKeyV("busPlatform", agentBusInfo.getBusPlatform()));
            if (i > 0 || agentBusInfo.getBusNum().equals(agentBusInfo.getBusParent())) throw new Exception("您已经升级成功，请勿重复提交！");*/

            if (null == agentBusInfo.getBusLoginNum() || "".equals(agentBusInfo.getBusLoginNum()) || "null".equals(agentBusInfo.getBusLoginNum())) throw new Exception("代理商升级，需填写对应平台登录账号！");

            String cooperator = Constants.cooperator;
            String tranCode = "ORG018"; // 交易码
            String charset = "UTF-8"; // 字符集
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("version", "1.0.0");
            jsonParams.put("data", FastMap.
                    fastMap("agentOrgId",upSingCheckMap.get("BUSNUM")).
                    putKeyV("orgId",agentBusInfo.getBusNum()).
                    putKeyV("organInitials",upSingCheckMap.get("POSANAMEPREFIX")).
                    putKeyV("loginName", agentBusInfo.getBusLoginNum()));
            String plainXML = jsonParams.toString();
            log.info("POS预升级请求参数:{}", plainXML);
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);

            // 请求报文加密结束
            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                log.info("POS预升级校验失败:{}", httpResult);
                throw new Exception("pos升级直签校验失败");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                    throw new MessageException("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    log.info("POS预升级返回参数："+respXML);
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    JSONObject retObj = JSONObject.parseObject(respXMLObj.getString("data"));
                    if (null != retObj.get("result_code") && null != retObj.get("result_msg") && "000000".equals(retObj.get("result_code"))){
                        return AgentResult.ok();
                    } else {
                        return AgentResult.fail(null != retObj.get("result_msg")?(String)retObj.get("result_msg"):"POS升级直签校验返回值异常，请联系管理员！！！");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}

