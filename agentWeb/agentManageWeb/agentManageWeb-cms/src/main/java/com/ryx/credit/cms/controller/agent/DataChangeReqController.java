package com.ryx.credit.cms.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.data.AttachmentService;
import com.ryx.credit.service.order.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DataChangeReqController
 * @Description TODO
 * @Author lrr
 * @Date 2018/6/6
 **/
@Controller
@RequestMapping("dataChangeReq")
public class DataChangeReqController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(DataChangeReqController.class);
    private static String EXPORT_TASK_PATH = AppConfig.getProperty("export.path");
    @Autowired
    private DateChangeReqService dateChangeReqService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private DataChangeActivityService dataChangeActivityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;


    @RequestMapping(value = {"/", "dataQuery"})
    public String enterView(HttpServletRequest request) {
        optionsData(request);
        return "agent/dataQuery";
    }

    /**
     * dataChangeReq/dataQueryAll
     * @param request
     * @return
     */
    @RequestMapping(value = { "dataQueryAll"})
    public String dataQueryAll(HttpServletRequest request) {
        optionsData(request);
        return "agent/dataQueryAll";
    }

    @RequestMapping("queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("appyStatus", request.getParameter("appyStatus"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("dataType", request.getParameter("dataType"))
                .putKeyV("cUser", getStringUserId());
        PageInfo info = dateChangeReqService.queryData(pageInfo, par);
        return info;
    }

    /**
     * 查询所有代理商数据修改申请
     *  dataChangeReq/queryDataAll
     * @param request
     * @return
     */
    @RequestMapping("queryDataAll")
    @ResponseBody
    public Object queryDataAll(HttpServletRequest request) {
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("appyStatus", request.getParameter("appyStatus"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("dataType", request.getParameter("dataType"))
                .putKeyV("finishTime", request.getParameter("finishTime"));
        PageInfo info = dateChangeReqService.queryData(pageInfo, par);
        return info;
    }

    @RequestMapping(value = {"/", "selectById"})
    public Object selectById(HttpServletRequest request, String id, Model model) {
        DateChangeRequest dateChangeRequest = dateChangeReqService.getById(id);
        List<Attachment> attachmentList = null;
        if (null != dateChangeRequest.getDataContent()) {
            optionsData(request);
            AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
            if (null != vo) {
                if (null != vo.getAgent()) {
                    Agent agent = vo.getAgent();
                    model.addAttribute("agent", agent);
                }
                if (null != vo.getBusInfoVoList() && vo.getBusInfoVoList().size() > 0) {
                    List<AgentBusInfoVo> agentBusInfos = vo.getBusInfoVoList();
                    model.addAttribute("agentBusInfos", agentBusInfos);
                }
                if (null != vo.getCapitalVoList() && vo.getCapitalVoList().size() > 0) {
                    List<CapitalVo> capitals = vo.getCapitalVoList();
                    for (CapitalVo capital : capitals) {
                        List<String> files = capital.getCapitalTableFile();
                        List<Attachment> capital_attachmentList = new ArrayList<>();
                        if(files!=null)
                            for (String file : files) {
                                Attachment a = attachmentService.getAttachmentById(file);
                                if(a!=null) {
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
                        if(files!=null)
                            for (String file : files) {
                                Attachment a = attachmentService.getAttachmentById(file);
                                if(a!=null) {
                                    agentContractVo_attachmentList.add(a);
                                }
                            }
                        String agentAssProtocol="";
                        String protocolRuleValue="";
                        if(StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())){
                            List<AssProtoCol> assProtoCols = agentAssProtocolService.queryProtocol(agentContractVo.getAgentAssProtocol(), "");
                            if(null!=assProtoCols && assProtoCols.size()>0){
                                AssProtoCol assProtoCol = assProtoCols.get(0);
                                agentAssProtocol= assProtoCol.getProtocolRule();
                            }
                        }
                        if(StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())){
                            protocolRuleValue= agentContractVo.getProtocolRuleValue();
                        }
                        String protocolRuleRel=agentAssProtocol.replace("{}",protocolRuleValue);

                        agentContractVo.setAttachmentList(agentContractVo_attachmentList);
                        agentContractVo.setAssProtocolMap(FastMap.fastMap("PROTOCOL_RULE_REL",protocolRuleRel));
                    }
                    model.addAttribute("agentContracts", agentContracts);
                }
                if (null != vo.getColinfoVoList() && vo.getColinfoVoList().size() > 0) {
                    List<AgentColinfoVo> agentColinfos = vo.getColinfoVoList();
                    //合同附件
                    for (AgentColinfoVo agentColinfoVo : agentColinfos) {
                        List<String> files = agentColinfoVo.getColinfoTableFile();
                        if(files==null){
                            continue;
                        }
                        List<Attachment> agentColinfoVo_attachmentList = new ArrayList<>();
                        for (String file : files) {
                            Attachment a = attachmentService.getAttachmentById(file);
                            if(a!=null) {
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
            request.setAttribute("agStatus",AgStatus.getAgStatusString(dateChangeRequest.getAppyStatus()));
            request.setAttribute("busIdImg",dateChangeRequest.getId());
            request.setAttribute("busTypeImg",dateChangeRequest.getDataType());
            for (AgentBusInfo agentBusInfo : vo.getBusInfoVoList()) {
                List<Organization> queryOrganList = organizationService.selectOrganization(agentBusInfo.getOrganNum());
                request.setAttribute("organList", queryOrganList);
                List<Organization> queryOrganLists = organizationService.selectOrganization(agentBusInfo.getFinaceRemitOrgan());
                request.setAttribute("finceOrganList", queryOrganLists);
                PlatForm queryByPlatId = platFormService.queryByPlatId(agentBusInfo.getBusPlatform());
                agentBusInfo.setPlatformUrl(queryByPlatId.getPlatformUrl());
            }
        }

        BusActRel busActRel = taskApprovalService.queryBusActRel(id, dateChangeRequest.getDataType(), AgStatus.getAgStatusString(dateChangeRequest.getAppyStatus()) );
        List<Map<String, Object>> actRecordList = null;
        if(busActRel!=null){
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList",actRecordList);
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        return "agent/agentQuery";
    }
   @RequestMapping("startData")
   @ResponseBody
   public Object startData(String id,String userId) {
       try {
           ResultVO resultVO = dataChangeActivityService.startDataChangeActivity(id, getUserId()+"");
           return resultVO;
       }catch (MessageException e) {
           e.printStackTrace();
           return ResultVO.fail(e.getMsg());
       }catch (Exception e) {
           return ResultVO.fail(e.getMessage());
       }

   }



    /**
     * 处理任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("taskApproval")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo){

        AgentResult result = null;
        try {
            result = dataChangeActivityService.approvalTask(agentVo, String.valueOf(getUserId()));
        } catch (MessageException e) {
            log.info("taskApproval处理任务异常:"+e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        }catch (ProcessException e) {
            log.info("taskApproval处理任务异常:"+e.getMsg());
            e.printStackTrace();
            result = AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            log.info("taskApproval处理任务异常:"+e.getMessage());
            e.printStackTrace();
            result = AgentResult.fail("处理失败");
        } finally {
            if(result==null){
                return ResultVO.fail("处理失败");
            }
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail("处理失败:"+result.getMsg());
            }
        }
    }



    /**
     * 导出数据
     * @param response
     */
    @RequestMapping(value = "exportData")
    public void exportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FastMap par = FastMap.fastMap("appyStatus", request.getParameter("appyStatus"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("dataType", request.getParameter("dataType"))
                .putKeyV("finishTime", request.getParameter("finishTime"));
        if (request.getParameter("dataType") != null ) {
            if (request.getParameter("dataType").equals(BusActRelBusType.DC_Colinfo.name())) {
                log.info("业务类型-->代理商账户修改申请，调用合并导出...");
                exportDcColinfo(par, response);
            }else{
                throw new MessageException("导出请选择业务类型");
            }
        } else {
            log.info("该业务类型暂不支持导出操作！");
            throw new MessageException("该业务类型暂不支持导出操作！");
        }
    }

    /**
     * 代理商账户修改申请
     * @param par
     * @param response
     * @throws Exception
     */

    public void exportDcColinfo(FastMap par, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> mapListMerge = dateChangeReqService.exportDcColinfo(par);
        Map<String, Object> param = new HashMap<String, Object>(6);
        String title = "代理商ID,代理商唯一编码,审批状态,完成审批时间,变更前,代理商名称,收款账户类型,收款账户名,收款账号,收款开户支行,支行联行号,是否开具分润发票,变更后,代理商名称,收款账户类型,收款账户名,收款账号,收款开户支行,支行联行号,是否开具分润发票";
        String column = "id,agUniqNum,appyStatus,cUpdate,before,agNamePre,cloTypePre,cloRealnamePre,cloBankAccountPre,cloBankBranchPre,branchLineNumPre,cloInvoicePre,after,agName,cloType,cloRealname,cloBankAccount,cloBankBranch,branchLineNum,cloInvoice";
        param.put("path", EXPORT_TASK_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", mapListMerge);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }


    @RequestMapping("deleteDataChange")
    @ResponseBody
    public Object deleteDataChange(String id, String userId) {
        try {
            ResultVO resultVO = dataChangeActivityService.deleteDataChange(id, getUserId()+"");
            return resultVO;
        }catch (MessageException e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            return ResultVO.fail(e.getMessage());
        }
    }

}
