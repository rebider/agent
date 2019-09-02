package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import com.ryx.credit.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author renshenghao
 * @Date 2019/04/22
 * @desc 代理商分润冻结解冻
 */
@Controller
@RequestMapping("/agentFreeze")
public class FreezeController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(FreezeController.class);
    @Autowired
    private IFreezeAgentSercice freezeAgentSercice;
    @Autowired
    private IUserService userService;

    @RequestMapping("/toFreezeAndThaw")
    public String toFreezeAndThaw(){
        return "profit/freeze/freezeAndThaw";
    }

    @RequestMapping("/getFreezeList")
    @ResponseBody
    public PageInfo getFreezeList(HttpServletRequest request){
        Page page=pageProcess(request);
        Map<String,Object> map=getRequestParameter(request);
        map.put("page",page);
        PageInfo pageInfo=new PageInfo();
        PageInfo info;
        if ("0".equals(request.getParameter("statusTemp"))){
            String cityID=null;
            List<Map<String, Object>> list = userService.orgCode(getUserId());
            if(list == null || list.isEmpty()){
                return new PageInfo();
            }
            Map<String, Object> mapList = list.get(0);
            if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")){//如果时省区，就根据省区查询，其他角色不设限制
                cityID = mapList.get("ORGID").toString();
                map.put("cityID",cityID);
            }
            info = freezeAgentSercice.getselectFreezeDate(map, pageInfo);
        }else if ("1".equals(request.getParameter("statusTemp"))){//查询冻结
            String orgId=null;
            List<Map<String, Object>> list = userService.orgCode(getUserId());
            if(list == null || list.isEmpty()){
                return new PageInfo();
            }
            Map<String, Object> mapList = list.get(0);
            if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")){//如果时省区，就根据省区查询，其他角色不设限制
                orgId = mapList.get("ORGID").toString();
            }

            FreezeAgent freezeAgent=new FreezeAgent();
            if (StringUtils.isNotBlank(request.getParameter("agentId"))){
                freezeAgent.setAgentId(request.getParameter("agentId"));
            }
            if (StringUtils.isNotBlank(request.getParameter("agentName"))){
                freezeAgent.setAgentName(request.getParameter("agentName"));
            }
            if (StringUtils.isNotBlank(request.getParameter("parentAgentId"))){
                freezeAgent.setParentAgentId(request.getParameter("parentAgentId"));
            }
            if (StringUtils.isNotBlank(request.getParameter("parentAgentName"))){
                freezeAgent.setParentAgentName(request.getParameter("parentAgentName"));
            }
            info = freezeAgentSercice.getFreezeData(freezeAgent,request.getParameter("isQuerySubordinate"),page,orgId);
        }else{
            info=null;
        }
        return info;
    }
    @RequestMapping("/toGetFreezeDetail")
    public String toGetFreezeDetailList(){

        return "profit/freeze/freezeAndThawDetail";
    }
    @RequestMapping("/getFreezeDetailList")
    @ResponseBody
    public PageInfo getFreezeDetailList(HttpServletRequest request){
        Page page=pageProcess(request);
        Map<String,Object> map=getRequestParameter(request);
        map.put("page",page);
        PageInfo pageInfo=new PageInfo();

        List<Map<String, Object>> list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> mapList = list.get(0);
        if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")){//如果时省区，就根据省区查询，其他角色不设限制
            String orgId  = mapList.get("ORGID").toString();
            map.put("orgId",orgId);
        }

       return freezeAgentSercice.getselectDetailFreezeDate(map, pageInfo);

    }

    @RequestMapping("/getFreezeApply")
    @ResponseBody
    public PageInfo getFreezeApply(HttpServletRequest request){
        Page page=pageProcess(request);
        Map<String,Object> map=getRequestParameter(request);
        map.put("page",page);
        PageInfo pageInfo=new PageInfo();
        PageInfo info = freezeAgentSercice.getselectFreezeDate(map, pageInfo);
        return info;
    }

    @RequestMapping("/getCheckHistory")
    public ModelAndView getCheckHistory(HttpServletRequest request){
        FreezeOperationRecord freezeOperationRecord = new FreezeOperationRecord();
        freezeOperationRecord.setAgentId(request.getParameter("AGENT_ID"));
        freezeOperationRecord.setFreezeBatch(request.getParameter("FREEZE_OPERATION_BATCH"));
        freezeOperationRecord.setParentAgentId(request.getParameter("PARENT_AGENT_ID"));
        freezeOperationRecord.setThawBatch(request.getParameter("RELIEVE_OPERATION_BATCH"));
        freezeOperationRecord.setFreezeType( request.getParameter("FREEZE_TYPE"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profit/freeze/freezeAndThawDetailImprotent");
        modelAndView.addObject("freezeOperationRecord",freezeOperationRecord);
        return modelAndView;
    }

    @RequestMapping("/getCheckHistoryDate")
    @ResponseBody
    public List<FreezeOperationRecord> getCheckHistoryDate(FreezeOperationRecord freezeOperationRecord){
        return   freezeAgentSercice.getCheckHistoryDate(freezeOperationRecord);

    }
    @RequestMapping("/getFreezeDate")
    @ResponseBody
    public Object getFreezeDate(@RequestBody List<FreezeOperationRecord> agentMap){
        try {
            if(agentMap.size()==0||agentMap==null){
                return renderError("请选择代理商");
            }
            String user = getStringUserId();
            freezeAgentSercice.operationFreezeDate(agentMap,user);
        }catch (Exception e){
            return renderError(e.getMessage());
        }
        return renderSuccess("冻结成功");

    }

    /**
     * 解冻申请
     * @param request
     * @param agentMap
     * @return
     */
    @RequestMapping("thawAgent")
    @ResponseBody
    public Object thawAgent(HttpServletRequest request,@RequestBody List<FreezeOperationRecord> agentMap){
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String batch= UUID.randomUUID().toString().replaceAll("-","");
        String operator=getStringUserId();
        for (FreezeOperationRecord record:agentMap){
            record.setThawTime(time);//设置解冻发起时间
            record.setThawOperator(operator);
            record.setStatus("2");//将状态设置为审批申请中
            record.setThawBatch(batch);
        }
        List<Map<String, Object>> list = userService.orgCode(getUserId());
        Map<String, Object> mapList = list.get(0);
        boolean result=false;
        if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")) {//如果时省区，就根据省区查询，其他角色不设限制
            result = freezeAgentSercice.applyThawAgent(agentMap, operator, batch, "thawAgentByCity");//省区发起
        }else{
            result = freezeAgentSercice.applyThawAgent(agentMap, operator, batch, "thawAgentByBusiness");//业务发起
        }
        if (result){
            LOG.info("审批流启动成功！");
            return ResultVO.success("解冻申请成功！");
        }
        return ResultVO.fail("解冻申请失败");
    }
    /**
     * 解冻审批流审批页
     * */
    @RequestMapping("toThawAgentTaskApproval")
    public String toThawAgentTaskApproval(HttpServletRequest request){
        String taskId = request.getParameter("taskId");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        request.setAttribute("taskId",taskId);
        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList",maps);
        request.setAttribute("busId", busId);
        request.setAttribute("dept", getStringUserId());/*传用户id到前端*/
        optionsData(request);

        List<FreezeAgent> freezeAgents=freezeAgentSercice.getThawDataByBatch(busId);
        request.setAttribute("freezeAgents", freezeAgents);
        Map<String,Object> temp=freezeAgentSercice.getThawOperator(busId);

        long user=Long.valueOf(temp.get("THAW_OPERATOR").toString());

        UserVo userVo = userService.selectVoById(user);
        String name = userVo.getName();
        List<Map<String, Object>>  list = userService.orgCode(userVo.getId());
        String city="";
        if(list!=null && list.size()>0){
            Map<String, Object> map = list.get(0);
            if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ) {
                city=userVo.getOrganizationName().replace("分公司","");
            }
        }


        Map<String,String> thawOperator=new HashMap<>();
        thawOperator.put("user",name);
        thawOperator.put("city",city);
        thawOperator.put("time",temp.get("THAW_TIME").toString());//解冻申请时间
        thawOperator.put("reason",temp.get("THAW_REASON").toString());

        request.setAttribute("thawOperator", thawOperator);

        return "activity/thawAgentTaskApproval";
    }

    /**
     * 处理任务
     * @return
     */
    @ResponseBody
    @RequestMapping("thawTaskApproval")
    public ResultVO thawTaskApproval(HttpServletRequest request,@RequestBody AgentVo agentVo){

        AgentResult result = null;
        try {
            List<String> list = agentVo.getAgentTableFile();
            if(list.size()>0){
                /*
                * 将此FreezeAgent状态改为冻结、删除REV1属性、
                */
                freezeAgentSercice.delFreezeAgentById(list);
                LOG.info("解冻发起人修改解冻审批流信息FreezeAgentIds:"+list);
            }


            //处理任务
            result = freezeAgentSercice.approvalThawTask(agentVo, String.valueOf(getUserId()));

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

    /**
     * 查看审批流
     * @param request
     * @return
     */
    @RequestMapping("examineDetail")
    public String examineDetail(HttpServletRequest request){
        String busId = request.getParameter("busId");
        String busType=request.getParameter("busType");


        List<Map<String, Object>> maps = queryApprovalRecord(busId, busType);
        request.setAttribute("actRecordList",maps);
        request.setAttribute("busId", busId);
        List<FreezeAgent> freezeAgents=freezeAgentSercice.getThawDataByBatch(busId);
        request.setAttribute("freezeAgents", freezeAgents);
        //申请人信息
        Map<String,Object> temp=freezeAgentSercice.getThawOperator(busId);
        long user=Long.valueOf(temp.get("THAW_OPERATOR").toString());
        UserVo userVo = userService.selectVoById(user);
        String name = userVo.getName();
        String city="";
        Map<String,String> thawOperator=new HashMap<>();
        thawOperator.put("user",name);
        thawOperator.put("city",city);
        thawOperator.put("time",temp.get("THAW_TIME").toString());
        thawOperator.put("reason",temp.get("THAW_REASON").toString());
        request.setAttribute("thawOperator", thawOperator);


        optionsData(request);
        return "profit/freeze/freezePlan";
    }
}
