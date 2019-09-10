package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSON;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateDetail;
import com.ryx.credit.profit.service.IAgentRelateService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
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
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author renshenghao
 * @Date 2019/01/24
 * @desc 代理商机具扣款关联关系
 */
@Controller
@RequestMapping("/agentRelate")
public class AgentRelateController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(AgentRelateController.class);

    @Autowired
    private IAgentRelateService agentRelateService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentBusinfoService agentBusinfoService;

    @RequestMapping("toRelateList")
    public String toRelateList(){
        return "profit/agentRelate/agentRelateList";
    }


    @ResponseBody
    @RequestMapping("query")
    public Object getList(HttpServletRequest request){
        Page page=pageProcess(request);
        Map<String,Object> map=getRequestParameter(request);
        map.put("page",page);
        PageInfo pageInfo=new PageInfo();
        return agentRelateService.getList(map,pageInfo);
    }
    @RequestMapping("toAddPage")
    public String toAddPage(){
        LOG.info("加载税点调整页面...");
        return "profit/agentRelate/addAgentRelate";
    }
    @RequestMapping("exportList")
    public void exportAgentRelateList(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String,Object> map=getRequestParameter(request);
        PageInfo pageInfo=new PageInfo();
        pageInfo=agentRelateService.getList(map,pageInfo);
        LOG.info("\n\n\n"+pageInfo.getTotal()+"\n\n\n");

        List<Map<String,Object>> list=pageInfo.getRows();

        String filePath = "D:/upload/";
        String filePrefix = "IN";
        List<String> fieldNames = new ArrayList<String>();
        List<String> fieldCodes = new ArrayList<String>();
        fieldCodes.add("ID");
        fieldNames.add("申请编号");
        fieldCodes.add("AGENT_NAME");
        fieldNames.add("代理商");
        fieldCodes.add("AGENT_ID");
        fieldNames.add("AG码");
        fieldCodes.add("RELATE_COMPANY1");
        fieldNames.add("关联公司");
        fieldCodes.add("RELATE_COMPANY2");
        fieldNames.add("关联公司");
        fieldCodes.add("RELATE_COMPANY3");
        fieldNames.add("关联公司");
        fieldCodes.add("RELATE_COMPANY4");
        fieldNames.add("关联公司");
        fieldCodes.add("BUS_PLATFORM");
        fieldNames.add("业务类型");
        fieldCodes.add("STATUS");
        fieldNames.add("状态");
        fieldCodes.add("CREATE_TIME");
        fieldNames.add("状态建立时间");
        fieldCodes.add("START_MONTH");
        fieldNames.add("开始执行月份");
        fieldCodes.add("REMARK");
        fieldNames.add("备注");

        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, 500);
        excelExportSXXSSF.writeDatasByMap(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ResponseBody
    @RequestMapping("searchPrimaryAgent")
    public Object searchPrimaryAgent(HttpServletRequest request){
        Map<String,Object> param=getRequestParameter(request);
        Map<String,Object> result=new HashMap<String,Object>();
        List<String> busPlatform=new ArrayList<String>();
        if(param.get("agentId")!=null){
            LOG.info("AgentId:"+param.get("agentId").toString());
            Agent agent=agentService.getAgentById(param.get("agentId").toString());
            if (agent!=null){
                result.put("agentId",agent.getId());
                result.put("agentName",agent.getAgName());
                result.put("isSuccess",true);
            }else{
                result.put("isSuccess",false);
                return result;
            }
            List<AgentBusInfo> agentBusInfos=agentBusinfoService.agentBusInfoList(param.get("agentId").toString());
            if(agentBusInfos.size()<1){
                result.put("msg","此代理商暂未开通业务！");
                return result;
            }
            for (AgentBusInfo agentBusInfo:agentBusInfos) {
                if(agentBusInfo.getBusPlatform()!=null)busPlatform.add(agentBusInfo.getBusPlatform());
            }
            result.put("busPlatform",busPlatform);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("addAgentRalate")
    public Object addAgentRalate(HttpServletRequest request){
        String dataString=request.getParameter("data");
        Map<String,Object> result=new HashMap<String,Object>();
        if(StringUtils.isBlank(dataString)){
            result.put("isSuccess",false);
            result.put("msg","数据异常！");
            return result;
        }
        try {
            Map<String,String> data=(Map<String,String>)JSON.parse(dataString);
            String executeMonth=data.get("executeMonth");
           /*
            查询此代理商正在审批的所有关联记录
            */
            Map<String,Object> temp=new HashMap<>();
            temp.put("agentId",data.get("agentId"));
            temp.put("agentName",data.get("agentName"));
            temp.put("status",Status.STATUS_0.status);
            PageInfo pageInfo=new PageInfo();
            pageInfo=agentRelateService.getList(temp,pageInfo);
            List<Map<String,Object>> records=pageInfo.getRows();
            for (Map<String,Object> record:records){
                String record_month=record.get("START_MONTH").toString();
                if(record_month.equals(executeMonth)){
                    /*
                    1.相同开始执行月并且有相同关联代理商的，禁止重复申请
                     */
                    for (int i=1;i<=4;i++){
                        String relateAgentName=data.get("agentName"+i);
                        if(record.get("RELATE_COMPANY1").toString().equals(relateAgentName)){
                            result.put("isSuccess",false);
                            result.put("msg","此月份已申请关联代理商");
                            return result;
                        }
                        if(record.get("RELATE_COMPANY2")!=null){
                            if(record.get("RELATE_COMPANY2").toString().equals(relateAgentName)){
                                result.put("isSuccess",false);
                                result.put("msg","此月份已申请关联代理商");
                                return result;
                            }
                        }
                        if(record.get("RELATE_COMPANY3")!=null){
                            if(record.get("RELATE_COMPANY3").toString().equals(relateAgentName)){
                                result.put("isSuccess",false);
                                result.put("msg","此月份已申请关联代理商");
                                return result;
                            }
                        }
                        if(record.get("RELATE_COMPANY4")!=null){
                            if(record.get("RELATE_COMPANY4").toString().equals(relateAgentName)){
                                result.put("isSuccess",false);
                                result.put("msg","此月份已申请关联代理商");
                                return result;
                            }
                        }

                    }
                    /*
                    2.相同开始执行月，无相同关联代理商的，原申请状态变更为无效
                     */
                    AgentRelate agentRelate=agentRelateService.selectById(record.get("ID").toString());
                    agentRelate.setStatus(Status.STATUS_3.status);
                    agentRelateService.updateAgentRelate(agentRelate);
                }
            }
            /*
            查询此代理商已审批的所有关联记录
             */
            temp.put("status",Status.STATUS_1.status);
            pageInfo=agentRelateService.getList(temp,pageInfo);
            records=pageInfo.getRows();
            for (Map<String,Object> record:records){
                String record_month=record.get("START_MONTH").toString();
                if(record_month.equals(executeMonth)){
                    /*
                    开始执行月份相同则将就的关联关系设为失效
                     */
                    AgentRelate agentRelate=agentRelateService.selectById(record.get("ID").toString());
                    agentRelate.setStatus(Status.STATUS_3.status);
                    agentRelateService.updateAgentRelate(agentRelate);
                }
            }



            AgentRelate agentRelate=new AgentRelate();
            agentRelate.setAgentId(data.get("agentId"));
            agentRelate.setAgentName(data.get("agentName"));
            agentRelate.setBusType(data.get("busType"));
            agentRelate.setBusPlatform(data.get("agentBusPlatform"));
            agentRelate.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            agentRelate.setStatus(BigDecimal.ZERO);
            agentRelate.setStartMonth(executeMonth);
            agentRelate.setRemark(data.get("remark"));
            Map<String,String> param=new HashMap<String,String>();
            param.put("agentId",data.get("agentId"));
            param.put("busPlatform",data.get("agentBusPlatform"));
            Map<String,String> parentAgent=agentRelateService.queryParentAgentByAgentId(param);
            if(parentAgent!=null){
                agentRelate.setParentAgentId(parentAgent.get("agentId"));
                agentRelate.setParentAgentName(parentAgent.get("agentName"));
            }
            agentRelate.setCreatePerson(getUserId().toString());
            agentRelate.setCreateName(getUserName(getUserId()));

            List<AgentRelateDetail> list=new ArrayList<>();
            for (int i=1;i<=4;i++){
                String agentId=data.get("agentId"+i);
                if(agentId==null||agentId.equals("")){
                    if(i==1){
                        result.put("isSuccess",false);
                        result.put("msg","请填写关联代理商");
                        return result;
                    }
                    break;
                }
                AgentRelateDetail agentRelateDetail=new AgentRelateDetail();
                agentRelateDetail.setAgentId(agentId);
                agentRelateDetail.setAgentName(data.get("agentName"+i));
                agentRelateDetail.setCreateTime(new Date());
                param.put("agentId",agentId);
                parentAgent=agentRelateService.queryParentAgentByAgentId(param);
                if(parentAgent!=null) {
                    agentRelateDetail.setParentAgentId(parentAgent.get("agentId"));
                    agentRelateDetail.setParentAgentName(parentAgent.get("agentName"));
                }
                agentRelateDetail.setOrderNum(new BigDecimal(i));
                list.add(agentRelateDetail);
            }
            String userId=getStringUserId();
            if(agentRelateService.applyAgentRelate(agentRelate,list,userId,"process_agent_relation")){
                result.put("isSuccess",true);
            }else{
                result.put("isSuccess",false);
                result.put("msg","审批流启动失败。");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("isSuccess",false);
            result.put("msg",e.getMessage());
        }finally {
            return result;
        }

    }
    @RequestMapping("toAgentRelateTaskApproval")
    public String toAgentRelateTaskApproval(HttpServletRequest request, HttpServletResponse response, Model model){
        String taskId = request.getParameter("taskId");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        AgentRelate agentRelate=agentRelateService.selectById(busId);
        request.setAttribute("agentRelate",agentRelate);
        request.setAttribute("taskId",taskId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList",maps);
        request.setAttribute("busId", busId);
        List<AgentRelateDetail> agentRelateDetails=agentRelateService.queryDetailByBusId(busId);
        request.setAttribute("detailList",agentRelateDetails);
        optionsData(request);
        return "activity/agentRelateTaskApproval";
    }
    @RequestMapping("examineDetail")
    public String examineDetail(HttpServletRequest request, HttpServletResponse response, Model model){
        String busType = "agentRelate";
        String busId = request.getParameter("busId");
        AgentRelate agentRelate=agentRelateService.selectById(busId);
        request.setAttribute("agentRelate",agentRelate);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList",maps);
        request.setAttribute("busId", busId);
        request.setAttribute("relateStatus", agentRelate.getStatus());
        List<AgentRelateDetail> agentRelateDetails=agentRelateService.queryDetailByBusId(busId);
        request.setAttribute("detailList",agentRelateDetails);
        optionsData(request);
        return "profit/agentRelate/agentRelatePlan";
    }


    /**
     * 处理任务
     * @return
     */
    @ResponseBody
    @RequestMapping("agentRelateTaskApproval")
    public ResultVO agentRelateTaskApproval(@RequestBody AgentVo agentVo){

        AgentResult result = null;
        try {
            result = agentRelateService.approvalAgentRelateTask(agentVo, String.valueOf(getUserId()));
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
}
