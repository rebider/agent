package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.IPTaxAdjustService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.data.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @desc 税点调整申请
 */
@Controller
@RequestMapping("/posTaxActivity")
public class PosTaxActivityController extends BaseController {
    @Autowired
    private ActHiVarinstService actHiVarinstService;
    @Autowired
    private IUserService userService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private IPTaxAdjustService taxAdjustService;
    @Autowired
    private ToolsDeductService toolsDeductService;

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

        PTaxAdjust taxAdjust = taxAdjustService.getPosTaxById(thawId);
        model.addAttribute("taxAdjust", taxAdjust);

        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        return "discount/posTaxTaskApproval";
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
            result = taxAdjustService.approvalTask(agentVo, String.valueOf(getUserId()));
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
        ModelAndView view = new ModelAndView();
        view.setViewName("discount/taskPosTax");
        PTaxAdjust taxAdjust = taxAdjustService.getPosTaxById(id);
        view.addObject("taxAdjust",taxAdjust);
        BusActRel busActRel = new BusActRel();
        busActRel.setBusId(id);
        BusActRel rel = null;
        try {
            rel = taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                view.addObject("actRecordList",queryApprovalRecord(rel.getActivId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        optionsData(request);
        return view;
    }

}
