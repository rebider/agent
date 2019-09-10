package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.NotifyType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentMergeService;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.order.TerminalTransferService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName AgentNotifyController
 * @Description lrr
 * @Author RYX
 * @Date 2018/6/11
 **/
@RequestMapping("notification")
@Controller
public class AgentNotifyController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AgentBusinfoController.class);

    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private AgentQuitService agentQuitService;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;

    @RequestMapping(value = {"/", "index"})
    public String index(HttpServletRequest request) {
        request.setAttribute("notifyTypeList", NotifyType.getKeyValueMap());
        return "agent/agentNotifyQuery";
    }

    @ResponseBody
    @RequestMapping(value = {"/", "agentNotifyQuery"})
    public Object agentNotifyQuery(HttpServletRequest request,AgentPlatFormSyn agentPlatFormSyn) {
        Page page = pageProcess(request);
        PageInfo pageInfo = agentNetInNotityService.queryList(page, agentPlatFormSyn);
        List<AgentPlatFormSyn> rows = pageInfo.getRows();
        for (AgentPlatFormSyn row : rows) {
            if(StringUtils.isNotBlank(row.getcUser())){
                Pattern pattern = Pattern.compile("[0-9]*$");
                Matcher isNum = pattern.matcher(row.getcUser());
                if(isNum.matches() ){
                    CUser user = userService.selectById(Integer.parseInt(row.getcUser()));
                    if(null!=user)
                        row.setcUser(user.getName());
                }
            }
        }
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        return pageInfo;
    }

    /**
     * 入网手动通知
     * @param busId
     */
    @RequestMapping("manualNotify")
    @ResponseBody
    public void manualNotify(String busId){
        agentNetInNotityService.netIn(busId,NotifyType.NetInAdd.getValue());
    }


    /**
     * 升级手动通知
     * @param busId
     */
    @RequestMapping("manualEnterInUpdateLevelNotify")
    @ResponseBody
    public void manualEnterInUpdateLevelNotify(String busId){
        try {
            agentNetInNotityService.upgrade(busId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改手动通知
     * @param busId
     */
    @RequestMapping("manualEnterInUpdateNotify")
    @ResponseBody
    public void manualEnterInUpdateNotify(String busId){
        try {
            agentNetInNotityService.netInApplyEdit(busId,NotifyType.NetInEdit.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("manualAgentMergeNotify")
    @ResponseBody
    public void manualAgentMergeNotify(String busId,String platformCode){
        try {
            agentMergeService.manualAgentMergeNotify(busId,platformCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("manualAgentQuitNotify")
    @ResponseBody
    public void manualAgentQuitNotify(String busId,String platformCode){
        try {
            agentQuitService.manualAgentQuitNotify(busId,platformCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private TerminalTransferService terminalTransferService;

    @RequestMapping("updateActivId")
    @ResponseBody
    public String updateActivId(){
        try {
            taskApprovalService.updateActivId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping("appTerminalTransfer")
    @ResponseBody
    public String appTerminalTransfer(){
        try {
            terminalTransferService.appTerminalTransfer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
