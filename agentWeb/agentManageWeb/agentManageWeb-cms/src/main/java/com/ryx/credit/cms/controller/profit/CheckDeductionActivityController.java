package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 考核分期审批处理
 *
 * @author chenliang
 * @create 2019/1/23
 *
 */
@RequestMapping("/checkDeductionActivity/")
@Controller
public class CheckDeductionActivityController extends BaseController {

    private static  final Logger LOG = Logger.getLogger(OtherDeductionActivityController.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private ActHiVarinstService actHiVarinstService;

    @Autowired
    private StagingService stagingServiceImpl;

    @Autowired
    private IUserService userService;

    @Autowired
    private TaskApprovalService taskApprovalService;

    /**
     * 进入待审批页面
     * @param request
     * @param response
     * @param model
     * @return 加载分期审批页面
     */
    @RequestMapping("checkToTaskApproval")
    public String toTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String stagId = request.getParameter("busId");
        optionsData(request);

        //获取分期信息
        ProfitStaging staging = stagingServiceImpl.getStagingById(stagId);
        if (staging != null) {
            ProfitDeduction deduction = profitDeductionServiceImpl.getProfitDeductionById(staging.getSourceId());
            model.addAttribute("agentName", deduction.getAgentName());
            model.addAttribute("agentId", deduction.getAgentId());
            model.addAttribute("staging", staging);
        }
        //审批参数
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
        List<Dict> TOOLS_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_MARKET.name());
        List<Dict> TOOLS_APR_BOSS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BOSS.name());
        List<Dict> TOOLS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_EXPAND.name());
        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        request.setAttribute("tools_apr_market", TOOLS_APR_MARKET);
        request.setAttribute("tools_apr_boss", TOOLS_APR_BOSS);
        request.setAttribute("tools_apr_expand", TOOLS_APR_EXPAND);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        return "profit/otherDeductionActivity/taskApproval";
    }

    /**
     * 查看审批页面
     * @param request
     * @param response
     * @param model
     * @return 查看审批页面
     */
    @RequestMapping("gotoTaskApproval")
    public String gotoTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String sourceId = request.getParameter("sourceId");


        //获取分期信息
        ProfitStaging staging = stagingServiceImpl.getStagingBySourceId(sourceId);
        if (staging != null) {
            model.addAttribute("staging", staging);
            ProfitDeduction deduction = profitDeductionServiceImpl.getProfitDeductionById(staging.getSourceId());
            if (deduction !=null) {
                model.addAttribute("agentName", deduction.getAgentName());
                model.addAttribute("agentId", deduction.getAgentId());
            }
            BusActRel busActRel = new BusActRel();
            busActRel.setBusId(staging.getId());
            BusActRel rel = null;
            try {
                rel = taskApprovalService.queryBusActRel(busActRel);
                if (rel != null) {
                    request.setAttribute("actRecordList", queryApprovalRecord(rel.getActivId()));
                    model.addAttribute("proIns", rel.getActivId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        optionsData(request);
        return "profit/stagApproval/taskApprovalDetail";
    }

}
