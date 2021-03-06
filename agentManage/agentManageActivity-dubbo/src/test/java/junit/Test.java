package junit;


import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.KafkaMessageTopic;
import com.ryx.credit.common.enumc.KafkaMessageType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.AgentKafkaService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author wangqi
 * @version 1.0
 * @date 2016年8月24日 21:18:17
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-context.xml" ,"classpath*:spring-mybatis.xml"})
public class Test {
    @Autowired
    StandaloneProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ActivityService activityService;
    @Autowired
    ActIdUserService actIdUserService;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private AgentKafkaService agentKafkaService;

    /**
     * 测试
     */
    @org.junit.Test
    public void onTest() throws Exception{
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();

        System.out.println("------processEngine:" + processEngine);
    }

    @org.junit.Test
    public void deloyFlow() throws Exception{
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
//        Deployment deployment = processEngine.getRepositoryService().createDeployment().name("测试1").addClasspathResource("activity.bpmn").addClasspathResource("diagram.png").deploy();
//        System.out.println("------processEngine:" + deployment.getId());
//        System.out.println("------processEngine:" + deployment.getName());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance =runtimeService.startProcessInstanceByKey("wq");
        System.out.println("------xxxxxxxxx:" + processInstance.getId());
        System.out.println("------xxxxxxxxxx:" + processInstance.getDeploymentId());
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getDeploymentId());
//        System.out.println("------xxxxxxxxx:" + processDefinition.getId());
//        System.out.println("------xxxxxxxxxx:" + processDefinition.getKey());
    }

    @org.junit.Test
    public void findMyPersonTask() throws Exception{
        String assignee="ada1";
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        for(Task task:taskList){
            System.out.println("待办"+task.getId());
            System.out.println("任务名"+task.getName());
            System.out.println("任务开始时间"+task.getCreateTime());
            System.out.println("办理人"+task.getAssignee());
            System.out.println("流程实例ID"+task.getProcessInstanceId());
            System.out.println("执行对象ID"+task.getExecutionId());
            System.out.println("流程定义ID"+task.getProcessDefinitionId());

        }

    }

    @org.junit.Test
    public void completeTask() throws Exception{
        String  taskId="115003";
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
        Map map =new HashMap<String, Object>();
        map.put("rs","同意");
        processEngine.getTaskService().complete(taskId,map);
        System.out.println("完成任务" + taskId);
    }

    @org.junit.Test
    public void findProcessDefinition() throws Exception{
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        for(ProcessDefinition processDefinition:list){
            System.out.println("待办"+processDefinition.getId());
            System.out.println("任务名"+processDefinition.getName());
            System.out.println("getKey"+processDefinition.getKey());
            System.out.println("getVersion"+processDefinition.getVersion());
            System.out.println("bpmn文件"+processDefinition.getResourceName());
            System.out.println("png"+processDefinition.getDiagramResourceName());
            System.out.println("部署ID"+processDefinition.getDeploymentId());
            System.out.println("=================");
        }
//        processEngine.getRepositoryService().deleteDeployment("105001",true);
    }


    @org.junit.Test
    public void findProcessExecution() throws Exception{
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
        List<Execution> list = processEngine.getRuntimeService().createExecutionQuery().orderByProcessDefinitionId().asc().list();
        for(Execution execution:list){
            System.out.println("待办"+execution.getId());
            System.out.println("任务名"+execution.getName());
            System.out.println("getKey"+execution.getActivityId());
            System.out.println("getVersion"+execution.getDescription());
            System.out.println("bpmn文件"+execution.getParentId());
            System.out.println("ProcessInstanceId"+execution.getProcessInstanceId());
            System.out.println("部署ID"+execution.getTenantId());
            System.out.println("部署ID"+execution.getSuperExecutionId());
            System.out.println("=================");
        }
    }

    @org.junit.Test
    public void getExecution() throws Exception{
//        Map map = activityService.getImageByExecuId("40010");
        List<ActIdUser> a =actIdUserService.selectByTaskId("240144");
        System.out.print(a.size());
    }

