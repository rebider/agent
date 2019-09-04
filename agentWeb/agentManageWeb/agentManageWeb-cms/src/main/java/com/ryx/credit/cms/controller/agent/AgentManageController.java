package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cx on 2018/6/8.
 * 代理商关系控制器
 */
@RequestMapping("agentManage")
@Controller
public class AgentManageController  extends BaseController {

    @Autowired
    private AgentService agentService;

    /**
     * /agentManage/agentList
     * @param request
     * @param response
     * @param agent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"agentList"})
    public PageInfo angetList(HttpServletRequest request, HttpServletResponse response, Agent agent){
        Page page =  pageProcess(request);
        agent.setAgStatus(AgStatus.Approved.name());
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        pageInfo =  agentService.queryAgentList(pageInfo,agent);
        return pageInfo;
    }

    /**
     * agent入网信息录入
     * agentManage/agentTaxPoint
     * @return
     */
    @RequestMapping(value = {"agentTaxPoint"})
    public String enterView(HttpServletRequest request){
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
        request.setAttribute("agStatusList",agStatusList);
        return "agent/agentTaxPoint";
    }


    /**
     * 单个修改税点
     * agentManage/updateAgentTaxPoint
     * @param id
     * @param p
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"updateAgentTaxPoint"})
    public ResultVO updateAgentTaxPoint(@RequestParam("id")String id, @RequestParam("p")BigDecimal p){
        Agent agent = agentService.getAgentById(id);
        agent.setCloTaxPoint(p);
        if(1==agentService.updateAgent(agent)){
            return ResultVO.success(null);
        }
        return ResultVO.fail("修改失败请重试");
    }


}
