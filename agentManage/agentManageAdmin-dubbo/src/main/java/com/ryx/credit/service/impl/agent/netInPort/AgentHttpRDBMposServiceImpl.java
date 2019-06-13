package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public Map<String, Object> packageParam(Map<String, Object> param) {
        return null;
    }

    @Override
    public AgentResult httpRequestNetIn(Map<String, Object> paramMap) throws Exception {

        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("mobileNo","");
            jsonParams.put("organId","");
            jsonParams.put("branchid","");
            jsonParams.put("direct","");
            jsonParams.put("cardno","");
            jsonParams.put("termCount","");
            jsonParams.put("bankbranchid","");
            jsonParams.put("bankbranchname","");
            jsonParams.put("customerPid","");
            jsonParams.put("address","");
            jsonParams.put("companyNo","");
            jsonParams.put("userName","");
            jsonParams.put("agencyName","");
            jsonParams.put("parentAgencyId","");
            jsonParams.put("agCode","");
            jsonParams.put("directLabel","");

            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知手刷请求参数：{}",json);

            //发送请求
            String httpResult = HttpClientUtil.doPostJson("http://12.3.10.161:9093/agency/agencyNetIn", json);

            log.info("通知手刷返回参数：{}",httpResult);
            if (httpResult.contains("data") && httpResult.contains("orgId")){

                return AgentResult.ok();
            }else{
                AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                log.info("http请求超时返回错误:{}",httpResult);
                throw new Exception("http返回有误");
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知手刷请求超时："+ MailUtil.printStackTrace(e), "入网通知手刷失败报警");
            log.info("http请求超时:{}",e.getMessage());
            throw new Exception("http请求超时");
        }
    }

    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        return null;
    }


    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception {
        return null;
    }


}
