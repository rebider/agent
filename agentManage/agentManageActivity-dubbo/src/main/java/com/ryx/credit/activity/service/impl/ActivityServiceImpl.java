package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.agent.BusActRelService;
import net.sf.json.JSONObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * ActivityServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 2017/6/29
 * To change this template use File | Settings | File Templates.
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private StandaloneProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private ActRuTaskService actRuTaskService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private ApprovalFlowRecordService approvalFlowRecordService;

    public static ProcessEngine processEngine;

    
    @Override
    public void createTable() {
        try {
            logger.info("------processEngine:" + processEngine);
        } catch (Exception e) {
            logger.error("createTable error", e);
        }
    }

    @Override
    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public String createDeloyFlow(String deployName, String workId, String activityPath, String activityImagePath,Map<String,Object> map)throws ProcessException {

        try {
            List<ProcessDefinition> processDefinitions = findProcessDefinition();
            if (processDefinitions.size() == 0) {
                Deployment deployment = processEngine.getRepositoryService().createDeployment().name(deployName).addClasspathResource(activityPath).addClasspathResource(activityImagePath).deploy();
                logger.info("------processEngine:" + deployment.getId());
                logger.info("------processEngine:" + deployment.getName());
            }
            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workId,map);
            logger.info("------processInstance:" + processInstance.getId());
            logger.info("------processInstance:" + processInstance.getDeploymentId());
            return processInstance.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("createDeloyFlow error", e);
            throw new ProcessException(e.getLocalizedMessage());
        }
    }
    
    @Override
    public List<Task> findMyPersonTask(String assignee,String group) {
        List<Task> taskList = new ArrayList<>();
        List<Task> taskListGroup = new ArrayList<>();

        if(StringUtils.isNotBlank(assignee)) {
           taskList = processEngine.getTaskService().createTaskQuery().taskCandidateUser(assignee).list();
        }
        if(StringUtils.isNotBlank(group)) {
            taskListGroup = processEngine.getTaskService().createTaskQuery().taskCandidateGroup(group).list();
        }
        taskList.addAll(taskListGroup);
        taskList.sort(Comparator.comparing(TaskInfo::getCreateTime));
        Collections.reverse(taskList);
//        for (Task task : taskList) {
//            logger.info("待办" + task.getId());
//            logger.info("任务名" + task.getName());
//            logger.info("任务开始时间" + task.getCreateTime());
//            logger.info("办理人" + task.getAssignee());
//            logger.info("流程实例ID" + task.getProcessInstanceId());
//            logger.info("执行对象ID" + task.getExecutionId());
//            logger.info("流程定义ID" + task.getProcessDefinitionId());
//        }
        return taskList;

    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public Map completeTask(String taskId, Map<String,Object>  map) throws ProcessException {
        Map<String,Object> rs = new HashMap<>(5);
        ApprovalFlowRecord upFlowRecord = new ApprovalFlowRecord();
        try {
            String approvalResult = String.valueOf(map.get("rs"));
            String approvalOpinion = String.valueOf(map.get("approvalOpinion"));
            String approvalPerson = String.valueOf(map.get("approvalPerson"));
            String createTime = String.valueOf(map.get("createTime"));
            Date approvalTime = DateUtil.format(createTime, DateUtil.DATE_FORMAT_1);
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(approvalPerson));
            String approvalDep = String.valueOf(orgCodeRes.get(0).get("ORGID"));
            ActRuTask actRuTask = actRuTaskService.selectByPrimaryKey(taskId);
            if (null== actRuTask){
                throw new ProcessException("任务不存在或者已经被处理！");
            }
            String procInstId = String.valueOf(actRuTask.getProcInstId());
            String taskName = String.valueOf(actRuTask.getName());
            BusActRel busActRel = busActRelService.findById(procInstId);
            String busId = busActRel.getBusId();
            String busType = busActRel.getBusType();

            ApprovalFlowRecord approvalFlowRecord = new ApprovalFlowRecord();
            approvalFlowRecord.setBusId(busId);
            approvalFlowRecord.setBusType(busType);
            approvalFlowRecord.setTaskName(taskName);
            approvalFlowRecord.setApprovalDep(approvalDep);
            approvalFlowRecord.setApprovalTime(approvalTime);
            approvalFlowRecord.setExecutionId(procInstId);
            approvalFlowRecord.setTaskId(taskId);
            approvalFlowRecord.setApprovalOpinion(approvalOpinion);
            approvalFlowRecord.setApprovalPerson(approvalPerson);
            approvalFlowRecord.setApprovalResult(approvalResult);
            approvalFlowRecord.setAgentId(busActRel.getAgentId());
            approvalFlowRecord.setAgentName(busActRel.getAgentName());
            String insert = approvalFlowRecordService.insert(approvalFlowRecord);
            upFlowRecord.setId(insert);
            TaskService taskService = processEngine.getTaskService();
            taskService.setVariable(taskId,taskId+"_ryx_wq", JSONObject.fromMap(map).toString());
            taskService.complete(taskId, map);
            logger.info("完成任务" + taskId);
            rs.put("rs",true);
            rs.put("msg","success");
            upFlowRecord.setActivityStatus(Status.STATUS_1.status);
        } catch (MessageException | ProcessException e) {
            e.printStackTrace();
            logger.error("completeTask error", e);
            rs.put("rs", false);
            rs.put("msg", e.getCause().getMessage());
            upFlowRecord.setStatus(Status.STATUS_0.status);
            upFlowRecord.setActivityStatus(Status.STATUS_0.status);
            upFlowRecord.setErrorMsg(e.getCause().getMessage());
            throw new ProcessException(e.getCause().getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("completeTask error", e);
            rs.put("rs", false);
            rs.put("msg", e.getCause().getMessage());
            upFlowRecord.setStatus(Status.STATUS_0.status);
            upFlowRecord.setActivityStatus(Status.STATUS_0.status);
            upFlowRecord.setErrorMsg(e.getCause().getMessage());
            throw new ProcessException(e.getCause().getMessage());
        }finally {
            if(StringUtils.isNotBlank(upFlowRecord.getId())) {
                approvalFlowRecordService.update(upFlowRecord);
            }
        }

        return rs;
    }

    @Override
    public Map completeTaskInNer(String taskId, Map<String, Object> map) throws ProcessException {
        TaskService taskService = processEngine.getTaskService();
        taskService.setVariable(taskId,taskId+"_ryx_wq", JSONObject.fromMap(map).toString());
        taskService.complete(taskId, map);
        return FastMap.fastSuccessMap();
    }

    @Override
    public List<ProcessDefinition> findProcessDefinition() {
        List<ProcessDefinition> list = null;
        try {
            list = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
//            for (ProcessDefinition processDefinition : list) {
//                logger.info("待办" + processDefinition.getId());
//                logger.info("任务名" + processDefinition.getName());
//                logger.info("getKey" + processDefinition.getKey());
//                logger.info("getVersion" + processDefinition.getVersion());
//                logger.info("bpmn文件" + processDefinition.getResourceName());
//                logger.info("png" + processDefinition.getDiagramResourceName());
//                logger.info("部署ID" + processDefinition.getDeploymentId());
//                logger.info("=================");
//            }
        } catch (Exception e) {
            logger.error("findProcessDefinition error", e);
        }
        return list;
    }

    
    @Override
    public void delProcessDefinition(String deploymentId) {
        try {
            ProcessEngine processEngine = processEngineConfiguration
                    .buildProcessEngine();
            processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
        } catch (Exception e) {
            logger.error("delProcessDefinition error", e);
        }
    }

    
    @Override
    public void setValue(String taskId, Map<String, Object> map) {
        try {
            TaskService taskService = processEngine.getTaskService();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                taskService.setVariable(taskId, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.error("setValue error", e);
        }
    }

    
    @Override
    public Object getValue(String taskId, String key) {
        Object o = null;
        try {
            TaskService taskService = processEngine.getTaskService();
            o = taskService.getVariable(taskId, key);
        } catch (Exception e) {
            logger.error("getValue error", e);
        }
        return o;
    }

    
    @Override
    public Map getImage(String taskId)  {
        Map<String,Object> map = null;
        try {
            RepositoryService repositoryService = processEngine.getRepositoryService();
            HistoryService historyService = processEngine.getHistoryService();
            TaskService taskService = processEngine.getTaskService();
            List<Task> taskList = taskService.createTaskQuery().list();
            Task task = null ;
            for(Task task1:taskList){
                if(task1.getId().equals(taskId)){
                    task =task1;
                }
            }
            //processInstanceId
            if(task==null){
                return null;
            }
            String processInstanceId = task.getProcessInstanceId();
            HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

            List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
            List<String> highLightedActivitis = new ArrayList<>();
            List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

            for(HistoricActivityInstance tempActivity : highLightedActivitList){
                String activityId = tempActivity.getActivityId();
                highLightedActivitis.add(activityId);
            }
            //获取流程图
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

            //中文显示的是口口口，设置字体就好了
            InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",processEngineConfiguration.getClassLoader(),1.0);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                byteArrayOutputStream.write(b, 0, len);
            }
            map = new HashMap<>(3);
            map.put("b",byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            logger.error("getImage error",e);
        }
        return map;
    }

    /**
     * 获取需要高亮的线
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<>();
        // 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    @Override
    public Map getImageByExecuId(String executionId)  {
        Page page = new Page();
        page.setCurrent(1);
        page.setLength(10);
        page.setBegin(0);
        page.setEnd(10);
        ActRuTask actRuTask = new ActRuTask();
        actRuTask.setProcInstId(executionId);
        HashMap<String,Object> hashMap = actRuTaskService.configExample(page, actRuTask);
        List<ActRuTask> actRuTaskList = (List<ActRuTask>) hashMap.get("list");
        if(actRuTaskList.size()>0){
            return getImage(actRuTaskList.get(0).getId().toString());
        }
        return null;
    }
}
