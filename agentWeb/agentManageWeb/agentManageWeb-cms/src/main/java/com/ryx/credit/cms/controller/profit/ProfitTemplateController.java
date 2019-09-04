package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.pojo.TemplateRecode;
import com.ryx.credit.profit.service.ITemplateRecodeService;
import com.ryx.credit.service.IUserService;
import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分润模板处理
 *@author cqt
 * @create 2019/8/15
 */
@Controller
@RequestMapping("/profit/template")
public class ProfitTemplateController extends BaseController {

    private static  final Logger logger = Logger.getLogger(ProfitTemplateController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private ITemplateRecodeService templateRecordService;

    @RequestMapping("/toProfitTemplatePage")
    public String toProfitTemplatePage(HttpServletRequest request){
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        return "profit/profitTemplate/profitTemplateList";
    }

    @RequestMapping("/getTemplateList")
    @ResponseBody
    public Object getApplyList(HttpServletRequest request, TemplateRecode templateRecode){
        logger.info("获取数据列表。。。");
        Page page = pageProcess(request);
        List<Map<String, Object>> list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        try{
            String str = map.get("ORGANIZATIONCODE").toString();
            if(str.indexOf("city") != -1 || str.indexOf("region") != -1){ // 省区,大区

            }else{
                map = null;
            }
        }catch (Exception e){
            e.printStackTrace();
            map = null;
        }
        templateRecode.setCreateUser(getStringUserId());
        return templateRecordService.getApplyList(page,templateRecode,map);
    }

    @RequestMapping("/toTemplateApplyPage")
    public String toTemplateApplyPage(HttpServletRequest request){
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        return "profit/profitTemplate/profitTemplateApplyPage";
    }

    @RequestMapping("/checkBusNum")
    @ResponseBody
    public ResultVO checkBusNum(HttpServletRequest request, String busNum){
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        if(StringUtils.isBlank(busNum)){
            return ResultVO.fail("业务平台编码不能为空！");
        }
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,String> map1 = templateRecordService.getAgentInfo(busNum);
        if(map1 == null ){
            return ResultVO.fail("未获取到该业务平台编码对应的代理商信息！");
        }else{
            result.put("agentInfo",map1); // 代理商基本信息
        }
        try{
            Map<String,Object> map2 = templateRecordService.getTemplateNow(busNum);
            result.put("templateNow",map2);
            return ResultVO.success(result);
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultVO.fail("获取该业务编码对应模板信息错误！");
    }

    @RequestMapping("saveAndApply")
    @ResponseBody
    public ResultVO saveAndApply(HttpServletRequest request){
        String busPlatform = request.getParameter("busPlatform");
        String startMonth = request.getParameter("startMonth");
        String endMonth = request.getParameter("endMonth");
        String templateName = request.getParameter("templateName");
        Object orgTemplatePriceList = JSONObject.parseArray(request.getParameter("orgTemplatePriceList"));
        Object orgTemplateRateList = JSONObject.parseArray(request.getParameter("orgTemplateRateList"));
        String orgId = request.getParameter("orgId");
        String agentName = request.getParameter("agentName");
        String agentId = request.getParameter("agentId");

        JSONObject map = new JSONObject();   // 接口传递参数
        map.put("orgId",orgId);
        map.put("startMonth",startMonth);
        map.put("templateName",templateName);
        map.put("createUser",getUserId());
        map.put("endMonth",endMonth);
        map.put("applyTemplatePriceList",orgTemplatePriceList);
        map.put("applyTemplateRateList",orgTemplateRateList);
        map.put("applyType","insert");



        Map<String,String> stringMap = new HashMap<String,String>();
        stringMap.put("agentId",agentId);
        stringMap.put("agentName",agentName);
        stringMap.put("busPlatform",busPlatform);
        stringMap.put("orgId",orgId);
        stringMap.put("userId",getUserId().toString());
        stringMap.put("templateName",templateName);

        List<Map<String, Object>> list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return ResultVO.fail("未获取到该账户角色！");
        }
        Map<String, Object> map1 = list.get(0);
        if(map1.get("ORGANIZATIONCODE").toString().indexOf("city") != -1){
            stringMap.put("docDic",list.get(0).get("ORGID").toString());
        }

        try{
            templateRecordService.saveAndApply(stringMap,map);
            return ResultVO.success("提交审批成功！");
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (RuntimeException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultVO.fail("提交审批失败！");
    }

    /**
     * 任务处理页面
     */
    @RequestMapping("/toThawAgentTaskApproval")
    public String toThawAgentTaskApproval(HttpServletRequest request, Model model){
        optionsData(request);
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busId = request.getParameter("busId");
        String busType = request.getParameter("busType");
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        TemplateRecode recode = templateRecordService.getTemplateRecodeById(busId);
        if(recode == null){
            request.setAttribute("resuuult","未查找到该申请记录对应数据");
            return "profit/profitTemplate/templateApplyTaskApproval";
        }
        request.setAttribute("templateRecode",recode);
        Map<String,Object> map = templateRecordService.getTemplateApplyDetail(recode.getTemplateId());
        if(map == null){
            logger.info("根据id获取申请记录信息有误！");
        }
        if(!(boolean)map.get("result")){
            request.setAttribute("applyDetail",null);
            request.setAttribute("applyTemplatePriceList",null); //新结算价
            request.setAttribute("applyTemplateRateList",null);// 新分润比例
            request.setAttribute("oldMap",null);
            request.setAttribute("orgTemplatePriceList",null); //老结算价
            request.setAttribute("orgTemplateRateList",null);// 老分润比例
        }else{
            Map<String,Object> objectMap = (Map<String,Object>)map.get("data");
            request.setAttribute("applyDetail",objectMap);
            List<Map<String,Object>> applyTemplatePriceList = (List<Map<String,Object>>)objectMap.get("applyTemplatePriceList");
            request.setAttribute("applyTemplatePriceList",applyTemplatePriceList); //新结算价
            List<Map<String,Object>> applyTemplateRateList = (List<Map<String,Object>>)objectMap.get("applyTemplateRateList"); // 新分润比例
            request.setAttribute("applyTemplateRateList",applyTemplateRateList);
            Map<String,Object> oldMap = (Map<String,Object>)objectMap.get("oldOrgTemplate");
            request.setAttribute("oldMap",oldMap);
            request.setAttribute("oldStartMon",oldMap.get("startMonth"));
            request.setAttribute("oldEndMon",oldMap.get("endMonth"));
            List<Map<String,Object>> orgTemplatePriceList = (List<Map<String,Object>>)oldMap.get("orgTemplatePriceList");
            request.setAttribute("orgTemplatePriceList",orgTemplatePriceList); //老结算价
            List<Map<String,Object>> orgTemplateRateList = (List<Map<String,Object>>)oldMap.get("orgTemplateRateList"); // 老分润比例
            request.setAttribute("orgTemplateRateList",orgTemplateRateList);
        }
        //审批记录
        List<Map<String, Object>> actRecordList = queryApprovalRecord(busId, BusActRelBusType.profitTempalteApply.name());
        request.setAttribute("actRecordList", actRecordList);
        //审批结果
        List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
        request.setAttribute("approvalType", approvalType);
        request.setAttribute("taskId", taskId);
        request.setAttribute("proIns", proIns);
        request.setAttribute("id", busId);
        return "profit/profitTemplate/templateApplyTaskApproval";
    }

    /**
     * 处理任务
     */

    @ResponseBody
    @RequestMapping("/doTaskApproval")
    public Object doTaskApproval(@RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            List<Map<String, Object>> list = userService.orgCode(getUserId());
            if(list == null || list.isEmpty()){
                return ResultVO.fail("未获取到该账户角色！");
            }
            Map<String, Object> map1 = list.get(0);
            if(map1.get("ORGANIZATIONCODE").toString().equals("business") && "pass".equals(agentVo.getApprovalResult()) && StringUtils.isBlank(agentVo.getOrderAprDept())){
                templateRecordService.checkAngAsign(agentVo.getPretest());
            }else if(map1.get("ORGANIZATIONCODE").toString().equals("manage") && "pass".equals(agentVo.getApprovalResult())){
                templateRecordService.checkAngAsign(agentVo.getPretest());
            }

            result = templateRecordService.approvalTask(agentVo, String.valueOf(getUserId()));
            if (result == null) {
                return ResultVO.fail("处理失败");
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功");
            } else {
                return ResultVO.fail("处理失败");
            }
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVO.success("处理成功");

    }

    /**
     * 修改信息
     */
    @RequestMapping("editInfo")
    @ResponseBody
    public ResultVO editInfo(HttpServletRequest request){
        String busPlatform = request.getParameter("busPlatform");
        String startMonth = request.getParameter("startMonth");
        String endMonth = request.getParameter("endMonth");
        String templateName = request.getParameter("templateName");
        Object orgTemplatePriceList = JSONObject.parseArray(request.getParameter("orgTemplatePriceList"));
        Object orgTemplateRateList = JSONObject.parseArray(request.getParameter("orgTemplateRateList"));
        String orgId = request.getParameter("orgId");
        String agentName = request.getParameter("agentName");
        String agentId = request.getParameter("agentId");

        JSONObject map = new JSONObject();   // 接口传递参数
        map.put("orgId",orgId);
        map.put("startMonth",startMonth);
        map.put("templateName",templateName);
        map.put("createUser",getUserId());
        map.put("endMonth",endMonth);
        map.put("applyTemplatePriceList",orgTemplatePriceList);
        map.put("applyTemplateRateList",orgTemplateRateList);
        map.put("applyType","insert");

        Map<String,String> stringMap = new HashMap<String,String>();
        stringMap.put("agentId",agentId);
        stringMap.put("agentName",agentName);
        stringMap.put("busPlatform",busPlatform);
        stringMap.put("orgId",orgId);
        stringMap.put("userId",getUserId().toString());
        stringMap.put("templateName",templateName);

        try{
            templateRecordService.editInfo(stringMap,map);
            return ResultVO.success("修改数据成功！");
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (RuntimeException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultVO.fail("修改数据失败！");
    }

    /**
     * 查看详情
     */
    @RequestMapping("/showDetail")
    public String showDetail(HttpServletRequest request,String id){
        optionsData(request);
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        TemplateRecode recode = templateRecordService.getTemplateRecodeById(id);
        request.setAttribute("templateRecode",recode);
        Map<String,Object> map = templateRecordService.getTemplateApplyDetail(recode.getTemplateId());
        if(map == null){
            logger.info("根据id获取申请记录信息有误！");
        }
        if(!(boolean)map.get("result")){
            logger.info("返回的result为null！");
            request.setAttribute("applyDetail",null);
            request.setAttribute("applyTemplatePriceList",null); //新结算价
            request.setAttribute("applyTemplateRateList",null);// 新分润比例
            request.setAttribute("oldMap",null);
            request.setAttribute("orgTemplatePriceList",null); //老结算价
            request.setAttribute("orgTemplateRateList",null);// 老分润比例
        }else{
            logger.info("查找到模板数据"+map.get("data"));
            Map<String,Object> objectMap = (Map<String,Object>)map.get("data");
            request.setAttribute("applyDetail",objectMap);
            List<Map<String,Object>> applyTemplatePriceList = (List<Map<String,Object>>)objectMap.get("applyTemplatePriceList");
            request.setAttribute("applyTemplatePriceList",applyTemplatePriceList); //新结算价
            List<Map<String,Object>> applyTemplateRateList = (List<Map<String,Object>>)objectMap.get("applyTemplateRateList"); // 新分润比例
            request.setAttribute("applyTemplateRateList",applyTemplateRateList);
            Map<String,Object> oldMap = (Map<String,Object>)objectMap.get("oldOrgTemplate");
            request.setAttribute("oldMap",oldMap);
            request.setAttribute("oldStartMon",oldMap.get("startMonth"));
            request.setAttribute("oldEndMon",oldMap.get("endMonth"));
            List<Map<String,Object>> orgTemplatePriceList = (List<Map<String,Object>>)oldMap.get("orgTemplatePriceList");
            request.setAttribute("orgTemplatePriceList",orgTemplatePriceList); //老结算价
            List<Map<String,Object>> orgTemplateRateList = (List<Map<String,Object>>)oldMap.get("orgTemplateRateList"); // 老分润比例
            request.setAttribute("orgTemplateRateList",orgTemplateRateList);
        }
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.profitTempalteApply.name());
        request.setAttribute("actRecordList", actRecordList);
        //审批结果
        List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
        request.setAttribute("approvalType", approvalType);
        request.setAttribute("busId", id);
        return "profit/profitTemplate/showDetailInfo";
    }

    @RequestMapping("/updateTemplateName")
    @ResponseBody
    public  ResultVO updateTemplateName(HttpServletRequest request){
        Object orgTemplatePriceList = JSONObject.parseArray(request.getParameter("orgTemplatePriceList"));
        Object orgTemplateRateList = JSONObject.parseArray(request.getParameter("orgTemplateRateList"));
        String templateName = request.getParameter("newName");
        Map<String,Object> applyDetail = JSONObject.parseObject(request.getParameter("applyDetail"));
        String orgId = request.getParameter("busNum");
        String id = request.getParameter("id");
        JSONObject map = new JSONObject();
        map.put("orgId",orgId);
        map.put("startMonth",request.getParameter("startMonth"));
        map.put("templateName",templateName);
        map.put("createUser",getUserId());
        map.put("endMonth",request.getParameter("endMonth"));
        map.put("applyTemplatePriceList",orgTemplatePriceList);
        map.put("applyTemplateRateList",orgTemplateRateList);
        map.put("applyType","update");
        map.put("applyId",request.getParameter("applyId"));

        Map<String,String> map1 = new HashMap<String, String>();
        map1.put("recordId",id);
        try {
            templateRecordService.editInfo(map1,map);
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.fail("保存失败");
        }
        return ResultVO.success("保存成功");
    }

    /**
     * 查看已审批任务
     */
    @RequestMapping("examineDetail")
    public String toAgentMergeQuery(HttpServletRequest request) {
        optionsData(request);
        String id = request.getParameter("id");
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        TemplateRecode recode = templateRecordService.getTemplateRecodeById(id);
        request.setAttribute("templateRecode",recode);
        Map<String,Object> map = templateRecordService.getTemplateApplyDetail(recode.getTemplateId());
        if(map == null){
            logger.info("根据id获取申请记录信息有误！");
        }
        if(!(boolean)map.get("result")){
            request.setAttribute("applyDetail",null);
            request.setAttribute("applyTemplatePriceList",null); //新结算价
            request.setAttribute("applyTemplateRateList",null);// 新分润比例
            request.setAttribute("oldMap",null);
            request.setAttribute("orgTemplatePriceList",null); //老结算价
            request.setAttribute("orgTemplateRateList",null);// 老分润比例
        }else{
            Map<String,Object> objectMap = (Map<String,Object>)map.get("data");
            request.setAttribute("applyDetail",objectMap);
            List<Map<String,Object>> applyTemplatePriceList = (List<Map<String,Object>>)objectMap.get("applyTemplatePriceList");
            request.setAttribute("applyTemplatePriceList",applyTemplatePriceList); //新结算价
            List<Map<String,Object>> applyTemplateRateList = (List<Map<String,Object>>)objectMap.get("applyTemplateRateList"); // 新分润比例
            request.setAttribute("applyTemplateRateList",applyTemplateRateList);
            Map<String,Object> oldMap = (Map<String,Object>)objectMap.get("oldOrgTemplate");
            request.setAttribute("oldMap",oldMap);
            List<Map<String,Object>> orgTemplatePriceList = (List<Map<String,Object>>)oldMap.get("orgTemplatePriceList");
            request.setAttribute("orgTemplatePriceList",orgTemplatePriceList); //老结算价
            List<Map<String,Object>> orgTemplateRateList = (List<Map<String,Object>>)oldMap.get("orgTemplateRateList"); // 老分润比例
            request.setAttribute("orgTemplateRateList",orgTemplateRateList);
        }
        //审批记录
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.profitTempalteApply.name());
        request.setAttribute("actRecordList", actRecordList);
        //审批结果
        List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
        request.setAttribute("approvalType", approvalType);
        return "profit/profitTemplate/alreadyCheckPage";
    }


}
