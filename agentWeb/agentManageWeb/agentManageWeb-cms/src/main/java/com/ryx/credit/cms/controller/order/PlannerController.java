package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.ReceiptPlanExportColum;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ReceiptOrderVo;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.PlannerService;
import com.ryx.credit.service.order.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by RYX on 2018/7/20.
 */
@RequestMapping("planner")
@Controller
public class PlannerController extends BaseController {

    @Autowired
    private PlannerService plannerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private IOrderReturnService orderReturnService;
    @Autowired
    private OrderActivityService orderActivityService;

    private static String EXPORT_PLANNNER_EXECL_PATH = AppConfig.getProperty("export.path");


    @RequestMapping(value = "toPlannerList")
    public String toPlannerList(HttpServletRequest request){
        return "order/plannerList";
    }

    @RequestMapping(value = "plannerList")
    @ResponseBody
    public Object plannerList(HttpServletRequest request, OReceiptOrder receiptOrder,OReceiptPro receiptPro,String returnDetailsId){
        Map map =new HashMap();
       if (StringUtils.isNotBlank(returnDetailsId)){
           map=orderReturnService.selectByReturnDeId(returnDetailsId);
       }
        if (StringUtils.isNotBlank(request.getParameter("agentName"))){
            map.put("par_agentName",request.getParameter("agentName"));
        }
        if (StringUtils.isNotBlank(request.getParameter("proName"))){
            map.put("par_proName",request.getParameter("proName"));
        }
        if (StringUtils.isNotBlank(request.getParameter("oInuretime"))){
            map.put("oInuretime",request.getParameter("oInuretime"));
        }
        if (StringUtils.isNotBlank(request.getParameter("avtivityName"))){
            map.put("par_avtivityName",request.getParameter("avtivityName"));
        }
        Page pageInfo = pageProcess(request);
        PageInfo resultPageInfo = plannerService.queryPlannerList(receiptOrder,receiptPro,pageInfo,map);
        return resultPageInfo;
    }

    @RequestMapping(value = "savePlanner")
    @ResponseBody
    public Object savePlanner(ReceiptPlan receiptPlan,String receiptProId,String activityId){
        try {
            receiptPlan.setProId(receiptProId);
            String userId = String.valueOf(getUserId());
            receiptPlan.setcUser(userId);
            receiptPlan.setUserId(userId);
            AgentResult agentResult = plannerService.savePlanner(receiptPlan, receiptProId, activityId);
            if(agentResult.isOK()){
                return renderSuccess("保存成功！");
            }
            return renderSuccess("保存失败！");
        }catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "batchPlanner")
    @ResponseBody
    public Object batchPlanner(@RequestBody AgentVo agentVo){
        try {
            AgentResult agentResult = plannerService.batchPlanner(agentVo.getReceiptPlanList(), String.valueOf(getUserId()));
            if(agentResult.isOK()){
                return renderSuccess("批量保存成功！");
            }
            return renderSuccess("批量保存失败！");
        } catch (MessageException e) {
            return renderError(e.getMsg());
        } catch (ProcessException e) {
            return renderError(e.getMessage());
        } catch (Exception e) {
            return renderError(Constants.FAIL_MSG);
        }
    }

    @RequestMapping(value = "findListByProCode")
    @ResponseBody
    public Object findListByProCode(@RequestParam("proCode") String proCode){
        List<OProduct> proCodes = productService.findListByProCode(proCode);
        for (OProduct code : proCodes) {
            Dict proComNameDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), code.getProCom());
            if(proComNameDict!=null)
            code.setProComName(proComNameDict.getdItemname());
        }
        return proCodes;
    }


    @RequestMapping(value = "findListByProCodeFroPlanner")
    @ResponseBody
    public Object findListByProCodeFroPlanner(@RequestParam("orderId") String orderId,@RequestParam("proId") String proId){
//        List<OProduct> proCodes = productService.findListByProCode(proCode);
//        for (OProduct code : proCodes) {
//            Dict proComNameDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), code.getProCom());
//            if(proComNameDict!=null)
//                code.setProComName(proComNameDict.getdItemname());
//        }
//        return proCodes;
        return orderActivityService.planChoiseProComAndModel(proId,orderId);
    }


    @RequestMapping("exportPlanner")
    public void exportPlanner(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FastMap fastMap = FastMap.fastMap("orderId", request.getParameter("orderId"))
                .putKeyV("oInuretime", request.getParameter("oInuretime"))
                .putKeyV("receiptNum", request.getParameter("receiptNum"))
                .putKeyV("addrRealname", request.getParameter("addrRealname"))
                .putKeyV("agentName", request.getParameter("agentName"))
                .putKeyV("proName", request.getParameter("proName"))
                .putKeyV("avtivityName", request.getParameter("avtivityName"));

        //查询当前用户的待排单数据
        fastMap.putKeyV("userId", getUserId());
        List<ReceiptOrderVo> listVo = plannerService.exportPlanner(fastMap);
        String filePath = "C:/upload/";
        String filePrefix = "Planner";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("订单编号");
        fieldCodes.add("orderId");
        fieldNames.add("子订单编号");
        fieldCodes.add("receiptNum");
        fieldNames.add("订单时间");
        fieldCodes.add("oinuretime");
        fieldNames.add("代理商唯一编码");
        fieldCodes.add("agentId");
        fieldNames.add("代理商名称");
        fieldCodes.add("agName");
        fieldNames.add("收货姓名");
        fieldCodes.add("addrRealname");
        fieldNames.add("活动名称");
        fieldCodes.add("activityName");
        fieldNames.add("商品ID");
        fieldCodes.add("proId");
        fieldNames.add("商品编号");
        fieldCodes.add("proCode");
        fieldNames.add("商品名称");
        fieldCodes.add("proName");
        fieldNames.add("订货量");
        fieldCodes.add("proNum");
        fieldNames.add("已排量");
        fieldCodes.add("sendNum");
        fieldNames.add("待排单");
        fieldCodes.add("forSendNum");
        fieldNames.add("厂家/机型");
        fieldCodes.add("proCom");
        fieldNames.add("数量");
        fieldCodes.add("planProNum");
        fieldNames.add("订单备注");
        fieldCodes.add("orderRemark");

        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(listVo);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
