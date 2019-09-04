package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import org.springframework.ui.Model;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.common.enumc.TransFlag;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentDebitCardService;
import com.ryx.credit.service.agent.AgentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author Chen Qiutian
 * @date 2019/7/30 17：15
 *  结算卡管理
 */
@RequestMapping("/agentDebitCard")
@Controller
public class AgentDebitCardController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AgentDebitCardController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private AgentDebitCardService agentDebitCardService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentService agentService;

    /**
     * 结算卡管理页面
     * @param request
     * @return
     */
    @RequestMapping(value = {"/toAgentCardPage","/toCardPage"})
    public String toInvoiceApplyList(HttpServletRequest request){
        logger.info("=======加载结算卡管理页面=======");
        optionsData(request);
        request.setAttribute("flagList", TransFlag.getItemMap());
        return "agent/debitCard/agentDebitcardList";
    }

    /**
     * 获取数据列表
     * @param request
     * @return
     */
    @RequestMapping("getAgentCardList")
    @ResponseBody
    public Object getAgentList(HttpServletRequest request){
        logger.info("=======加载结算卡管理数据列表=======");
        Page page = pageProcess(request);
        Map<String,String> map = getRequestParameter(request);

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String,Object> objectMap = list.get(0);
        try{
            String string = list.get(0).get("ORGANIZATIONCODE").toString();
            if(string.indexOf("city") != -1 && null != string){  // 分为省区 大区和其他
                map.put("docPro",objectMap.get("ORGID").toString());
            }else if(null != string && ("north".equals(string) || "south".equals(string))){
                map.put("docDis",objectMap.get("ORGID").toString());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return agentDebitCardService.getDebitCardList(map,page);
    }

    /**
     * 查看详情
     * @param request
     * @return
     */
    @RequestMapping("toAgentDetailPage")
    public String toAgentDetailPage(HttpServletRequest request, Model model,String id){
        logger.info("=======查看页面详情=======");
        optionsData(request);
        request.setAttribute("flagList", TransFlag.getItemMap());
        selectAll(request,model,id);
        return "agent/debitCard/debitDetailPage";
    }

    private void selectAll(HttpServletRequest request, Model model,String id){
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id); //代理商收款信息
        List<Map<String,String>> agentBusInfos = agentDebitCardService.getBusInfoById(id); //机构
        Map<String,String> stringMap = agentDebitCardService.getColAndAgentById(id); //基础
        model.addAttribute("agent",agent);
        model.addAttribute("agentColinfos", agentColinfos);
        model.addAttribute("agentBusInfos", agentBusInfos);
        model.addAttribute("agentInfo", stringMap);

    }

    /**
     * 通知修改状态更改
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = {"doUpdateAdvice","doUpdateAdviceNow"})
    @ResponseBody
    public ResultVO doUpdateAdvice(HttpServletRequest request, @RequestParam("id") String id,@RequestParam("status")String status){
        try {
            agentDebitCardService.updateSuggestStatusById(id,status);
        }catch (Exception e){
            return ResultVO.fail("提示失败！");
        }
        return ResultVO.success("提示成功！");
    }

    /**
     * 跳转至编辑页面
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/updateNow")
    public Object toAgentUpdatePage(HttpServletRequest request, @RequestParam("id") String id,@RequestParam("acId")String acId, Model model){
        logger.info("=======跳转修改页面=======");
        optionsData(request);
        request.setAttribute("flagList", TransFlag.getItemMap());
        selectAll(request,model,id);
        request.setAttribute("id",id);
        return "agent/debitCard/editDetailPage";
    }


    /**
     * 修改提交
     */
    @RequestMapping(value = {"toSubmit"},method =  RequestMethod.POST)
    @ResponseBody
    public ResultVO toSubmit(HttpServletRequest request,@RequestBody AgentVo agentVo){
        Agent agent = agentVo.getAgent();
        List<AgentColinfoVo> list = agentVo.getColinfoVoList();
        try {
            Agent agent1 = agentService.updateAgentInfo(agent,getUserId().toString());
            agentColinfoService.updateAgentColinfoVoNow(list,agent1,getUserId().toString(),null);
        }catch (MessageException e){
            return ResultVO.fail(e.getMsg());
        }catch (ProcessException e){
            return ResultVO.fail(e.getMsg());
        }catch (Exception e){
            return ResultVO.fail("保存失败");
        }
        return ResultVO.success("保存成功！");
    }

    /**
     * 导出
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("doDownload")
    public void export(HttpServletResponse response, HttpServletRequest request) throws Exception{
        Map<String,String> map = getRequestParameter(request);
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list != null && list.size() != 0){
            Map<String,Object> objectMap = list.get(0);
            String string = list.get(0).get("ORGANIZATIONCODE").toString();
            if(string.indexOf("city") != -1){  // 分为省区 大区和其他
                map.put("docPro",objectMap.get("ORGID").toString());
            }else if(string.equals("north") || string.equals("south")){
                map.put("docDis",objectMap.get("ORGID").toString());
            }
        }
        List<Map<String,Object>> list1 = agentDebitCardService.exports(map);
        String filePath = "D:/upload/";
        String filePrefix = "IN";
        List<String> fieldNames = new ArrayList<String>();
        List<String> fieldCodes = new ArrayList<String>();
        fieldNames.add("代理商唯一码");
        fieldCodes.add("AGENTID");
        fieldNames.add("代理商名称");
        fieldCodes.add("AGENTNAME");
        fieldNames.add("审批状态");
        fieldCodes.add("agStatus".toUpperCase());
        fieldNames.add("收款账户类型");
        fieldCodes.add("cloType".toUpperCase());
        fieldNames.add("账户验证状态");
        fieldCodes.add("flag".toUpperCase());
        fieldNames.add("收款账户名");
        fieldCodes.add("cloRealName".toUpperCase());
        fieldNames.add("收款账号");
        fieldCodes.add("cloBankAccount".toUpperCase());
        fieldNames.add("收款账户总行");
        fieldCodes.add("cloBank".toUpperCase());
        fieldNames.add("收款账户支行");
        fieldCodes.add("cloBankBranch".toUpperCase());
        fieldNames.add("总行联行号");
        fieldCodes.add("allLineNum".toUpperCase());
        fieldNames.add("支行联行号");
        fieldCodes.add("branchLineNum".toUpperCase());
        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, 500);
        excelExportSXXSSF.writeDatasByMap(list1);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("toDebitNoticePage")
    public String toDebitNoticePage(HttpServletRequest request){
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
        }else{
            String string = list.get(0).get("ORGANIZATIONCODE").toString();
            if(string.indexOf("city") != -1){
               String  orgId = list.get(0).get("ORGID").toString();
                PageInfo pageInfo = agentDebitCardService.getNoticeList(orgId,null);
                request.setAttribute("countTotal",pageInfo.getTotal());
            }
        }
        return "agent/debitCard/debitNoticePage";
    }

    @RequestMapping("getNoticeList")
    @ResponseBody
    public PageInfo getNoticeList(HttpServletRequest request){
        Page page = pageProcess(request);
        String orgId = null;
        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        String string = list.get(0).get("ORGANIZATIONCODE").toString();
        if(string.indexOf("city") != -1){
            orgId = list.get(0).get("ORGID").toString();
        }else{
            return null;
        }
        PageInfo pageInfo = agentDebitCardService.getNoticeList(orgId,page);
        return pageInfo;
    }



}
