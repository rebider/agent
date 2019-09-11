package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.CapitalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by RYX on 2019/2/12.
 * 资金流水
 */
@RequestMapping("capitalFlow")
@Controller
public class CapitalFlowController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CapitalFlowController.class);
    @Autowired
    private CapitalFlowService capitalFlowService;

    @RequestMapping(value = "toCapitalFlowPage")
    public String toCapitalFlowPage(HttpServletRequest request) {
        logger.info("加载资金流水页...");
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        request.setAttribute("capitalType", capitalType);
        request.setAttribute("agentId", getAgentId());
        return "agent/capital/capitalFlowList";
    }

    /**
     * 资金流水列表
     * @param request
     * @param capitalFlow
     * @return
     */
    @RequestMapping(value = "getCapitalFlowList")
    @ResponseBody
    public Object getCapitalFlowList(HttpServletRequest request, CapitalFlow capitalFlow) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            capitalFlow.setAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = capitalFlowService.queryCapitalFlowList(capitalFlow, page, dataRole, getUserId());
        return pageInfo;
    }

}
