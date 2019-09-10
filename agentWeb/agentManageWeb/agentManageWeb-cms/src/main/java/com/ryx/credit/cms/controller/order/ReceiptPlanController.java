package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.PlannerStatus;
import com.ryx.credit.common.enumc.ReceiptPlanExportColum;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author yangmx
 * @desc 已排单查询
 */
@Controller
@RequestMapping("/receiptPlan")
public class ReceiptPlanController extends BaseController{

    @Autowired
    private ReceiptPlanService receiptPlanService;

    private static String EXPORT_PLAN_EXECL_PATH = AppConfig.getProperty("export.path");

    @GetMapping("/listPage")
    public String viewJsp(HttpServletRequest request){
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        return "order/receiptPlanList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getPageList(HttpServletRequest request){
        List<ReceiptPlan> list = new ArrayList<ReceiptPlan>();
        for (PlannerStatus plannerStatus : PlannerStatus.values()) {
            ReceiptPlan receiptPlan = new ReceiptPlan();
            receiptPlan.setPlanOrderStatus(new BigDecimal(plannerStatus.getValue()));
            list.add(receiptPlan);
        }
        request.setAttribute("planList",list);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return  receiptPlanService.getReceiptPlanList(map, pageInfo,true);
    }

    /**
     * 已派单信息导出
     * @param request
     * @param response
     */
    @RequestMapping("/export")
    public void exportReceiptPlanWxecl(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = receiptPlanService.getReceiptPlanList(map, new PageInfo(),true);
        Map<String, Object> param = new HashMap<String, Object>(6);

        param.put("path",EXPORT_PLAN_EXECL_PATH);
        param.put("title", ReceiptPlanExportColum.ReceiptPlanExportColum_title.code);
        param.put("column", ReceiptPlanExportColum.ReceiptPlanExportColum_column.code);
        param.put("dataList",pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    /**
     * 导出历史派单记录
     * @param request
     * @param response
     */
    @RequestMapping("/exportHistory")
    public void exportHistory(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = receiptPlanService.getReceiptPlanList(map, new PageInfo(),false);
        Map<String, Object> param = new HashMap<String, Object>(6);

        param.put("path",EXPORT_PLAN_EXECL_PATH);
        param.put("title", ReceiptPlanExportColum.ReceiptPlan_ViewTitle.code);
        param.put("column", ReceiptPlanExportColum.ReceiptPlan_ViewColumn.code);
        param.put("dataList",pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    @ResponseBody
    @RequestMapping(value = "revocationPlanner")
    public AgentResult revocationPlanner(@RequestParam("planNum") String planNum,
                                         @RequestParam("orderId") String orderId) {
        if(StringUtils.isBlank(planNum) && StringUtils.isBlank(orderId)) {
            return AgentResult.fail("参数错误");
        }
        try {
            return receiptPlanService.revocationPlanner(planNum, orderId, getUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "startShipping")
    public AgentResult startShipping(@RequestParam("planNum") String planNum,
                                         @RequestParam("orderId") String orderId) {
        if(StringUtils.isBlank(planNum) && StringUtils.isBlank(orderId)) {
            return AgentResult.fail("参数错误");
        }
        try {
            return receiptPlanService.startShipping(planNum, orderId, getUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }

}