//    @org.junit.Test
//    public void setValue() throws Exception{
//        ProcessEngine processEngine = processEngineConfiguration
//                .buildProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        String task_id="105009";
//        taskService.setVariable(task_id,"请假天数",7);
//        taskService.setVariable(task_id,"请假日期",new Date());
//        taskService.setVariable(task_id,"请假原因","年假");
//        taskService.setVariable(task_id,"price",1001);
//        Person p = new Person();
//        p.setAge(30);
//        p.setId(new BigDecimal(1));
//        p.setName("马丽丽");
//        taskService.setVariable(task_id,"person",p);
//
//    }
//
//    @org.junit.Test
//    public void getValue() throws Exception{
//        ProcessEngine processEngine = processEngineConfiguration
//                .buildProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        String task_id="87502";
//        Integer day = (Integer) taskService.getVariable(task_id,"请假天数");
//        Date date = (Date) taskService.getVariable(task_id, "请假日期");
//        String reason = (String) taskService.getVariable(task_id,"请假原因");
//        Person p = (Person) taskService.getVariable(task_id,"person");
//        String rs = (String) taskService.getVariable(task_id,"rs");
//
//        System.out.println("请假天数"+day);
//        System.out.println("请假日期"+date);
//        System.out.println("请假原因"+reason);
//        System.out.println("rs"+rs);
//        System.out.println("person"+p.getId()+p.getName()+p.getAge());
//
//
//    }


    @org.junit.Test
    public void deleteDepWar(){
//        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        repositoryService.deleteDeployment("2465008",true);

    }

    @org.junit.Test
    public void testKafka(){

        ListenableFuture listenableFuture = kafkaTemplate.send("agent","123123213");
        listenableFuture.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                if(ex instanceof KafkaProducerException) {
                    ProducerRecord recode = ((KafkaProducerException) ex).getProducerRecord();
                    System.out.println(recode.key());
                    System.out.println(recode.key());
                    ex.printStackTrace();
                }
            }

            @Override
            public void onSuccess(Object result) {
                System.out.println(result.toString());
            }
        });

    }


    /**
     * 测试对公对私并行操作
     */
    @org.junit.Test
    public void testStartProcess(){
        RuntimeService runtimeService = processEngineConfiguration.buildProcessEngine().getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test_bingxing_back",
//                FastMap.fastMap("v",1)
//                .putKeyV("res","pass")
//                        .putKeyV("user1","pass")
//                        .putKeyV("user2","reject"));
//        System.out.println(processInstance.getId());
//        System.out.println(processInstance.getDeploymentId());
//        System.out.println(processInstance.getBusinessKey());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OrderAdjustRefund_2.4",
                FastMap.fastMap("settlementCardDs",1)
                        .putKeyV("settlementCardDg","0"));
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getDeploymentId());
        System.out.println(processInstance.getBusinessKey());
//        test_process_var
    }


    @org.junit.Test
    public void completTask(){
        TaskService taskService = processEngineConfiguration.buildProcessEngine().getTaskService();
        taskService.complete("5280011",FastMap.fastMap("provReject","0"));
        taskService.complete("5280015",FastMap.fastMap("provReject","0"));
//        taskService.complete("4475005",FastMap.fastMap("v",1)
//                .putKeyV("user1","pass"));
//        taskService.complete("4475007",FastMap.fastMap("v",1)
//                .putKeyV("user2","pass"));
        taskService.complete("5332523",FastMap.fastMap("rejectCount","1").putKeyV("rs","pass"));
//        taskService.complete("5325033",FastMap.fastMap("v",1)
//                .putKeyV("user1","pass"));
//        taskService.complete("4475007",FastMap.fastMap("v",1)
//                .putKeyV("user2","pass"));
        System.out.println("任务完成");
    }

    @org.junit.Test
    public void qeurySelectActivity(){
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();


        RepositoryService repositoryService =  processEngineConfiguration.getRepositoryService();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.createProcessDefinitionQuery().processDefinitionKey("test_bingxing_back").active().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessDefinition> definitions =  processEngine.getRepositoryService()
                .createProcessDefinitionQuery().processDefinitionKey("test_bingxing_back").list();

        List<ProcessInstance> processDefinitionQuery = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("test_bingxing_back")
                .processInstanceId("4482501").list();
        for (ProcessInstance processInstance : processDefinitionQuery) {
            ExecutionImpl execution = (ExecutionImpl)processInstance;
            execution.getActivity();
            System.out.println("--------:"+processInstance.getTenantId());
            System.out.println("--------:"+processInstance.getBusinessKey());
            System.out.println("--------:"+processInstance.getDeploymentId());
            System.out.println("--------:"+processInstance.getProcessDefinitionKey());
            System.out.println("--------:"+processInstance.getProcessDefinitionId());
            System.out.println("--------:"+processInstance.getActivityId());

        }
    }
    @org.junit.Test
    public void singleToGobal(){
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.signalEventReceived("testSig");
    }


    /**
     * 基础信息及结算卡信息变更申请
     */
    @org.junit.Test
    public void testAgentAppyChangeCards(){
        RuntimeService runtimeService = processEngineConfiguration.buildProcessEngine().getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("appy_change_card",
                FastMap.fastMap("provList", Arrays.asList("kermit","gonzo","fozzie"))
                        .putKeyV("provReject","0")
                        .putKeyV("distList",Arrays.asList("kermit","gonzo")));
        System.out.println("基础信息及结算卡信息变更申请"+processInstance.getId());
    }

}

