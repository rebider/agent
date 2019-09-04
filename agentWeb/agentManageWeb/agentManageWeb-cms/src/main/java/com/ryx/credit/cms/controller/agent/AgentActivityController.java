package com.ryx.credit.cms.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.data.AttachmentService;
import com.ryx.credit.service.order.OrganizationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by cx on 2018/5/29.
 */
@RequestMapping("agActivity")
@Controller
public class AgentActivityController extends BaseController {

    protected Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private ActHiVarinstService actHiVarinstService;
    @Autowired
    private DateChangeReqService dateChangeReqService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;

    /**
     * 启动代理商入网流程，并进行验证
     *  agActivity/startAg
     * @param request
     * @param response
     * @return
     */
   /* @ResponseBody
    @RequestMapping("startAg")
    public ResultVO startAgentProcessing(HttpServletRequest request, HttpServletResponse response){
        try {
            String agentId = request.getParameter("agentId");
            ResultVO rv = agentEnterService.startAgentEnterActivity(agentId,getUserId()+"");
            return rv;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }*/
    @ResponseBody
    @RequestMapping("startAg")
    public ResultVO startAgentProcessing(HttpServletRequest request, HttpServletResponse response,@RequestBody AgentVo agentVo){
        try {
            ResultVO resultVO = agentEnterService.updateAgentVo(agentVo, getUserId() + "",false,"2");
            if(!"1".equals(resultVO.getResCode())){
                return resultVO;
            }else{
                String agentId = agentVo.getAgent().getId();
                resultVO = agentEnterService.startAgentEnterActivity(agentId,getUserId()+"");
                return resultVO;
            }
        }catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        }catch (MessageException e) {
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }
    }


    /**
     * 启动业务审批
     * agActivity/startBus
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("startBus")
    public ResultVO startAgentBusProcessing(HttpServletRequest request, HttpServletResponse response){
        try {
            String busId = request.getParameter("busId");
            agentEnterService.startAgentBusiEnterActivity(busId,getUserId()+"");
            return ResultVO.success("成功");
        } catch (MessageException e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }

    /**
     * 进入审批页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("toTaskApproval")
    public String toTaskApproval(HttpServletRequest request, HttpServletResponse response,Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busTypes = request.getParameter("busType");
        String agentId = request.getParameter("busId");
        Agent agent = agentQueryService.informationQuery(agentId);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(agentId);
        List<Capital> capitals = agentQueryService.paymentQuery(agentId);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(agentId);
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQuery(agentId);
        List<Attachment> attachment = agentQueryService.accessoryQuery(agentId, AttachmentRelType.Agent.name());
        List<PayComp> payCompList = apaycompService.recCompList();

        //审核信息
        AgentBusInfo agentBusInfo = new AgentBusInfo();
        agentBusInfo.setAgentId(agentId);
        List<Map<String, Object>> agentBusInfoList = taskApprovalService.queryBusInfoAndRemit(agentBusInfo);
        request.setAttribute("agentBusInfoList", agentBusInfoList);
        List<Map<String, Object>> mistakeList = businessPlatformService.queryIsBZYDList(agent.getAgBusLic(), agentBusInfos);

        model.addAttribute("agent",agent);
        model.addAttribute("agentColinfos",agentColinfos);
        model.addAttribute("capitals",capitals);
        model.addAttribute("agentContracts",agentContracts);
        model.addAttribute("agentBusInfos",agentBusInfos);
        model.addAttribute("attachment",attachment);
        model.addAttribute("mistakeList",mistakeList);
        model.addAttribute("payCompList", payCompList);
        optionsData(request);
        request.setAttribute("ablePlatForm",  businessPlatformService.queryAblePlatForm());
        request.setAttribute("taskId", taskId);
        request.setAttribute("proIns", proIns);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        List<Dict> netin_market = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.NETIN_MARKET.name());
        request.setAttribute("netin_market",netin_market);

        FastMap platOrg = FastMap.fastSuccessMap();
        FastMap platParentOrg = FastMap.fastSuccessMap();
        for (AgentBusInfo busInfo : agentBusInfos) {
            //上级顶级机构
            if(StringUtils.isNotBlank(busInfo.getBusParent())) {
                AgentBusInfo busInfo_parent =  businessPlatformService.findById(busInfo.getBusParent());
                if(null!=busInfo_parent && StringUtils.isNotBlank(busInfo_parent.getOrganNum())){
                    platParentOrg.putKeyV(busInfo.getId(),busInfo_parent.getOrganNum());
                }
            }
            List<Organization> platOrg_list = platFormService.queryByOrganName(busInfo.getBusPlatform(),null,null);
            platOrg.putKeyV(busInfo.getBusPlatform(),platOrg_list);
            PlatForm queryByPlatId = platFormService.queryByPlatId(busInfo.getBusPlatform());
            busInfo.setPlatformUrl(queryByPlatId.getPlatformUrl());
        }
        //上级顶级机构
        request.setAttribute("platParentOrg", platParentOrg);
        //可用顶级机构
        request.setAttribute("platOrg", platOrg);
        //财务所有打款机构
        List<Organization> organizations = organizationService.queryAllOrgan();
        request.setAttribute("allFinceOrganList", organizations);
        return "activity/TaskApproval";
    }

    /**
     * 进入业务审批页面
     *  agActivity/agentBusTaskApproval
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("agentBusTaskApproval")
    public String toAgentBusTaskApproval(HttpServletRequest request, HttpServletResponse response,Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String agentbusId = request.getParameter("busId");
        AgentBusInfo abi = businessPlatformService.findById(agentbusId);
        Agent agent = agentQueryService.informationQuery(abi.getAgentId());
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(agent.getId());
        List<Attachment> attachment = agentQueryService.accessoryQuery(agent.getId(), AttachmentRelType.Agent.name());
        List<Capital> capitals = agentQueryService.paymentQuery(agent.getId());
        List<AgentContract> agentContracts = agentQueryService.compactQuery(agent.getId());

        PlatForm queryByPlatId = platFormService.queryByPlatId(abi.getBusPlatform());
        abi.setPlatformUrl(queryByPlatId.getPlatformUrl());
        List<AgentBusInfo> agentBusInfosForApprove = Arrays.asList(abi);
        //审核信息
        request.setAttribute("agentBusInfoList", taskApprovalService.queryBusInfoAndRemitByBusId(agentbusId));
        model.addAttribute("comp",agentBusinfoService.selectComp(agentbusId));
        model.addAttribute("agent",agent);
        model.addAttribute("agentColinfos",agentColinfos);
        model.addAttribute("agentBusInfos",agentBusInfosForApprove);
        model.addAttribute("agentContracts",agentContracts);
        model.addAttribute("attachment",attachment);
        model.addAttribute("taskId",taskId);
        model.addAttribute("agentbusId",agentbusId);
        model.addAttribute("capitals",capitals);
        optionsData(request);
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        List<Dict> netin_market = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.NETIN_MARKET.name());
        request.setAttribute("netin_market",netin_market);
        List<Organization> queryOrganList = platFormService.queryByOrganName(null, null, null);
        request.setAttribute("organList", queryOrganList);

        //上级顶级机构
        FastMap platParentOrg = FastMap.fastSuccessMap();
        if(StringUtils.isNotBlank(abi.getBusParent())) {
            AgentBusInfo busInfo =  businessPlatformService.findById(abi.getBusParent());
            if(null!=busInfo && StringUtils.isNotBlank(busInfo.getOrganNum())){
                platParentOrg.putKeyV(abi.getId(),busInfo.getOrganNum());
            }
        }
        request.setAttribute("platParentOrg", platParentOrg);
        //可用顶级机构
        List<Organization> platOrg = platFormService.queryByOrganName(abi.getBusPlatform(),null,null);
        request.setAttribute("platOrg", FastMap.fastMap(abi.getBusPlatform(),platOrg));
        //财务所有打款机构
        List<Organization> organizations = organizationService.queryAllOrgan();
        request.setAttribute("allFinceOrganList", organizations);
        request.setAttribute("proIns", proIns);
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        return "activity/agentBusTaskApproval";
    }


    /**
     * 业务修改审批界面
     * /agActivity/dataChangerUpdateAprroval
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("dataChangerUpdateAprroval")
    public String toDataChangerUpdateAprrovalView(HttpServletRequest request, HttpServletResponse response,Model model){
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");

        model.addAttribute("taskId",taskId);
        model.addAttribute("busId",busId);

        DateChangeRequest dateChangeRequest = dateChangeReqService.getById(busId);
        model.addAttribute("dataType",dateChangeRequest.getDataType());
        List<Attachment> attachmentList = null;
        request.setAttribute("actRecordList", queryApprovalRecord(proIns));
        if("DC_Colinfo".equals(busType)){
            if (null != dateChangeRequest.getDataContent()) {
                optionsData(request);
                JSONObject jsonObject = JSONObject.parseObject(dateChangeRequest.getDataContent());
                AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                //老数据
                AgentVo voPre = JSONObject.parseObject(dateChangeRequest.getDataPreContent(), AgentVo.class);
                List<AgentColinfoVo> preAgentColinfos = voPre.getColinfoVoList();
                model.addAttribute("preAgentColinfos", preAgentColinfos);
                if (null != vo) {
                    if (null != vo.getAgent()) {
                        Agent agent = vo.getAgent();
                        Agent preAgent = voPre.getAgent();
                        model.addAttribute("agent", agent);
                        model.addAttribute("preAgent", preAgent);
                    }
                    if (null != vo.getBusInfoVoList() && vo.getBusInfoVoList().size() > 0) {
                        List<AgentBusInfoVo> agentBusInfos = vo.getBusInfoVoList();
                        model.addAttribute("agentBusInfos", agentBusInfos);
                    }
                    if (null != vo.getCapitalVoList() && vo.getCapitalVoList().size() > 0) {
                        List<CapitalVo> capitals = vo.getCapitalVoList();
                        for (CapitalVo capital : capitals) {
                            //获取老数据的实际到账金额

                            if (null != voPre) {
                                if (null != voPre.getCapitalVoList() && voPre.getCapitalVoList().size() > 0) {
                                    List<CapitalVo> capitalsPre = voPre.getCapitalVoList();
                                    for (CapitalVo capitalVo : capitalsPre) {
                                        if (capitalVo.getId().equals(capital.getId())) {
                                            capital.setcInAmount(capitalVo.getcInAmount());
                                        }
                                    }
                                }
                            }

                            List<String> files = capital.getCapitalTableFile();
                            List<Attachment> capital_attachmentList = new ArrayList<>();
                            if (files != null)
                                for (String file : files) {
                                    Attachment a = attachmentService.getAttachmentById(file);
                                    if (a != null) {
                                        capital_attachmentList.add(a);
                                    }
                                }
                            capital.setAttachmentList(capital_attachmentList);
                        }
                        model.addAttribute("capitals", capitals);
                    }
                    if (null != vo.getContractVoList() && vo.getContractVoList().size() > 0) {
                        List<AgentContractVo> agentContracts = vo.getContractVoList();

                        //合同附件
                        for (AgentContractVo agentContractVo : agentContracts) {
                            List<String> files = agentContractVo.getContractTableFile();
                            List<Attachment> agentContractVo_attachmentList = new ArrayList<>();
                            if (files != null)
                                for (String file : files) {
                                    Attachment a = attachmentService.getAttachmentById(file);
                                    if (a != null) {
                                        agentContractVo_attachmentList.add(a);
                                    }
                                }
                            agentContractVo.setAttachmentList(agentContractVo_attachmentList);
                            String agentAssProtocol = "";
                            String protocolRuleValue = "";
                            if (StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())) {
                                List<AssProtoCol> assProtoCols = agentAssProtocolService.queryProtocol(agentContractVo.getAgentAssProtocol(), "");
                                if (null != assProtoCols && assProtoCols.size() > 0) {
                                    AssProtoCol assProtoCol = assProtoCols.get(0);
                                    agentAssProtocol = assProtoCol.getProtocolRule();
                                }
                            }
                            if (StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())) {
                                protocolRuleValue = agentContractVo.getProtocolRuleValue();
                            }
                            String protocolRuleRel = agentAssProtocol.replace("{}", protocolRuleValue);

                            agentContractVo.setAttachmentList(agentContractVo_attachmentList);
                            agentContractVo.setAssProtocolMap(FastMap.fastMap("PROTOCOL_RULE_REL", protocolRuleRel));
                        }

                        model.addAttribute("agentContracts", agentContracts);
                    }
                    if (null != vo.getColinfoVoList() && vo.getColinfoVoList().size() > 0) {
                        List<AgentColinfoVo> agentColinfos = vo.getColinfoVoList();

                        //合同附件
                        for (AgentColinfoVo agentColinfoVo : agentColinfos) {
                            List<String> files = agentColinfoVo.getColinfoTableFile();
                            if (files == null) {
                                continue;
                            }
                            List<Attachment> agentColinfoVo_attachmentList = new ArrayList<>();
                            for (String file : files) {
                                Attachment a = attachmentService.getAttachmentById(file);
                                if (a != null) {
                                    agentColinfoVo_attachmentList.add(a);
                                }
                            }
                            agentColinfoVo.setAttachmentList(agentColinfoVo_attachmentList);
                        }

                        model.addAttribute("agentColinfos", agentColinfos);
                    }
                    if (null != vo.getAgentTableFile() && vo.getAgentTableFile().size() > 0) {
                        List<String> attachment = vo.getAgentTableFile();
                        for (String s : attachment) {
                            attachmentList = agentQueryService.accessoryQuery(s, AttachmentRelType.Agent.name());
                        }
                        model.addAttribute("attachment", attachmentList);
                    }
                }
                List<PayComp> payCompList = apaycompService.recCompList();
                request.setAttribute("payCompList", payCompList);
                request.setAttribute("agentVo", vo);
                for (AgentBusInfo agentBusInfo : vo.getBusInfoVoList()) {
                    AgentBusInfo businfoById = agentBusinfoService.getById(agentBusInfo.getId());
                    agentBusInfo.setOrganNum(businfoById.getOrganNum());
                    agentBusInfo.setFinaceRemitOrgan(businfoById.getFinaceRemitOrgan());
                    PlatForm queryByPlatId = platFormService.queryByPlatId(agentBusInfo.getBusPlatform());
                    agentBusInfo.setPlatformUrl(queryByPlatId.getPlatformUrl());
                }
                List<Organization> organizations = organizationService.queryAllOrgan();
                request.setAttribute("allFinceOrganList", organizations);
            }
            return "activity/dataChangeApproval_Colinfo";
        }else{
            if (null != dateChangeRequest.getDataContent()) {
                optionsData(request);
                JSONObject jsonObject = JSONObject.parseObject(dateChangeRequest.getDataContent());
                AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                //老数据
                AgentVo voPre = JSONObject.parseObject(dateChangeRequest.getDataPreContent(), AgentVo.class);

                if (null != vo) {
                    if (null != vo.getAgent()) {
                        Agent agent = vo.getAgent();
                        model.addAttribute("agent", agent);
                    }
                    if (null != vo.getBusInfoVoList() && vo.getBusInfoVoList().size() > 0) {
                        List<AgentBusInfoVo> agentBusInfos = vo.getBusInfoVoList();
                        model.addAttribute("agentBusInfos", agentBusInfos);
                    }
                    if (null != vo.getAgentTableFile() && vo.getAgentTableFile().size() > 0) {
                        List<String> attachment = vo.getAgentTableFile();
                        for (String s : attachment) {
                            attachmentList = agentQueryService.accessoryQuery(s, AttachmentRelType.Agent.name());
                        }
                        model.addAttribute("attachment", attachmentList);
                    }
                }
                List<PayComp> payCompList = apaycompService.recCompList();
                request.setAttribute("payCompList", payCompList);
                request.setAttribute("agentVo", vo);
                for (AgentBusInfo agentBusInfo : vo.getBusInfoVoList()) {
                    AgentBusInfo businfoById = agentBusinfoService.getById(agentBusInfo.getId());
                    agentBusInfo.setOrganNum(businfoById.getOrganNum());
                    agentBusInfo.setFinaceRemitOrgan(businfoById.getFinaceRemitOrgan());
                    PlatForm queryByPlatId = platFormService.queryByPlatId(agentBusInfo.getBusPlatform());
                    agentBusInfo.setPlatformUrl(queryByPlatId.getPlatformUrl());

                }
                for (AgentBusInfo agentBusInfo : voPre.getBusInfoVoList()) {
                    for (AgentBusInfo agentBusInfoafter : vo.getBusInfoVoList()) {
                        selectAllMap(voPre.getAgent().getId(), agentBusInfoafter.getId(), model, request, getUserId());
                    }
                }
                List<Organization> organizations = organizationService.queryAllOrgan();
                request.setAttribute("allFinceOrganList", organizations);
            }
            return "agent/business/dataChangeApproval_buss";
        }
     //   return "activity/dataChangeApproval";
    }


    public void selectAllMap(String id,String bussinessId, Model model, HttpServletRequest request,Long userId) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQuery(id,bussinessId,request.getParameter("isZpos"),userId);
        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.Agent.name());

        List<String> busIds = new ArrayList<>();
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            busIds.add(agentBusInfo.getId());
            PlatForm queryByPlatId = platFormService.queryByPlatId(agentBusInfo.getBusPlatform());
            agentBusInfo.setPlatformUrl(queryByPlatId.getPlatformUrl());
        }
        List<AssProtoColRel> assProtoColRelList = new ArrayList<>();
        if (busIds.size() > 0) {
            assProtoColRelList = agentAssProtocolService.queryProtoColByBusIds(busIds);
        }
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        model.addAttribute("assProtoColRelList", assProtoColRelList);
        model.addAttribute("ass", ass);
        model.addAttribute("preAgent", agent);
        model.addAttribute("preBusInfoVoList", agentBusInfos);
        model.addAttribute("attachment", attachment);
        optionsData(request);
    }


    /**
     * 处理任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("taskApproval")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response,@RequestBody AgentVo agentVo){

        AgentResult result = null;
        try {
            result = taskApprovalService.approvalTask(agentVo, String.valueOf(getUserId()));
        } catch (MessageException e) {
            logger.info("taskApproval处理任务异常:"+e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        }catch (ProcessException e) {
            logger.info("taskApproval处理任务异常:"+e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("taskApproval处理任务异常:"+e.getMessage());
            e.printStackTrace();
            result = AgentResult.fail("处理失败");
        } finally {
            if(result==null){
                return ResultVO.fail("处理失败");
            }
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail(result.getMsg());
            }
        }
    }


    /**
     * 图片获取
     * agActivity/approvalImage?taskId=
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approvalImage")
    public String approvalImage(HttpServletRequest request, HttpServletResponse response){
        try {
            String taskId = request.getParameter("taskId");
            Map dataRes = activityService.getImage(taskId);
            Object data =  dataRes.get("b");
            outImg(data,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取整体流程图
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approvalActImage") // todo
    public String approvalActImage(HttpServletRequest request, HttpServletResponse response){
        try {
            String busId = request.getParameter("busId");
            String busType = request.getParameter("busType");
            Map dataRes = taskApprovalService.findBusActByBusId(busId,busType,AgStatus.Approving.name());
            if(dataRes==null){
                return null;
            }
            Object data =  dataRes.get("b");
            outImg(data,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void outImg(Object data,HttpServletResponse response)throws IOException{
        if(data!=null){
            byte[] img = (byte[])data;
            response.setContentType("image/jpeg");
            OutputStream toClient = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(img);
            int len;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                toClient.write(buf, 0, len);
            }
            toClient.close();
        }
    }

}
