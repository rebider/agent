//package com.ryx.credit.cms.controller.profit;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ryx.credit.activity.entity.ActHiVarinst;
//import com.ryx.credit.cms.controller.BaseController;
//import com.ryx.credit.cms.controller.agent.AgentEnterController;
//import com.ryx.credit.cms.util.ServiceFactory;
//import com.ryx.credit.common.enumc.BusActRelBusType;
//import com.ryx.credit.common.enumc.DictGroup;
//import com.ryx.credit.common.result.AgentResult;
//import com.ryx.credit.common.util.JsonUtils;
//import com.ryx.credit.common.util.Page;
//import com.ryx.credit.common.util.ResultVO;
//import com.ryx.credit.pojo.admin.CUser;
//import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
//import com.ryx.credit.pojo.admin.agent.BusActRel;
//import com.ryx.credit.pojo.admin.agent.Capital;
//import com.ryx.credit.pojo.admin.agent.Dict;
//import com.ryx.credit.pojo.admin.vo.AgentVo;
//import com.ryx.credit.profit.pojo.PAgentMerge;
//import com.ryx.credit.profit.pojo.PAgentQuit;
//import com.ryx.credit.profit.service.IProfitAgentMergerService;
//import com.ryx.credit.profit.service.PAgentQuitService;
//import com.ryx.credit.profit.service.ProfitDeductionService;
//import com.ryx.credit.service.ActHiVarinstService;
//import com.ryx.credit.service.IUserService;
//import com.ryx.credit.service.agent.AgentBusinfoService;
//import com.ryx.credit.service.agent.CapitalService;
//import com.ryx.credit.service.agent.TaskApprovalService;
//import com.ryx.credit.service.order.OrderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author Lihl
// * @Desc 代理商合并申请审批
// * @Date 2018/10/15
// */
//@Controller
//@RequestMapping("/pAgentMergeActivity")
//public class PAgentMergeActivityController extends BaseController{
//    private static final Logger logger = LoggerFactory.getLogger(PAgentMergeActivityController.class);
//    @Autowired
//    private AgentEnterController agentEnterController;
//    @Autowired
//    private ActHiVarinstService actHiVarinstService;
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private TaskApprovalService taskApprovalService;
//    @Autowired
//    private IProfitAgentMergerService pAgentMergerService;
//    @Autowired
//    private CapitalService capitalService;
//    @Autowired
//    private AgentBusinfoService agentBusinfoService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private ProfitDeductionService profitDeductionService;
//    /**
//     * 进入待审批页面
//     * @param request
//     * @param model
//     * @return 加载合并审批页面
//     */
//    @RequestMapping(value = {"agentMergeInfo"})
//    public String agentMergeInfo(HttpServletRequest request, Model model, String busId){
//        String taskId = request.getParameter("taskId");
//        String proIns = request.getParameter("proIns");
//        String thawId = request.getParameter("busId");
//
//        PAgentMerge pAgentMergeA = pAgentMergerService.getMergeById(thawId);
//
//        //根据subAgentId查询服务费类型
//        List<Capital> capitalList =  capitalService.queryCapital(pAgentMergeA.getSubAgentId());
//        request.setAttribute("capitalList", capitalList);
//        //根据agentId查询业务类型
//        List<AgentBusInfo> agentList  = agentBusinfoService.selectByAgenId(pAgentMergeA.getMainAgentId());
//        request.setAttribute("agentList", agentList);
//        //根据subAgentId查询业务类型
//        List<AgentBusInfo> subAgentList  = agentBusinfoService.selectByAgenId(pAgentMergeA.getSubAgentId());
//        request.setAttribute("subAgentList", subAgentList);
//        //查询欠款信息
//        BigDecimal a = orderService.queryAgentDebt(pAgentMergeA.getSubAgentId());
//        BigDecimal b =  profitDeductionService.getNotDeductionSum(pAgentMergeA.getSubAgentId());
//        Capital capital1 = new Capital();
//        capital1.setcType("欠款");
//        capital1.setcAmount(a.add(b));
//        Capital capital2 = new Capital();
//        capital2.setcType("欠票");
//        capital2.setcAmount(BigDecimal.ZERO);
//        capitalList.add(capital1);
//        capitalList.add(capital2);
//        request.setAttribute("capitalList", capitalList);
//
//        optionsData(request);
//
//        PAgentMerge pAgentMerge = pAgentMergerService.getMergeById(thawId);
//        model.addAttribute("pAgentMerge", pAgentMerge);
//
//        //审批参数
//        List<Dict> MERGE_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.MERGE.name(), DictGroup.MERGE_MARKET.name());
//        request.setAttribute("merge_market", MERGE_MARKET);
//        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
//        model.addAttribute("taskId", taskId);
//        model.addAttribute("proIns", proIns);
//        return "profit/agentMerge/agentMergeTaskApproval";
//    }
//
//    /**
//     * 查询审批记录
//     * @param proIns
//     */
//    public List<Map<String,Object>> queryApprovalRecord(String proIns){
//        ActHiVarinst actHiVarinst = new ActHiVarinst();
//        actHiVarinst.setProcInstId(proIns);
//        actHiVarinst.setName("_ryx_wq");
//        HashMap<String, Object> actHiVarinstMap = actHiVarinstService.configExample(new Page(), actHiVarinst);
//        List<Map<String,Object>> actRecordList = new ArrayList<>();
//        List<ActHiVarinst> list = (List<ActHiVarinst>)actHiVarinstMap.get("list");
//        for (ActHiVarinst hiVarinst : list) {
//            Map map = JSONObject.parseObject(String.valueOf(hiVarinst.getText()));
//            String approvalPerson = String.valueOf(map.get("approvalPerson"));
//            CUser cUser = userService.selectById(approvalPerson);
//            if(null != cUser){
//                map.put("approvalPerson", cUser.getName());
//            }
//            actRecordList.add(map);
//        }
//        return actRecordList;
//    }
//
//    /**
//     * 处理任务
//     * @param agentVo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/taskApproval")
//    public ResultVO taskApproval(@RequestBody AgentVo agentVo){
//        AgentResult result = null;
//        try {
//            result = pAgentMergerService.approvalTask(agentVo, String.valueOf(getUserId()));
//        } catch (Exception e) {
//            logger.info("处理任务异常" + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (result == null) {
//                return ResultVO.fail("处理失败");
//            }
//            if (result.isOK()) {
//                return ResultVO.success("处理成功");
//            } else {
//                return ResultVO.fail(result.getMsg());
//            }
//        }
//    }
//
//    /**
//     * 合并申请详情
//     * @param request
//     * @param id
//     * @return
//     */
//    @RequestMapping("/mergeTaskApproval")
//    public ModelAndView mergeTaskApproval(HttpServletRequest request, String id){
//        ModelAndView view = new ModelAndView();
//        view.setViewName("profit/agentMerge/agentMergePlan");
//        PAgentMerge pAgentMerge = pAgentMergerService.getMergeById(id);
//        //根据subAgentId查询服务费类型
//        List<Capital> capitalList =  capitalService.queryCapital(pAgentMerge.getSubAgentId());
//        request.setAttribute("capitalList", capitalList);
//        //根据agentId查询业务类型
//        List<AgentBusInfo> agentList  = agentBusinfoService.selectByAgenId(pAgentMerge.getMainAgentId());
//        request.setAttribute("agentList", agentList);
//        //根据subAgentId查询业务类型
//        List<AgentBusInfo> subAgentList  = agentBusinfoService.selectByAgenId(pAgentMerge.getSubAgentId());
//        request.setAttribute("subAgentList", subAgentList);
//        view.addObject("pAgentMerge", pAgentMerge);
//        try {
//            request.setAttribute("mergeStatus", pAgentMerge.getMergeStatus());
//            request.setAttribute("busIdImg", id);
//            request.setAttribute("busTypeImg", BusActRelBusType.MERGE.name());
//            BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.MERGE.name(), pAgentMerge.getMergeStatus());
//            if (busActRel != null) {
//                view.addObject("actRecordList", queryApprovalRecord(busActRel.getActivId()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        optionsData(request);
//        return view;
//    }
//
//}
