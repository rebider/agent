package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.AgentVoTerminalTransferDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.agent.PosOrgStatisticsService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.order.TerminalTransferService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
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
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 终端划拨控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/20 15:30
 */
@RequestMapping("terminal")
@Controller
public class TerminalTransferController extends BaseController{

    private static Logger log  = LoggerFactory.getLogger(TerminalTransferController.class);
    private static String EXPORT_Logistics_PATH = AppConfig.getProperty("export.path");
    @Autowired
    private TerminalTransferService terminalTransferService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private PosOrgStatisticsService posOrgStatisticsService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private RedisService redisService;

  /*  private static List<Map<String, Object>> listExport;*/

    @RequestMapping(value = "toTerminalTransferList")
    public String toTerminalTransferList(HttpServletRequest request,TerminalTransfer terminalTransfer){
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList",agStatusList);
        request.setAttribute("agentId",getAgentId());
        return "order/terminalTransferList";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "terminalTransferList")
    @ResponseBody
    public Object terminalTransferList(HttpServletRequest request,TerminalTransfer terminalTransfer,String agName){
        Page page = pageProcess(request);
        if(StringUtils.isNotBlank(getAgentId())){
            terminalTransfer.setAgentId(getAgentId());
        }
        String dataRole = request.getParameter("dataRole");
        PageInfo pageInfo = terminalTransferService.terminalTransferList(terminalTransfer,page,agName,dataRole,getUserId());
        return pageInfo;
    }


    @RequestMapping(value = "toTerminalTransferSave")
    public String toProductAdd(HttpServletRequest request){

        request.setAttribute("platformTypeList",TerminalPlatformType.getContentMap());
        List<AgentBusInfo> agentBusInfos = businessPlatformService.selectByAgentId(getAgentId());
        request.setAttribute("agentPlatformTypeList",agentBusInfos);
        return "order/terminalTransferSave";
    }

