//package com.ryx.credit.cms.controller.profit;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ryx.credit.activity.entity.ActHiVarinst;
//import com.ryx.credit.cms.controller.BaseController;
//import com.ryx.credit.cms.controller.agent.AgentEnterController;
//import com.ryx.credit.cms.util.ServiceFactory;
//import com.ryx.credit.common.enumc.DictGroup;
//import com.ryx.credit.common.result.AgentResult;
//import com.ryx.credit.common.util.JsonUtils;
//import com.ryx.credit.common.util.Page;
//import com.ryx.credit.common.util.ResultVO;
//import com.ryx.credit.pojo.admin.CUser;
//import com.ryx.credit.pojo.admin.agent.BusActRel;
//import com.ryx.credit.pojo.admin.agent.Capital;
//import com.ryx.credit.pojo.admin.agent.Dict;
//import com.ryx.credit.pojo.admin.vo.AgentVo;
//import com.ryx.credit.profit.pojo.PAgentQuit;
//import com.ryx.credit.profit.service.PAgentQuitService;
//import com.ryx.credit.service.ActHiVarinstService;
//import com.ryx.credit.service.IUserService;
//import com.ryx.credit.service.agent.CapitalService;
//import com.ryx.credit.service.agent.TaskApprovalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author Lihl
// * @Desc 代理商退出申请审批
// * @Date 2018/10/12
// */
//@Controller
//@RequestMapping("/pAgentQuitActivity")
//public class PAgentQuitActivityController extends BaseController{
//    @Autowired
//    private AgentEnterController agentEnterController;
//    @Autowired
//    private ActHiVarinstService actHiVarinstService;
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private TaskApprovalService taskApprovalService;
//    @Autowired
//    private PAgentQuitService pAgentQuitService;
//    @Autowired
//    private CapitalService capitalService;
//
//    /**
//     * 进入待审批页面
//     * @param request
//     * @param model
//     * @return 加载退出审批页面
//     */
//    @RequestMapping(value = {"agentQuitInfo"})
//    public String toTaskApproval(HttpServletRequest request, Model model, String busId){
//        agentEnterController.selectAll(busId, model, request);
//        String taskId = request.getParameter("taskId");
//        String proIns = request.getParameter("proIns");
//        String thawId = request.getParameter("busId");
//        optionsData(request);
//
//        PAgentQuit pAgentQuit = pAgentQuitService.getBusIdByAgentId(thawId);
//        model.addAttribute("pAgentQuit", pAgentQuit);
//
//        //审批参数
////        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
////        List<Dict> TOOLS_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_MARKET.name());
////        List<Dict> TOOLS_APR_BOSS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BOSS.name());
////        List<Dict> TOOLS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_EXPAND.name());
////        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
////        request.setAttribute("tools_apr_market", TOOLS_APR_MARKET);
////        request.setAttribute("tools_apr_boss", TOOLS_APR_BOSS);
////        request.setAttribute("tools_apr_expand", TOOLS_APR_EXPAND);
//        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
//        model.addAttribute("taskId", taskId);
//        model.addAttribute("proIns", proIns);
//        return "profit/agentQuit/agentQuitTaskApproval";
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
//            result = pAgentQuitService.approvalTask(agentVo, String.valueOf(getUserId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (result == null) {
//                return ResultVO.fail("处理失败");
//            }
//            if (result.isOK()) {
//                return ResultVO.success("处理成功");
//            } else {
//                return ResultVO.fail("处理失败");
//            }
//        }
//    }
//
//    /**
//     * 退出申请详情
//     * @param request
//     * @param agentId
//     * @return
//     */
//    @RequestMapping("/gotoTaskApproval")
//    public ModelAndView gotoTaskApproval(HttpServletRequest request, String agentId){
//        ModelAndView view = new ModelAndView();
//        view.setViewName("profit/agentQuit/agentQuitInfo");
//        PAgentQuit pAgentQuit = pAgentQuitService.getBusIdByAgentId(agentId);
//        view.addObject("pAgentQuit", pAgentQuit);
//        //保证金信息
//        List<Capital> capitalList = capitalService.queryCapital(agentId);
//        request.setAttribute("capitalList", capitalList);
//        BusActRel busActRel = new BusActRel();
//        busActRel.setBusId(agentId);
//        BusActRel rel = null;
//        try {
//            rel = taskApprovalService.queryBusActRel(busActRel);
//            if (rel != null) {
//                view.addObject("actRecordList", queryApprovalRecord(rel.getActivId()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        optionsData(request);
//        return view;
//    }
//
//
//}
