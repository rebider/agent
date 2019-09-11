package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OperationType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.enumc.*;
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
import com.ryx.credit.service.order.IPaymentDetailService;
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
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/2/12.
 * 保证金变更申请
 */
@RequestMapping("capitalChange")
@Controller
public class CapitalChangeApplyController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CapitalChangeApplyController.class);
    @Autowired
    private CapitalChangeApplyService capitalChangeApplyService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private IPaymentDetailService paymentDetailService;


    @RequestMapping(value = "toCapitalChangePage")
    public String toCapitalChangePage(HttpServletRequest request) {
        logger.info("加载保证金变更列表页...");
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        List<Dict> colInfoType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        request.setAttribute("agentId", getAgentId());
        request.setAttribute("capitalType", capitalType);
        request.setAttribute("colInfoType", colInfoType);
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        return "agent/capital/capitalChangeList";
    }

    /**
     * 保证金列表
     * @param request
     * @param capitalChangeApply
     * @return
     */
    @RequestMapping(value = "getCapitalChangeList")
    @ResponseBody
    public Object getCapitalChangeList(HttpServletRequest request, CapitalChangeApply capitalChangeApply) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            capitalChangeApply.setAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = capitalChangeApplyService.queryCapitalChangeList(capitalChangeApply, page, dataRole, getUserId());
        return pageInfo;
    }

    public void selectAll(String id, Model model, HttpServletRequest request, String busId) {
        BigDecimal subAgentOweTicket = agentMergeService.getSubAgentOweTicket(id);
        BigDecimal sumDebt = paymentDetailService.getSumDebt(id);
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        model.addAttribute("subAgentOweTicket", subAgentOweTicket);
        model.addAttribute("sumDebt", sumDebt);
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        optionsData(request);
    }

    @RequestMapping(value = "toCapitalChangeAdd")
    public String toCapitalChangeAdd(Model model, HttpServletRequest request) {
        String agentId = request.getParameter("agentId");
        String ccType = request.getParameter("cType");
        String amount = request.getParameter("amount");
        Agent agents = agentService.getAgentById(agentId);
        AgentColinfo agentColinfos = agentColinfoService.selectByAgentId(agentId);
        List<Dict> colInfoType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        List<Dict> yesOrNo = ServiceFactory.dictOptionsService.dictList(DictGroup.ALL.name(), DictGroup.YESORNO.name());
        BigDecimal sumDebt = paymentDetailService.getSumDebt(agentId);
        request.setAttribute("sumDebt", sumDebt);
        request.setAttribute("agentId", agentId);
        request.setAttribute("ccType", ccType);
        Dict dict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name(), ccType);
        request.setAttribute("cTypeName", dict!=null?dict.getdItemname():"");
        request.setAttribute("amount",amount);
        request.setAttribute("operationTypeList",OperationType.getContentMap());
        request.setAttribute("agentName", agents.getAgName());
        request.setAttribute("agentColinfos", agentColinfos);
        request.setAttribute("colInfoType", colInfoType);
        request.setAttribute("yesOrNo", yesOrNo);
        selectAll(agentId, model, request,"");
        return "agent/capital/capitalChangeAdd";
    }

    @RequestMapping(value = "capitalChangeAdd")
    @ResponseBody
    public Object capitalChangeAdd(HttpServletRequest request,CapitalChangeApply capitalChangeApply) {
        try {
            String flag = request.getParameter("flag");
            String[] capitalChangeFiles = request.getParameterValues("capitalChangeFiles");
            String payTemplet = request.getParameter("payTemplet");
            List<OCashReceivablesVo> oCashReceivables = JsonUtil.jsonToList(payTemplet, OCashReceivablesVo.class);
            AgentResult agentResult = capitalChangeApplyService.saveCapitalChange(capitalChangeApply,capitalChangeFiles,getStringUserId(),flag,oCashReceivables);
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

    /**
     * 查看数据信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "toCapitalChangeQuery")
    public String toCapitalChangeQuery(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        CapitalChangeApply capitalChangeApply = capitalChangeApplyService.queryCapitalChangeById(id);
        String cloReviewStatus = String.valueOf(capitalChangeApply.getCloReviewStatus());
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        List<Dict> colInfoType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.CAPITALCHANGE, id,null);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.capitalChange.name());
        request.setAttribute("capitalChangeApply", capitalChangeApply);
        request.setAttribute("capitalType", capitalType);
        request.setAttribute("colInfoType", colInfoType);
        request.setAttribute("paylist", listoCashReceivables);
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.capitalChange.name(), AgStatus.getAgStatusString(new BigDecimal(cloReviewStatus)));
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        request.setAttribute("cloReviewStatus", cloReviewStatus);
        request.setAttribute("refundType", RefundType.getContentMap());
        request.setAttribute("operationType", OperationType.getContentMap());
        selectAll(capitalChangeApply.getAgentId(), model, request,"");
        return "agent/capital/capitalChangeQuery";
    }

    @RequestMapping(value = "toCapitalChangeApproval")
    public String toCapitalChangeApproval(Model model, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        CapitalChangeApply capitalChangeApply = capitalChangeApplyService.queryCapitalChangeById(busId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        List<Dict> colInfoType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.CAPITALCHANGE, busId,null);
        request.setAttribute("taskId", taskId);
        request.setAttribute("proIns", proIns);
        request.setAttribute("busId", busId);
        request.setAttribute("capitalChangeApply", capitalChangeApply);
        request.setAttribute("actRecordList", maps);
        request.setAttribute("capitalType", capitalType);
        request.setAttribute("colInfoType", colInfoType);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("paylist_approve", listoCashReceivables);
        request.setAttribute("operationType", OperationType.getContentMap());
        selectAll(capitalChangeApply.getAgentId(), model, request,"");
        //审批参数
        List<Dict> capitalMarket_param = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_MIARKET.name());
        request.setAttribute("capitalMarket_param", capitalMarket_param);
        return "agent/capital/capitalChangeTask";
    }

    /**
     * 处理任务
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "capitalChangeTaskApproval")
    public ResultVO capitalChangeTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            result = capitalChangeApplyService.approvalCapitalChangeTask(agentVo, String.valueOf(getUserId()), agentVo.getAgentBusId());
        } catch (MessageException e) {
            logger.info("taskApproval处理任务异常：" + e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        } catch (ProcessException e) {
            logger.info("taskApproval处理任务异常：" + e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("taskApproval处理任务异常：" + e.getMessage());
            e.printStackTrace();
            result = AgentResult.fail("处理失败！");
        } finally {
            if (result == null) {
                return ResultVO.fail("处理失败！");
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功！");
            } else {
                return ResultVO.fail("处理失败：" + result.getMsg());
            }
        }
    }

}
