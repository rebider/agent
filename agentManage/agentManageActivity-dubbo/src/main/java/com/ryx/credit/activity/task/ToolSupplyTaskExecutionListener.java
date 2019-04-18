package com.ryx.credit.activity.task;

import com.ryx.credit.activity.task.taskListenterCommon.TaskListenterCommon;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.profit.service.ToolsDeductService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 省区补款或者上级代扣
 * @Author chen Liang
 * @Date 2019/04/10
 */
public class ToolSupplyTaskExecutionListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ToolSupplyTaskExecutionListener.class);



    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            //数据变更服务类
            ToolsDeductService toolsDeductService = (ToolsDeductService) MySpringContextHandler.applicationContext.getBean("toolsDeductService");
            //省区审批拒绝
            if ("reject_end".equals(activityName)) {
                toolsDeductService.updateStatus(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.name(),"01");
                logger.info("=========TerminalTransferTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                toolsDeductService.updateStatus(delegateExecution.getProcessInstanceId(), AgStatus.Approved.name(),"02");
                logger.info("=========TerminalTransferTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskListenterCommon taskListenterCommon = new TaskListenterCommon();
        taskListenterCommon.notify(delegateTask,logger);
    }
}
