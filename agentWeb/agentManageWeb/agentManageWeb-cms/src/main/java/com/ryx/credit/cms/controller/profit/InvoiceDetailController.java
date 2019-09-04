package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.*;
import com.ryx.credit.common.enumc.PDataAdjustType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.service.IAgeInvoiceApplyService;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.profit.service.ITaxDeductionDetailService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;


/**
 * 欠票页面展示
 * @Author CQT
 * @Create 2018/12/18
 */
@Controller
@RequestMapping("/profit/invoiceDetail")
public class InvoiceDetailController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(InvoiceDetailController.class);

    @Autowired
    private IOwnInvoiceService ownInvoiceService;
    @Autowired
    private IAgeInvoiceApplyService ageInvoiceApplyService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private ITaxDeductionDetailService taxDeductionDetailService;
    @Autowired
    private IUserService userService;


    private String UPLOAD_PATH = AppConfig.getProperty("picture.path");

    private String INVOICE = "invoice";

    private  String JD_PATH = AppConfig.getProperty("jindie.path");
    private String CLIENT_ID =  AppConfig.getProperty("jindie.clientId");
    private String CLIENT_SECRET = AppConfig.getProperty("jindie.clientSecret");
    private  String TIN = AppConfig.getProperty("rui.tin");



    /**
     * 加载欠票展示页面
     * @return
     */
    @RequestMapping("/gotoInvoiceDetailList")
    public String gotoUnderTicket(Model model){
        LOGGER.info("加载欠票展示页面。");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/InvoiceDetail/InvoiceDetailList";
    }

    /**获取数据列表*/
    @RequestMapping(value = "getInvoiceDetailList")
    @ResponseBody
    public Object  getInvoiceDetailList(HttpServletRequest request){
        LOGGER.info("获取数据列表。");
        Page page = pageProcess(request);
        Map<String,String> param = new HashMap<String,String>();
        param.put("agentId",request.getParameter("agentId"));
        param.put("agentName",request.getParameter("agentName"));
        param.put("concludeChild",request.getParameter("concludeChild"));
        param.put("dateStart",request.getParameter("dateStart"));
        param.put("dateEnd",request.getParameter("dateEnd"));
        return ownInvoiceService.getInvoiceDetailList(page,param);
    }

    /**
     * 跳转导入表格
     * @return
     */
    @RequestMapping("gotoExportPage")
    public String  gotoExportPage(){
        return "profit/InvoiceDetail/importData";
    }

    /**
     * 导入数据
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("importData")
    @ResponseBody
    public ResultVO exportData(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request){
        if (null == file) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<List<Object>> deductionist = ExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
            String loginName = getShiroUser().getLoginName();//获取当前登录对象
            ownInvoiceService.exportData(deductionist,loginName);
        }catch (RuntimeException e){
            LOGGER.info("欠票导入失败，"+e.getMessage());
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail("导入失败。");
        }
        return ResultVO.success("导入成功。");
    }

    /**跳转调整页面，并传入id*/
    @RequestMapping("gotoAdjustPage")
    public String gotoAdjustPage(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        //根据id获取对应数据的代理商id，代理商姓名
        InvoiceDetail invoiceDetail = ownInvoiceService.getInvoiceById(id);
        model.addAttribute("id",id);
        model.addAttribute("agentId",invoiceDetail.getAgentId());
        model.addAttribute("agentName",invoiceDetail.getAgentName());
        return "profit/InvoiceDetail/AdjustPage";
    }

    /**
     * 设置调整金额
     * @return
     */
    @RequestMapping("/setAdjustAMT")
    @ResponseBody
    public ResultVO  setAdjustAMT(HttpServletRequest request){
        InvoiceDetail invoiceDetail = ownInvoiceService.getInvoiceById(request.getParameter("id"));
        invoiceDetail.setAdjustAmt((invoiceDetail.getAdjustAmt()==null?BigDecimal.ZERO:invoiceDetail.getAdjustAmt()).add(new BigDecimal(request.getParameter("adjustAmt"))));
        invoiceDetail.setAdjustReson(request.getParameter("adjustReson"));
        String adjustAccount = getShiroUser().getLoginName();//设置调整人和调整时间
        invoiceDetail.setAdjustAccount(adjustAccount);

        InvoiceDetail adjustDetail = new InvoiceDetail();
        adjustDetail.setAdjustAmt(new BigDecimal(request.getParameter("adjustAmt")));
        try{
            ownInvoiceService.setAdjustAMT(invoiceDetail,adjustDetail);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("调整失败。");
            return ResultVO.fail("调整失败。");
        }
        return ResultVO.success("调整成功。");
    }

    /**
     * 跳转调整详情页
     * @param request
     * @return
     */
    @RequestMapping("/examineAdjust")
    public ModelAndView examineAdjust(HttpServletRequest request){
        ModelAndView view=new ModelAndView();
        Map<String,Object> map=new HashMap<>();
        map.put("agentId",request.getParameter("agentId"));
        map.put("profitMonth",request.getParameter("profitMonth"));
        view.addObject("param",map);
        view.addObject("adjustType","QP");
        view.setViewName("common/adjustDetail");
        return view;
    }

    /**
     * 查询调整详情
     * @param request
     * @return
     */
    @RequestMapping("adjustDetailList")
    @ResponseBody
    public Object adjustDetailList(HttpServletRequest request){
        PageInfo pageInfo=new PageInfo();
        Map<String,Object>param=new HashMap<>();
        param.put("agentId",request.getParameter("agentId"));
        param.put("profitMonth",request.getParameter("profitMonth"));
        pageInfo=taxDeductionDetailService.adjustDetailList(param, PDataAdjustType.QP.adjustType,pageInfo);
        pageInfo.setTotal(pageInfo.getRows().size());
        return pageInfo;
    }

    /**
     * 导出数据
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/dowmloadData")
    public void dowmloadData(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //Page page = pageProcess(request);
        String agentId = request.getParameter("agentId");
        String agentName = request.getParameter("agentName");
        String concludeChild = request.getParameter("concludeChild");
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        List<InvoiceDetail> lists = ownInvoiceService.exportInvoiceData(agentId,agentName,concludeChild,dateStart,dateEnd);

        String filePath = "D:/upload/";
        String filePrefix = "IN";
        List<String> fieldNames = new ArrayList<String>();
        List<String> fieldCodes = new ArrayList<String>();
        fieldNames.add("分润月份");
        fieldCodes.add("profitMonth");
        fieldNames.add("代理商名称");
        fieldCodes.add("agentName");
        fieldNames.add("代理商唯一码");
        fieldCodes.add("agentId");
        fieldNames.add("上月剩余欠票基数");
        fieldCodes.add("preLeftAmt");
        fieldNames.add("本月日返现");
        fieldCodes.add("dayBackAmt");
        fieldNames.add("本月日分润");
        fieldCodes.add("dayProfitAmt");
        fieldNames.add("上分润月月分润");
        fieldCodes.add("preProfitMonthAmt");
        fieldNames.add("上分润月保理");
        fieldCodes.add("preProfitMonthBlAmt");
        fieldNames.add("调整金额");
        fieldCodes.add("adjustAmt");
        fieldNames.add("本月到票金额");
        fieldCodes.add("addInvoiceAmt");
        fieldNames.add("本月欠票");
        fieldCodes.add("ownInvoice");
        fieldNames.add("备注");
        fieldCodes.add("adjustReson");

        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, 500);
        excelExportSXXSSF.writeDatasByObject(lists);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        String agentName=request.getParameter("agentName");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        Map<String, Object> param ;
        param=getRequestParameter(request);
        param.put("agentId",request.getParameter("agentId"));
        param.put("agentName",agentName);
        param.put("concludeChild",request.getParameter("concludeChild"));
        param.put("dateStart",request.getParameter("dateStart"));
        param.put("dateEnd",request.getParameter("dateEnd"));
        Map<String,Object> map=ownInvoiceService.profitCount(param);
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }

    /**
     * 代理商登陆欠票维护页面
     * @return
     */
    @RequestMapping(value = "toAgentInvoiceDetailList")
    public String toAgentInvoiceDetailList(Model model){
        String agentId = getAgentId();
        String agentName = getStaffName();
        model.addAttribute("agentId",agentId);
        model.addAttribute("agentName",agentName);
        return "profit/InvoiceDetail/AgentInvoiceDetailList";
    }

    /**
     * 获取某代理商欠票维护列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getAgentInvoiceDetailList")
    @ResponseBody
    public Object getAgentInvoiceDetailList(HttpServletRequest request,InvoiceDetail invoiceDetail){
        Page page = pageProcess(request);
        String agentId = getAgentId();
        return ownInvoiceService.getAgentInvoiceDetailList(page,agentId,invoiceDetail);
    }

    /**************************发票明细页面以及相关操作******************************/

    /**
     * 发票明细页面
     */
    @RequestMapping("toInvoiceApplyList")
    public String toInvoiceApplyList(ModelMap modelMap){
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        String eid = getAgentId();
        String sourceType = "socket";
        String ticketParam = "0000";
        long timestamp = System.currentTimeMillis();
        double random = new Random(15).nextDouble();
        try{
            String sign = encodeByMd5(CLIENT_ID+CLIENT_SECRET+TIN+eid+timestamp);
            String content = JD_PATH + "?timestamp="+timestamp+"&client_id="+CLIENT_ID+"&tin="+TIN+"&sign="+sign+"&eid="+
                    eid+"&random="+random+"&sourceType="+sourceType+"&ticketParam="+ticketParam;
            modelMap.put("timestamp",timestamp);
            modelMap.put("random",random);
            modelMap.put("invoice_Result",AppConfig.getProperty("invoice.result"));
            modelMap.put("content",content);
            if(list.size()>0){
                if( Objects.equals(list.get(0).get("ORGANIZATIONCODE"), "agent")){
                    modelMap.put("numType","AG");
                }else{
                    modelMap.put("numType","FI");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "profit/InvoiceDetail/AgentInvoiceList";
    }

    /**
     * 获取发票明细列表
     */
    @RequestMapping("getAgentList")
    @ResponseBody
    public Object getAgentList(HttpServletRequest request, InvoiceApply invoiceApply){
        boolean flag = false;
        Page page = pageProcess(request);
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String,Object> map = list.get(0);

        if( Objects.equals(map.get("ORGANIZATIONCODE"), "agent")){
            map = null;
            invoiceApply.setAgentId(getAgentId());
        }else if( Objects.equals(map.get("ORGANIZATIONCODE"), "finance")){
            map = null;
            invoiceApply.setYsResult("1");
            flag = true;
        }
        return ageInvoiceApplyService.queryInvoiceDetail(invoiceApply,page,map,flag);
    }

    /**
     *根据id删除数据
     */
    @RequestMapping("deletebyId")
    @ResponseBody
    public ResultVO deleteById(String id){
        try{
            ageInvoiceApplyService.deleteById(id);
        }catch (Exception e){
            return ResultVO.fail("删除失败！");
        }
        return ResultVO.success("删除成功！");

    }

    /**
     * 寄出操作
     */
    @RequestMapping("/updateExpressInfo")
    @ResponseBody
    public ResultVO updateExpressInfo(HttpServletRequest request,InvoiceApply invoiceApply){
        try{
            ageInvoiceApplyService.updateExpressInfo(invoiceApply);
        }catch (Exception e){
            return ResultVO.fail("信息保存失败！");
        }
        return ResultVO.success("信息保存成功！");
    }

    @RequestMapping("/getInvoiceApplyById")
    @ResponseBody
    public InvoiceApply getInvoiceApplyById(HttpServletRequest request,String id){
        return ageInvoiceApplyService.getInvoiceApplyById(id);
    }

    /**
     * 批量删除
     */
    @RequestMapping("deletebyBatchId")
    @ResponseBody
    public ResultVO deletebyBatchId(@RequestBody List<String> agentMap){
        if(agentMap.size() <= 0){
            return ResultVO.fail("请选择要删除的数据！");
        }
        try{
            for ( String id:agentMap ) {
                ageInvoiceApplyService.deleteById(id);
            }
        }catch (Exception e){
            return ResultVO.fail("删除失败！");
        }
        return ResultVO.success("删除成功！");

    }

    /*** 批量寄出****/
    @RequestMapping("/expressByBatchId")
    @ResponseBody
    public ResultVO expressByBatchId(@RequestBody Map<String,Object> param){
       try{
           List<String> list = (List<String>) param.get("id");
           Map<String,String> map =  (Map<String,String>)param.get("invoiceApply");
           InvoiceApply invoiceApply = new InvoiceApply();
           invoiceApply.setExpressDate(map.get("expressDate"));
           invoiceApply.setExpressCompany(map.get("expressCompany"));
           invoiceApply.setExpressNumber(map.get("expressNumber"));
           invoiceApply.setExpressRemark(map.get("expressRemark"));
           invoiceApply.setReturnDate(map.get("returnDate"));
           invoiceApply.setReturnExpressCompany(map.get("returnExpressCompany"));
           invoiceApply.setReturnExpressNumber(map.get("returnExpressNumber"));
           invoiceApply.setReturnReason(map.get("returnReason"));
           for (String id:list) {
               invoiceApply.setId(id);
               ageInvoiceApplyService.updateExpressInfo(invoiceApply);
           }
       }catch (Exception e){
           return ResultVO.fail("操作失败！");
       }
        return ResultVO.success("操作成功！");
    }


    /***使用md5进行加密****/
    private String encodeByMd5(String str) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        System.out.println(md);
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * 保存发票云传送的数据
     */
    @RequestMapping("/saveInvoiceInfo")
    @ResponseBody
    public ResultVO saveInvoiceInfo(@RequestBody Map<String,Object> invoiceDate){
        LOGGER.info("====================初审发票信息:============="+invoiceDate);
        List<Map<String,Object>> list = (List<Map<String,Object>>)invoiceDate.get("invoiceData");
        String agentId = getAgentId();
        try{
            ageInvoiceApplyService.saveInvoiceApply(list,agentId);
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.fail("保存失败！");
        }
        return ResultVO.success("保存成功！");
    }

    /**
     * 财务端审核发票
     * @param invoiceDate
     * @return
     */
    @RequestMapping("/finalCheckInvoice")
    @ResponseBody
    public ResultVO finalCheckInvoice(@RequestBody Map<String,Object> invoiceDate){
        LOGGER.info("====================终审发票信息:============="+invoiceDate);
        try{
            List<Map<String,Object>> list = (List<Map<String,Object>>)invoiceDate.get("invoiceData");
            List<Map<String,String>> results = ageInvoiceApplyService.finalCheckInvoice(list);
            if(results.size() >0 ){
                String str = "";
                for (Map<String,String> map:results ) {
                    str += "发票号："+map.get("invoiceCode")+" ,"+"错误原因:"+map.get("errorInfo")+";   ";
                }
                return ResultVO.fail(str);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.fail("审核失败！");
        }
        return ResultVO.success("审核成功！");
    }

    private static String PATH = "D:/upload/";
    private static String TITLE = "代理商唯一编码,代理商名称,开票公司,销售方公司名称,开票日期,货物类型,发票号,发票编码,价,税,价税合计,税率,终审结果,终审日期,快递单号";
    private static String COLUME = "AGENTID,AGENTNAME,INVOICECOMPANY,SALLERNAME,INVOICEDATE,INVOICEITEM,INVOICENUMBER,INVOICECODE,AMOUNT,AMOUNTTAX,SUMAMT,TAX,ESRESULT,ESDATE,EXPRESSNUMBER";
    /**
     * 导出
     */
    @RequestMapping("doDownload")
    public void doDownload(HttpServletResponse response,InvoiceApply invoiceApply)throws Exception{
        List<Map<String,Object>> mapList = ageInvoiceApplyService.exports(invoiceApply);
        Map<String, Object> param = new HashMap<>(10);
        param.put("path", PATH);
        param.put("title", TITLE);
        param.put("column", COLUME);
        param.put("dataList", mapList);
        param.put("response", response);
        com.ryx.credit.common.util.ExcelUtil.downLoadExcel(param);
    }


}
