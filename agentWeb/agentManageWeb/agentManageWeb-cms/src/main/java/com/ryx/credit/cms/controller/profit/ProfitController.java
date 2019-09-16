package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.service.BusiPlatService;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.profit.service.IProfitDirectService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;


/**
 * 分潤控制层
 * @version V1.0
 * @Description:
 * @author: WANGY
 * @date: 2018/7/23 15:30
 */
@RequestMapping("profit")
@Controller
public class ProfitController extends BaseController{
    @Autowired
    private IProfitDService profitDService;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private IProfitDirectService profitDirectService;
    @Autowired
    private BusiPlatService busiPlatService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private IUserService userService;

    private static final String FINANCE ="finance";
    private static final String AGENT = "agent";

    private static String EXPORT_Logistics_PATH = AppConfig.getProperty("export.path");

    private static final Logger logger = LoggerFactory.getLogger(ProfitController.class);

    @RequestMapping(value = "toProfitDList")
    public String toProfitDList(HttpServletRequest request,ProfitDay day){
        return "profit/profitDList";
    }

    /**
     * 日分润管理-代理商
     * @LiuQY
     * */
    @RequestMapping(value = "toProfitDListAgent")
    public String toProfitDListAgent(HttpServletRequest request,ProfitDay day){
        return "profit/profitDListAgent";
    }

