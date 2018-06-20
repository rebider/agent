package com.ryx.credit.service;

import com.ryx.credit.activity.entity.ActRuTask;

import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/30.
 */
public interface ActUtilService {
    /**
     * 查询任务详情
     * @param taskId
     * @return
     */
    ActRuTask queryTaskInfo(String taskId);

    /**
     * 查询用户列表
     * @param assignee
     * @return
     */
    List<ActRuTask> queryTaskInfoList(String assignee,String group);

    Map<String,Object> querHiTaskInfo(String taskId);

    Map<String,Object> querHiTaskInfoList(String assignee);
}
