package com.ryx.credit.cms.controller.profit;/**
 * @Auther: zhaodw
 * @Date: 2018/7/27 16:11
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.common.util.JsonUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.pojo.ProfitUnfreeze;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.data.AttachmentService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/thawActivity/")
@Controller
public class ThawActivityController extends BaseController {

    private static  final  Logger LOG = Logger.getLogger(ThawActivityController.class);

    @Autowired
    private ProfitMonthService profitMonthService;

    @Autowired
    private ActHiVarinstService actHiVarinstService;

    @Autowired
    private IUserService userService;

    @Autowired
    private TaskApprovalService taskApprovalService;

    @Autowired
    private AttachmentService attachmentService;


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
        String thawId = request.getParameter("busId");
        optionsData(request);

        //获取分期信息
        ProfitUnfreeze unfreeze = profitMonthService.getProfitUnfreezeById(thawId);
        if (unfreeze != null) {
            model.addAttribute("thaw", unfreeze);
            if (StringUtils.isNotBlank(unfreeze.getAttachInfo())) {
               Attachment info = attachmentService.getAttachmentById(unfreeze.getAttachInfo());
               if (info != null) {
                  model.addAttribute("attName", info.getAttName());
                  model.addAttribute("attDbpath", info.getAttDbpath());
               }
            }
        }
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        return "profit/thawApproval/taskApproval";
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
        String profitId = request.getParameter("id");
        //获取分期信息
        ProfitUnfreeze unfreeze = profitMonthService.getProfitUnfreezeByProfitId(profitId);
        if (unfreeze != null) {
            model.addAttribute("thaw", unfreeze);
            if (StringUtils.isNotBlank(unfreeze.getAttachInfo())) {
                Attachment info = attachmentService.getAttachmentById(unfreeze.getAttachInfo());
                if (info != null) {
                    model.addAttribute("attName", info.getAttName());
                    model.addAttribute("attDbpath", info.getAttDbpath());
                }
            }
            BusActRel busActRel = new BusActRel();
            busActRel.setBusId(unfreeze.getId());
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
        return "profit/thawApproval/taskApprovalDetail";
    }
}
