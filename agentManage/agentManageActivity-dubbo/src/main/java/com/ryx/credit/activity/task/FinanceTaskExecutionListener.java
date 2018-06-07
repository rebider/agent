package com.ryx.credit.activity.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FinanceTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/21
 * @Time: 17:24
 * @description: FinanceTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class FinanceTaskExecutionListener implements TaskListener,ExecutionListener{
    private static final Logger logger = LoggerFactory.getLogger(FinanceTaskExecutionListener.class);



    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
        }else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            if("reject_end".equals(activityName)){
            }
            if("finish_end".equals(activityName)){
            }
        }else if ("take".equals(eventName)) {
            logger.info("take========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            logger.info("create========="+"Assignee:"+delegateTask.getAssignee() + "  ProcessInstanceId:"+delegateTask.getProcessInstanceId()+"  task:"+delegateTask.getId());
        }else if ("assignment".endsWith(eventName)) {
            AppConfig.sendEmails("Assignee:"+delegateTask.getAssignee() + "  ProcessInstanceId:"+delegateTask.getProcessInstanceId()+"  task:"+delegateTask.getId(),"task工作流通知"+eventName);
            logger.info("assignment========"+"Assignee:"+delegateTask.getAssignee() + "  ProcessInstanceId:"+delegateTask.getProcessInstanceId()+"  task:"+delegateTask.getId());
        }else if ("complete".endsWith(eventName)) {
            AppConfig.sendEmails("name:"+delegateTask.getName()+"  Assignee:"+delegateTask.getAssignee() + "  ProcessInstanceId:"+delegateTask.getProcessInstanceId()+"  task:"+delegateTask.getId(),"task工作流通知"+eventName);
            logger.info("complete==========="+"Assignee:"+delegateTask.getAssignee() + "  ProcessInstanceId:"+delegateTask.getProcessInstanceId()+"  task:"+delegateTask.getId());
        }else if ("delete".endsWith(eventName)) {
            logger.info("delete=============");
        }
    }
}
