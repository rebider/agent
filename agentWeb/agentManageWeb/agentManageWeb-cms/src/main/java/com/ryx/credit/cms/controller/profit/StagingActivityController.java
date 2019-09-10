package com.ryx.credit.cms.controller.profit;/**
 * @Auther: zhaodw
 * @Date: 2018/7/27 16:11
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.JsonUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.BusActRel;
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
 * 分期审批处理
 *
 * @author zhaodw
 * @create 2018/7/27
 * @since 1.0.0
 */
@RequestMapping("/stagActivity/")
@Controller
public class StagingActivityController extends BaseController {

    private static  final  Logger LOG = Logger.getLogger(StagingActivityController.class);

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
    @RequestMapping("toTaskApproval")
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
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        return "profit/stagApproval/taskApproval";
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

        if (staging==null) {
            staging = stagingServiceImpl.getStagingById(sourceId);
        }
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
