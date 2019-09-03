package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.service.IProfitCityApplicationService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.String.valueOf;

@Controller
@RequestMapping("/profit/application")
public class DeductionAppController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(DeductionAppController.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private IProfitCityApplicationService profitCityApplicationService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentService agentService;

    /**
     * 省区加载其他扣款申请页面
     */
    @RequestMapping(value = "gotoCityDeductionPage")
    public String gotoCityDeductionPage() {
        LOGGER.info("加载其他扣款申请页面。");
        return "profit/otherDeduction/cityOtherDeductionList";
    }

    /**
     * 加载省区申请扣款页面
     *
     * @return
     */
    @RequestMapping(value = "gotoCityApplicationPage")
    public String gotoApplicationPage() {
        LOGGER.info("加载申请扣款页面。");
        return "profit/otherDeduction/cityApplicationPage";
    }


    /**
     * 获取数据列表
     *
     * @return
     */
    @RequestMapping("getDeductionAppList")
    @ResponseBody
    public Object getDeductionAppList(HttpServletRequest request, PCityApplicationDetail pCityApplicationDetail) {
        //获取传入参数
        Page page = pageProcess(request);
        //获得当前登录对象
        String userId = getStringUserId();
        //将map传入获取该省区数据列表
        PageInfo pageInfo = profitCityApplicationService.getDeductionAppList(page, userId, pCityApplicationDetail);
        return pageInfo;
    }

    /**
     * 提交申请
     */
    @RequestMapping("commitInfo")
    @ResponseBody
    public Object commitApplication(PCityApplicationDetail pCityApplicationDetail) {
        String loginName = getStringUserId();
        pCityApplicationDetail.setCreateDate(new Date());
        pCityApplicationDetail.setCreateName(loginName);

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list.size() >0){
            if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ){
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",pCityApplicationDetail.getAgentId());
                map.put("orgId",list.get(0).get("ORGID").toString());
                List<Agent> list1 = agentService.getListByORGAndId(map);
                if(list1.size()<=0){
                    return renderError("所申请代理商不属于该省区");
                }
            }
        }

        try {
            Set<String> roles = getShiroUser().getRoles();
            String workId = null;
            if(roles !=null && roles.contains("业务审批")) {
                workId = "businessOtherRepair1.0";
            }else {
                workId = "otherRepair1.0";
            }
            String cUser = getStringUserId();
            profitCityApplicationService.applyOtherDeduction(pCityApplicationDetail, valueOf(getUserId()), workId, cUser);
            return renderSuccess("已提交审批！");
        } catch (ProcessException e) {
            e.printStackTrace();
            return renderError("其他扣款申请审批流启动失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }


    @RequestMapping("toOtherDeductApprovalView")
    public String approvalCompensateView(HttpServletRequest request, HttpServletResponse response, Model model) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busId = request.getParameter("busId");
        String busType = request.getParameter("busType");

        PCityApplicationDetail pCityApplicationDetail = profitCityApplicationService.getDataById(busId);
        model.addAttribute("pCityApplicationDetail", pCityApplicationDetail);

        if (pCityApplicationDetail != null) {
            PCityApplicationDetail pc = profitCityApplicationService.getDataById(pCityApplicationDetail.getId());
            model.addAttribute("pCityApplicationDetail", pc);
        }

        //查询审批记录
        List<Map<String, Object>> actRecordList = queryApprovalRecord(busId, BusActRelBusType.CityApplyDeduction.name());
        //下级审批部门
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), "OTHER_DEDUCT");
        //审批结果
        List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());

        model.addAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        model.addAttribute("approvalType", approvalType);
        model.addAttribute("actRecordList", actRecordList);
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        model.addAttribute("id", busId);

        return "profit/otherDeduction/OtherDeductTaskApproval";
    }


    /**
     * 处理任务
     */
    @ResponseBody
    @RequestMapping("/doTaskApproval")
    public Object doTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            result = profitCityApplicationService.approvalTask(agentVo, String.valueOf(getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result == null) {
                return ResultVO.fail("处理失败");
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功");
            } else {
                return ResultVO.fail("处理失败");
            }
        }

    }

    /**
     * 驳回-----修改信息
     */
    @RequestMapping("editOtherDeductionInfo")
    @ResponseBody
    public Object editOtherDeductionInfo(PCityApplicationDetail pCityApplicationDetail) {
        try {
            //调用方法，更新数据库
            profitCityApplicationService.editCheckRegect(pCityApplicationDetail);
            return renderSuccess("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }


    /**
     * 查看申请审批记录
     */
    @RequestMapping("gotoTaskApproval")
    public String gotoTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String id = request.getParameter("sourceId");
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

        return "profit/otherDeduction/taskApprovalDetail";
    }

    /**
     * 查看审批数据：查看审批信息和审批记录
     */
    @RequestMapping(value = "toAgentMergeQuery")
    public String toAgentMergeQuery(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        PCityApplicationDetail pCityApplicationDetail = profitCityApplicationService.getDataById(id);
        model.addAttribute("pCityApplicationDetail", pCityApplicationDetail);

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
        return "profit/otherDeduction/OtherDeductApplyDetail";
    }

    /**
     * 获取代理商名称以及上级代理商名称
     */
    @RequestMapping("queryAgentName")
    @ResponseBody
    public Object getAgentName(String agentId){
        //根据agentId 获取代理商名称
        ResultVO resultVO = profitCityApplicationService.getAgentNameById(agentId);
        return resultVO;
    }
    /**
     * 获取并判断代理商上级关系
     */
   @RequestMapping("queryParentAgentName")
    @ResponseBody
    public Object getParentAgentName(String agentId,String parentAgentId){
        ResultVO resultVO = profitCityApplicationService.getParentNameByID(agentId,parentAgentId);
        return resultVO;
    }

}
