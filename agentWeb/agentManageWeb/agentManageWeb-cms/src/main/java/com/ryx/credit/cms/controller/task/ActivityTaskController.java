package com.ryx.credit.cms.controller.task;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.controller.agent.AgentMergeController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by RYX on 2018/9/5.
 */
@Controller
@RequestMapping("/activity/task")
public class ActivityTaskController extends BaseController{

    private static String EXPORT_TASK_PATH = AppConfig.getProperty("export.path");
    private static Logger logger = LoggerFactory.getLogger(AgentMergeController.class);
    @Autowired
    private ApprovalFlowRecordService approvalFlowRecordService;
    @Autowired
    private IUserService userService;


    @RequestMapping(value = "toAlreadyTaskList")
    public String addressManageList(HttpServletRequest request) {
        List<Map<String, Object>> orgCodeRes = userService.orgCode(getUserId());
        if(null!=orgCodeRes){
            request.setAttribute("orgId",orgCodeRes.get(0).get("ORGID"));
        }
        request.setAttribute("userId",getUserId());
        Map<String, Object> busTypeList = BusActRelBusType.getItemMap();
        request.setAttribute("busTypeList",busTypeList);
        List<Dict> approvalResultList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
        request.setAttribute("approvalResultList",approvalResultList);
        return "activity/alreadyTaskList";
    }


    @RequestMapping(value = "alreadyTaskList")
    @ResponseBody
    public Object addressManageDataList(HttpServletRequest request, ApprovalFlowRecord approvalFlowRecord) {
        String appResult = request.getParameter("appResult");
        Page page = pageProcess(request);
        String approvalPersonName = request.getParameter("approvalPersonName");
        if(StringUtils.isNotBlank(approvalPersonName)){
            List<UserVo> userVos = userService.selectListByName("%"+approvalPersonName+"%");
            StringBuffer sb = new StringBuffer();
            userVos.forEach(row->{
                sb.append(row.getId()+",");
            });
            approvalFlowRecord.setApprovalPerson(sb.substring(0,sb.length()-1));
        }
        approvalFlowRecord.setApprovalResult(appResult);
        PageInfo pageInfo = approvalFlowRecordService.approvalFlowList(approvalFlowRecord, page);
        return pageInfo;
    }

    /**
     * 导出数据
     * @param approvalFlowRecord
     * @param response
     */
    @RequestMapping(value = "exportTaskData")
    public void exportTaskData(ApprovalFlowRecord approvalFlowRecord, HttpServletResponse response) throws Exception {
        String busType = approvalFlowRecord.getBusType();
        if (busType != null ) {
            if (busType.equals(BusActRelBusType.MERGE.name())) {
                logger.info("业务类型-->代理商合并，调用合并导出...");
                exportTaskMerge(approvalFlowRecord, response);
            }else if (busType.equals(BusActRelBusType.Agent.name())) {
                logger.info("业务类型-->代理商入网，调用入网导出...");
                exportTaskAgent(approvalFlowRecord, response);
            }else if (busType.equals(BusActRelBusType.DC_Agent.name())) {
                logger.info("业务类型-->代理商修改，调用入网导出...");
                exportAgentEdit(approvalFlowRecord, response);
            }else{
                throw new MessageException("导出请选择业务类型");
            }
        } else {
            logger.info("该业务类型暂不支持导出操作！");
            throw new MessageException("该业务类型暂不支持导出操作！");
        }
    }

    /**
     * 代理商合并
     * @param approvalFlowRecord
     * @param response
     * @throws Exception
     */
    public void exportTaskMerge(ApprovalFlowRecord approvalFlowRecord, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> mapListMerge = approvalFlowRecordService.exportAgentMerge(approvalFlowRecord);
        Map<String, Object> param = new HashMap<String, Object>(6);
        String title = "合并时间,主代理商ID,主代理商名称,主代理商所属省区,主代理商业务类型,主代理商业务平台,主代理商业务区域,副代理商ID,副代理商名称,副代理商所属省区,副代理商业务类型,副代理商业务平台,副代理商业务区域";
        String column = "uTime,mainAgentId,mainAgentName,agDocProMain,busTypeMain,busPlatformMain,busRegionMain,subAgentId,subAgentName,agDocProSub,busTypeSub,busPlatformSub,busRegionSub";
        param.put("path", EXPORT_TASK_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", mapListMerge);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    /**
     * 代理商入网
     * @param approvalFlowRecord
     * @param response
     * @throws Exception
     */
    public void exportTaskAgent(ApprovalFlowRecord approvalFlowRecord, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> mapListAgent = approvalFlowRecordService.exportAgentNetln(approvalFlowRecord);
        Map<String, Object> param = new HashMap<String, Object>(6);
        String title = "签约时间,代理商ID,代理商名称,所属省区,业务类型,业务平台,缴纳款项,打款人,打款时间,上级关系";
        String column = "cUtime,agentId,agentName,agDocPro,busType,busPlatform,cPayType,cpayuser,cPaytime,parentBusInfo";
        param.put("path", EXPORT_TASK_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", mapListAgent);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }


    public void exportAgentEdit(ApprovalFlowRecord approvalFlowRecord, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> mapListAgent = approvalFlowRecordService.exportAgentEdit(approvalFlowRecord);
        Map<String, Object> param = new HashMap<String, Object>(6);
        String title = "代理商ID,代理商名称,省区,变更数据";
        String column = "agentId,agentName,agDocPro,dateChange";
        param.put("path", EXPORT_TASK_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", mapListAgent);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }
}
