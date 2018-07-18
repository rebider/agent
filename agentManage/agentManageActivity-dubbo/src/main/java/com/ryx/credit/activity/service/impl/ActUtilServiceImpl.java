package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.ActRuTaskMapper;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.service.ActivityService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/30.
 */
@Service("actUtilService")
public class ActUtilServiceImpl implements com.ryx.credit.service.ActUtilService {


    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActRuTaskMapper actRuTaskMapper;

    /**
     * 查询任务详情
     * @param taskId
     * @return
     */
    @Override
    public ActRuTask queryTaskInfo(String taskId){
        return actRuTaskMapper.selectByPrimaryKey(taskId);
    }

    /**
     * 查询用户列表
     * @param assignee
     * @return
     */
    @Override
    public List<ActRuTask> queryTaskInfoList(String assignee,String group){
        List<Task> taskList = activityService.findMyPersonTask(assignee,group);
        List<ActRuTask> taskListActRuTask = new ArrayList<>();
        for (Task task : taskList) {
          String taskId =  task.getId();
          ActRuTask ruTask = actRuTaskMapper.selectByPrimaryKey(taskId);
            taskListActRuTask.add(ruTask);
        }
        return taskListActRuTask;
    }

    @Override
    public Map<String,Object> querHiTaskInfo(String taskId){
        return null;
    }

    @Override
    public Map<String,Object> querHiTaskInfoList(String assignee){
        return null;
    }


}