    @RequestMapping(value = "saveTerminalTransfer")
    @ResponseBody
    public Object saveTerminalTransfer(HttpServletRequest request,@RequestBody AgentVo agentVo){
        try {
            AgentResult agentResult = terminalTransferService.saveTerminalTransfer(agentVo.getTerminalTransfer(), agentVo.getTerminalTransferDetailList(), getStringUserId(), getAgentId(), agentVo.getFlag());
            if(agentResult.isOK()){
                return renderSuccess(agentResult.getData().toString());
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("提交失败");
        }
    }




    @RequestMapping(value = "getGoAgentType")
    @ResponseBody
    public String getGoAgentType(HttpServletRequest request){
        try {
        Map<String, Object> map1 =  terminalTransferService.getAgentType(request.getParameter("goalOrgId"));
        Map<String, Object> platFromMap = terminalTransferService.queryPlatFrom(map1.get("BUS_PLATFORM").toString());
        Map<String, Object>  map  = terminalTransferService.getAgentType(request.getParameter("goalOrgId"));
        String agentType = (String) map.get("BUS_TYPE");
        if(platFromMap.get("PLATFORM_TYPE").toString().equals("POS")){
            if("1".equals(agentType)){
                agentType="二代直签直发+30";
            }else  if("2".equals(agentType)){
                agentType="机构+0";
            }else if("3".equals(agentType)){
                agentType="机构一代+100";
            }else if("5".equals(agentType)){
                agentType="一代X+100";
            }else if("6".equals(agentType)){
                agentType="标准一代+0";
            }else if("8".equals(agentType)){
                agentType="直签不直发+30";
            }else if("9".equals(agentType)){
                agentType="直签+30";
            }
        }else{
            if("1".equals(agentType)){
                agentType="二代直签直发+200";
            }else  if("2".equals(agentType)){
                agentType="机构+0";
            }else if("3".equals(agentType)){
                agentType="机构一代+500";
            }else if("5".equals(agentType)){
                agentType="一代X+500";
            }else if("6".equals(agentType)){
                agentType="标准一代+0";
            }else if("8".equals(agentType)){
                agentType="直签不直发+200";
            }else if("9".equals(agentType)){
                agentType="直签+200";
            }

        }
        return agentType;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    @RequestMapping(value = "startActivity")
    @ResponseBody
    public Object startActivity(HttpServletRequest request){
        try {
            String busId = request.getParameter("busId");
            AgentResult agentResult = terminalTransferService.startTerminalTransferActivity(busId,getStringUserId(),getAgentId(),false);
            if(agentResult.isOK()){
                return renderSuccess("提交成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("提交失败");
        }
    }

    @RequestMapping("toTerminalTaskApproval")
    public String toTerminalTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        TerminalTransfer terminalTransfer = terminalTransferService.queryTerminalTransfer(busId);
        request.setAttribute("terminalTransfer",terminalTransfer);
        request.setAttribute("taskId",taskId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList",maps);
        request.setAttribute("busId", busId);
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferService.queryImprotMsgList(busId);
        request.setAttribute("improtMsgList",terminalTransferDetails);
        optionsData(request);
        request.setAttribute("platformTypeList",TerminalPlatformType.getContentMap());
        return "activity/terminalTaskApproval";
    }

    /**
     * 处理任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("terminalTaskApproval")
    public ResultVO terminalTaskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo){

        AgentResult result = null;
        try {
            result = terminalTransferService.approvalTerminalTransferTask(agentVo, String.valueOf(getUserId()),agentVo.getAgentBusId());
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


    @RequestMapping("toTerminalQuery")
    public String toTerminalQuery(HttpServletRequest request, HttpServletResponse response, Model model) {

        String id = request.getParameter("id");
        String reviewStatus = request.getParameter("reviewStatus");
        TerminalTransfer terminalTransfer = terminalTransferService.queryTerminalTransfer(id);
        request.setAttribute("terminalTransfer",terminalTransfer);
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.agentTerminal.name());
        BusActRel busActRel = taskApprovalService.queryBusActRel(id, BusActRelBusType.agentTerminal.name(), AgStatus.getAgStatusString(new BigDecimal(reviewStatus)));
        List<Map<String, Object>> actRecordList = null;
        if (busActRel != null) {
            actRecordList = queryApprovalRecord(busActRel.getActivId());
        }
        request.setAttribute("actRecordList", actRecordList);
        request.setAttribute("reviewStatus", reviewStatus);
        optionsData(request);
        request.setAttribute("platformTypeList",TerminalPlatformType.getContentMap());
        return "order/terminalTransferQuery";
    }

    @RequestMapping(value = "toTerminalTransferDetailsList")
    public String toTerminalTransferDetailsList(HttpServletRequest request,TerminalTransferDetail terminalTransferDetail){
        request.setAttribute("adjustStatusList", AdjustStatus.getValueMap());
        request.setAttribute("agentId",getAgentId());
        return "order/terminalTransferDetailsList";
    }


    @RequestMapping(value = "terminalTransferDetailsList")
    @ResponseBody
    public Object terminalTransferDetailsList(HttpServletRequest request,TerminalTransferDetail terminalTransferDetail,String agName){
        Page page = pageProcess(request);
        String dataRole = request.getParameter("dataRole");
        if(StringUtils.isNotBlank(getAgentId())){
            terminalTransferDetail.setAgentId(getAgentId());
        }
        PageInfo pageInfo = terminalTransferService.terminalTransferDetailList(terminalTransferDetail,page,agName,dataRole,getUserId());

        return pageInfo;
    }

    @RequestMapping(value = "terminalTransferDetailsListExport")
    @ResponseBody
    public Integer terminalTransferDetailsListExport(@RequestBody AgentVoTerminalTransferDetail terminalTransferDetail){

        /*if(StringUtils.isNotBlank(getAgentId())){
            terminalTransferDetail.setAgentId(getAgentId());
        }*/
        PageInfo pageInfo = terminalTransferService.terminalTransferDetailListExport(terminalTransferDetail);
        redisService.setValue(getUserId().toString(), JSONObject.toJSONString(pageInfo.getRows()) ,10L);
     /*   listExport = pageInfo.getRows();*/
        return pageInfo.getRows().size();
    }

    @RequestMapping(value = "exprotTerminalTransferDetails")
    public void exprotTerminalTransferDetails(HttpServletRequest request,HttpServletResponse response,TerminalTransferDetail terminalTransferDetail,String excelType,String agName){
        Map<String, Object> param = new HashMap<>();

        String title = "明细编号,SN开始,SN结束,SN数量,厂商,型号,原业务平台编码,原业务平台名称,目标业务平台编码,目标业务平台名称,代理商ID,代理商名称,对接人,调整结果,备注";

        String column = "ID,SN_BEGIN_NUM,SN_END_NUM,COM_SN_NUM,PRO_COM,PRO_MODEL,ORIGINAL_ORG_ID,ORIGINAL_ORG_NAME,GOAL_ORG_ID,GOAL_ORG_NAME,AGENT_ID,AG_NAME,BUTT_JOINT_PERSON,ADJUST_STATUS_MSG,REMARK";

        List<Map<String, Object>>  listExport = (List<Map<String, Object>>) JSONObject.parse(redisService.getValue(getUserId().toString()));
        param.put("path", EXPORT_Logistics_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", listExport);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);

    }

    @RequestMapping("/importTerminalPage")
    public String importPage(HttpServletRequest request) {
        request.setAttribute("busId",request.getParameter("busId"));
        return "order/terminalTransferImport";
    }

    @RequestMapping(value ="importTerminal")
    @ResponseBody
    public Object importTerminal(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        if(file.getSize()==0){
            return renderError("请上传文件");
        }
    /*    String busId = request.getParameter("busId");
        if(StringUtils.isBlank(busId)){
            return renderError("缺少业务编号");
        }*/
        try {
            InputStream in = file.getInputStream();
            List<List<Object>> excelList = ImportExcelUtil.getListByExcel(in, file.getOriginalFilename());
            if(null==excelList || excelList.size()==0){
                return renderError("文档记录为空");
            }
            AgentResult agentResult = terminalTransferService.importTerminal(excelList, getStringUserId());
            return agentResult.getData();
        } catch (MessageException e) {
            e.getStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.getStackTrace();
            return renderError("excel解析异常");
        }
    }

    @RequestMapping(value = "terminalDel")
    @ResponseBody
    public Object terminalDel(HttpServletRequest request){
        try {
            String busId = request.getParameter("busId");
            AgentResult agentResult = terminalTransferService.delTerminalTransfer(busId,getStringUserId());
            if(agentResult.isOK()){
                return renderSuccess("删除成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("删除失败");
        }
    }

    @RequestMapping("/toTerminalEdit")
    public String toTerminalEdit(HttpServletRequest request) {
        String busId = request.getParameter("busId");
        TerminalTransfer terminalTransfer = terminalTransferService.queryTerminalTransfer(busId);
        request.setAttribute("terminalTransfer",terminalTransfer);
        request.setAttribute("platformTypeList",TerminalPlatformType.getContentMap());
        List<AgentBusInfo> agentBusInfos = businessPlatformService.selectByAgentId(getAgentId());
        request.setAttribute("agentPlatformTypeList",agentBusInfos);
        return "order/terminalTransferEdit";
    }


    @RequestMapping("/disposeSN")
    @ResponseBody
    public Long disposeSN(@RequestParam("StratNum") String snBeginNum, @RequestParam("endNum")String snEndNum) {
        Map<String,Object> map= null;
        try {
            map = terminalTransferService.disposeSN(snBeginNum,snEndNum);
        } catch (MessageException e) {
            e.printStackTrace();
        }
        Long snBeginNum1 = Long.parseLong(map.get("snBeginNum1").toString());
        Long snEndNum1 = Long.parseLong(map.get("snEndNum1").toString()) ;
        return snEndNum1- snBeginNum1+1;

    }



    @RequestMapping(value = "terminalEdit")
    @ResponseBody
    public Object terminalEdit(HttpServletRequest request,@RequestBody AgentVo agentVo){
        try {
            AgentResult agentResult = terminalTransferService.editTerminalTransfer(agentVo.getTerminalTransfer(),agentVo.getTerminalTransferDetailList(),getStringUserId(),getAgentId());
            if(agentResult.isOK()){
                return renderSuccess("修改成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("修改成功");
        }
    }

    @RequestMapping("statistics")
    public String statistics(@RequestParam("originalOrgId")String originalOrgId,@RequestParam("platformType")String platformType, HttpServletRequest request){
        try {
            AgentResult agentResult = posOrgStatisticsService.posOrgStatistics(originalOrgId,platformType);
            if(!agentResult.isOK()){
                return "error/500";
            }
            request.setAttribute("platformType",agentResult.getMsg());
            if(agentResult.getMsg().equals(PlatformType.MPOS.getValue())){
                request.setAttribute("statisticsList",agentResult.getData());
            }else if(agentResult.getMsg().equals(PlatformType.RDBPOS.getValue())){
                request.setAttribute("rdbStatisticsList",agentResult.getData());
            }else{
                Map<String,List<Map<String,Object>>> map = (Map<String,List<Map<String,Object>>>)agentResult.getData();
                List<Map<String, Object>> statList = map.get("organSnList");
                for (Map<String, Object> stringObjectMap : statList) {
                    Object supDorgIdObj = stringObjectMap.get("supDorgId");
                    if(null!=supDorgIdObj){
                        String supDorgId = String.valueOf(supDorgIdObj);
                        AgentBusInfo agentBusInfo = agentBusinfoService.selectBusInfo(supDorgId);
                        if(null!=agentBusInfo){
                            stringObjectMap.put("busType",BusType.getContentByValue(agentBusInfo.getBusType()));
                        }
                    }
                }
                request.setAttribute("statisticsMapList",map);
            }
            return "agent/posOrgStatistics";
        }catch (MessageException e) {
            e.getStackTrace();
            request.setAttribute("error",e.getMsg());
            return "error/500";
        } catch (Exception e) {
            e.getStackTrace();
            request.setAttribute("error",e.getMessage());
            return "error/500";
        }
    }

}
