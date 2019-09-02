package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.order.OCashReceivablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author RYX
 * @Date 2019/1/26
 * @Desc 代理商退出
 */
@RequestMapping("agentQuit")
@Controller
public class AgentQuitController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitController.class);
    @Autowired
    private AgentQuitService agentQuitService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private TaskApprovalService taskApprovalService;


    @RequestMapping(value = "toAgentQuitPage")
    public String toAgentQuitPage(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("agentId", getAgentId());
        return "agent/quit/agentQuitList";
    }

    /**
     * 退出列表
     * @param request
     * @param agentQuit
     * @return
     */
    @RequestMapping(value = "getAgentQuitList")
    @ResponseBody
    public Object getAgentQuitList(HttpServletRequest request, AgentQuit agentQuit) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            agentQuit.setAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = agentQuitService.queryAgentQuitList(agentQuit, page, dataRole, getUserId());
        return pageInfo;
    }


    public void selectAll(String id, Model model, HttpServletRequest request,String busId) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        List<Capital> capitals = agentQuitService.queryCapital(id);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(id);
        List<AgentBusInfo> agentBusInfos = null;
        if(StringUtils.isBlank(busId)){
            agentBusInfos = agentQueryService.businessQuery(id,request.getParameter("isZpos"),getUserId());
        }else{
            agentBusInfos = agentQuitService.getBusInfosById(busId);
        }
        model.addAttribute("agent", agent);
        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("capitals", capitals);
        model.addAttribute("agentContracts", agentContracts);
        model.addAttribute("agentBusInfos", agentBusInfos);
        BigDecimal subAgentOweTicket = agentMergeService.getSubAgentOweTicket(id);
        BigDecimal subAgentDebt = agentMergeService.getSubAgentDebt(id);
        BigDecimal profitDebt = agentMergeService.profitDebt(id);
        BigDecimal orderDebt = agentMergeService.getOrderDebt(id);
        BigDecimal capitalDebt = agentMergeService.getCapitalDebt(id);
        Map<String,Object> suppMap = agentQuitService.calculateSuppDept(id);
        BigDecimal capitalSumAmt = agentQuitService.getCapitalSumAmt(id);//保证金可用金额
        model.addAttribute("subAgentOweTicket",subAgentOweTicket);
        model.addAttribute("subAgentDebt", suppMap.get("subAgentDebt")==null?subAgentDebt:suppMap.get("subAgentDebt"));
        model.addAttribute("profitDebt", suppMap.get("profitDebt")==null?profitDebt:suppMap.get("profitDebt"));
        model.addAttribute("orderDebt", suppMap.get("orderDebt")==null?orderDebt:suppMap.get("orderDebt"));
        model.addAttribute("capitalDebt", suppMap.get("capitalDebt")==null?capitalDebt:suppMap.get("capitalDebt"));
        model.addAttribute("suppMap", suppMap);
        model.addAttribute("capitalSumAmt", capitalSumAmt);
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getAllOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        optionsData(request);
        request.setAttribute("quitPlatformList", QuitPlatform.getContentMap());
        List<PayComp> payCompList = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
    }

    @RequestMapping(value = "toAgentQuitSave")
    public String toAgentQuitSave(Model model,HttpServletRequest request) {
        String agentId = request.getParameter("agentId");
        if(StringUtils.isBlank(agentId)){
            agentId = getAgentId();
        }
        if(StringUtils.isBlank(agentId)){
            request.setAttribute("error","缺少代理商ID");
            return "error/error";
        }
        selectAll(agentId, model, request,"");
        return "agent/quit/agentQuitSave";
    }


    @RequestMapping(value = "agentQuitSave")
    @ResponseBody
    public Object agentQuitSave(HttpServletRequest request,AgentQuit agentQuit) {

        try {
            String flag = request.getParameter("flag");
            String[] agentQuitFile = request.getParameterValues("agentQuitFile");
            String payTemplet = request.getParameter("payTemplet");
            List<OCashReceivablesVo> oCashReceivables = JsonUtil.jsonToList(payTemplet, OCashReceivablesVo.class);
            AgentResult agentResult = agentQuitService.saveAgentQuit(agentQuit,agentQuitFile,getStringUserId(),flag,oCashReceivables);
            if (agentResult.isOK()) {
                return renderSuccess("提交成功！");
            } else {
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @RequestMapping(value = "toAgentQuitQuery")
    public String toAgentQuitQuery(Model model,HttpServletRequest request) {
        String id = request.getParameter("id");
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(id);
        selectAll(agentQuit.getAgentId(), model, request,id);
        request.setAttribute("agentQuit",agentQuit);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.AGENTQUIT,id, null);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("cloReviewStatus",agentQuit.getCloReviewStatus());
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.QUIT.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.QUIT.name(), AgStatus.getAgStatusString(agentQuit.getCloReviewStatus()));
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        return "agent/quit/agentQuitQuery";
    }

    @RequestMapping(value = "toAgentQuitApproval")
    public String toAgentQuitApproval(Model model,HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        String sid = request.getParameter("sid");
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(busId);
        selectAll(agentQuit.getAgentId(), model, request,busId);
        request.setAttribute("agentQuit",agentQuit);
        //审批参数
        List<Dict> MERGE_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.QUIT_MARKET.name());
        request.setAttribute("quit_param", MERGE_MARKET);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.AGENTQUIT,busId, null);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("paylist_approve", listoCashReceivables);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList", maps);
        request.setAttribute("proIns", proIns);
        request.setAttribute("taskId", taskId);
        request.setAttribute("busId", busId);
        List<Dict> deadlineList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.QUIT_MARKET_DEADLINE.name());
        request.setAttribute("deadlineList", deadlineList);
        List<Dict> migrationPlatforms = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.QUIT_MIGR_PLATFORM.name());
        request.setAttribute("migrationPlatforms", migrationPlatforms);
        request.setAttribute("sid", sid);
        return "agent/quit/agentQuitTask";
    }


    /**
     * 处理任务
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "quitTaskApproval")
    public ResultVO quitTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            result = agentQuitService.approvalAgentQuitTask(agentVo, String.valueOf(getUserId()), agentVo.getAgentBusId());
        } catch (MessageException e) {
            logger.info("taskApproval处理任务异常：" + e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        }catch (ProcessException e) {
            logger.info("taskApproval处理任务异常：" + e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("taskApproval处理任务异常:：" + e.getMessage());
            e.printStackTrace();
            result = AgentResult.fail("处理失败！");
        } finally {
            if(result == null){
                return ResultVO.fail("处理失败！");
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功！");
            } else {
                return ResultVO.fail("处理失败：" + result.getMsg());
            }
        }
    }


    /**
     * 提交审批
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "quitStartActivity")
    @ResponseBody
    public Object quitStartActivity(HttpServletRequest request) {
        try {
            String busId = request.getParameter("busId");
            AgentResult agentResult = agentQuitService.startAgentMergeActivity(busId, getStringUserId(), false);
            if (agentResult.isOK()) {
                return renderSuccess("提交成功！");
            } else {
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "toAgentQuitEdit")
    public String toAgentQuitEdit(Model model,HttpServletRequest request) {
        String busId = request.getParameter("busId");
        String index = request.getParameter("index");//1暂存修改2审批修改
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(busId);
        selectAll(agentQuit.getAgentId(),model,request,busId);
        request.setAttribute("agentQuit", agentQuit);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.AGENTQUIT,busId, null);
        request.setAttribute("paylist", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("index", index);
        return "agent/quit/agentQuitEdit";
    }

    /**
     * 修改数据
     * @param request
     * @return
     */
    @RequestMapping(value = "editAgentQuit")
    @ResponseBody
    public Object editAgentQuit(HttpServletRequest request,AgentQuit agentQuit){
        try {
            String[] agentQuitFile = request.getParameterValues("agentQuitFile");
            String payTemplet = request.getParameter("payTemplet");
            List<OCashReceivablesVo> oCashReceivables = JsonUtil.jsonToList(payTemplet, OCashReceivablesVo.class);
            AgentResult agentResult = agentQuitService.editAgentQuit(agentQuit,getStringUserId(),agentQuitFile,oCashReceivables);
            if (agentResult.isOK()) {
                return renderSuccess("修改成功！");
            } else {
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }


    @RequestMapping(value = "agentQuitDelete")
    @ResponseBody
    public Object agentQuitDelete(HttpServletRequest request){
        try {
            String busId = request.getParameter("busId");
            if (StringUtils.isBlank(busId)) {
                logger.info("代理商退出删除，合并ID为空:{}", busId);
                return AgentResult.fail("代理商退出删除，合并ID为空！");
            }
            AgentResult agentResult = agentQuitService.deleteAgentQuit(busId, getStringUserId());
            if(agentResult.isOK()){
                return renderSuccess("删除成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("删除失败！");
        }
    }


    /**
     * 查看解除合同
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "toAgentQuitUpload")
    public String toAgentQuitUpload(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(id);
        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.agentQuitUpload.name());
        request.setAttribute("agentQuit", agentQuit);
        model.addAttribute("attachments", attachment);
        return "agent/quit/agentQuitUpload";
    }

    /**
     * 上传解除合同
     * @param request
     * @param agentQuit
     * @return
     */
    @RequestMapping(value = "agentQuitUploadRtc")
    @ResponseBody
    public Object agentQuitUpload(HttpServletRequest request, AgentQuit agentQuit){
        try {
            String[] agentQuitFile = request.getParameterValues("agentQuitFile");
            AgentResult agentResult = agentQuitService.agentQuitUploadRtc(agentQuit, getStringUserId(), agentQuitFile);
            if (agentResult.isOK()) {
                return renderSuccess("解除合同上传成功！");
            } else {
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

}
