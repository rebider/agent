package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.agent.imsOrganMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;
import com.ryx.credit.pojo.admin.agent.imsOrgan;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.agent.PlatformSynService;
import com.ryx.credit.util.Constants;
import com.sun.org.apache.bcel.internal.generic.FASTORE;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by RYX on 2018/8/11.
 */
@Service("platformSynServicePos")
public class PlatformSynServicePos implements PlatformSynService {




    private Logger log = LoggerFactory.getLogger(PlatformSynServicePos.class);

    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private imsOrganMapper imsOrganMapper;
    @Override
    public Boolean isMyPlatform(String id) {
            AgentBusInfo agentBusInfo = agentBusinfoService.getById(id);
            PlatFormExample example = new PlatFormExample();
            example.or().andPlatformNumEqualTo(agentBusInfo.getBusPlatform()).andStatusEqualTo(Status.STATUS_1.status);
            List<PlatForm> list = platFormMapper.selectByExample(example);
            if(list.size()>0){
                PlatForm p = list.get(0);
                if("POS".equals(p.getPlatformType()) || "ZPOS".equals(p.getPlatformType())){
                    return true;
                }
            }
            return false;
    }

    @Override
    public Boolean isMyPlatformByPlatformCode(String platformCode) {
        PlatFormExample example = new PlatFormExample();
        example.or().andPlatformNumEqualTo(platformCode).andStatusEqualTo(Status.STATUS_1.status);
        List<PlatForm> list = platFormMapper.selectByExample(example);
        if(list.size()>0){
            PlatForm p = list.get(0);
            if("POS".equals(p.getPlatformType()) || "ZPOS".equals(p.getPlatformType())){
                return true;
            }
        }
        return false;
    }

    @Override
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


    /**
     *  use_organ=886  代理商
        org_level=2  and org_id=top_org_id  and org_type=01 普通一代
        org_level=2  and org_id=top_org_id  and org_type=02 直签一代
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

    /**
     *  4.2.1	机构上下级迁移：ORG004
     *  （限制886代理商类型）
     *    //            {
         //                "version": "1.0.0",
         //                    "msgType": "02",
         //                    "reqDate": "20170630162653",
         //                    "respDate": "20170630162714",
         //                    "respType": "S",
         //                    "respCode": "000000",
         //                    "respMsg": "处理成功",
         //                    "data": {
         //                "qrCode": "weixin://wxpay/bizpayurl?pr=rTY7WDM"
         //            }
         //            }
     * orgId
     * operType
     * supOrgId
     * @param data
     * @return
     */
    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception{
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
            AppConfig.sendEmails("http请求超时:"+e.getStackTrace(), "升级通知POS失败报警");
            throw new Exception("http请求超时");
        }
    }
}
