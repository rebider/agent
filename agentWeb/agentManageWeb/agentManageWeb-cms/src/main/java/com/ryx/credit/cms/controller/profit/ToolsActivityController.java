package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.JsonUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.data.AttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangmx
 * @desc 机具押金分期扣款调整
 */
@Controller
@RequestMapping("/toolsActivity")
public class ToolsActivityController  extends BaseController {
    @Autowired
    private ActHiVarinstService actHiVarinstService;
    @Autowired
    private IUserService userService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;
    @Autowired
    private ToolsDeductService toolsDeductService;
    @Autowired
    private StagingService stagingServiceImpl;
    @Autowired
    private IAgentRelateService agentRelateService;
    @Autowired
    StagingService stagingService;
    private String UPLOAD_PATH = AppConfig.getProperty("picture.path");
    private String SUPPLY = "supply";

    /**
     * 进入待审批页面
     * @param request
     * @param response
     * @param model
     * @return 加载分期审批页面
     */
    @RequestMapping("/toTaskApproval")
    public String toTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String taskId = request.getParameter("taskId");

        String proIns = request.getParameter("proIns");
        String thawId = request.getParameter("busId");
        optionsData(request);


        ProfitStagingDetail detail = toolsDeductService.getProfitStagingDetail(thawId);
        model.addAttribute("profitStagingDetail", detail);

        if (detail !=null) {
            ProfitDeduction profitDeduction = profitDeductionServiceImpl.getProfitDeductionById(detail.getStagId());
            BigDecimal actAmt = profitDeduction.getMustDeductionAmt().subtract(detail.getRealAmt());
            profitDeduction.setActualDeductionAmt(actAmt);
            model.addAttribute("profitDeduction", profitDeduction);
            model.addAttribute("approvalDeduceAmt", profitDeduction.getSumDeductionAmt().subtract(detail.getMustAmt()).toString());
        }

