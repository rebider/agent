package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.*;
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
 * @Author Lihl
 * @Date 2019/1/26
 * @Desc 代理商退出--申请退款
 */
@RequestMapping("quitRefund")
@Controller
public class AgentQuitRefundController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitRefundController.class);
    @Autowired
    private AgentQuitRefundService agentQuitRefundService;
    @Autowired
    private AgentQuitService agentQuitService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private AgentQueryService agentQueryService;


    @RequestMapping(value = "toQuitRefundPage")
    public String toQuitRefundPage(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("agentId", getAgentId());
        return "agent/quitRefund/agentRefundList";
    }

    /**
     * 退款列表
     * @param request
     * @param agentQuitRefund
     * @return
     */
    @RequestMapping(value = "getQuitRefundList")
    @ResponseBody
    public Object getQuitRefundList(HttpServletRequest request, AgentQuitRefund agentQuitRefund) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            agentQuitRefund.setAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = agentQuitRefundService.queryQuitRefundList(agentQuitRefund, page, dataRole, getUserId());
        return pageInfo;
    }

    /**
     * 申请退款-审批
     * @param request
     * @return
     */
    @RequestMapping(value = "applyQuitRefund")
    @ResponseBody
    public Object applyQuitRefund(HttpServletRequest request) {
        try {
            String quitId = request.getParameter("quitId");//退出ID
            AgentQuit quitById = agentQuitService.getAgentQuitById(quitId);
            AgentQuitRefund agentQuitRefund = new AgentQuitRefund();
            agentQuitRefund.setQuitId(quitId);
            agentQuitRefund.setRealitySuppDept(quitById.getRealitySuppDept());
            agentQuitRefund.setRefundAmt(quitById.getSuppDept());
            agentQuitRefund.setAgentId(quitById.getAgentId());
            agentQuitRefund.setAgentName(quitById.getAgentName());
            AgentResult agentResult = agentQuitRefundService.startQuitRefundActivity(agentQuitRefund, getStringUserId());
            if (agentResult.isOK()) {
                return renderSuccess("申请退款-提交成功！");
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

    @RequestMapping(value = "toQuitRefundApproval")
    public String toQuitRefundApproval(Model model, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        AgentQuitRefund agentQuitRefund = agentQuitRefundService.queryQuitRefundById(busId);
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(agentQuitRefund.getQuitId());
        selectAll(agentQuitRefund.getAgentId(), model, request);
        request.setAttribute("agentQuitRefund", agentQuitRefund);
        request.setAttribute("agentQuit", agentQuit);
        request.setAttribute("taskId", taskId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList", maps);
        request.setAttribute("busId", busId);
//        //审批参数
//        List<Dict> quitRefund_param = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.QUIT_REFUND_XUHUI.name());
//        request.setAttribute("quitRefund_param", quitRefund_param);
        return "agent/quitRefund/approval_quitRefund_task";
    }

    /**
     * 处理任务
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "quitRefundTaskApproval")
    public ResultVO quitRefundTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            result = agentQuitRefundService.approvalQuitRefundTask(agentVo, String.valueOf(getUserId()), agentVo.getAgentBusId());
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

    /**
     * 查看申请退款数据
     * @param request
     * @return
     */
    @RequestMapping(value = "toQuitRefundQuery")
    public String toQuitRefundQuery(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        AgentQuitRefund agentQuitRefund = agentQuitRefundService.queryQuitRefundById(id);
        String cloReviewStatus = String.valueOf(agentQuitRefund.getCloReviewStatus());
        AgentQuit agentQuit = agentQuitService.queryAgentQuit(agentQuitRefund.getQuitId());
        selectAll(agentQuitRefund.getAgentId(), model, request);
        request.setAttribute("agentQuitRefund", agentQuitRefund);
        request.setAttribute("agentQuit", agentQuit);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.agentQuitRefund.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.agentQuitRefund.name(), AgStatus.getAgStatusString(new BigDecimal(cloReviewStatus)));
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        request.setAttribute("cloReviewStatus", cloReviewStatus);
        return "agent/quitRefund/approval_quitRefund_query";
    }

    public void selectAll(String id, Model model, HttpServletRequest request) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        List<Capital> capitals = agentQueryService.paymentQuery(id);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(id);
        model.addAttribute("agent", agent);
        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("capitals", capitals);
        model.addAttribute("agentContracts", agentContracts);
        BigDecimal subAgentOweTicket = agentMergeService.getSubAgentOweTicket(id);
        BigDecimal subAgentDebt = agentMergeService.getSubAgentDebt(id);
        BigDecimal profitDebt = agentMergeService.profitDebt(id);
        BigDecimal orderDebt = agentMergeService.getOrderDebt(id);
        BigDecimal capitalDebt = agentMergeService.getCapitalDebt(id);
        Map<String,Object> suppMap = agentQuitService.calculateSuppDept(id);
        model.addAttribute("subAgentOweTicket", subAgentOweTicket);
        model.addAttribute("subAgentDebt", subAgentDebt);
        model.addAttribute("profitDebt", profitDebt);
        model.addAttribute("orderDebt", orderDebt);
        model.addAttribute("capitalDebt", capitalDebt);
        model.addAttribute("suppMap", suppMap);
        List<Dict> migrationPlatform = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.QUIT_MIGR_PLATFORM.name());
        request.setAttribute("migrationPlatforms", migrationPlatform);
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getAllOption());
        request.setAttribute("quitPlatformList", QuitPlatform.getContentMap());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        optionsData(request);
    }

}
