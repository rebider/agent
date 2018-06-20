package com.ryx.credit.service;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * ActivityService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/6/29
 * @Time: 14:44
 * To change this template use File | Settings | File Templates.
 */

public interface ActivityService {
    void createTable();

    String createDeloyFlow(String deployName, String workId, String activity_path, String activity_image_path,Map<String,Object> map);

    List<Task> findMyPersonTask(String assignee,String group);

    Map completeTask(String taskId, Map<String, Object> map);

    List<ProcessDefinition> findProcessDefinition();

    void delProcessDefinition(String deploymentId);

    void setValue(String task_id, Map<String, Object> map);

    Object getValue(String task_id, String key);

    Map getImage(String taskId);

    Map getImageByExecuId(String executionId);
}
