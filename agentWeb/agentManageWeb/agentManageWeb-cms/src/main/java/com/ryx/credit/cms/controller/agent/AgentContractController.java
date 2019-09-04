package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.agent.AgentContractService;
import com.ryx.credit.service.agent.AgentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lhl on 2019/7/27.
 * 代理商：合同管理
 */
@RequestMapping("agentContract")
@Controller
public class AgentContractController extends BaseController {

    private static Logger logger  = LoggerFactory.getLogger(AgentContractController.class);
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;

    @RequestMapping(value = "toAgentConPage")
    public String toAgentContractPage(HttpServletRequest request) {
        List<Dict> agStatuss = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
        List<Dict> contractType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CONTRACT_TYPE.name());
        List<Dict> yesOrNo = ServiceFactory.dictOptionsService.dictList(DictGroup.ALL.name(), DictGroup.YESORNO.name());
        request.setAttribute("agStatuss", agStatuss);
        request.setAttribute("contractType", contractType);
        request.setAttribute("yesOrNo", yesOrNo);
        return "agent/contract/agentContractList";
    }

    @RequestMapping(value = "getAgentConList")
    @ResponseBody
    public PageInfo getAgentContractList(HttpServletRequest request) {
        Page page = pageProcess(request);
        FastMap fastMap = FastMap
                .fastMap("agentId", request.getParameter("agentId"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("agStatus", request.getParameter("agStatus"))
                .putKeyV("contType", request.getParameter("contType"))
                .putKeyV("contNum", request.getParameter("contNum"))
                .putKeyV("contDateStart", request.getParameter("contDateStart"))
                .putKeyV("contDateEnd", request.getParameter("contDateEnd"))
                .putKeyV("contEndDateStart", request.getParameter("contEndDateStart"))
                .putKeyV("contEndDateEnd", request.getParameter("contEndDateEnd")
                );
        Long userId = getUserId();
        PageInfo pageInfo = agentContractService.getAgentContractList(page, fastMap, userId);
        return pageInfo;
    }

    @RequestMapping(value = "agentConQuery")
    public String toAgentContractQuery(String agentId, Model model, HttpServletRequest request) {
        Long userId = getUserId();
        selectConAll(agentId, model, request, userId);
        return "agent/contract/agentContractQuery";
    }

    @RequestMapping(value = "agentConEditPage")
    public String toAgentContractEditPage(String agentId, Model model, HttpServletRequest request) {
        Long userId = getUserId();
        selectConAll(agentId, model, request, userId);
        return "agent/contract/agentContractEdit";
    }

    @RequestMapping(value = "agentConEdit")
    @ResponseBody
    public ResultVO getAgentContractEdit(@RequestBody AgentVo agentVo, HttpServletRequest request) {
        try {
            agentVo.getAgent().setcUser(getUserId()+"");
            ResultVO resultVO = agentContractService.updateAgentContractVo(agentVo.getContractVoList(), agentVo.getAgent(), getUserId()+"");
            return resultVO;
        }catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            logger.info("合同修改错误{}{}{}", getUserId()+"", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }
    }

    public void selectConAll(String agentId, Model model, HttpServletRequest request, Long userId) {
        Agent agent = agentQueryService.informationQuery(agentId);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(agentId);
        List<Attachment> attachment = agentQueryService.accessoryQuery(agentId, AttachmentRelType.Agent.name());
        List<Dict> agStatuss = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
        List<Dict> contractType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CONTRACT_TYPE.name());
        List<Dict> yesOrNo = ServiceFactory.dictOptionsService.dictList(DictGroup.ALL.name(), DictGroup.YESORNO.name());
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        model.addAttribute("agent", agent);
        model.addAttribute("agentContracts", agentContracts);
        model.addAttribute("attachment", attachment);
        model.addAttribute("agStatuss", agStatuss);
        model.addAttribute("contractType", contractType);
        model.addAttribute("yesOrNo", yesOrNo);
        model.addAttribute("ass", ass);
        optionsData(request);
    }

}
