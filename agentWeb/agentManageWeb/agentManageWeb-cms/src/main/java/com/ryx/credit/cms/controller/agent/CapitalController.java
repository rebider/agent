package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.CapitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.TreeMap;

/**
 * @Author Lihl
 * @Date 2019/02/11
 * @Desc 保证金
 */
@RequestMapping("capitalSummary")
@Controller
public class CapitalController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CapitalController.class);
    @Autowired
    private CapitalService capitalService;

    @RequestMapping(value = "pageCapitalSummary")
    public String pageCapitalSummary(HttpServletRequest request) {
        logger.info("加载保证金汇总页...");
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        request.setAttribute("agentId", getAgentId());
        request.setAttribute("capitalType", capitalType);
        return "agent/capital/capitalSummaryList";
    }

    /**
     * 汇总列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getCapitalSummaryList")
    @ResponseBody
    public PageInfo getCapitalSummaryList(HttpServletRequest request) {
        Page page = pageProcess(request);
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return capitalService.getCapitalSummaryList(map, pageInfo,dataRole,getUserId());
    }

    @RequestMapping(value = "pageCapital")
    public String pageCapital(HttpServletRequest request) {
        logger.info("加载缴纳款项记录页...");
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        List<Dict> payType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.PAY_TYPE.name());
        List<Dict> agStatusI = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agentId", getAgentId());
        request.setAttribute("capitalType", capitalType);
        request.setAttribute("payType", payType);
        request.setAttribute("agStatusI", agStatusI);
        return "agent/capital/capitalList";
    }

    /**
     * 缴纳款记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getCapitalList")
    @ResponseBody
    public Object getCapitalList(HttpServletRequest request, Capital capital) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            capital.setcAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = capitalService.queryCapitalList(capital, page, dataRole, getUserId());
        return pageInfo;
    }

}
