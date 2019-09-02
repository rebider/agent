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
import com.ryx.credit.profit.pojo.PPosHuddleReward;
import com.ryx.credit.profit.pojo.PosHuddleRewardDetail;
import com.ryx.credit.profit.pojo.PosReward;
import com.ryx.credit.profit.service.IPosRewardService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/06
 * @desc POS奖励申请
 */
@Controller
@RequestMapping("/rewardActivity")
public class RewardActivityController extends BaseController {
    @Autowired
    private ActHiVarinstService actHiVarinstService;
    @Autowired
    private IUserService userService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IPosRewardService posRewardService;
    @Autowired
    private StagingService stagingServiceImpl;

    /**
     * 进入待审批页面
     * @param request
     * @param model
     * @return 加载奖励审批页面
     */
    @RequestMapping("/toTaskApproval")
    public String toTaskApproval(HttpServletRequest request, Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String thawId = request.getParameter("busId");
        optionsData(request);

        PosReward reward = posRewardService.getPosRewardById(thawId);

        model.addAttribute("posReward", reward);

        if (reward != null) {
            PosReward posReward = posRewardService.getPosRewardById(reward.getId());
            model.addAttribute("posReward", posReward);
        }

        //审批参数
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
        List<Dict> TOOLS_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_MARKET.name());
        List<Dict> TOOLS_APR_BOSS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BOSS.name());
        List<Dict> TOOLS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_EXPAND.name());
        List<Dict> POS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.POS.name(), DictGroup.POS_APR_BUSNISS.name());
        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        request.setAttribute("tools_apr_market", TOOLS_APR_MARKET);
        request.setAttribute("tools_apr_boss", TOOLS_APR_BOSS);
        request.setAttribute("tools_apr_expand", TOOLS_APR_EXPAND);
        request.setAttribute("pos_apr_busniss", POS_APR_EXPAND);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));

        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);

        Map<String, Object> mapTask = null;
        if (taskId != null) {
            mapTask = stagingServiceImpl.byTaskId(taskId);
            if ((mapTask.get("NAME_").toString().indexOf("修改") == -1) && (mapTask.get("GROUP_ID_").toString().equals("city"))) {
                return "discount/normalRewardTaskApproval";
            } else {
                return "discount/rewardTaskApproval";
            }
        }

        return "discount/rewardTaskApproval";

    }


    /**
     * POS抱团进入待审批页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/toHuddleTaskApproval")
    public String toHuddleTaskApproval(HttpServletRequest request, Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String thawId = request.getParameter("busId");
        optionsData(request);

        PPosHuddleReward reward = posRewardService.selectByPrimaryKey(thawId);
        /*String huddleCode = reward.getHuddleCode();
        List<PosHuddleRewardDetail> listHuddle  =  posRewardService.selectByHuddleCode(huddleCode);
        StringBuffer sbId = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (PosHuddleRewardDetail pos:listHuddle) {
            sbId.append(pos.getPosAgentId()+",");
            sbName.append(pos.getPosAgentName()+",");
        }
        model.addAttribute("AllId", sbId.toString());
        model.addAttribute("AllName", sbName.toString());
        model.addAttribute("posReward", reward);*/

        if (reward != null) {
            PPosHuddleReward posReward = posRewardService.selectByPrimaryKey(reward.getId());
            model.addAttribute("posReward", posReward);
            String posHuddleCode = reward.getHuddleCode();
            List<PosHuddleRewardDetail> posListHuddle  =  posRewardService.selectByHuddleCode(posHuddleCode);

            List<String> posListStr = new ArrayList<>();
            for (PosHuddleRewardDetail pos:posListHuddle) {
                posListStr.add(pos.getPosAgentName()+"  ，  "+pos.getPosAgentId());

            }
            model.addAttribute("AllAgent", posListStr);
            model.addAttribute("posReward", posReward);
        }

        //审批参数
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BUSNISS.name());
        List<Dict> TOOLS_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_MARKET.name());
        List<Dict> TOOLS_APR_BOSS = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_BOSS.name());
        List<Dict> POS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.POS.name(), DictGroup.POS_APR_BUSNISS.name());
        List<Dict> TOOLS_APR_EXPAND = ServiceFactory.dictOptionsService.dictList(DictGroup.TOOLS.name(), DictGroup.TOOLS_APR_EXPAND.name());
        request.setAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        request.setAttribute("tools_apr_market", TOOLS_APR_MARKET);
        request.setAttribute("tools_apr_boss", TOOLS_APR_BOSS);
        request.setAttribute("tools_apr_expand", TOOLS_APR_EXPAND);
        request.setAttribute("pos_apr_busniss", POS_APR_EXPAND);

        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        return "discount/rewardHuddleTaskApproval";
    }


    /**
     * 处理任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/taskApproval")
    public ResultVO taskApproval(@RequestBody AgentVo agentVo){
        AgentResult result = null;
        try {
            result = posRewardService.approvalTask(agentVo, String.valueOf(getUserId()));
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
        view.setViewName("discount/posRewardPlan"); //查看POS奖励考核 审批进度页面
        PosReward reward = posRewardService.getPosRewardById(id);
        view.addObject("posReward",reward);
        List<PosReward> list = posRewardService.getPosRewardByDataId(id);
        if(list != null && !list.isEmpty()){
            PosReward posReward = list.get(0);
            view.addObject("posReward",posReward);
            BusActRel busActRel = new BusActRel();
            busActRel.setBusId(posReward.getId());
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



    @RequestMapping("/gotoHuddleTaskApproval")
    public ModelAndView gotoHuddleTaskApproval(HttpServletRequest request, @RequestParam(value = "id") String id){
        ModelAndView view = new ModelAndView();
        view.setViewName("discount/posHuddleRewardPlan"); //查看POS奖励考核 审批进度页面
        PPosHuddleReward posReward = posRewardService.selectByPrimaryKey(id);
        String posHuddleCode = posReward.getHuddleCode();
        List<PosHuddleRewardDetail> posListHuddle  =  posRewardService.selectByHuddleCode(posHuddleCode);
        List<String> posListStr = new ArrayList<>();
        for (PosHuddleRewardDetail pos:posListHuddle) {
            posListStr.add(pos.getPosAgentName()+"  ，  "+pos.getPosAgentId());

        }
        view.addObject("AllAgent", posListStr);
        view.addObject("posReward", posReward);

        List<PPosHuddleReward> list = posRewardService.getHuddlePosRewardByDataId(id);
        if(list != null && !list.isEmpty()){
            BusActRel busActRel = new BusActRel();
            busActRel.setBusId(posReward.getId());
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


}
