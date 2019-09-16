package com.ryx.credit.cms.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.service.order.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cx on 2018/5/23.
 */
@RequestMapping("agentEnter")
@Controller
public class AgentEnterController extends BaseController {


    private static Logger logger = LoggerFactory.getLogger(AgentBusinfoController.class);

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgentMergeService agentMergeService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private OrganizationService organizationService;


    /**
     * 代理商
     *
     * @return
     */
    @RequestMapping(value = {"/", "index"})
    public String enterView(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("userId",String.valueOf(getUserId()));
        return "agent/agentNetIn";
    }


    /**
     * 代理商
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "agentAll")
    public PageInfo agentAll(HttpServletRequest request) {
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("agUniqNum", request.getParameter("agUniqNum"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("busPlatform", request.getParameter("busPlatform"))
                .putKeyV("busNum", request.getParameter("busNum"))
                .putKeyV("agStatus", request.getParameter("agStatus"))
                .putKeyV("time", request.getParameter("time"))
                .putKeyV("agDocDistrict", request.getParameter("agDocDistrict"))
                .putKeyV("flag", request.getParameter("flag"))
                .putKeyV("isZpos", request.getParameter("isZpos"));
        Long userId = getUserId();
        PageInfo info = agentService.queryAgentAll(pageInfo, par,userId);
        return info;
    }


    /**
     * 代理商列表管理界面
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentManageListView"})
    public String agentManageListView(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("userId",String.valueOf(getUserId()));
        optionsData(request);
        return "agent/agentNetInManageList";
    }


    /**
     *代理商列表管理界面查询数据
     * /agentEnter/agentManageList
     */
    @ResponseBody
    @RequestMapping(value = "agentManageList")
    public PageInfo agentManageList(HttpServletRequest request) {
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("agUniqNum", request.getParameter("agUniqNum"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("busPlatform", request.getParameter("busPlatform"))
                .putKeyV("busNum", request.getParameter("busNum"))
                .putKeyV("agStatus", request.getParameter("agStatus"))
                .putKeyV("time", request.getParameter("time"))
                .putKeyV("agDocDistrict", request.getParameter("agDocDistrict"))
                .putKeyV("flag", request.getParameter("flag"))
                .putKeyV("isZpos", request.getParameter("isZpos"))
                .putKeyV("reportStatus", request.getParameter("reportStatus"));
        Long userId = getUserId();
        PageInfo info = agentService.agentManageList(pageInfo, par,userId);
        return info;
    }


    /**
     * 代理商入网表单
     * agentEnter/agentForm
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"agentForm", "form"})
    public String agentNetInFormView(HttpServletRequest request, HttpServletResponse response) {
        optionsData(request);
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("ass", ass);
        request.setAttribute("payCompList", payCompList);

        //当前登录用户所属省区
        List<Map<String, Object>> userOrg = iUserService.orgCode(getUserId());
        if (userOrg.size() > 0) {
            request.setAttribute("userOrg", userOrg.get(0));
        }
        request.setAttribute("reqType","netIn");
        return "agent/agentNetInForm";
    }


    /**
     * 代理商入网信息保存
     * agentEnter/agentEnterIn
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"agentEnterIn"}, method = RequestMethod.POST)
    public ResultVO agentEnterIn(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody AgentVo agentVo) {
        try {
            Agent a = agentVo.getAgent();
            a.setcUser(getUserId() + "");
            logger.info("入网提交消息:"+ JSONObject.toJSONString(agentVo));
           // ResultVO res = agentEnterService.agentEnterIn(agentVo);
            ResultVO res = agentEnterService.saveAgentInfo(agentVo);
            if(StringUtils.isEmpty(res.getResInfo())){
                logger.info("入网返回错误消息:"+ JSONObject.toJSONString(res));
            }
            return res;
        } catch (ProcessException e) {
            e.printStackTrace();
            logger.error("入网返回错误消息",e);
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("入网返回错误消息",e);
            return ResultVO.fail(e.getMessage());
        }


    }

    /**
     * 代理商保存并审核
     */
    @ResponseBody
    @RequestMapping(value = {"saveAndaudit"}, method = RequestMethod.POST)
    public ResultVO saveAndaudit(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody AgentVo agentVo) {

        try {
            Agent a = agentVo.getAgent();
            a.setcUser(getUserId() + "");
            ResultVO res = agentEnterService.agentEnterIn(agentVo);
            if (null != res.getObj()) {
                AgentVo agentVos = (AgentVo) res.getObj();
                String agentId = agentVos.getAgent().getId();
                try {
                    res = agentEnterService.startAgentEnterActivity(agentId, getUserId() + "");
                } catch (ProcessException e) {
                    e.printStackTrace();
                    if(StringUtils.isEmpty(e.getMsg())){
                        return ResultVO.fail("流程启动失败");
                    }
                    return ResultVO.fail(e.getMsg());
                }catch(Exception e){
                    e.printStackTrace();
                    throw e;
                }
            }
            return res;
        } catch (ProcessException e) {
            e.printStackTrace();
            logger.error("入网保存失败，",e);
            if(StringUtils.isNotBlank(e.getMsg())){
                return ResultVO.fail(e.getMsg());
            }
            return ResultVO.fail("入网保存失败:"+e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("入网保存失败，",e);
            return ResultVO.fail("入网保存失败:"+e.getMessage());
        }

    }

    /**
     * 代理商入网查看(全部)
     * /agentEnter/agentQuery
     *
     * @return
     */
    @RequestMapping(value = {"agentQuery"})
    public String agentQuery(String id, String agStatus, Model model, HttpServletRequest request) {
        selectAll(id, model, request,getUserId());
        request.setAttribute("agStatus", agStatus);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.Agent.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.Agent.name(), agStatus);
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        request.setAttribute("actRecordList", actRecordList);
        return "agent/agentQuery";
    }


    /**
     * 财务查看收款账户信息
     */
    @RequestMapping(value = {"selectColinfos"})
    public String selectColinfos(String id, Model model, HttpServletRequest request) {
        optionsData(request);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        model.addAttribute("agentColinfos",agentColinfos);
        return "agent/business/queryAgentColinfoTable_model_buss";
    }

    /**
     * 代理商入网查看(当前省区下的)
     * @param id
     * @param agStatus
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentQueryCity"})
    public String agentQueryCity(String id, String agStatus, Model model, HttpServletRequest request) {
        selectAllCity(id, model, request,getUserId());
        request.setAttribute("agStatus", agStatus);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.Agent.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.Agent.name(), agStatus);
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        request.setAttribute("actRecordList", actRecordList);
        return "agent/agentQuery";
    }

    /**
     * /agentEnter/agentByid?id={agentid}
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentByid"})
    public String agentByid(String id, Model model, HttpServletRequest request) {
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        selectAllCity(id, model, request,getUserId());
        model.addAttribute("editWatch",request.getParameter("editWatch"));
        return "agent/agentEdit";
    }

    /**
     * 信息展示（申请修改审批使用）
     * /agentEnter/agentInfo?id={agentid}
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentInfo"})
    public String agentInfo(String id, Model model, HttpServletRequest request) {
        selectAll(id, model, request,getUserId());
        return "agent/agentInfo";
    }

    /**
     * 代理商入网修改
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"agentEdit"}, method = RequestMethod.POST)
    public ResultVO agentEdit(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody AgentVo agentVo) {
        try {
            ResultVO resultVO = agentEnterService.updateAgentVo(agentVo, getUserId() + "",false,"1");
            return resultVO;
        }catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        }catch (MessageException e) {
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            logger.info("代理商修改错误{}{}{}", getUserId() + "", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }

    }

    @ResponseBody
    @RequestMapping(value = {"agentEditSubmit"}, method = RequestMethod.POST)
    public ResultVO agentEditSubmit(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody AgentVo agentVo) {
        try {
            ResultVO resultVO = agentEnterService.updateAgentVo(agentVo, getUserId() + "",false,"2");
            return resultVO;
        }catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        }catch (MessageException e) {
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            logger.info("代理商修改错误{}{}{}", getUserId() + "", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }

    }

    /**
     * 代理商入网的导出
     */
    @RequestMapping("exportAgent")
    public void exportUserHistory(HttpServletResponse response, HttpServletRequest request) throws Exception {

        agentQueryService.loadCach();

        FastMap par = FastMap.fastMap("agUniqNum", request.getParameter("agUniqNum"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("busPlatform", request.getParameter("busPlatform"))
                .putKeyV("busNum", request.getParameter("busNum"))
                .putKeyV("agStatus", request.getParameter("agStatus"))
                .putKeyV("time", request.getParameter("time"))
                .putKeyV("agDocDistrict", request.getParameter("agDocDistrict"))
                .putKeyV("flag", request.getParameter("flag"))
                .putKeyV("isZpos", request.getParameter("isZpos"))
                .putKeyV("reportStatus", request.getParameter("reportStatus"));
        Long userId = getUserId();
        List<AgentoutVo> list = agentEnterService.exportAgent(par,userId);
        String filePath = "C:/upload/";
        String filePrefix = "TH";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //指导导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("编号");
        fieldCodes.add("id");
        fieldNames.add("代理商");
        fieldCodes.add("agName");
        fieldNames.add("代理商唯一编号");
        fieldCodes.add("agUniqNum");
        fieldNames.add("公司负责人");
        fieldCodes.add("agHead");

        fieldNames.add("业务平台");
        fieldCodes.add("platformName");
        fieldNames.add("业务平台编号");
        fieldCodes.add("busNum");
        fieldNames.add("类型");
        fieldCodes.add("busType");
        fieldNames.add("直属上级代理");
        fieldCodes.add("busParentId");
        fieldNames.add("二阶上级代理");
        fieldCodes.add("twoParentName");
        fieldNames.add("三阶上级代理");
        fieldCodes.add("threeParentName");

        fieldNames.add("风险承担所属代理商");
        fieldCodes.add("busRiskParent");
        fieldNames.add("激活及返现所属代理商");
        fieldCodes.add("busActivationParent");
        fieldNames.add("是否直发");
        fieldCodes.add("busSentDirectly");
        fieldNames.add("是否独立考核");
        fieldCodes.add("busIndeAss");
        fieldNames.add("业务范围");
        fieldCodes.add("busScope");
        fieldNames.add("业务区域");
        fieldCodes.add("busRegion");

        fieldNames.add("收款账户类型");
        fieldCodes.add("cloString");
        fieldNames.add("收款账户名");
        fieldCodes.add("cloRealname");
        fieldNames.add("收款账号");
        fieldCodes.add("cloBankAccount");
        fieldNames.add("收款开户总行");
        fieldCodes.add("cloBank");
        fieldNames.add("开户地区");
        fieldCodes.add("bankRegion");
        fieldNames.add("收款开户支行");
        fieldCodes.add("cloBankBranch");
        fieldNames.add("总行联行号");
        fieldCodes.add("allLineNum");
        fieldNames.add("支行联行号");
        fieldCodes.add("branchLineNum");
        fieldNames.add("税点");
        fieldCodes.add("point");
        fieldNames.add("是否开具分润发票");
        fieldCodes.add("yesOrNo");
        fieldNames.add("打款公司");
        fieldCodes.add("comName");

        fieldNames.add("报备状态");
        fieldCodes.add("reportString");
        fieldNames.add("报备时间");
        fieldCodes.add("time");

        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public void selectAll(String id, Model model, HttpServletRequest request,Long userId) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        List<Capital> capitals = agentQueryService.paymentQuery(id);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(id);
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQuery(id,request.getParameter("isZpos"),userId);
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
        model.addAttribute("agent", agent);
        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("capitals", capitals);
        model.addAttribute("agentContracts", agentContracts);
        model.addAttribute("agentBusInfos", agentBusInfos);
        model.addAttribute("attachment", attachment);
        optionsData(request);
    }

    public void selectAllCity(String id, Model model, HttpServletRequest request,Long userId) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        List<Capital> capitals = agentQueryService.paymentQuery(id);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(id);
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQueryCity(id,request.getParameter("isZpos"),userId);
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
        model.addAttribute("agent", agent);
        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("capitals", capitals);
        model.addAttribute("agentContracts", agentContracts);
        model.addAttribute("agentBusInfos", agentBusInfos);
        model.addAttribute("attachment", attachment);
        optionsData(request);
    }

    @ResponseBody
    @RequestMapping(value = "queryPlatIdByOrganName", method = RequestMethod.GET)
    public AgentResult queryAgentCanCap(@RequestParam("platId") String busPlatform){
        List<Organization> platIdOrganList = platFormService.queryByOrganName(busPlatform, null, null);
        return AgentResult.ok(platIdOrganList);
    }

    @ResponseBody
    @RequestMapping(value = {"queryIsBZYD"}, method = RequestMethod.POST)
    public ResultVO queryIsBZYD(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody AgentVo agentVo) {
        try {
            ResultVO res = new ResultVO();
            Map<String, Object> resultMap = businessPlatformService.queryIsBZYD(agentVo.getAgent().getAgBusLic(), agentVo.getBusInfoVoList());
            res.setResCode(String.valueOf(resultMap.get("code")));
            res.setResInfo(String.valueOf(resultMap.get("msg")));
            return res;
        } catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        }

    }

    @RequestMapping("invoiceShow")
    public String invoiceShow(){
        return "agent/invoiceShow";
    }

    /**
     * 代理商查看合并业务
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "agentBusQuery")
    public String agentBusQuery (String id, HttpServletRequest request) {
        logger.info("加载代理商查看-合并业务页面...");
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeService.queryMainAgentMergeBus(id);
        request.setAttribute("agentMergeBusInfos", agentMergeBusInfos);
        optionsData(request);

        return "agent/merge/agent_query_mergeBus";
    }

    /**
     * 代理商解冻
     * @param request
     * @return
     */
    @RequestMapping(value = "agentUnfreeze")
    @ResponseBody
    public Object agentUnfreeze(HttpServletRequest request){
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                logger.info("代理商解冻，数据ID为空:{}", id);
                return AgentResult.fail("代理商解冻，数据ID为空！");
            }
            AgentResult agentResult = agentService.agentUnfreeze(id, getStringUserId());
            if(agentResult.isOK()){
                return renderSuccess("解冻成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("解冻失败！");
        }
    }

    @RequestMapping(value = "selectOneSelf")
    public String selectOneSelf (String id,Model model, HttpServletRequest request) {
        String agentId = getAgentId();
        if(StringUtils.isBlank(agentId)){
            request.setAttribute("error","缺少代理商编号");
            return "error/error";
        }
        selectAll(agentId, model, request,getUserId());
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        return "agent/agentSelfQuery";
    }

    @RequestMapping("toEditCompany")
    public String toEditCompany(HttpServletRequest request, String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        AgentBusInfo agentBusInfo = businessPlatformService.findByAgentId(id);
        request.setAttribute("agentBusInfo", agentBusInfo);
        optionsData(request);
        return "agent/editBusinessPlatform";
    }

    @RequestMapping("updateCompany")
    @ResponseBody
    public Object updateCompany(HttpServletRequest request, AgentBusInfo agentBusInfo) {

        AgentResult result = new AgentResult(500, "参数错误", "");
        if (StringUtils.isBlank(agentBusInfo.getAgentId())) {
            return result;
        }
        try {
            int i = businessPlatformService.updateCompany(agentBusInfo,String.valueOf(getUserId()));
            if (i == 1) {
                return AgentResult.ok();
            }
        } catch (Exception e) {
            return AgentResult.fail("修改失败");
        }
        return result;
    }

    /**
     * 修改报备界面
     * @param request
     * @return
     */

    @RequestMapping("toReportEdit")
    public String toReportEdit(HttpServletRequest request, String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }

        optionsData(request);
        Agent agent = agentService.getAgentById(id);
        request.setAttribute("agent", agent);
        request.setAttribute("id", id);
        return "agent/reportEdit";
    }


    /**
     * 修改报备
     * @param request
     * @return
     */
    @RequestMapping("reportUpdate")
    @ResponseBody
    public Object reportUpdate(final HttpServletRequest request, Agent agent) {

        AgentResult result = new AgentResult(500, "参数错误", "");

        try {
            TreeMap<String, String> treeMap = getRequestParameter(request);
            String reportStatus = treeMap.get("reportStatus");
            if (StringUtils.isBlank(reportStatus)){
                throw new MessageException("请选择报备状态");
            }
            agent.setReportTime(DateUtil.format(treeMap.get("time"), "yyyy-MM-dd HH:mm:ss"));
            agent.setId(treeMap.get("id"));
            agent.setReportStatus(new BigDecimal(treeMap.get("reportStatus")));
            if (StringUtils.isBlank(agent.getId())) {
                return result;
            }
            int i = agentService.reportEdit(agent,String.valueOf(getUserId()));
            if (i == 1) {
                return AgentResult.ok();
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        }catch (Exception e) {
            return AgentResult.fail("修改报备失败");
        }
        return result;
    }
}
