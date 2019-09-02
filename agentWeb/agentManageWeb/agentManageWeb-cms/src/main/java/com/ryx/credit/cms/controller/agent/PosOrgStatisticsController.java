package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.BusType;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.TermType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.agent.PosOrgStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/10/9.
 */
@Controller
@RequestMapping("posOrg")
public class PosOrgStatisticsController extends BaseController{

    @Autowired
    private PosOrgStatisticsService posOrgStatisticsService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private BusinessPlatformService businessPlatformService;


    @RequestMapping("statistics")
    public String statistics(@RequestParam("busNum")String busNum,@RequestParam("busPlatform")String busPlatform,
                             @RequestParam("busParent")String busParent, HttpServletRequest request){
        try {
            FastMap par = FastMap.fastMap("busNum", busNum)//业务平台号(平台机构编号)
                    .putKeyV("busParent", busParent)//所属上级代理(平台机构编号)
                    .putKeyV("busPlatform", busPlatform)//业务平台
                    .putKeyV("termType", TermType.Z.getValue());//升级查询终端--0
            AgentResult agentResult = posOrgStatisticsService.posOrgStatistics(par);
            if(!agentResult.isOK()){
                request.setAttribute("error",agentResult.getData());
                return "error/1000";
            }
            setAttribute(agentResult,request);

            return "agent/posOrgStatistics";
        }catch (MessageException e) {
            e.getStackTrace();
            request.setAttribute("error",e.getMsg());
            return "error/1000";
        } catch (Exception e) {
            e.getStackTrace();
            request.setAttribute("error",e.getMessage());
            return "error/1000";
        }
    }



    public void setAttribute(AgentResult agentResult, HttpServletRequest request){

        request.setAttribute("platformType",agentResult.getMsg());
        if(agentResult.getMsg().equals(PlatformType.MPOS.getValue())){
            request.setAttribute("statisticsList",agentResult.getData());
        }else if(agentResult.getMsg().equals(PlatformType.RDBPOS.getValue())){
            request.setAttribute("rdbStatisticsList",agentResult.getData());
        }else if(agentResult.getMsg().equals(PlatformType.RHPOS.getValue())){
            request.setAttribute("rhbStatisticsList",agentResult.getData());
        }else{
            Map<String,List<Map<String,Object>>> map = (Map<String,List<Map<String,Object>>>)agentResult.getData();
            List<Map<String, Object>> statList = map.get("organSnList");
           if (null!=statList && statList.size()>0){
               for (Map<String, Object> stringObjectMap : statList) {
                   Object supDorgIdObj = stringObjectMap.get("supDorgId");
                   if(null!=supDorgIdObj){
                       String supDorgId = String.valueOf(supDorgIdObj);
                       AgentBusInfo agentBusInfo = agentBusinfoService.selectBusInfo(supDorgId);
                       if(null!=agentBusInfo){
                           stringObjectMap.put("busType",BusType.getContentByValue(agentBusInfo.getBusType()));
                       }
                   }
               }
               request.setAttribute("statisticsMapList",map);
           }
        }

    }
}
