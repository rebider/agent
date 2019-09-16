package com.ryx.credit.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.json.JSONUtil;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RYX on 2018/9/17.
 */
@RequestMapping("agentBusInfo")
@Controller
public class AgentBusInfoController {

    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;


    @RequestMapping(value = "updatePhone")
    @ResponseBody
    public AgentResult updatePhone(@RequestBody String param){
        JSONObject jsonObject = JSONObject.parseObject(param);
        String oldBusLoginNum = String.valueOf(jsonObject.get("oldBusLoginNum"));
        String busLoginNum = String.valueOf(jsonObject.get("busLoginNum"));
        if(StringUtils.isBlank(oldBusLoginNum) || oldBusLoginNum.equals("null")){
            return AgentResult.fail("旧手机号不能为空");
        }
        if(StringUtils.isBlank(busLoginNum) || busLoginNum.equals("null")){
            return AgentResult.fail("手机号不能为空");
        }
        AgentResult agentResult = AgentResult.ok();
        try {
            agentBusinfoService.updateBusLoginNum(oldBusLoginNum,busLoginNum);
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail("更新失败");
        }
        return agentResult;
    }


    @RequestMapping(value = "queryAgentId")
    @ResponseBody
    public AgentResult queryAgentId(@RequestBody String param){
        JSONObject jsonObject = JSONObject.parseObject(param);
        String busNum = String.valueOf(jsonObject.get("busNum"));
        if(StringUtils.isBlank(busNum) || busNum.equals("null")){
            return AgentResult.fail("业务编号不能为空");
        }
        AgentResult agentResult = AgentResult.ok();
        try {
            AgentBusInfo agentBusInfo = agentBusinfoService.queryAgentBusInfo(busNum);
            agentResult.setData(agentBusInfo.getAgentId());
        } catch (MessageException e) {
            e.getStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.getStackTrace();
            return AgentResult.fail("查询失败");
        }
        return agentResult;
    }


    @RequestMapping(value = "queryColInfo")
    @ResponseBody
    public AgentResult queryColInfo(@RequestBody String param){
        JSONObject jsonObject = JSONObject.parseObject(param);
        String busNum = String.valueOf(jsonObject.get("busNum"));
        if(StringUtils.isBlank(busNum) || busNum.equals("null")){
            return AgentResult.fail("业务编号不能为空");
        }
        AgentResult agentResult = AgentResult.ok();
        try {
            Map<String, String> busInfoMap = agentBusinfoService.queryBusInfoByBrandNum(busNum);
            AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(busInfoMap.get("agentId"));
            if(agentColinfo==null){
                return AgentResult.fail("收款信息不存在");
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("cloTaxPoint",agentColinfo.getCloTaxPoint());
            resultMap.put("cloInvoice",agentColinfo.getCloInvoice());
            busInfoMap.remove("agentId");
            resultMap.put("busInfo",busInfoMap);
            agentResult.setMapData(resultMap);
        } catch (MessageException e) {
            e.getStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.getStackTrace();
            return AgentResult.fail("查询失败");
        }
        return agentResult;
    }

}
