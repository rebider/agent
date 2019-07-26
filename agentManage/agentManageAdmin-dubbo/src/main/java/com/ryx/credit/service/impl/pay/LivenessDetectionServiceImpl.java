package com.ryx.credit.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.NotifyType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentPlatFormSynMapper;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.pay.LivenessDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 活体认证接口
 * Created by RYX on 2018/9/10.
 */
@Service("livenessDetectionService")
public class LivenessDetectionServiceImpl implements LivenessDetectionService {

    private static final Logger log = LoggerFactory.getLogger(LivenessDetectionServiceImpl.class);

    private static final String LIVENESS_DETECTION_URL = AppConfig.getProperty("liveness_detection_url");
    private static final String THREEELEMENT_DETECTION_URL = AppConfig.getProperty("threeElement_detection_url");
    private static final String LIVENESS_DETECTION_SYTERMID = AppConfig.getProperty("liveness_detection_sytermId");

    @Autowired
    private IdService idService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;


    /**
     * 二要素鉴权
     * @param trueName
     * @param certNo
     * @return
     */
    @Override
    public AgentResult livenessDetection(String trueName,String certNo,String userId){
        AgentResult result = new AgentResult(500,"参数错误","");
        if(StringUtils.isBlank(trueName)){
            return result;
        }
        if(StringUtils.isBlank(certNo)){
            return result;
        }
        if(StringUtils.isBlank(userId)){
            return result;
        }
        AgentPlatFormSyn record = new AgentPlatFormSyn();
        record.setId(idService.genId(TabId.a_agent_platformsyn));
        record.setNotifyTime(new Date());
        record.setVersion(Status.STATUS_1.status);
        record.setcTime(new Date());
        record.setNotifyStatus(Status.STATUS_0.status);
        record.setNotifyCount(new BigDecimal(1));
        record.setNotifyType(NotifyType.IdentityAuth.getValue());
        try {
            record.setcUser(userId);
            record.setSendJson("trueName:"+trueName+",certNo:"+certNo);
            Map<String, Object> resultMap = requestForLiveness(record.getId(),trueName, certNo);
            if(null!=resultMap){
                record.setNotifyJson(resultMap.toString());
                result.setMsg(String.valueOf(resultMap.get("ExecMsg")));
                String validateStatus = String.valueOf(resultMap.get("ValidateStatus"));
                if(validateStatus.equals("00")){
                    record.setNotifyStatus(Status.STATUS_1.status);
                    record.setSuccesTime(new Date());
                    result.setStatus(200);
                }else{
                    result.setStatus(400);
                }
            }else{
                record.setNotifyJson("resultMap is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("身份认证异常:{}",e.getMessage());
            record.setNotifyJson(e.getMessage());
        }
        agentPlatFormSynMapper.insertSelective(record);
        return result;
    }


    @Override
    public AgentResult threeElementsCertificationDetection(String trueName, String certNo, String userId,String bankNo) {
        AgentResult result = new AgentResult(500,"参数错误","");
        if(StringUtils.isBlank(trueName)){
            return result;
        }
        if(StringUtils.isBlank(certNo)){
            return result;
        }
        if(StringUtils.isBlank(userId)){
            return result;
        }
        if(StringUtils.isBlank(bankNo)){
            return result;
        }
        AgentPlatFormSyn record = new AgentPlatFormSyn();
        record.setId(idService.genId(TabId.a_agent_platformsyn));
        record.setNotifyTime(new Date());
        record.setVersion(Status.STATUS_1.status);
        record.setcTime(new Date());
        record.setNotifyStatus(Status.STATUS_0.status);
        record.setNotifyCount(new BigDecimal(1));
        record.setNotifyType(NotifyType.ThreeElements.getValue());
        record.setcUser(userId);
        record.setSendJson("trueName:"+trueName+",certNo:"+certNo+",bankNo:"+bankNo);
        try {
            Map<String, Object> resultMap = threeElementsCertification(record.getId(),trueName, certNo,bankNo);
            record.setNotifyJson(resultMap.toString());
            if(null!=resultMap ){
                if("00".equals(resultMap.get("ResponseCode"))) {
                    result.setMsg(String.valueOf(resultMap.get("ResultMsg")));
                    String validateStatus = String.valueOf(resultMap.get("Result"));
                    if (validateStatus.equals("00")) {
                        record.setNotifyStatus(Status.STATUS_1.status);
                        record.setSuccesTime(new Date());
                        result.setMsg("认证成功");
                        result.setStatus(200);
                    } else {
                        record.setNotifyStatus(Status.STATUS_2.status);
                        result.setMsg(resultMap.get("ResultMsg")+"");
                        result.setStatus(400);
                    }
                }else{
                    record.setNotifyStatus(Status.STATUS_2.status);
                    result.setMsg("银行卡三要素认证失败");
                    result.setStatus(400);
                }
            }else{
                record.setNotifyStatus(Status.STATUS_2.status);
                result.setMsg("银行卡三要素认证失败");
                record.setNotifyJson("resultMap is null");
            }
        } catch (Exception e) {
            log.info("身份认证异常:{}",e.getMessage());
            e.printStackTrace();
            record.setNotifyJson(e.getMessage());
            record.setNotifyStatus(Status.STATUS_2.status);
            result.setMsg("银行卡三要素认证失败");
        }finally {
            agentPlatFormSynMapper.insertSelective(record);
        }
        return result;
    }

    /**
     * 身份认证
     * @param serialNo
     * @param trueName
     * @param certNo
     * @return
     * @throws Exception
     */
    private Map<String, Object> requestForLiveness(String serialNo,String trueName,String certNo)throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("Transcode", "000020");
        map.put("SytermId", LIVENESS_DETECTION_SYTERMID);
        map.put("SerialNo", serialNo);
        Date now = DateKit.getNow(true);
        map.put("TransDate", DateKit.getCompactDateString(now));
        map.put("TransTime", DateKit.getCompactTimeString(now));
        map.put("TrueName", trueName);
        map.put("CertType", "ZR01");
        map.put("CertNo", certNo);
        map.put("ReturnPic", "1");
        String paramsJson = JSONObject.toJSONString(map);
        log.info("--------身份证认证,url:{}请求参数:{}------",LIVENESS_DETECTION_URL,paramsJson);
        String result = "";
        if(EnvironmentUtil.isProduction()){
            result = HttpPostUtil.postForJSON(LIVENESS_DETECTION_URL, paramsJson);
        }else{
            result = "{'ExecMsg':'认证成功','ValidateStatus':'00'}";
        }
        log.info("--------身份证认证返回参数:{}------", result);
        Map resultMap = JSONObject.parseObject(result);
        return resultMap;
    }


    /**
     * 三要素认证
     * @param serialNo
     * @param trueName
     * @param certNo
     * @return
     * @throws Exception
     */
    private Map<String, Object> threeElementsCertification(String serialNo,String trueName,String certNo,String bankNo)throws Exception{
        Map<String, Object> map = new HashMap<>();
        Date now = DateKit.getNow(true);
        map.put("Transcode", "000010");
        map.put("Serial", serialNo);
        map.put("SytermId", LIVENESS_DETECTION_SYTERMID);
        map.put("BankNo", bankNo);
        map.put("UserName", trueName);
        map.put("IdentityType", "ZR01");
        map.put("IdentityId", certNo);
        map.put("StartDate", DateKit.getCompactDateString(now));
        map.put("StartTime", DateKit.getCompactTimeString(now));
        map.put("isDirect", "0");

        String paramsJson = JSONObject.toJSONString(map);
        log.info("--------三要素认证,url:{}请求参数:{}------",THREEELEMENT_DETECTION_URL,paramsJson);
        String result = "";
        if(EnvironmentUtil.isProduction()){
            result = HttpPostUtil.postForJSON(THREEELEMENT_DETECTION_URL, paramsJson);
        }else{
            result = JSONObject.toJSONString(FastMap.fastMap("ResultMsg","认证成功").putKeyV("ResponseCode","00").putKeyV("Result","00"));
        }
        log.info("--------三要素认证返回参数:{}------", result);
        Map resultMap = JSONObject.parseObject(result);
        return resultMap;
    }

}
