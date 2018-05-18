package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.service.ActivityService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * RuleController
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/6/1
 * @Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController{
    protected Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ActivityService activityService;


    @RequestMapping("/start")
    @ResponseBody
    public Object start(final HttpServletRequest request,
                           final HttpServletResponse response){
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        String id = treeMap.get("id");
        activityService.createDeloyFlow("团建工作流1",id, AppConfig.getProperty("activity_path"), AppConfig.getProperty("activity_image_path"));
        return "OK";
    }

    @RequestMapping("/task")
    @ResponseBody
    public Object task(final HttpServletRequest request,
                                final HttpServletResponse response){
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        List<Task> taskList = activityService.findMyPersonTask(treeMap.get("user"));
        List<TaskObject> taskObjects = new ArrayList<>();
        for(Task task :taskList){
            TaskObject taskObject =new TaskObject();
            taskObject.setName(task.getName());
            taskObject.setId(task.getId());
            taskObject.setAssignee(task.getAssignee());
            taskObjects.add(taskObject);
        }
        return taskObjects;
    }

    class TaskObject {
        private String id;
        private String assignee;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @RequestMapping("/setTask")
    @ResponseBody
    public Object setTask(final HttpServletRequest request,
                       final HttpServletResponse response){
        //返回对象
        TreeMap<String, Object> treeMap = getRequestParameter(request);
        activityService.setValue(treeMap.get("taskId").toString(),treeMap);
        Map<String,Object> map = activityService.completeTask(treeMap.get("taskId").toString(), treeMap);
        return map;
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/test")
    public void test(final HttpServletRequest request,
                     final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            Map map = activityService.getImage(treeMap.get("taskId"));
            byte[] bytes = (byte[]) map.get("b");
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 读取带跟踪的图片
//     */
//    @RequestMapping(value = "/test2")
//    public void test2(final HttpServletRequest request,
//                     final HttpServletResponse response) {
//        TreeMap<String, String> treeMap = getRequestParameter(request);
//        try {
//            ProcessEngine processEngine = processEngineConfiguration
//                    .buildProcessEngine();
//            RepositoryService repositoryService = processEngine.getRepositoryService();
//            HistoryService historyService = processEngine.getHistoryService();
//            //获取历史流程实例
//           Map<String, String> coordinates = new HashMap<String, String>();
//            // 1. 获取到当前活动的ID
//            Task task = processEngine.getTaskService()
//                    .createTaskQuery().taskId(treeMap.get("taskId")).singleResult();
//            ProcessInstance pi =processEngine.getRuntimeService()
//                    .createProcessInstanceQuery()
//                    .processInstanceId(task.getProcessInstanceId()).singleResult();
//            String currentActivitiId = pi.getActivityId();
//            // 2. 获取到流程定义
//            ProcessDefinitionEntity pd =(ProcessDefinitionEntity) processEngine.getRepositoryService()
//                    .getProcessDefinition(task.getProcessDefinitionId());
//            // 3. 使用流程定义通过currentActivitiId得到活动对象
//            ActivityImpl activity = pd.findActivity(currentActivitiId);
//            // 4. 获取活动的坐标
//            coordinates.put("x", activity.getX()+"");
//
//            coordinates.put("y", activity.getY()+"");
//            coordinates.put("width", activity.getWidth()+"");
//            coordinates.put("height", activity.getHeight()+"");
//            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
//
//            //中文显示的是口口口，设置字体就好了
//            InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null,1.0);
//            //单独返回流程图，不高亮显示
////        InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
//            // 输出资源内容到相应对象
//            byte[] b = new byte[1024];
//            int len;
//            while ((len = imageStream.read(b, 0, 1024)) != -1) {
//                response.getOutputStream().write(b, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 打开流程图显示页面
     **/
    @RequestMapping(params = "openActivitiProccessImagePage")
    public ModelAndView openActivitiProccessImagePage(String pProcessInstanceId) throws Exception {
        logger.info("[开始]-打开流程图显示页面");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("processInstanceId", pProcessInstanceId);
        modelAndView.setViewName("common/jsp/ActivitiProccessImagePage.jsp");
        logger.info("[完成]-打开流程图显示页面");
        return modelAndView;
    }



}