        //审批参数
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
        List<Dict> TOOLS_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_MARKET.name());
        List<Dict> TOOLS_APR_BOSS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BOSS.name());
        List<Dict> TOOLS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_EXPAND.name());
        List<Dict> POS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.POS.name(), DictGroup.POS_APR_BUSNISS.name());

        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        request.setAttribute("POS_APR_BUSNISS", POS_APR_BUSNISS);
        request.setAttribute("tools_apr_market", TOOLS_APR_MARKET);
        request.setAttribute("tools_apr_boss", TOOLS_APR_BOSS);
        request.setAttribute("tools_apr_expand", TOOLS_APR_EXPAND);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        Map<String,Object> mapTask = null;
        if(taskId!=null){
            mapTask = stagingServiceImpl.byTaskId(taskId);
            if ((mapTask.get("NAME_").toString().indexOf("修改")==-1)&&(mapTask.get("GROUP_ID_").toString().equals("city"))) {

                return "profit/toolsDeduct/normalTaskApproval";
            }else{
                return "profit/toolsDeduct/taskApproval";
            }
        }
        return "profit/toolsDeduct/taskApproval";
    }

    /**
     * 进入待审批页面
     * @param request
     * @param response
     * @param model
     * @return 加载省区补款审批页面
     */
    @RequestMapping("/toToolSupplyTaskApproval")
    public String toToolSupplyTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String thawId = request.getParameter("busId");
        optionsData(request);

        PToolSupply pToolSupply1 = new PToolSupply();
        if (thawId!=null) {
            pToolSupply1.setExaminrId(thawId);
        }
        List<PToolSupply> pToolSupplies = toolsDeductService.selectByExample(pToolSupply1);
        model.addAttribute("pToolSupplies", pToolSupplies);
        model.addAttribute("supplyAndDeductRemark", pToolSupplies.get(0).getRev1());
        model.addAttribute("citySupplyId", pToolSupplies.get(0).getExaminrId());
        List<PRemitInfo> pRemitInfos =  toolsDeductService.brCitySupplyId(thawId);
        if(pRemitInfos.size()==1){
            PRemitInfo pRemitInfo = pRemitInfos.get(0);
            String annexFilePath=pRemitInfo.getFilePath();
            annexFilePath=annexFilePath.replace('\\','/');
            model.addAttribute("pRemitInfos", pRemitInfo);
            model.addAttribute("annexFilePath", annexFilePath);
            model.addAttribute("remitDate", new SimpleDateFormat("yyyy-MM-dd").format(pRemitInfo.getRemitDate()));
        }

        //判断是否可以选择上级代扣
        for (PToolSupply pToolSupply:pToolSupplies){
            pToolSupply.setRev3("2");
            if (pToolSupply.getParenterAgentId()!=null&&!"".equals(pToolSupply.getParenterAgentId())){
                List<String> relateAgents=agentRelateService.getRelateAgentIdByAgentIdAndTime(pToolSupply.getAgentId(),pToolSupply.getProfitDate());
                for (int i = 0; i <relateAgents.size(); i++) {
                    if (relateAgents.get(i).equals(pToolSupply.getParenterAgentId())){
                        pToolSupply.setRev3("1");
                        break;
                    }
                }
            }
        }


        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
       /* Map<String,Object> mapTask = null;
        if(taskId!=null){
            mapTask = stagingServiceImpl.byTaskId(taskId);
            if ((mapTask.get("NAME_").toString().indexOf("修改")==-1)&&(mapTask.get("GROUP_ID_").toString().equals("city"))) {

                return "profit/toolsDeduct/normalTaskApproval";
            }else{
                return "profit/toolsDeduct/taskApproval";
            }
        }*/
        return "activity/supplyAndDeductionApproval";
    }






    /**
     * 查询审批记录
     * @param proIns
     */
    public List<Map<String,Object>> queryApprovalRecord(String proIns){
        ActHiVarinst actHiVarinst = new ActHiVarinst();
        actHiVarinst.setProcInstId(proIns);
        actHiVarinst.setName("_ryx_wq");
        HashMap<String, Object> actHiVarinstMap = actHiVarinstService.configExample(new Page(), actHiVarinst);
        List<Map<String,Object>> actRecordList = new ArrayList<>();
        List<ActHiVarinst> list = (List<ActHiVarinst>)actHiVarinstMap.get("list");
        for (ActHiVarinst hiVarinst : list) {
            Map map = JSONObject.parseObject(String.valueOf(hiVarinst.getText()));
            String approvalPerson = String.valueOf(map.get("approvalPerson"));
            CUser cUser = userService.selectById(approvalPerson);
            if(null!=cUser){
                map.put("approvalPerson",cUser.getName());
            }
            actRecordList.add(map);
        }
        return actRecordList;
    }


    /**
     * 处理省区补款或者线下补款任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/taskToolSupplyApproval")
    public ResultVO taskToolSupplyApproval(@RequestParam(value = "annexFile",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response,AgentVo agentVo){
        agentVo.setApprovalOpinion(request.getParameter("approvalOpinion"));
        agentVo.setApprovalResult(request.getParameter("approvalResult"));
        agentVo.setTaskId(request.getParameter("taskId"));
        if (file!=null){
            try{
                if(file != null){

                    PRemitInfo pRemitInfo=toolsDeductService.brCitySupplyId(request.getParameter("citySupplyId")).get(0);
                    String amtArrivalTime=request.getParameter("amtArrivalTime");
                    String amtArrivalUser=request.getParameter("amtArrivalUser");
                    String pRemitInfosId=pRemitInfo.getId();
                    pRemitInfo.setRev1(amtArrivalTime);
                    pRemitInfo.setRev2(amtArrivalUser);

                    String fileName=file.getOriginalFilename();
                    fileName=fileName.split("\\.")[1];
                    fileName=pRemitInfosId+"."+fileName;
                    File file1 = new File(pRemitInfo.getFilePath());
                    if(!file1.exists()){
                        file1.mkdirs();
                    }

                    String filePath=pRemitInfo.getFilePath();
                    filePath=filePath.replaceAll(pRemitInfo.getFileName(),fileName);

                    pRemitInfo.setRev3(filePath);
                    toolsDeductService.updateByPrimaryKey(pRemitInfo);
                    file.transferTo(new File(filePath));//写文件

                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        AgentResult result = null;
        try {
            result = toolsDeductService.approvalToolSupplyTask(agentVo, String.valueOf(getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(result==null){
                return ResultVO.fail("处理失败");
            }
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail("处理失败");
            }
        }
    }



    /**
     * 处理任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/taskApproval")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo){
        AgentResult result = null;
        try {
            result = toolsDeductService.approvalTask(agentVo, String.valueOf(getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(result==null){
                return ResultVO.fail("处理失败");
            }
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail("处理失败");
            }
        }
    }

    @RequestMapping("/gotoTaskApproval")
    public ModelAndView gotoTaskApproval(HttpServletRequest request, @RequestParam(value = "id") String id){
        ProfitStagingDetail profitStagingDetail= stagingService.selectByPrimaryKey(id);
        ModelAndView view = new ModelAndView();
        view.setViewName("profit/toolsDeduct/taskApprovalDetail");
        ProfitDeduction profitDeduction = profitDeductionServiceImpl.getProfitDeductionById(profitStagingDetail.getStagId());
        view.addObject("profitDeduction",profitDeduction);
        if(profitStagingDetail != null){
            view.addObject("profitStagingDetail",profitStagingDetail);
            view.addObject("approvalDeduceAmt", profitDeduction.getSumDeductionAmt().subtract(profitStagingDetail.getMustAmt()).toString());
            BusActRel busActRel = new BusActRel();
            busActRel.setBusId(profitStagingDetail.getId());
            BusActRel rel = null;
            try {
                rel = taskApprovalService.queryBusActRel(busActRel);
                if (rel != null) {
                    view.addObject("actRecordList",queryApprovalRecord(rel.getActivId()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        optionsData(request);
        return view;
    }

    /**
     * 查询申请审批记录
     */
    @RequestMapping("toApplyDetail")
    public String gotoTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String id = request.getParameter("id");
        List<ProfitStagingDetail> profitStagingDetails = toolsDeductService.getProfitStagingDetailByStagId(id);
        ProfitStagingDetail profitStagingDetail = null;
        if(profitStagingDetails.size() != 0){
            profitStagingDetail = profitStagingDetails.get(0);
        }
        if(profitStagingDetail != null){
            id = profitStagingDetail.getId();
        }
        //根据id获得对应信息
        BusActRel busActRel = new BusActRel();
        busActRel.setBusId(id);
        BusActRel rel = null;
        try {
            rel = taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                model.addAttribute("actRecordList", queryApprovalRecord(rel.getActivId()));
                model.addAttribute("proIns", rel.getActivId());
                List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
                model.addAttribute("approvalType", approvalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "profit/toolsDeduct/taskSPDetail";
    }

}
