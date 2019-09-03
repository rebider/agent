package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.MergeType;
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
import com.ryx.credit.service.agent.AgentMergeService;
import com.ryx.credit.service.agent.TaskApprovalService;
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
import java.util.*;
import java.util.List;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/1/7 16:24
 * @Param
 * @return
 **/
@RequestMapping("agentMerge")
@Controller
public class AgentMergeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AgentMergeController.class);
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;

    @RequestMapping(value = "toAgentMergePage")
    public String toAgentMergePage(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("agentId", getAgentId());
        return "agent/merge/agentMergeList";
    }

    /**
     * 合并列表
     * @param request
     * @param agentMerge
     * @return
     */
    @RequestMapping(value = "getAgentMergeList")
    @ResponseBody
    public Object getAgentMergeList(HttpServletRequest request, AgentMerge agentMerge) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            agentMerge.setMainAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = agentMergeService.selectAgentMergeList(agentMerge, page, dataRole, getUserId());
        return pageInfo;
    }

    @RequestMapping(value = "toAgentMergeSave")
    public String toAgentMergeSave(HttpServletRequest request) {
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        return "agent/merge/approval_merge_apply";
    }

    /**
     * 保存数据
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveAgentMerge")
    @ResponseBody
    public Object saveAgentMerge(HttpServletRequest request) {
        try {
            AgentMerge agentMerge = new AgentMerge();
            String mainAgentId = request.getParameter("mainAgentId");
            String mainAgentName = request.getParameter("mainAgentName");
            String subAgentId = request.getParameter("subAgentId");
            String subAgentName = request.getParameter("subAgentName");
            String[] busType = request.getParameterValues("busType");
            String flag = request.getParameter("flag");
            String suppAgentId = request.getParameter("suppAgentId");
            String suppAgentName = request.getParameter("suppAgentName");
            String suppType = request.getParameter("suppType");
            String payTemplet = request.getParameter("payTemplet");
            String[] agentMergeFiles = request.getParameterValues("agentMergeFile");
            String remark = request.getParameter("remark");
            List<OCashReceivablesVo> oCashReceivables = JsonUtil.jsonToList(payTemplet, OCashReceivablesVo.class);
            if (StringUtils.isBlank(mainAgentId)) {
                logger.info("代理商合并提交，主代理商编码为空:{}", mainAgentId);
                return AgentResult.fail("代理商合并提交，主代理商编码为空！");
            }
            if (StringUtils.isBlank(mainAgentName)) {
                logger.info("代理商合并提交，主代理商名称为空:{}", mainAgentName);
                return AgentResult.fail("代理商合并提交，主代理商名称为空！");
            }
            if (StringUtils.isBlank(subAgentId)) {
                logger.info("代理商合并提交，副代理商编码为空:{}", subAgentId);
                return AgentResult.fail("代理商合并提交，副代理商编码为空！");
            }
            if (StringUtils.isBlank(subAgentName)) {
                logger.info("代理商合并提交，主代理商编码为空:{}", subAgentName);
                return AgentResult.fail("代理商合并提交，主代理商编码为空！");
            }
            if (busType == null || busType.length == 0) {
                logger.info("代理商合并提交，业务类型名称为空:{}", busType);
                return AgentResult.fail("代理商合并提交，业务类型为空！");
            }
            if (StringUtils.isBlank(flag)) {
                logger.info("代理商合并提交，Flag为空:{}", flag);
                return AgentResult.fail("代理商合并提交，Flag为空！");
            }
            agentMerge.setMainAgentId(mainAgentId);     //主代理商编码
            agentMerge.setMainAgentName(mainAgentName); //主代理商名称
            agentMerge.setSubAgentId(subAgentId);       //副代理商编码
            agentMerge.setSubAgentName(subAgentName);   //副代理商名称
            agentMerge.setSuppAgentId(suppAgentId);
            agentMerge.setSuppAgentName(suppAgentName);
            agentMerge.setSuppType(StringUtils.isBlank(suppType)?mergeSuppType.W.getValue():new BigDecimal(suppType));
            agentMerge.setSubAgentDebt(getSubAgentDebt(subAgentId));
            agentMerge.setSubAgentOweTicket(getSubAgentOweTicket(subAgentId));
            agentMerge.setRemark(remark);
            AgentResult agentResult = agentMergeService.saveAgentMerge(agentMerge, busType, getStringUserId(), flag, oCashReceivables,agentMergeFiles);
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
     * 提交审批
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "mergeStartActivity")
    @ResponseBody
    public Object mergeStartActivity(HttpServletRequest request) {
        try {
            String busId = request.getParameter("busId");
            AgentResult agentResult = agentMergeService.startAgentMergeActivity(busId, getStringUserId(), false);
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

    /**
     * 查看合并业务数据
     * @param request
     * @return
     */
    @RequestMapping(value = "toAgentMergeQuery")
    public String toAgentMergeQuery(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String cloReviewStatus = request.getParameter("cloReviewStatus");
        AgentMerge agentMerge = agentMergeService.queryAgentMerge(id);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeService.queryAgentMergeBusInfo(id);
        request.setAttribute("agentMerge", agentMerge);
        request.setAttribute("agentMergeBusInfos", agentMergeBusInfos);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.MERGE.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.MERGE.name(), AgStatus.getAgStatusString(new BigDecimal(cloReviewStatus)));
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        request.setAttribute("cloReviewStatus", agentMerge.getCloReviewStatus());
        request.setAttribute("mergeType", MergeType.getContentMap());
        request.setAttribute("suppType", mergeSuppType.getValueMap());
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.AGENTMERGE,id, null);
        request.setAttribute("paylist", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        optionsData(request);
        return "agent/merge/approval_merge_query";
    }

    @RequestMapping(value = "toAgentMergeApproval")
    public String toAgentMergeApproval(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        String sid = request.getParameter("sid");
        AgentMerge agentMerge = agentMergeService.queryAgentMerge(busId);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeService.queryAgentMergeBusInfo(busId);
        request.setAttribute("agentMerge", agentMerge);
        request.setAttribute("agentMergeBusInfos", agentMergeBusInfos);
        request.setAttribute("mergeType", MergeType.getContentMap());
        request.setAttribute("suppType", mergeSuppType.getValueMap());
        request.setAttribute("taskId", taskId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList", maps);
        request.setAttribute("busId", busId);
        optionsData(request);
        //审批参数
        List<Dict> MERGE_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.MERGE.name(), DictGroup.MERGE_MARKET.name());
        request.setAttribute("merge_param", MERGE_MARKET);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.AGENTMERGE,busId, null);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("paylist_approve", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("sid",sid);
        request.setAttribute("proIns",proIns);
        return "agent/merge/approval_merge_task";
    }

    /**
     * 处理任务
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "mergeTaskApproval")
    public ResultVO mergeTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            result = agentMergeService.approvalAgentMergeTask(agentVo, String.valueOf(getUserId()), agentVo.getAgentBusId());
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

    @RequestMapping(value = "toAgentMergeEdit")
    public String toAgentMergeEdit(HttpServletRequest request) {
        String busId = request.getParameter("busId");
        String proIns = request.getParameter("proIns");
        AgentMerge agentMerge = agentMergeService.queryAgentMerge(busId);
        request.setAttribute("agentMerge", agentMerge);
        request.setAttribute("mergeSuppTypeList",mergeSuppType.getValueMap());
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.AGENTMERGE,busId, null);
        request.setAttribute("paylist", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("proIns", proIns);
        return "agent/merge/approval_merge_edit";
    }

    /**
     * 修改数据
     * @param request
     * @return
     */
    @RequestMapping(value = "editAgentMerge")
    @ResponseBody
    public Object editAgentMerge(HttpServletRequest request){
        try {
            AgentMerge agentMerge = new AgentMerge();
            String id = request.getParameter("id");
            String mainAgentId = request.getParameter("mainAgentId");
            String mainAgentName = request.getParameter("mainAgentName");
            String subAgentId = request.getParameter("subAgentId");
            String subAgentName = request.getParameter("subAgentName");
            String[] busType = request.getParameterValues("busType");
            String suppAgentId = request.getParameter("suppAgentId");
            String suppAgentName = request.getParameter("suppAgentName");
            String suppType = request.getParameter("suppType");
            String payTemplet = request.getParameter("payTemplet");
            String[] agentMergeFiles = request.getParameterValues("agentMergeFile");
            String remark = request.getParameter("remark");
            String proIns = request.getParameter("proIns");
            List<OCashReceivablesVo> oCashReceivables = JsonUtil.jsonToList(payTemplet, OCashReceivablesVo.class);
            if (StringUtils.isBlank(id)) {
                logger.info("代理商合并修改，合并编码为空:{}", id);
                return AgentResult.fail("代理商合并修改，合并编码为空！");
            }
            if (StringUtils.isBlank(mainAgentId)) {
                logger.info("代理商合并修改，主代理商编码为空:{}", mainAgentId);
                return AgentResult.fail("代理商合并修改，主代理商编码为空！");
            }
            if (StringUtils.isBlank(mainAgentName)) {
                logger.info("代理商合并修改，主代理商名称为空:{}", mainAgentName);
                return AgentResult.fail("代理商合并修改，主代理商名称为空！");
            }
            if (StringUtils.isBlank(subAgentId)) {
                logger.info("代理商合并修改，副代理商编码为空:{}", subAgentId);
                return AgentResult.fail("代理商合并修改，副代理商编码为空！");
            }
            if (StringUtils.isBlank(subAgentName)) {
                logger.info("代理商合并提交，主代理商编码为空:{}", subAgentName);
                return AgentResult.fail("代理商合并提交，主代理商编码为空！");
            }
            if (busType == null || busType.length == 0) {
                logger.info("代理商合并提交，业务类型名称为空:{}", busType);
                return AgentResult.fail("代理商合并提交，业务类型为空！");
            }
            agentMerge.setId(id);                       //合并编码
            agentMerge.setMainAgentId(mainAgentId);     //主代理商编码
            agentMerge.setMainAgentName(mainAgentName); //主代理商名称
            agentMerge.setSubAgentId(subAgentId);       //副代理商编码
            agentMerge.setSubAgentName(subAgentName);   //副代理商名称
            agentMerge.setSuppAgentId(suppAgentId);
            agentMerge.setSuppAgentName(suppAgentName);
            agentMerge.setSuppType(StringUtils.isBlank(suppType)?mergeSuppType.W.getValue():new BigDecimal(suppType));
            agentMerge.setSubAgentDebt(getSubAgentDebt(subAgentId));
            agentMerge.setSubAgentOweTicket(getSubAgentOweTicket(subAgentId));
            agentMerge.setRemark(remark);
            AgentResult agentResult = agentMergeService.editAgentMerge(agentMerge, busType, getStringUserId(),oCashReceivables,agentMergeFiles,proIns);
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

    @ResponseBody
    @RequestMapping("agentPlatformBus")
    public Map<String,Object> agentPlatformBus(HttpServletRequest request) {
        logger.info("代理商平台AgentPlatformBus{}", getUserId());
        Map<String,Object> result = new HashMap<>();
        String agentId = request.getParameter("agentId");
        if(StringUtils.isBlank(agentId)){
            return result;
        }
        List<Map> maps = agentBusinfoService.agentBus(agentId,getUserId());
        result.put("agentbus",maps);
        result.put("debt",getSubAgentDebt(agentId));
        result.put("oweTicket",getSubAgentOweTicket(agentId));
        result.put("mergeSuppTypeList",mergeSuppType.getValueMap());
        return result;
    }

    /**
     * 删除合并业务数据
     * @param request
     * @return
     */
    @RequestMapping(value = "agentMergeDelete")
    @ResponseBody
    public Object agentMergeDelete(HttpServletRequest request){
        try {
            String busId = request.getParameter("busId");
            if (StringUtils.isBlank(busId)) {
                logger.info("代理商合并删除，合并ID为空:{}", busId);
                return AgentResult.fail("代理商合并删除，合并ID为空！");
            }
            AgentResult agentResult = agentMergeService.deleteAgentMerge(busId, getStringUserId());
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

    @RequestMapping(value = "toMergeBusinfoPage")
    public String toMergeBusinfoPage(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("agentId", getAgentId());
        return "agent/merge/mergeBusinfoList";
    }

    /**
     * 合并业务明细列表
     * @param request
     * @param agentMergeBusInfo
     * @return
     */
    @RequestMapping(value = "getMergeBusinfoList")
    @ResponseBody
    public Object getMergeBusinfoList(HttpServletRequest request, AgentMergeBusInfo agentMergeBusInfo) {
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(getAgentId())) {
            agentMergeBusInfo.setMainAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = agentMergeService.selectMergeBusinfoList(agentMergeBusInfo, page, dataRole, getUserId());
        return pageInfo;
    }

    /**
     * 欠款
     * @return
     */
    public BigDecimal getSubAgentDebt(String agentId){
        return agentMergeService.getSubAgentDebt(agentId);
    }

    /**
     * 欠票
     * @return
     */
    public BigDecimal getSubAgentOweTicket(String agentId){
        return agentMergeService.getSubAgentOweTicket(agentId);
    }

}
