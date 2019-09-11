package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.pay.LivenessDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.agent.BusinessCAService;

@Controller
@RequestMapping("/agent")
public class BusinessCAController extends BaseController{

    @Autowired
    private BusinessCAService businessCAService;
    @Autowired
    private LivenessDetectionService livenessDetectionService;


    /**
     * 工商认证
     * @param agentBusinfoName
     * @return
     */
    @RequestMapping("/businessCA")
    @ResponseBody
    public AgentResult queryBusinessPlatform(String agentBusinfoName){
    	
    	if (StringUtils.isBlank(agentBusinfoName)) {
			throw new ProcessException("代理商名称不能为空");
		}
        AgentResult result = businessCAService.agentBusinessCA(agentBusinfoName);
        return result;
    }

    @RequestMapping("/livenessDetection")
    @ResponseBody
    public AgentResult livenessDetection(String trueName,String certNo){
        if (StringUtils.isBlank(trueName) || StringUtils.isBlank(certNo)) {
            throw new ProcessException("身份证和姓名不能为空!");
        }
        AgentResult result = livenessDetectionService.livenessDetection(trueName,certNo,String.valueOf(getUserId()));
        return result;
    }
}
