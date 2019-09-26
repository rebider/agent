//package com.ryx.credit.cms.controller.profit;
//
//import com.ryx.credit.cms.controller.BaseController;
//import com.ryx.credit.cms.controller.agent.AgentEnterController;
//import com.ryx.credit.common.enumc.BusActRelBusType;
//import com.ryx.credit.common.exception.ProcessException;
//import com.ryx.credit.common.result.AgentResult;
//import com.ryx.credit.common.util.Page;
//import com.ryx.credit.common.util.PageInfo;
//import com.ryx.credit.common.util.ResultVO;
//import com.ryx.credit.commons.utils.StringUtils;
//import com.ryx.credit.pojo.admin.agent.Agent;
//import com.ryx.credit.pojo.admin.agent.BusActRel;
//import com.ryx.credit.profit.pojo.PAgentQuit;
//import com.ryx.credit.profit.service.PAgentQuitService;
//import com.ryx.credit.service.IUserService;
//import com.ryx.credit.service.agent.AgentBusinfoService;
//import com.ryx.credit.service.agent.AgentService;
//import com.ryx.credit.service.agent.TaskApprovalService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import sun.awt.ModalityListener;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @Author Lihl
// * @Date 2018/9/26
// * @Desc 代理商退出申请
// */
//@Controller
//@RequestMapping("/pAgentQuit")
//public class PAgentQuitController extends BaseController{
//    private static Logger logger = LoggerFactory.getLogger(DiscountController.class);
//    @Autowired
//    private PAgentQuitService pAgentQuitService;
//    @Autowired
//    private IUserService iUserService;
//    @Autowired
//    private AgentService agentService;
//    @Autowired
//    private AgentBusinfoService agentBusinfoService;
//    @Autowired
//    private TaskApprovalService taskApprovalService;
//
//    @RequestMapping(value = "index")
//    public String quitApplyView() {
//        return "profit/agentQuit/agentQuitList";
//    }
//
//    @RequestMapping(value = "list")
//    @ResponseBody
//    public Object getAgentQuitList(HttpServletRequest request) {
//        Page page = pageProcess(request);
//        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
//        TreeMap map = getRequestParameter(request);
//        map.put("begin", page.getBegin());
//        map.put("end", page.getEnd());
//        return pAgentQuitService.getAgentQuitList(map, pageInfo);
//    }
//
//    @RequestMapping(value = {"addQuit"}, method = RequestMethod.POST)
//    @ResponseBody
//    public Object addQuit(PAgentQuit pAgentQuit) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Set<String> roles = getShiroUser().getRoles();
//            String workId = null;
//            if(roles !=null && roles.contains("代理商")) {
//                workId = "quitAgent";
//            }else {
//                workId = "quitCity";
//            }
//            pAgentQuit.setApplyUser(getUserId() + "");
//            pAgentQuit.setCreateDate(df.format(new Date()));//创建日期
//            pAgentQuitService.applyPAgentQuit(pAgentQuit, String.valueOf(getUserId()), workId);
//            return renderSuccess("代理商退出申请添加成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return renderError("系统异常，请联系维护人员！");
//    }
//
//    @RequestMapping(value = "editPage")
//    public ModelAndView getQuitApplyfor(String id) {
//        if (StringUtils.isNotBlank(id)) {
//            PAgentQuit pAgentQuit = pAgentQuitService.selectByPrimaryKey(id);
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("profit/agentQuit/agentQuitEdit");
//            modelAndView.addObject("agentQuit", pAgentQuit);
//            return modelAndView;
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "edit")
//    @ResponseBody
//    public Object editQuitApplyfor(PAgentQuit pAgentQuit) {
//        if (pAgentQuit == null) {
//            return renderError("系统异常，请联系维护人员！");
//        }
//        if(StringUtils.isBlank(pAgentQuit.getApplyPlat())
//                || StringUtils.isBlank(pAgentQuit.getDebtBill().toString())
//                || StringUtils.isBlank(pAgentQuit.getSupplyDebtBill().toString())
//                || StringUtils.isBlank(pAgentQuit.getDebtAmt().toString())
//                || StringUtils.isBlank(pAgentQuit.getSupplyDebtAmt().toString())
//                || StringUtils.isBlank(pAgentQuit.getSupplyType().toString())
//                || StringUtils.isBlank(pAgentQuit.getPayCompany().toString())){
//            return renderError("请填写完毕");
//        }
//        try {
//            pAgentQuit.setPassDate(String.valueOf(new Date()));//退出日期
//            pAgentQuit.setApplyUser(getShiroUser().getLoginName());
//            pAgentQuitService.updateAgentQuit(pAgentQuit);
//            return renderSuccess("代理商退出申请修改成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return renderError("系统异常，请联系维护人员！");
//    }
//
//
//}
