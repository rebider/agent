package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentContractVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.BusinessOutVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.order.OrganizationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务平台管理 控制层
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:30
 */
@Controller
@RequestMapping("/business")
public class BusinessPlatformController extends BaseController {
    protected Logger logger = LogManager.getLogger(this.getClass());
    private static org.slf4j.Logger log = LoggerFactory.getLogger(BusinessPlatformController.class);
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;

    @RequestMapping("platformList")
    public String platformList(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        request.setAttribute("busTypes", ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name()));
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("userId",String.valueOf(getUserId()));
        return "agent/businessPlatformList";
    }

    @RequestMapping("platformListManager")
    public String platformListManager(HttpServletRequest request) {
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        request.setAttribute("busTypes", ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name()));
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("userId",String.valueOf(getUserId()));
        return "agent/businessPlatformListManager";
    }

    @RequestMapping(value = "queryBusinessPlatform")
    @ResponseBody
    public Object queryBusinessPlatform(HttpServletRequest request, AgentBusInfo agentBusInfo, Agent agent) {
        Page pageInfo = pageProcess(request);
        Long userId = getUserId();
        agentBusInfo.setcUser(String.valueOf(userId));
        PageInfo resultPageInfo = businessPlatformService.queryBusinessPlatformList(agentBusInfo, agent, pageInfo,userId);
        return resultPageInfo;
    }
    @RequestMapping(value = "queryBusinessPlatformManager")
    @ResponseBody
    public Object queryBusinessPlatformManager(HttpServletRequest request, AgentBusInfo agentBusInfo, Agent agent) {
        Page pageInfo = pageProcess(request);
        Long userId = getUserId();
        agentBusInfo.setcUser(String.valueOf(userId));
        String approveTimeStart = request.getParameter("approveTimeStart");
        String approveTimeEnd = request.getParameter("approveTimeEnd");
        PageInfo resultPageInfo = businessPlatformService.queryBusinessPlatformListManager(agentBusInfo, agent, pageInfo,userId,approveTimeStart,approveTimeEnd);
        return resultPageInfo;
    }

    @RequestMapping("toAddBusPlatPage")
    public String toAddBusPlatPage(HttpServletRequest request) {
        optionsData(request);
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        request.setAttribute("ass", ass);
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        //当前登录用户所属省区
        List<Map<String, Object>> userOrg = iUserService.orgCode(getUserId());
        if (userOrg.size() > 0) {
            request.setAttribute("userOrg", userOrg.get(0));
        }
        request.setAttribute("reqType","netInBus");
        return "agent/businessPlatformAdd";
    }

    @RequestMapping("toEditBusPlatPage")
    public String toEditBusPlatPage(HttpServletRequest request, String id) {
        optionsData(request);
        AgentBusInfo agentBusInfo = businessPlatformService.findById(id);
        List<AgentBusInfo> agentBusInfos = new ArrayList<>();
        agentBusInfos.add(agentBusInfo);
        request.setAttribute("agentBusInfos", agentBusInfos);
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        request.setAttribute("ass", ass);
        return "agent/businessPlatformEdit";
    }

    @RequestMapping("toEditBusinfoPage")
    public String toEditBusinfoPage(HttpServletRequest request, String id) {
        optionsData(request);
        AgentBusInfo agentBusInfo = businessPlatformService.findById(id);
        List<AgentBusInfo> agentBusInfos = new ArrayList<>();
        agentBusInfos.add(agentBusInfo);
        request.setAttribute("agentBusInfos", agentBusInfos);
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        request.setAttribute("ass", ass);
        List<Organization> platOrg_list = platFormService.queryByOrganName(agentBusInfo.getBusPlatform(),null,null);
        request.setAttribute("platOrg_list", platOrg_list);
        return "agent/businfoEdit";
    }

    @RequestMapping("editBusinfoData")
    @ResponseBody
    public Object editBusinfoData(HttpServletRequest request, @RequestBody AgentVo agentVo) {
        List<AgentBusInfoVo> busInfoVoList = agentVo.getBusInfoVoList();
        if (busInfoVoList == null) {
            return 0;
        }
        try {
            businessPlatformService.updateBusinfoData(busInfoVoList);
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            AgentResult fail = AgentResult.fail(e.getMessage());
            return fail;
        }
        return 1;
    }

    @RequestMapping("toSeeBusPlatPage")
    public String toSeeBusPlatPage(HttpServletRequest request, String id, Model model) {
        selectAll(id, model, request);
        String cloReviewStatus = request.getParameter("cloReviewStatus");
        request.setAttribute("cloReviewStatus", cloReviewStatus);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.Business.name());

        /*BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.Business.name(), AgStatus.getAgStatusString(new BigDecimal(cloReviewStatus)));*/
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.Business.name(), null);//审批流增加撤销以后，查看审批过程不再根据审批结果查询
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        return "agent/businessPlatformSee";
    }

    @RequestMapping("addBusPlat")
    @ResponseBody
    public Object addBusPlat(HttpServletRequest request, @RequestBody AgentVo agentVo) {
        Agent agent = new Agent();
        agent.setId(agentVo.getAgentId());
        agent.setcUser(String.valueOf(getUserId()));
        agentVo.setAgent(agent);
        AgentResult result = AgentResult.fail("失败");
        try {
            result = businessPlatformService.saveBusinessPlatform(agentVo);
        }catch (ProcessException e) {
            e.printStackTrace();
            result.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("editBusPlat")
    @ResponseBody
    public Object editBusPlat(HttpServletRequest request, @RequestBody AgentVo agentVo) {
        List<AgentBusInfoVo> busInfoVoList = agentVo.getBusInfoVoList();
        if (busInfoVoList == null) {
            return 0;
        }
        try {
            String agentId="";
            for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                agentId=  agentBusInfoVo.getAgentId();
            }
            FastMap par = FastMap.fastMap("userId", getUserId() + "")
                    .putKeyV("agentId", agentId)
                    .putKeyV("sid", agentVo.getSid());

            businessPlatformService.updateBusInfoList(busInfoVoList);
            //修改合同
            agentContractService.updateContractList(agentVo.getContractVoList(),par);
            //修改缴纳款项
            accountPaidItemService.updateListCapitalVo(agentVo.getCapitalVoList(),par);

        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            AgentResult fail = AgentResult.fail(e.getMessage());
            return fail;
        }
        return 1;
    }

    @RequestMapping("verifyAgent")
    @ResponseBody
    public Object verifyAgent(String agUniqNum) {
        List<String> agStatusList = new ArrayList<>();
        agStatusList.add(AgStatus.Approved.name());
        AgentResult agentResult = businessPlatformService.verifyAgent(agUniqNum,agStatusList);
        if(agentResult.isOK()){
            return renderSuccess(JsonUtil.objectToJson(agentResult.getData()));
        }else{
            return renderError(agentResult.getMsg());
        }
    }


    private void selectAll(String busId, Model model, HttpServletRequest request) {
        AgentBusInfo agentBusInfo = agentBusinfoService.getById(busId);
        Agent agent = agentQueryService.informationQuery(agentBusInfo.getAgentId());
        String agentId = agentBusInfo.getAgentId();
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(agentId);
        List<Capital> capitals = agentQueryService.paymentQuery(agentId);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(agentId);
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQuery(agentId);
        List<Attachment> attachment = agentQueryService.accessoryQuery(agentId, AttachmentRelType.Agent.name());
        List<String> busIds = new ArrayList<>();
        for (AgentBusInfo agentBus : agentBusInfos) {
            busIds.add(agentBus.getId());
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

        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("capitals", capitals);
        model.addAttribute("agentContracts", agentContracts);
        List<AgentBusInfo> agentBusInfoList = new ArrayList<>();
        agentBusInfoList.add(agentBusInfo);
        model.addAttribute("agentBusInfos", agentBusInfoList);
        model.addAttribute("attachment", attachment);
        model.addAttribute("agent", agent);
        List<Organization> queryOrganList = organizationService.selectOrganization(agentBusInfo.getOrganNum());
        request.setAttribute("organList", queryOrganList);
        List<Organization> queryOrganLists = organizationService.selectOrganization(agentBusInfo.getFinaceRemitOrgan());
        request.setAttribute("finceOrganList", queryOrganLists);
        optionsData(request);
    }

    @RequestMapping("proceedsSet")
    public String proceedsSet(HttpServletRequest request, String agentId, String id) {
        optionsData(request);
        //基础信息
        Agent agent = agentQueryService.informationQuery(agentId);
        //业务信息
        List<AgentBusInfo> agentBusInfos = agentQueryService.businessQuery(agentId);
        AgentBusInfo agentBuss = agentBusinfoService.getById(id);
        List<String> busIds = new ArrayList<>();
        for (AgentBusInfo agentBus : agentBusInfos) {
            busIds.add(agentBus.getId());
        }
        List<AssProtoColRel> assProtoColRelList = new ArrayList<>();
        if (busIds.size() > 0) {
            assProtoColRelList = agentAssProtocolService.queryProtoColByBusIds(busIds);
        }
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        List<AgentBusInfo> agentBusInfoLists = new ArrayList<>();
        agentBusInfoLists.add(agentBuss);
        //打款公司和收款账户
        AgentBusInfo agentBusInfo = new AgentBusInfo();
        agentBusInfo.setAgentId(id);
        List<Map<String, Object>> agentBusInfoList = taskApprovalService.queryById(agentBusInfo);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(agentId);
        request.setAttribute("agentBusInfoList", agentBusInfoList);
        request.setAttribute("agent", agent);
        request.setAttribute("agentColinfos", agentColinfos);
        request.setAttribute("assProtoColRelList", assProtoColRelList);
        request.setAttribute("ass", ass);
        request.setAttribute("agentBusInfos", agentBusInfoLists);
        return "agent/proceedsSet";
    }

    @ResponseBody
    @RequestMapping("save")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo) {
        agentVo.setApprovalResult("pass");
        AgentResult result = null;
        try {
            result = taskApprovalService.updateApproval(agentVo, String.valueOf(getUserId()));
        } catch (Exception e) {
            logger.info("taskApproval处理任务异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (result == null) {
                return ResultVO.fail("处理失败");
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功");
            } else {
                return ResultVO.fail("处理失败");
            }
        }
    }

    @RequestMapping("toEditBusPlatDkgsPage")
    public String toEditBusPlatDkgsPage(HttpServletRequest request, String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        AgentBusInfo agentBusInfo = businessPlatformService.findById(id);
        request.setAttribute("agentBusInfo", agentBusInfo);
        optionsData(request);
        return "agent/businessPlatformEditDkgs";
    }

    @RequestMapping("editBusPlatDkgs")
    @ResponseBody
    public Object editBusPlatDkgs(HttpServletRequest request, AgentBusInfo agentBusInfo) {

        AgentResult result = new AgentResult(500, "参数错误", "");
        if (StringUtils.isBlank(agentBusInfo.getId())) {
            return result;
        }
        try {
            int i = businessPlatformService.updateBusPlatDkgsBySelective(agentBusInfo,String.valueOf(getUserId()));
            if (i == 1) {
                return AgentResult.ok();
            }
        } catch (Exception e) {
            return AgentResult.fail("修改失败");
        }
        return result;
    }

    @RequestMapping("importView")
    public String importView(HttpServletRequest request, HttpServletResponse response) {
        return "agent/businessImport";
    }

    /**
     * 上传
     *
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("importExcel")
    public ResultVO importExcel(@RequestParam(value = "file", required = false) MultipartFile[] files, HttpServletRequest request) {
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            String userid = getUserId() + "";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                log.info("用户{}文件{}", userid, filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(), filename);
                try {
                    if (null == workbook) {
                        log.info("用户{}消息", userid, "创建Excel工作薄为空");
                        return ResultVO.fail("创建Excel工作薄为空");
                    }
                    Sheet sheet = null;
                    Row row = null;
                    Cell cell = null;
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

                        sheet = workbook.getSheetAt(i);
                        if (sheet == null) {
                            continue;
                        }
                        List list = new ArrayList<List<Object>>();
                        // 遍历当前sheet中的所有行
                        for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) { // 这里的加一是因为下面的循环跳过取第一行表头的数据内容了
                            row = sheet.getRow(j);
                            if (row == null || row.getFirstCellNum() == j) {
                                continue;
                            }
                            // 遍历所有的列
                            List<Object> li = new ArrayList<Object>();
                            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                                cell = row.getCell(y);
                                if (null == cell) {
                                    li.add("");
                                } else {
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if (li.size() > 0) {
                                list.add(li);
                            } else {
                                log.info("用户{}地址信息导入dubbo调用{}", userid, list.size());
                            }
                        }
                        log.info("用户{}地址信息导入dubbo调用{}", userid, list.size());
                           List<String> resd = businessPlatformService.addList(list, userid);
                        log.info("用户{}代理商导入dubbo调用结果返回{}", userid, resd.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof MessageException) {
                        String msg = ((MessageException) e).getMsg();
                        return ResultVO.fail(msg);
                    }
                    log.info("导入地址信息异常：", e.getMessage());
                    return ResultVO.fail(e.getMessage());
                } finally {
                    workbook.close();
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     *
     * 业务的导出
     */
    @RequestMapping("exportAgent")
    public void exportAgent(Agent agent, HttpServletResponse response,HttpServletRequest request) throws Exception {
        agentQueryService.loadCach();
         FastMap par = FastMap.fastMap("agUniqNum", request.getParameter("agUniqNum"))
                .putKeyV("id", request.getParameter("id"))
                .putKeyV("agName", request.getParameter("agName"))
                .putKeyV("busPlatform", request.getParameter("busPlatform"))
                .putKeyV("busNum", request.getParameter("busNum"))
                .putKeyV("cloReviewStatus", request.getParameter("cloReviewStatus"))
                .putKeyV("approveTimeStart", request.getParameter("approveTimeStart"))
                .putKeyV("approveTimeEnd", request.getParameter("approveTimeEnd"))
                .putKeyV("busType", request.getParameter("busType"));
        Long userId = getUserId();
        List<BusinessOutVo> list =businessPlatformService.exportAgent(par,userId);
        String filePath = "C:/upload/";
        String filePrefix = "Business";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //指导导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("代理商名称");
        fieldCodes.add("agName");
        fieldNames.add("代理商唯一编号");
        fieldCodes.add("agUniqNum");
        fieldNames.add("负责人");
        fieldCodes.add("agHead");
        fieldNames.add("负责人联系电话");
        fieldCodes.add("agHeadMobile");
        fieldNames.add("注册地址");
        fieldCodes.add("agRegAdd");

        fieldNames.add("业务平台");
        fieldCodes.add("platformName");
        fieldNames.add("业务平台编号");
        fieldCodes.add("busNum");
        fieldNames.add("类型");
        fieldCodes.add("busType");
        fieldNames.add("平台登录账号");
        fieldCodes.add("busLoginNum");
        fieldNames.add("上级代理");
        fieldCodes.add("busParent");
        fieldNames.add("风险承担所属代理商");
        fieldCodes.add("busRiskParent");
        fieldNames.add("激活及返现所属代理商");
        fieldCodes.add("busActivationParent");
        fieldNames.add("是否独立考核");
        fieldCodes.add("busIndeAss");
        fieldNames.add("业务范围");
        fieldCodes.add("busScope");
        fieldNames.add("业务区域");
        fieldCodes.add("busRegion");
        fieldNames.add("投诉及风险风控邮箱");
        fieldCodes.add("busRiskEmail");

        fieldNames.add("业务对接省区");
        fieldCodes.add("agDocPro");
        fieldNames.add("业务对接人");
        fieldCodes.add("busContactPerson");
        fieldNames.add("新签日期");
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

    @RequestMapping("queryAgentName")
    @ResponseBody
    public Object queryAgentName(String id) {
        System.out.println("代理商编码：" + id);
        AgentResult agentResult = businessPlatformService.selectByAgentApproved(id);
        if(agentResult.isOK()){
            return renderSuccess(JsonUtil.objectToJson(agentResult.getData()));
        }else{
            return renderError(agentResult.getMsg());
        }
    }

    @RequestMapping("queryByBusId")
    @ResponseBody
    public Object queryByBusId(String busId){
        AgentBusInfo agentBusInfo = businessPlatformService.findById(busId);
        if(StringUtils.isNotBlank(agentBusInfo.getBusRegion())){
            List<DPosRegion> dPosRegions = ServiceFactory.posRegionService.queryByCodes(agentBusInfo.getBusRegion());
            String rname = "";
            for (int i=0;i<dPosRegions.size();i++) {
                rname = rname + dPosRegions.get(i).getName() + ((i!=dPosRegions.size()-1)?",":"");
            }
            agentBusInfo.setBusRegionName(rname);
            String codeType = "";
            for (int i=0;i<dPosRegions.size();i++) {
                codeType = codeType + dPosRegions.get(i).getCodeType() + ((i!=dPosRegions.size()-1)?",":"");
            }
            agentBusInfo.setCodeTypes(codeType);
        }
        return agentBusInfo;
    }

    @RequestMapping("noticeBusinfo")
    @ResponseBody
    public Object noticeBusinfo(String busId){
        try{
            logger.info("=======================同步业务平台:busId="+busId);
            agentNetInNotityService.asynNotifyPlatform(busId, NotifyType.NetInEdit.getValue());
            logger.info("=======================同步业务平台[同步成功]:busId="+busId);
            return ResultVO.success("同步成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.success("同步失败，请联系系统管理员");
        }
    }

    /**
     * 业务审批退回修改
     */
    @RequestMapping("toEditBusBack")
    public String toEditBusBack(HttpServletRequest request, String id,String proIns) {
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        optionsData(request);
        AgentBusInfo agentBusInfo = businessPlatformService.findById(id);
        List<AgentContract> agentContracts = agentContractService.queryContract(proIns);
        List<Capital> capitals = capitalService.queryCapitalPro(proIns);
        List<AgentBusInfo> agentBusInfos = new ArrayList<>();
        agentBusInfos.add(agentBusInfo);
        request.setAttribute("agentBusInfos", agentBusInfos);
        request.setAttribute("capitals", capitals);
        request.setAttribute("agentContracts", agentContracts);
        request.setAttribute("proIns", proIns);
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        request.setAttribute("ass", ass);
        return "agent/businessApproveEdit";
    }

}