    @RequestMapping(value = "profitDList")
    @ResponseBody
    public Object profitDList(HttpServletRequest request){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap<String,Object> treeMap = getRequestParameter(request);
        treeMap.put("page",page);
        List<Map<String, Object>>  list = userService.orgCode( getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        if(map.get("ORGANIZATIONCODE").toString().contains("south") || map.get("ORGANIZATIONCODE").toString().contains("north")){
            treeMap.put("orgId",map.get("ORGID").toString());  // 省区
        }else if(Objects.equals("south", map.get("ORGANIZATIONCODE").toString() )|| Objects.equals("north", map.get("ORGANIZATIONCODE").toString())){
            treeMap.put("area",map.get("ORGID").toString());   // 大区
        }else if(Objects.equals(map.get("ORGANIZATIONCODE"), AGENT)){  // 代理商
            treeMap.put("agentId",getAgentId());
        }

        PageInfo resultPageInfo = profitDService.profitDayList(treeMap,pageInfo);
        return resultPageInfo;
    }

    /**
     * 日分润管理-代理商
     * @LiuQY
     * */
    @RequestMapping(value = "profitDListAgent")
    @ResponseBody
    public Object profitDListAgent(HttpServletRequest request,ProfitDay day){
        Page pageInfo = pageProcess(request);
        TreeMap<String,String> map = getRequestParameter(request);
        PageInfo resultPageInfo = profitDService.profitDList(map,pageInfo);
        return resultPageInfo;
    }

    @RequestMapping(value = {"profitByLeader"})
    public String profitByLeader(HttpServletRequest request){
        return "profit/profitByLeader";
    }

    @ResponseBody
    @RequestMapping(value = "profitFList")
    public PageInfo getMeTotalList(HttpServletRequest request){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap treeMap = getRequestParameter(request);
        treeMap.put("begin", page.getBegin());
        treeMap.put("end", page.getEnd());

        List<Map<String, Object>>  list = userService.orgCode( getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        if(map.get("ORGANIZATIONCODE").toString().contains("north") || map.get("ORGANIZATIONCODE").toString().contains("south") ){
            treeMap.put("orgId",map.get("ORGID").toString());
        }else if(Objects.equals(map.get("ORGANIZATIONCODE"),AGENT)){
            treeMap.put("agentId",getAgentId());
            treeMap.put("agentName",getStaffName());
        }
        return profitMonthService.queryProfitDetailMonthList(treeMap, pageInfo);
    }

    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request){
        String agentName=request.getParameter("agentName");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TreeMap param = getRequestParameter(request);
        param.put("agentName",agentName);
        logger.info("\n\n"+param.toString());
        ModelAndView view=new ModelAndView();
        Map<String,Object> map=profitMonthService.profitCount(param);
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }

    @RequestMapping(value = {"profitByFinance"})
    public String profitByFinance(Model model){
        //终审后不能进行操作
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/profitByFinance";
    }
    /**
     * 金额调整
     * */
    @RequestMapping("/toAmtAdjust")
    public ModelAndView adjustMPage(HttpServletRequest request, @RequestParam(value = "id") String id) {
        ProfitDetailMonth profitDetailMonthM = profitMonthService.getProfitDetailMonth(id);
        ModelAndView view = new ModelAndView();
        view.setViewName("profit/monthProfit/profitAdjustm");
        view.addObject("profitAdjust", profitDetailMonthM);
        return view;
    }

    /**
     * 代理商月分润导出数据
     * @Author chenqiutian
     * @Date 2019/1/11
     * @param response
     * @throws Exception
     */
    @RequestMapping("exportByF")
    public void exportByF(HttpServletResponse response, HttpServletRequest request) throws Exception {

        TreeMap map = getRequestParameter(request);
        List<Map<String,String>> list = profitMonthService.exportByF(map);
        Map<String, Object> param = new HashMap<String, Object>(6);

        String title = "分润月份,代理商唯一编码,代理商名称,上级代理商唯一码,上级代理商名称,实发分润,基础分润合计," +
                "POS付款分润,POS出款分润,瑞银信分润,瑞银信活动分润,贴牌分润" +
                ",瑞刷分润,瑞刷活动分润,瑞和宝分润,直发平台分润,POS直签补差分润,手刷直签补差分润,POS奖励,分润补款合计," +
                "POS退单补款,手刷退单补款,机具返现,其他补款,分润扣款合计,POS退单实扣款," +
                "手刷退单实扣,考核扣款" +
                ",智能POS预发分润扣款,机具扣款,保理扣款,其它扣款,涉税事项前应发分润汇总,税款扣除,补税点差" +
                ",收款户名,打款公司,分润状态";
        String column = "PROFIT_DATE,AGENT_ID,AGENT_NAME,PARENT_AGENT_ID,parentAgentName,REAL_PROFIT_AMT,PROFIT_SUM_AMT," +
                "TRAN_PROFIT_AMT,PAY_PROFIT_AMT,RYX_PROFIT_AMT,RYX_HD_PROFIT_AMT,TP_PROFIT_AMT," +
                "RS_PROFIT_AMT,RS_HD_PROFIT_AMT,RHB_PROFIT_AMT,ZF_PROFIT_AMT,POS_ZQ_SUPPLY_PROFIT_AMT,MPOS_ZQ_SUPPLY_PROFIT_AMT,POS_REWARD_AMT,PROFITSUPPLYSUM" +
                ",POS_TD_SUPPLY_AMT,MPOS_TD_SUPPLY_AMT,TOOLS_RETURN_AMT,OTHER_SUPPLY_AMT,PROFITCHARGESUM,POS_TD_REAL_DEDUCTION_AMT" +
                ",MPOS_TD_REAL_DEDUCTION_AMT,KHDEDUCTIONAMT," +
                "ZNPOS_PROFIT_AMT,TOOLSHARGEAMT,BU_DEDUCTION_AMT,OTHER_DEDUCTION_AMT," +
                "SSSXQYFFRHZ,TAXPAYDEDUCT,SUPPLY_TAX_AMT,ACCOUNT_NAME,accountName,STATUS";

        param.put("path", EXPORT_Logistics_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", list);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);

    }

    /**
     * 导出数据：
     * 1、分润展示出入款
     */
    @RequestMapping("exportByFinance")
    public void exportByFinance(ProfitDetailMonth profitDetailMonth, HttpServletResponse response) throws Exception {
        List<ProfitDirect> list = profitMonthService.exportByFinance(profitDetailMonth);
        String filePath = "D:/upload/";
        String filePrefix = "PDM";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;

        //导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("分润日期");
        fieldCodes.add("profitDate");
        fieldNames.add("商户编号");
        fieldCodes.add("agentId");
        fieldNames.add("代理商名称");
        fieldCodes.add("agentName");
        fieldNames.add("上级商户编号");
        fieldCodes.add("parentAgentId");
        fieldNames.add("付款交易额(pos交易额+二维码交易额+云闪付+银联二维)");
        fieldCodes.add("tranAmt");
        fieldNames.add("出款交易额(S0+D1)");
        fieldCodes.add("payAmt");
        fieldNames.add("付款交易分润比例");
        fieldCodes.add("tranProfitScale");
        fieldNames.add("出款交易分润比例");
        fieldCodes.add("payProfitScale");
        fieldNames.add("付款交易分润额");
        fieldCodes.add("tranProfitAmt");
        fieldNames.add("出款交易分润额");
        fieldCodes.add("payProfitAmt");

        fieldNames.add("瑞银信分润");
        fieldCodes.add("ryxProfitAmt");
        fieldNames.add("瑞银信活动分润");
        fieldCodes.add("ryxHdProfitAmt");
        fieldNames.add("贴牌分润");
        fieldCodes.add("tpProfitAmt");
        fieldNames.add("瑞刷分润");
        fieldCodes.add("rsProfitAmt");
        fieldNames.add("瑞刷活动分润");
        fieldCodes.add("rsHdProfitAmt");
        fieldNames.add("瑞和宝分润");
        fieldCodes.add("rhbProfitAmt");
        fieldNames.add("直发平台分润");
        fieldCodes.add("zfProfitAmt");
        fieldNames.add("POS直签补差分润");
        fieldCodes.add("posZqSupplyProfitAmt");
        fieldNames.add("手刷直签补差分润");
        fieldCodes.add("mposZqSupplyProfitAmt");
        fieldNames.add("分润汇总");
        fieldCodes.add("profitSumAmt");

        fieldNames.add("POS退单应扣款");
        fieldCodes.add("posTdMustDeductionAmt");
        fieldNames.add("POS退单实扣款");
        fieldCodes.add("posTdRealDeductionAmt");
        fieldNames.add("手刷退单应扣");
        fieldCodes.add("mposTdMustDeductionAmt");
        fieldNames.add("手刷退单实扣");
        fieldCodes.add("mposTdRealDeductionAmt");
        fieldNames.add("瑞和宝订购应扣总额");
        fieldCodes.add("rhbDgMustDeductionAmt");
        fieldNames.add("瑞和宝订购实扣");
        fieldCodes.add("rhbDgRealDeductionAmt");
        fieldNames.add("POS订购应扣总额");
        fieldCodes.add("posDgMustDeductionAmt");
        fieldNames.add("POS订购实扣");
        fieldCodes.add("posDgRealDeductionAmt");
        fieldNames.add("智能POS订购应扣总额");
        fieldCodes.add("zposDgMustDeductionAmt");
        fieldNames.add("智能POS订购实扣");
        fieldCodes.add("zposTdRealDeductionAmt");

        fieldNames.add("POS考核扣款(新国都、瑞易送)");
        fieldCodes.add("posKhDeductionAmt");
        fieldNames.add("手刷考核扣款(小蓝牙、MPOS)");
        fieldCodes.add("mposKhDeductionAmt");
        fieldNames.add("商业保理扣款");
        fieldCodes.add("buDeductionAmt");
        fieldNames.add("其他扣款");
        fieldCodes.add("otherDeductionAmt");
        fieldNames.add("POS退单补款");
        fieldCodes.add("posTdSupplyAmt");
        fieldNames.add("手刷退单补款");
        fieldCodes.add("mposTdSupplyAmt");
        fieldNames.add("其他补款");
        fieldCodes.add("otherSupplyAmt");
        fieldNames.add("POS奖励");
        fieldCodes.add("posRewardAmt");
        fieldNames.add("扣当月之前税额（包含当月日结分润）");
        fieldCodes.add("deductionTaxMonthAgoAmt");
        fieldNames.add("扣本月税额");
        fieldCodes.add("deductionTaxMonthAmt");

        fieldNames.add("补下级税点");
        fieldCodes.add("supplyTaxAmt");
        fieldNames.add("实发分润");
        fieldCodes.add("realProfitAmt");
        fieldNames.add("本月分润");
        fieldCodes.add("profitMonthAmt");
        fieldNames.add("账号");
        fieldCodes.add("accountId");
        fieldNames.add("户名");
        fieldCodes.add("accountName");
        fieldNames.add("开户行");
        fieldCodes.add("openBankName");
        fieldNames.add("邮箱地址");
        fieldCodes.add("email");
        fieldNames.add("联行号");
        fieldCodes.add("bankCode");
        fieldNames.add("打款状态");
        fieldCodes.add("payStatus");

        fieldNames.add("分润状态");
        fieldCodes.add("status");
        fieldNames.add("备注");
        fieldCodes.add("remark");

        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 调整机具扣款
     * @param id
     *p:调整后金额
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"updatePosBack"})
    public ResultVO updatePosBack(@RequestParam("id")String id, @RequestParam("p")BigDecimal p){
        return ResultVO.fail("调整失败请重试");
    }

    /**
     * 冻结分润，填写冻结原因
     * @param id
     * @author LiuQY
     */
    @RequestMapping(value = {"frozenProfit"})
    public String editPage(Model model, String id) {
        ProfitDetailMonth profitDetailM = profitMonthService.selectByPrimaryKey(id);
        model.addAttribute("profitDetailM", profitDetailM);
        return "profit/profitEdit";
    }



    /**
     * 冻结分润，修改所关联直发分润状态
     * @param remark
     * @author LiuQY
     */
    @RequestMapping("/profit_refuse")
    public Object edit_refuse(ProfitDetailMonth profitM, String remark) {
        profitM.setRemark(remark);
        profitM.setStatus(String.valueOf(Status.STATUS_1.status));
        profitMonthService.updateByPrimaryKeySelective(profitM);
        ProfitDirect profitDirect = new ProfitDirect();
        profitDirect.setFristAgentPid(profitM.getAgentId());
        List<ProfitDirect> list = profitDirectService.selectByFristAgentPid(profitDirect);
        if (list.size() > 0) {
            for (ProfitDirect profitDirectSingleList : list) {
                if (profitDirectSingleList != null) {
                    profitDirectSingleList.setStatus(String.valueOf(Status.STATUS_1.status));
                    profitDirectService.updateByStatus(profitDirectSingleList);
                }
            }
        }

        //通过接口方式联动业务平台完成冻结
        List<String> agentIds = new ArrayList<String>();
        agentIds.add(profitM.getAgentId());
        boolean fail = busiPlatService.mPos_Frozen(agentIds);
        if (fail) {
            return ResultVO.fail("通知手刷冻结失败！");
        }
       return ResultVO.success("冻结成功！");
    }

    /**
     * 代理商日分润的导出
     */
    @RequestMapping("exportProfitD")
    public void exportProfitD(ProfitDay day, HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<ProfitDay> list = profitDService.exportProfitD(day);
        String filePath = "C:/upload/";
        String filePrefix = "PD";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;

        //导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("代理商名称");
        fieldCodes.add("agentName");
        fieldNames.add("代理商唯一码");
        fieldCodes.add("agentPid");
        fieldNames.add("代理商编号");
        fieldCodes.add("agentId");
        fieldNames.add("交易时间");
        fieldCodes.add("transDate");
        fieldNames.add("出款时间");
        fieldCodes.add("remitDate");
        fieldNames.add("补款金额");
        fieldCodes.add("redoMoney");
        fieldNames.add("返现金额");
        fieldCodes.add("returnMoney");
        fieldNames.add("本日应发分润");
        fieldCodes.add("totalProfit");
        fieldNames.add("本日实发分润");
        fieldCodes.add("realMoney");
        fieldNames.add("冻结分润金额");
        fieldCodes.add("frozenMoney");
        fieldNames.add("打款成功金额");
        fieldCodes.add("successMoney");
        fieldNames.add("打款失败金额");
        fieldCodes.add("failMoney");

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

}
