package com.ryx.credit.cms.controller;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActUtilService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

/**
 * ActivityAdminController
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @Date 2018/5/29
 * @Time: 10:43
 * @description: ActivityAdminController
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/activity")
public class ActivityAdminController extends BaseController {

    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ActUtilService actUtilService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private ActRuTaskService actRuTaskService;
    @Autowired
    private TaskApprovalService taskApprovalService;


    @RequestMapping("/activityList.htmls")
    @ResponseBody
    public Object activityList(final HttpServletRequest request,
                               final HttpServletResponse response) {

        Map<String, Object> param = getRequestParameter(request);
        Page page = pageProcess(request);
        List<Map<String, Object>>  org = iUserService.orgCode(getUserId());
        if(org.size()==0){throw new ProcessException("部门信息未找到");}
        List<Dict>  disc = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.ACTIVITY_TASK_REX.name());
        String group = null;
        for (Dict dict : disc) {
            if (Pattern.matches(dict.getdItemvalue(), org.get(0).get("ORGANIZATIONCODE") + "")) {
                group = dict.getdItemname();
                break;
            }
        }
        PageInfo pageInfo = null;
        List<Map<String, Object>> listMap = null;
        param.put("userId",getUserId());
        if(group.equals("agent") || group.equals("city")){
            if(group.equals("agent")){
                param.put("cUser",getUserId());
                param.put("group","agent");
            }else{
                param.put("group","city");
                param.put("orgId",org.get(0).get("ORGID"));
                param.put("orgCode",org.get(0).get("ORGANIZATIONCODE"));
            }
            pageInfo = actRuTaskService.queryMyTaskPage(page,param);
            listMap = (List<Map<String, Object>>)pageInfo.getRows();
        }else{
            param.put("group",group);
            param.put("orgId",org.get(0).get("ORGID"));
            pageInfo = actRuTaskService.queryMyTaskPage(page,param);
            listMap = (List<Map<String, Object>>)pageInfo.getRows();
        }
        List<TaskObject> taskObjects = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            String id = String.valueOf(map.get("ID_"));
            String name = String.valueOf(map.get("NAME_"));
            String activId = String.valueOf(map.get("ACTIV_ID"));
            String agentId = String.valueOf(map.get("AGENT_ID"));
            String agentName = String.valueOf(map.get("AGENT_NAME"));
            String busId = String.valueOf(map.get("BUS_ID"));
            String busType = String.valueOf(map.get("BUS_TYPE"));
            String cTime = String.valueOf(map.get("C_TIME"));
            String agDocProName = String.valueOf(map.get("AG_DOC_PRO_NAME"));
            String agDocDistrictName = String.valueOf(map.get("AG_DOC_DISTRICT_NAME"));
            ActRuTask art = actUtilService.queryTaskInfo(id);
            TaskObject taskObject =new TaskObject();
            taskObject.setName(name);
            taskObject.setId(id);
            taskObject.setExecutionId(activId);
            taskObject.setProcInstId(art.getProcInstId()+"");
            taskObject.setCreateTime(art.getCreateTime());
            taskObject.setAssignee(art.getAssignee()+"");
            taskObject.setSid(art.getTaskDefKey()+"");
            taskObject.setAgentId(agentId);
            taskObject.setAgName(agentName);
            taskObject.setBusId(busId);
            taskObject.setBusType(busType);
            taskObject.setBusData(BusActRelBusType.getItemString(busType)+":"+busId);
            taskObject.setSubmitTime(cTime);
            taskObject.setAgDocProName(agDocProName);
            taskObject.setAgDocDistrictName(agDocDistrictName);
            taskObjects.add(taskObject);
        }
        pageInfo.setRows(taskObjects);
        return pageInfo;
    }


    @RequestMapping("/toList.htmls")
    public Object toList(final HttpServletRequest request,
                         final HttpServletResponse response
    ){
        Map items = BusActRelBusType.getItemMap();
        request.setAttribute("BusActRelBusType",items);
        return "activity/activityList";
    }

    @RequestMapping("/async.htmls")
    @ResponseBody
    public Object async() {
        taskApprovalService.updateShrioBusActRel();
        return "success";
    }

}

class TaskObject {
    private String id;
    private String executionId;
    private String name;
    private String procInstId;
    private String assignee;
    private Date createTime;
    private String busData;
    private String busType;
    private String busId;
    private String agentId;
    private String agName;
    private String agHead;
    private String agHeadMobile;
    private String agRegAdd;
    private String agRemark;
    private Date cTime;
    private String agDocPro;
    private String agDocDistrict;
    private String sid;
    private String submitTime;
    private String agDocProName;
    private String agDocDistrictName;

    public String getAgDocProName() {
        return agDocProName;
    }

    public void setAgDocProName(String agDocProName) {
        this.agDocProName = agDocProName;
    }

    public String getAgDocDistrictName() {
        return agDocDistrictName;
    }

    public void setAgDocDistrictName(String agDocDistrictName) {
        this.agDocDistrictName = agDocDistrictName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getBusData() {
        return busData;
    }

    public void setBusData(String busData) {
        this.busData = busData;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }


    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead;
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile;
    }

    public String getAgRegAdd() {
        return agRegAdd;
    }

    public void setAgRegAdd(String agRegAdd) {
        this.agRegAdd = agRegAdd;
    }

    public String getAgRemark() {
        return agRemark;
    }

    public void setAgRemark(String agRemark) {
        this.agRemark = agRemark;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

}
