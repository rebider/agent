package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.util.*;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 异步通知 pos/手刷开户
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
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private ImportAgentMapper importAgentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;

    @Override
    public void asynNotifyPlatform(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                ImportAgentExample example = new ImportAgentExample();
                ImportAgentExample.Criteria criteria = example.createCriteria();
                List<String> dataType = new ArrayList<>();
                dataType.add(AgImportType.NETINAPP.getValue());
                dataType.add(AgImportType.BUSAPP.getValue());
                criteria.andDatatypeIn(dataType);
                criteria.andDealstatusEqualTo(Status.STATUS_0.status);
                List<ImportAgent> importAgents = importAgentMapper.selectByExample(example);
                for (ImportAgent importAgent : importAgents) {
                    if(importAgent.getDatatype().equals(AgImportType.NETINAPP.getValue())){
                        AgentBusInfoExample AgBusExample = new AgentBusInfoExample();
                        AgentBusInfoExample.Criteria AgBusCriteria = AgBusExample.createCriteria();
                        AgBusCriteria.andAgentIdEqualTo(importAgent.getDataid());
                        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(AgBusExample);
                        for (AgentBusInfo agentBusInfo : agentBusInfos) {
                            try {
                                agentNotifyService.notifyPlatform(agentBusInfo.getId(),importAgent.getId());
                            } catch (Exception e) {
                                log.info("异步通知pos手刷接口异常:{},busId:{},--{}",e.getMessage(),agentBusInfo.getId(),AgImportType.NETINAPP.getValue());
                                e.printStackTrace();
                            }
                        }
                    }
                    if(importAgent.getDatatype().equals(AgImportType.BUSAPP.getValue())){
                        try {
                            agentNotifyService.notifyPlatform(importAgent.getDataid(),importAgent.getId());
                        } catch (Exception e) {
                            log.info("异步通知pos手刷接口异常:{},busId:{},--{}",e.getMessage(),importAgent.getDataid(),AgImportType.BUSAPP.getValue());
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void asynNotifyPlatform(String busId){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    agentNotifyService.notifyPlatform(busId,null);
                } catch (Exception e) {
                    log.info("异步通知pos手刷接口异常:{},busId:{}",e.getMessage(),busId);
                    e.printStackTrace();
                }
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void notifyPlatform(String busId,String impId)throws Exception{
        if(StringUtils.isBlank(busId)){
            log.info("notifyPlatform业务ID为空");
            return;
        }
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        if(agentBusInfo==null){
            log.info("notifyPlatform记录不存在:{}",busId);
            if(impId!=null){
                updateImportAgent(impId,Status.STATUS_1.status,"记录不存在");
            }
            return;
        }
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        if(agentBusInfo.getBusRegion()!=null){
            List<String> regionList = getParent(agentBusInfo.getBusRegion());
            if(regionList!=null){
                if(regionList.size()==3){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                    agentNotifyVo.setCityArea(regionList.get(2));
                }else if(regionList.size()==2){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                }else if(regionList.size()==1){
                    agentNotifyVo.setProvince(regionList.get(0));
                }
            }
        }
        agentNotifyVo.setUniqueId(agent.getAgUniqNum());
        agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentNotifyVo.setOrgName(agent.getAgName());
        agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
        agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
        String agentJson = JsonUtil.objectToJson(agent);
        String busJson = JsonUtil.objectToJson(agentBusInfo);
        agentNotifyVo.setBaseMessage(agentJson);
        agentNotifyVo.setBusMessage(busJson);
        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
        agentNotifyVo.setOrgType(dictByValue.getdItemname().equals(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
        if(null!=agentParent){
            agentNotifyVo.setSupDorgId(agentParent.getBusNum());
        }
        for(int i = 1 ; i <= 5 ; i++){
            AgentPlatFormSyn record = new AgentPlatFormSyn();
            AgentResult result = null;
            try {
                record.setId(idService.genId(TabId.a_agent_platformsyn));
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
                record.setNotifyTime(new Date());
                record.setAgentId(agentBusInfo.getAgentId());
                record.setBusId(agentBusInfo.getId());
                record.setPlatformCode(agentBusInfo.getBusPlatform());
                record.setVersion(Status.STATUS_1.status);
                record.setcTime(new Date());
                record.setNotifyStatus(Status.STATUS_0.status);
                record.setNotifyCount(new BigDecimal(i));
                record.setcUser(agentBusInfo.getcUser());

                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                if(platForm.getPlatformType().equals(PlatformType.POS.getValue())){
                    result = httpRequestForPos(agentNotifyVo);
                }
                if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                    result = httpRequestForMPOS(agentNotifyVo);
                }
                record.setNotifyJson(String.valueOf(result.getData()));
            } catch (Exception e) {
                log.info("通知pos手刷http请求异常:{}",e.getMessage());
                record.setNotifyCount(new BigDecimal(i));
            }
            int czResult = 0;
            if(null!=result && result.isOK()){
                record.setSuccesTime(new Date());
                record.setNotifyStatus(Status.STATUS_1.status);
            }
            AgentPlatFormSyn querySyn = findByBusId(record.getBusId());
            if(querySyn!=null){
                czResult = agentPlatFormSynMapper.updateByBusId(record);
            }else{
                czResult = agentPlatFormSynMapper.insert(record);
            }
            if(czResult==1 && null!=result && result.isOK()){
                //更新入网状态
                Agent updateAgent = new Agent();
                updateAgent.setId(agent.getId());
                updateAgent.setVersion(agent.getVersion());
                updateAgent.setcIncomStatus(AgentInStatus.IN.status);
                Date nowDate = new Date();
                updateAgent.setcIncomTime(nowDate);
                updateAgent.setcUtime(nowDate);
                int upResult1 = agentMapper.updateByPrimaryKeySelective(updateAgent);
                //更新业务编号
                AgentBusInfo updateBusInfo = new AgentBusInfo();
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result.getData()));
                updateBusInfo.setVersion(agentBusInfo.getVersion());
                updateBusInfo.setId(agentBusInfo.getId());
                updateBusInfo.setBusNum(jsonObject.getString("orgId"));
                int upResult2 = agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo);
                if(upResult1!=1 || upResult2!=1){
                    if(i==5){
                        if(impId!=null) {
                            updateImportAgent(impId, Status.STATUS_1.status, "更新异常");
                        }
                    }
                    throw new Exception("更新入网状态/业务编号异常");
                }
                if(impId!=null){
                    updateImportAgent(impId,Status.STATUS_2.status,"处理成功");
                }
                break;
            }
        }
    }

    /**
     * 更新
     * @param impId
     * @return
     */
    private int updateImportAgent(String impId,BigDecimal dealstatus,String dealMsg){
        int i = 0;
        if(impId!=null){
            ImportAgent importAgent = importAgentMapper.selectByPrimaryKey(impId);
            ImportAgent impRecord = new ImportAgent();
            impRecord.setId(impId);
            impRecord.setVersion(importAgent.getVersion());
            impRecord.setDealstatus(dealstatus);
            impRecord.setDealmsg(dealMsg);
            impRecord.setDealTime(new Date());
            i = importAgentMapper.updateByPrimaryKeySelective(impRecord);
        }
        return i;
    }

    /**
     * 递归获取层级
     * @param busRegion
     * @return
     */
    private List<String> getParent(String busRegion){
        List<String> resultList = new ArrayList<>();
        Region region = queryParent(busRegion);
        resultList.add(0,region.getrCode());
        while (true){
            if(!region.getpCode().equals("0")){
                region = queryParent(region.getpCode());
                resultList.add(0,region.getrCode());
            }else{
                break;
            }
        }
        return resultList;
    }

    private Region queryParent(String busRegion){
        RegionExample example = new RegionExample();
        RegionExample.Criteria criteria = example.createCriteria();
        criteria.andRCodeEqualTo(busRegion);
        List<Region> regions = regionMapper.selectByExample(example);
        return regions.get(0);
    }

    private AgentResult httpRequestForPos(AgentNotifyVo agentNotifyVo)throws Exception{
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
            data.put("uniqueId",agentNotifyVo.getUniqueId());
            data.put("useOrgan",agentNotifyVo.getUseOrgan()); //使用范围
            data.put("orgName",agentNotifyVo.getOrgName());
            if(StringUtils.isNotBlank(agentNotifyVo.getProvince()))
                data.put("province",agentNotifyVo.getProvince());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                data.put("city",agentNotifyVo.getCity());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                data.put("cityArea",agentNotifyVo.getCity());
            data.put("orgType",agentNotifyVo.getOrgType());
            if(agentNotifyVo.getOrgType().equals(OrgType.STR.getValue()))
                data.put("supDorgId",agentNotifyVo.getSupDorgId());

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

            log.info("通知pos请求参数:",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            //            String httpResult = "{\"orgId\":\"1234564654654\"}";
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
                System.out.println("通知pos返回明文======" + respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    System.out.println("签名验证失败");
                } else {
                    System.out.println("签名验证成功");
                    if (respXML.contains("data") && respXML.contains("orgId")){
                        JSONObject respXMLObj = JSONObject.parseObject(respXML);
                        JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                        System.out.println(dataObj);
                        return AgentResult.ok(dataObj);
                    }else{
                        log.info("http请求超时返回错误:{}",httpResult);
                        throw new Exception("http返回有误");
                    }
                }
                return new AgentResult(500,"http请求异常","");
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw new Exception("http请求超时");
        }
    }

    private AgentResult httpRequestForMPOS(AgentNotifyVo agentNotifyVo)throws Exception{

        try {
            Map<String,String> jsonParams = new HashMap<>();
            jsonParams.put("uniqueId",agentNotifyVo.getUniqueId());
            jsonParams.put("useOrgan",agentNotifyVo.getUseOrgan()); //使用范围
            jsonParams.put("orgName",agentNotifyVo.getOrgName());
            jsonParams.put("busPlatform",agentNotifyVo.getBusPlatform());
            jsonParams.put("agHeadMobile",agentNotifyVo.getAgHeadMobile());
            jsonParams.put("baseMessage",agentNotifyVo.getBaseMessage());
            jsonParams.put("busMessage",agentNotifyVo.getBusMessage());
            if(StringUtils.isNotBlank(agentNotifyVo.getProvince()))
                jsonParams.put("province",agentNotifyVo.getProvince());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("city",agentNotifyVo.getCity());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("cityArea",agentNotifyVo.getCity());
            jsonParams.put("orgType",agentNotifyVo.getOrgType());
            if(agentNotifyVo.getOrgType().equals(OrgType.STR.getValue()))
                jsonParams.put("supDorgId",agentNotifyVo.getSupDorgId());

            log.info("通知手刷请求参数：{}",jsonParams);
//            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_mpos_notify_url"), jsonParams);
            String httpResult = "{\"orgId\":\"123456789\"}";
            System.out.println("通知手刷httpResult返回："+httpResult);
            return AgentResult.ok(httpResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AgentResult(500,"http请求异常","http请求异常");
    }

    @Override
    public AgentPlatFormSyn findByBusId(String busId){
        AgentPlatFormSynExample example = new AgentPlatFormSynExample();
        AgentPlatFormSynExample.Criteria criteria = example.createCriteria();
        criteria.andBusIdEqualTo(busId);
        List<AgentPlatFormSyn> agentPlatFormSyns = agentPlatFormSynMapper.selectByExample(example);
        if(null==agentPlatFormSyns || agentPlatFormSyns.size()!=1){
            return null;
        }
        return agentPlatFormSyns.get(0);
    }


    @Override
    public PageInfo queryList(Page page, AgentPlatFormSyn agentPlatFormSyn) {
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

}
