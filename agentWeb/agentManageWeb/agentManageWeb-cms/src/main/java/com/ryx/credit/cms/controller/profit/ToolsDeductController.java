package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangmx
 * @desc 机具押金分期扣款调整
 */
@Controller
@RequestMapping("/toolsDeduct")
public class ToolsDeductController extends BaseController {

    private static Logger LOG = LoggerFactory.getLogger(ToolsDeductController.class);
    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;
    @Autowired
    private ToolsDeductService toolsDeductService;
    @Autowired
    private IAgentRelateService agentRelateService;
    @Autowired
    private StagingService stagingServiceImpl;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;
    private static final String FINANCE ="finance";
    private static final String AGENT = "agent";

    private String UPLOAD_PATH = AppConfig.getProperty("picture.path");
    private String SUPPLY = "supply";

    @RequestMapping("/pageList")
    public String rewardTempPage() {
        return "profit/toolsDeduct/toolsDeductList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getToolsDeductList(HttpServletRequest request, ProfitDeduction profitDeduction){
        Page page = pageProcess(request);
        if(profitDeduction != null){
            profitDeduction.setDeductionType("02");
        }
        Long userId = getUserId();
        String agentId = getAgentId();
        LOG.info("userId编号：{}，代理商编号：{}", userId, agentId);
        List<Map<String, Object>>  list = userService.orgCode(userId);
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        LOG.info("部门编号：{}", map.get("ORGANIZATIONCODE"));
        if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)){
            map = null;
        } else if (Objects.equals(map.get("ORGANIZATIONCODE"), AGENT)) {
            map = null;
            profitDeduction.setAgentId(agentId);
        }
        return profitDeductionServiceImpl.getProfitDeductionList(map ,profitDeduction, page);
    }

    @RequestMapping("/editPage")
    public ModelAndView getToolsDeduct(String id){
        if(StringUtils.isNotBlank(id)){
            ProfitDeduction profitDeduction = profitDeductionServiceImpl.getProfitDeductionById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profit/toolsDeduct/toolsDeductEdit");
            modelAndView.addObject("profitDeduction" ,profitDeduction);
            return modelAndView;
        }
        return null;
    }


    /**
     * 创建补款审批审批流
     * @param pToolSupplys
     * chen liang
     */
    public Object toolSupplyCreate(List<PToolSupply> pToolSupplys,String type,PRemitInfo pRemitInfo){
        if(pToolSupplys == null ){
            return  renderError("系统异常，请联系维护人员！");
        }
        try {
            for (PToolSupply pToolSupply:pToolSupplys ) {
                if(pToolSupply.getRemitAmt().compareTo(pToolSupply.getToolsInvoiceAmt()) == 1){
                    return renderError("申请失败，补款总金额金额大于总扣款金额。");
                }
            }
            String workId = null;
            if(type !=null && type.equals("toolSupplyCity")) {
                //省区补款
                workId = "toolSupplyCity";
            }else if(type !=null && type.equals("toolSupplyUp")){
                //上级代扣
                workId = "toolSupplyUp";
                pRemitInfo = null;
            }else {
                return  renderError("系统异常，请联系维护人员！");
            }
            toolsDeductService.applySupplystment(pToolSupplys, String.valueOf(getUserId()), workId, pRemitInfo);
            return  renderSuccess("补款申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("系统异常，请联系维护人员！");
        }

    }


    /**
     * 补款审批修改申请信息
     * @param pToolSupplys
     * chen liang
     */
    @RequestMapping("/toolSupplyEdit")
    @ResponseBody
    public Object toolSupplyEdit(HttpServletRequest request, List<PToolSupply> pToolSupplys,PRemitInfo pRemitInfo){

        try {
            if (pToolSupplys == null) {
                return renderError("系统异常，请联系维护人员！");
            }
            for (PToolSupply pToolSupply : pToolSupplys) {
                ProfitDeduction profitDeduction = toolsDeductService.selectByPrimaryKey(pToolSupply.getDeductionId());
                if (profitDeduction.getStagingStatus().equals("5")) {
                    return renderError("系统已自动扣款，扣款金额已经不能重新补款");
                }
                BigDecimal sumSupplyAMT = pToolSupply.getMonthProfitAmt().add(pToolSupply.getRemitAmt()).add(pToolSupply.getParenterSupplyAmt());
                if (sumSupplyAMT.compareTo(pToolSupply.getToolsInvoiceAmt()) == 1) {
                    return renderError("申请失败，补款总金额金额大于总扣款金额。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String detailId = request.getParameter("detailId");
            LOG.info("机具扣款补款审批修改，团ID：{}", detailId);
            LOG.info("机具扣款补款审批修改，扣款ID：{}", pToolSupplys.stream().map(PToolSupply::getDeductionId));

            toolsDeductService.editToolSupply(pToolSupplys,detailId,pRemitInfo);
            return  renderSuccess("申请信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");



    }





    /**
     * 创建调整审批流审批流
     * @param profitDeduction
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object editToolsDeduct(ProfitDeduction profitDeduction){
        if(profitDeduction == null ){
            return  renderError("系统异常，请联系维护人员！");
        }
        try {
            if(profitDeduction.getMustDeductionAmt().compareTo(profitDeduction.getSumDeductionAmt()) == 1){
                return renderError("申请失败，申请金额大于总扣款金额。");
            }
            if(profitDeduction.getMustDeductionAmt().compareTo(profitDeduction.getSumDeductionAmt()) == 0){
                return renderError("请重新输入调整金额。");
            }
            Set<String> roles = getShiroUser().getRoles();
            String workId = null;
            if(roles !=null && roles.contains("代理商")) {
                workId = "toolsInstallmentAgent";
            }else {
                workId = "toolsInstallmentCity";
            }
            toolsDeductService.applyAdjustment(profitDeduction, String.valueOf(getUserId()), workId);
            return  renderSuccess("调整申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    /**
     * 审批修改申请信息
     */
    @RequestMapping("/editToolDeduct")
    @ResponseBody
    public Object editToolDeduct(HttpServletRequest request, ProfitDeduction profitDeduction){
        if(profitDeduction == null ){
            return  renderError("系统异常，请联系维护人员！");
        }
        if(profitDeduction.getStagingStatus().equals("5")){
            return  renderError("系统已自动扣款，扣款金额已经不能重新调整");
        }
        try {
            if (profitDeduction.getMustDeductionAmt().compareTo(profitDeduction.getSumDeductionAmt()) == 1) {
                return renderError("保存失败，申请金额大于总扣款金额。");
            }
            if (profitDeduction.getMustDeductionAmt().compareTo(profitDeduction.getSumDeductionAmt()) == 0) {
                return renderError("请重新输入调整金额。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            String detailId = request.getParameter("detailId");
            LOG.info("机具扣款审批修改，明细ID：{}", detailId);
            LOG.info("机具扣款审批修改，扣款ID：{}", profitDeduction.getId());

            toolsDeductService.editToolDeduct(profitDeduction, detailId);
            return  renderSuccess("申请信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    @RequestMapping("/detail/page")
    public ModelAndView showDetailPage(HttpServletRequest request, @RequestParam(value = "id") String id){
        if(StringUtils.isNotBlank(id)){
            Page page = pageProcess(request);
            ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
            profitStagingDetail.setStagId(id);
            PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail, page);
            List<ProfitStagingDetail> list = pageInfo.getRows();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profit/toolsDeduct/toolsAdjustDetailList");
            if(list != null && !list.isEmpty()){
                modelAndView.addObject("profitDeduction", list.get(0));
                return modelAndView;
            }
        }
        return null;
    }

    @RequestMapping("/deduct/detail")
    public ModelAndView deductDetailPage(HttpServletRequest request, @RequestParam(value = "id") String id){
        Page page = pageProcess(request);
        ProfitDeduction detail = new ProfitDeduction();
        detail.setId(id);
        List<ProfitDeducttionDetail> list = profitDeducttionDetailService.getDeducttionDetailList(detail);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profit/toolsDeduct/toolsDeductDetailList");
        if(list != null && !list.isEmpty()){
            modelAndView.addObject("deductList", list);
            return modelAndView;
        }
        return null;
    }

    /**
     * 获取关联/担保扣款明细
     */
    @RequestMapping("toRevpage")
    public ModelAndView getRev1List(String id){
        List<Map<String,Object>> list1 =  profitDeductionServiceImpl.getRev1DetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rev1List",list1);
        modelAndView.setViewName("profit/toolsDeduct/rev1Detail");
        return modelAndView;

    }

    @RequestMapping("toRecoupAndDeduct")
    public ModelAndView toRecoupAndDeduct(ProfitDeduction profitDeduction){
        ModelAndView modelAndView = new ModelAndView();
        List<Map<String,Object>> list = profitDeductionServiceImpl.getPorfitDataByAgentIdAndProfitMonth(profitDeduction.getAgentId(),profitDeduction.getDeductionDate());
        for (Map<String,Object> map:list){
            if (map.get("PARENT_AGENT_ID")!=null&&!"".equals(map.get("PARENT_AGENT_ID"))){
                List<String> relateAgents=agentRelateService.getRelateAgentIdByAgentIdAndTime(profitDeduction.getAgentId(),profitDeduction.getDeductionDate());
                for (int i = 0; i <relateAgents.size(); i++) {
                    if (relateAgents.get(i).equals(map.get("PARENT_AGENT_ID"))){
                        map.put("hasParentAgent",true);
                        break;
                    }
                }
            }
            map.put("repaymentCycle",((BigDecimal)map.get("MUST_DEDUCTION_AMT")).divide((BigDecimal)map.get("PROFIT_AMT"),2));
        }
        modelAndView.addObject("profitDatas",list);
        modelAndView.addObject("agentId",profitDeduction.getAgentId());
        modelAndView.setViewName("profit/toolsDeduct/supplyOrDeduction/addRecoupAndDeduct");
        return modelAndView;
    }
    @PostMapping("/addRecoupAndDeduct")
    @ResponseBody
    public Object addRecoupAndDeduct(@RequestParam(value = "annexFile",required = false) MultipartFile file, HttpServletRequest request){
        //examinrId 审批流标识 用作补款文件名、补款明细审批标识、打款审批标识
        String examinrId=UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String[] p_deduction_ids=request.getParameter("P_DEDUCTION_ID").split(",");
        String[] profit_dates=request.getParameter("PROFIT_DATE").split(",");
        String[] agent_names=request.getParameter("AGENT_NAME").split(",");
        String[] agent_ids=request.getParameter("AGENT_ID").split(",");
        String[] parent_agent_names=request.getParameter("PARENT_AGENT_NAME").split(",");
        String[] parent_agent_ids=request.getParameter("PARENT_AGENT_ID").split(",");
        String[] bus_codes=request.getParameter("BUS_CODE").split(",");
        String[] c_incom_times=request.getParameter("C_INCOM_TIME").split(",");
        String[] must_deduction_amts=request.getParameter("MUST_DEDUCTION_AMT").split(",");
        String[] profit_amts=request.getParameter("PROFIT_AMT").split(",");
        String[] repaymentCycles=request.getParameter("repaymentCycle").split(",");
        String[] remit_amts=request.getParameter("REMIT_AMT").split(",");
        String[] isDeductParentAgent=request.getParameter("isDeductParentAgent").split(",");
        String paymentAccountType=request.getParameter("paymentAccountType");
        String paymentAccountName=request.getParameter("paymentAccountName");
        String paymentAccount=request.getParameter("paymentAccount");
        String paymentBank=request.getParameter("paymentBank");
        String payAmt=request.getParameter("payAmt");
        String paymentDate=request.getParameter("paymentDate");
        List<PToolSupply> pToolSupplyList=new ArrayList<>();
        String userId=getStringUserId();
        for (int i = 0; i < p_deduction_ids.length; i++) {
            PToolSupply pToolSupply=new PToolSupply();
            pToolSupply.setDeductionId(p_deduction_ids[i]);
            pToolSupply.setProfitDate(profit_dates[i]);
            pToolSupply.setAgentName(agent_names[i]);
            pToolSupply.setAgentId(agent_ids[i]);
            pToolSupply.setParenterAgentName(parent_agent_names[i]);
            pToolSupply.setParenterAgentId(parent_agent_ids[i]);
            pToolSupply.setBusCode(bus_codes[i]);
            pToolSupply.setNetInDate(c_incom_times[i]);
            pToolSupply.setToolsInvoiceAmt(new BigDecimal(must_deduction_amts[i]));
            pToolSupply.setMonthProfitAmt(new BigDecimal(profit_amts[i]));
            pToolSupply.setRepaymentPeriod(new BigDecimal(repaymentCycles[i]));
            pToolSupply.setRemitAmt(new BigDecimal(remit_amts[i]));
            pToolSupply.setParenterSupplyAmt(BigDecimal.ZERO);
            pToolSupply.setcUser(userId);
            pToolSupply.setcTime(new Date());
            /*pToolSupply.setExaminrStatus("00");*/
            pToolSupply.setExaminrId(examinrId);
            if (!"".equals(parent_agent_ids[i])){
                if ("true".equals(isDeductParentAgent[i])){
                    pToolSupply.setParenterSupplyAmt(new BigDecimal("1"));
                }else {
                    pToolSupply.setParenterSupplyAmt(BigDecimal.ZERO);
                }
            }
            /*if(pToolSupply.getRemitAmt().compareTo(BigDecimal.ZERO)==0&&pToolSupply.getParenterSupplyAmt().compareTo(BigDecimal.ZERO)==0){
                continue;
            }*/
            pToolSupplyList.add(pToolSupply);
        }
        if ("true".equals(request.getParameter("isSupply"))){   //是否含有线下补款
            String realPath = UPLOAD_PATH;
            String fileName=null;
            String filePath=null;
            if (file!=null){
                try{
                    if(file != null){
                        fileName=file.getOriginalFilename();
                        fileName=fileName.split("\\.")[1];
                        fileName=examinrId+"."+fileName;
                        String date = new SimpleDateFormat("yyyyMM").format(new Date());
                        realPath = realPath+SUPPLY+ File.separator+date+File.separator;
                        File file1 = new File(realPath);
                        if(!file1.exists()){
                            file1.mkdirs();
                        }
                        filePath=realPath+fileName;
                        file.transferTo(new File(filePath));
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    return renderError("附件上传失败！");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                return renderError("附件异常！");
            }
            PRemitInfo pRemitInfo=new PRemitInfo();
            pRemitInfo.setInAccountType(paymentAccountType);
            pRemitInfo.setInAccountName(paymentAccountName);
            pRemitInfo.setOutAccount(paymentAccount);
            pRemitInfo.setOutAccountBank(paymentBank);
            pRemitInfo.setRemitAmt(new BigDecimal(payAmt));
            pRemitInfo.setFileName(fileName);
            pRemitInfo.setFilePath(filePath);
            pRemitInfo.setCitySupplyId(examinrId);
            try {
                pRemitInfo.setRemitDate(new SimpleDateFormat("yyyy-MM-dd").parse(paymentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Object result = toolSupplyCreate(pToolSupplyList, "toolSupplyCity", pRemitInfo);
            return result;
        }else{
            String supplyAndDeductRemark=request.getParameter("supplyAndDeductRemark");
            for (PToolSupply pToolSupply:pToolSupplyList){
                pToolSupply.setRev1(supplyAndDeductRemark);
            }
            Object result = toolSupplyCreate(pToolSupplyList, "toolSupplyUp", null);
            return result;
        }
    }
    @PostMapping("/updateRecoupAndDeduct")
    @ResponseBody
    public Object updateRecoupAndDeduct(@RequestParam(value = "annexFile",required = false) MultipartFile file, HttpServletRequest request){
        //examinrId 审批流标识 用作补款文件名、补款明细审批标识、打款审批标识
        String examinrId=request.getParameter("citySupplyId");
        String[] remit_amts=request.getParameter("REMIT_AMT").split(",");
        String paymentAccountType=request.getParameter("paymentAccountType");
        String paymentAccountName=request.getParameter("paymentAccountName");
        String paymentAccount=request.getParameter("paymentAccount");
        String paymentBank=request.getParameter("paymentBank");
        String payAmt=request.getParameter("payAmt");
        String paymentDate=request.getParameter("paymentDate");
        PToolSupply example=new PToolSupply();

        example.setExaminrId(request.getParameter("citySupplyId"));
        List<PToolSupply> pToolSupplyList=toolsDeductService.selectByExample(example);
        for (int i = 0; i < pToolSupplyList.size(); i++) {
            pToolSupplyList.get(i).setRemitAmt(new BigDecimal(remit_amts[i]));
        }
        if ("true".equals(request.getParameter("isSupply"))){   //是否含有线下补款
            PRemitInfo pRemitInfo=toolsDeductService.brCitySupplyId(examinrId).get(0);
            pRemitInfo.setInAccountType(paymentAccountType);
            pRemitInfo.setInAccountName(paymentAccountName);
            pRemitInfo.setOutAccount(paymentAccount);
            pRemitInfo.setOutAccountBank(paymentBank);
            pRemitInfo.setRemitAmt(new BigDecimal(payAmt));
            pRemitInfo.setCitySupplyId(examinrId);
            if (file!=null){
                try{
                    if(file != null){
                        String fileName=file.getOriginalFilename();
                        fileName=fileName.split("\\.")[1];//新文件后缀
                        fileName=examinrId+"."+fileName;//组成新的文件名(后缀替换)
                        String filePath=pRemitInfo.getFilePath().replaceAll(pRemitInfo.getFileName(),"");
                        File file1 = new File(filePath);
                        if(!file1.exists()){
                            file1.mkdirs();
                        }
                        filePath=pRemitInfo.getFilePath().replaceAll(pRemitInfo.getFileName(),fileName);
                        file.transferTo(new File(filePath));
                        pRemitInfo.setFilePath(filePath);
                        pRemitInfo.setFileName(fileName);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    return renderError("附件上传失败！");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                return renderError("附件异常！");
            }
            try {
                pRemitInfo.setRemitDate(new SimpleDateFormat("yyyy-MM-dd").parse(paymentDate));
            } catch (ParseException e) {
                e.printStackTrace();
                return  ResultVO.success("文件保存失败，详情见日志。");
            }
            toolsDeductService.editToolSupply(pToolSupplyList,examinrId,pRemitInfo);
        }else{
            String supplyAndDeductRemark=request.getParameter("supplyAndDeductRemark");
            for (PToolSupply pToolSupply:pToolSupplyList){
                pToolSupply.setRev1(supplyAndDeductRemark);
            }
            toolsDeductService.editToolSupply(pToolSupplyList,examinrId,null);
        }
        return ResultVO.success("保存成功！");
    }

    //查看打款附件
    @RequestMapping("/toExamineAnnexFile")
    public Object examineAnnexFile(HttpServletRequest request){
        ModelAndView view=new ModelAndView();
        String path=request.getParameter("annexFilePath");
        view.addObject("annexFilePath",path);
        view.setViewName("profit/toolsDeduct/supplyOrDeduction/examineAnnexFile");
        return view;
    }

    @RequestMapping(value="/examineAnnexFile" )
    public void getImg(String imgPathEncode, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg");
        String imgPath = URLDecoder.decode(imgPathEncode, "utf-8");
        File file = new File(imgPath);
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            while(in.read(b)!= -1) {
                os.write(b);
            }
            in.close();
            os.flush();
            os.close();
        }
    }


    /**
     *跳转补款提示页面
     */
    @RequestMapping("toSupplyPromptListPage")
    public String toSupplyPromptList(){
        return "profit/toolsDeduct/SupplyPromptList";
    }

    /**
     * 补款提示-----查询
     */
    @RequestMapping("getSupplyPromptList")
    @ResponseBody
    public Object getSupplyPromptList(HttpServletRequest request,ProfitDeduction profitDeduction){
        Page page = pageProcess(request);
        profitDeduction.setStagingStatus("0,1,3,4,01");
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        LOG.info("部门编号：{}", map.get("ORGANIZATIONCODE"));
        if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)){
            map = null;
        } else if (Objects.equals(map.get("ORGANIZATIONCODE"), AGENT)) {
            map = null;
            profitDeduction.setAgentId(getAgentId());
        }
        return toolsDeductService.getSupplyPromptList(map,page,profitDeduction);
    }
}
