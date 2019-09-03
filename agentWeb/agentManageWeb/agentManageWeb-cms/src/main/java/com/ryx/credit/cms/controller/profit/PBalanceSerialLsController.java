package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.service.IPBalanceSerialLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 月分润出款展示
 * @author chenliang
 */
@Controller("pBalanceSerialLs")
@RequestMapping(value = {"/pBalanceSerialLs"})
public class PBalanceSerialLsController extends BaseController {
    @Autowired
    IPBalanceSerialLsService pBalanceSerialLsService;
    @RequestMapping(value = {"/pBalanceSerialLsPage"})
    public String pBalanceSerialLsPage(){
        return "profit/balanceSerial/PBalanceSerialLs";

    }

    @RequestMapping(value = {"/pBalanceSerialLsList"})
    @ResponseBody
    public Object pBalanceSerialLsLis(HttpServletRequest request){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);

        Set<String> roles = getShiroUser().getRoles();
        if (roles != null && roles.contains("代理商")) {
            map.put("AGENT_ID",getAgentId());
            map.put("AGENT_NAME",getStaffName());
        }

        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return pBalanceSerialLsService.getPBalanceSerialLsList(map,pageInfo);

    }
    /**
     * 导出
      */
    @RequestMapping("exportPBalanceSerialLs")
    public void exportPBalanceSerialLs(TreeMap map, HttpServletResponse response) throws Exception {
        List<Map<String,Object>>  list =null;

        String filePath = "D:/upload/";
        String filePrefix = "PDM";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //创建标题
        fieldNames.add("分润月份");
        fieldCodes.add("PAY_DATE");

        fieldNames.add("代理商名称");
        fieldCodes.add("AGENT_NAME");

        fieldNames.add("代理商唯一码");
        fieldCodes.add("AGENT_ID");

        fieldNames.add("上级代理商名称");
        fieldCodes.add("PARENT_AGENT_NAME");

        fieldNames.add("上级代理商唯一码");
        fieldCodes.add("PARENT_AGENT_ID");

        fieldNames.add("一级代理商名称");
        fieldCodes.add("FIRFT_AGENT_NAME");

        fieldNames.add("一级代理商唯一码");
        fieldCodes.add("FIRFT_AGENT_ID");

        fieldNames.add("出款日期");
        fieldCodes.add("PAY_DATE");

        fieldNames.add("出款金额");
        fieldCodes.add("PROFIT_AMT");

        fieldNames.add("收款人");
        fieldCodes.add("ACCOUNT_NAME");

        fieldNames.add("交易流水号");
        fieldCodes.add("BALANCE_ID");

        fieldNames.add("卡号");
        fieldCodes.add("CARD_NO");

        fieldNames.add("状态");
        fieldCodes.add("STATUS");

        fieldNames.add("出款账户");
        fieldCodes.add("PAY_COMPANY");

        fieldNames.add("备注");
        fieldCodes.add("REMARK");


        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);

       excelExportSXXSSF.writeDatasByMap(list);
        //执行导出
        excelExportSXXSSF.exportFile();
    }

}
