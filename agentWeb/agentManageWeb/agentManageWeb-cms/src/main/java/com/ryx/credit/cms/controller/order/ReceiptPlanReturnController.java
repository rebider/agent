package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.PlannerStatus;
import com.ryx.credit.common.enumc.ReceiptPlanReturnExportColum;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author liudh
 * @desc 退货排单
 */
@Controller
@RequestMapping("/receiptPlanReturn")
public class ReceiptPlanReturnController extends BaseController{

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
        return  receiptPlanService.getReceiptPlanList(map, pageInfo,false);
    }

    /**
     * 已派单信息导出
     * @param request
     * @param response
     */
    @RequestMapping("/export")
    public void exportReceiptPlanWxecl(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = receiptPlanService.getReceiptPlanList(map, new PageInfo(),false);
        Map<String, Object> param = new HashMap<String, Object>(6);

        param.put("path",EXPORT_PLAN_EXECL_PATH);
        param.put("title", ReceiptPlanReturnExportColum.ReceiptPlanExportColum_title.code);
        param.put("column", ReceiptPlanReturnExportColum.ReceiptPlanExportColum_column.code);
        param.put("dataList",pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

}
