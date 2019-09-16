package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.service.IProfitDirectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Wangy
 * @Date 2018/08/06
 * 分润管理：直发分润展示
 */
@Controller
@RequestMapping("/profitDirect")
public class ProfitDirectController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProfitDirectController.class);

    @Resource
    private IProfitDirectService directService;

    @RequestMapping(value = "pageList")
    public String profitFactorPage() {
        return "profit/profitDirect/profitDirectList";
    }

    /**
     * 1、分页展示
     */
    @RequestMapping(value = "getList")
    @ResponseBody
    public Object getProfitFactorList(HttpServletRequest request){
        Page page = pageProcess(request);

        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        Set<String> roles = getShiroUser().getRoles();

        if (roles != null && roles.contains("代理商")) {
            map.put("AGENT_ID",getAgentId());
            map.put("AGENT_NAME",getStaffName());
        }
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return directService.getProfitDirectList(map, pageInfo);
    }
    /*
     * 统计
     */
    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request){
        String agentName=request.getParameter("AGENT_NAME");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        TreeMap map = getRequestParameter(request);
        map.put("AGENT_NAME",agentName);
        Map<String,Object> result=directService.profitCount(map);
        view.addObject("totalNum",result.get("TOTALNUM"));
        view.addObject("totalMoney",result.get("TOTALMONEY")==null?0:result.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }


    /**
     * 页面导出
     */
    @RequestMapping(value = "exportProfitDirect")
    public void exportProfitD(ProfitDirect record, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TreeMap map = getRequestParameter(request);
        List<ProfitDirect> list = directService.exportProfitDirect(map);

        String filePath = "C:/upload/";
        String filePrefix = "PD";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //指导导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("代理商编号");
        fieldCodes.add("agentId");
        fieldNames.add("代理商名称");
        fieldCodes.add("agentName");
        fieldNames.add("月份");
        fieldCodes.add("transMonth");
        fieldNames.add("上级代理商名称");
        fieldCodes.add("parentAgentName");
        fieldNames.add("一级代理商名称");
        fieldCodes.add("fristAgentName");
        fieldNames.add("直发交易额");
        fieldCodes.add("transAmt");
        fieldNames.add("直发手续费");
        fieldCodes.add("transFee");
        fieldNames.add("直发分润");
        fieldCodes.add("profitAmt");
        fieldNames.add("退单补款");
        fieldCodes.add("supplyAmt");
        fieldNames.add("退单扣款");
        fieldCodes.add("buckleAmt");
        fieldNames.add("应发分润");
        fieldCodes.add("shouldProfit");
        fieldNames.add("实发分润");
        fieldCodes.add("actualProfit");
        fieldNames.add("月份");
        fieldCodes.add("transMonth");
        fieldNames.add("邮箱");
        fieldCodes.add("agentEmail");
        fieldNames.add("账号");
        fieldCodes.add("accountCode");
        fieldNames.add("户名");
        fieldCodes.add("accountName");
        fieldNames.add("开户行");
        fieldCodes.add("bankOpen");
        fieldNames.add("银行号");
        fieldCodes.add("bankCode");
        fieldNames.add("总行行号");
        fieldCodes.add("bossCode");
        fieldNames.add("代下级退单补款");
        fieldCodes.add("parentBuckle");
        fieldNames.add("代下级退单扣款");
        fieldCodes.add("parentSupply");
        fieldNames.add("冻结状态");
        fieldCodes.add("status");
        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/deductionDetail")
    public ModelAndView deductionDetail(HttpServletRequest request){
        String agentId=request.getParameter("agentId");
        String month=request.getParameter("trans_month");

        Map<String,Object> param=new HashMap<>();
        param.put("bearAgentId",agentId);
        param.put("runDate",month);
        List<List<Map<String,Object>>> result=directService.getBuckleRunByAgentIdAndRunDate(param);
        List<Map<String,Object>> chargebackAgentList=new ArrayList<>();
        for (List<Map<String,Object>> list:result) {
            for (Map<String,Object> map:list){
                if(agentId.equals(map.get("BEAR_AGENT_ID").toString())){
                    map.put("mainAgent","true");
                    break;
                }
            }
            Map<String,Object> map=new HashMap<>();
            ProfitDirect profitDirect=new ProfitDirect();
            profitDirect.setAgentId(list.get(0).get("AGENT_ID").toString());
            profitDirect.setTransMonth(month);
            profitDirect=directService.selectByAgentAndMon(profitDirect);
            map.put("title",profitDirect.getAgentName()+"的上级代扣款明细");
            map.put("total",profitDirect.getBuckleAmt());
            Map<String,Object> temp=new HashMap<>();
            temp.put("BEAR_AGENT_ID",profitDirect.getAgentId());
            temp.put("AGENT_NAME",profitDirect.getAgentName());
            temp.put("RUN_AMT",profitDirect.getProfitAmt().add(profitDirect.getSupplyAmt()));
            list.add(temp);
            map.put("ChargebackAgents",list);
            chargebackAgentList.add(map);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ChargebackAgentList",chargebackAgentList);
        modelAndView.setViewName("profit/profitDirect/deductionDetail");
        return modelAndView;
    }
    @RequestMapping("/supplyDetail")
    public ModelAndView supplyDetail(HttpServletRequest request){
        String agentId=request.getParameter("agentId");
        String month=request.getParameter("trans_month");

        Map<String,Object> param=new HashMap<>();
        param.put("bearAgentId",agentId);
        param.put("runDate",month);
        List<List<Map<String,Object>>> result=directService.getSupplyByAgentIdAndRunDate(param);
        List<Map<String,Object>> chargebackAgentList=new ArrayList<>();
        for (List<Map<String,Object>> list:result) {
            for (Map<String,Object> map:list){
                if(agentId.equals(map.get("BEAR_AGENT_ID").toString())){
                    map.put("mainAgent","true");
                    break;
                }
            }
            Map<String,Object> map=new HashMap<>();
            ProfitDirect profitDirect=new ProfitDirect();
            profitDirect.setAgentId(list.get(0).get("AGENT_ID").toString());
            profitDirect.setTransMonth(month);
            profitDirect=directService.selectByAgentAndMon(profitDirect);
            map.put("title",profitDirect.getAgentName()+"的上级退单补款明细");
            Map<String,Object> temp=new HashMap<>();
            temp.put("BEAR_AGENT_ID",profitDirect.getAgentId());
            temp.put("AGENT_NAME",profitDirect.getAgentName());
            temp.put("SUPPLY_AMT",profitDirect.getSupplyAmt());
            list.add(temp);
            map.put("ChargebackAgents",list);
            BigDecimal sumSupply=BigDecimal.ZERO;
            sumSupply.add(profitDirect.getSupplyAmt());
            for (Map<String,Object> maps:list) {
                BigDecimal tempNum=maps.get("SUPPLY_AMT")==null?BigDecimal.ZERO:new BigDecimal(maps.get("SUPPLY_AMT").toString());
                sumSupply=sumSupply.add(tempNum);
            }
            map.put("total",sumSupply);
            chargebackAgentList.add(map);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ChargebackAgentList",chargebackAgentList);
        modelAndView.setViewName("profit/profitDirect/supplyDetail");
        return modelAndView;
    }

}
