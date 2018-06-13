package com.ryx.credit.activity.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BusinessTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/21
 * @Time: 17:24
 * @description: BusinessTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class BusinessTaskExecutionListener implements TaskListener,ExecutionListener{
    private static final Logger logger = LoggerFactory.getLogger(BusinessTaskExecutionListener.class);



    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
        }else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            DataChangeActivityService dataChange = (DataChangeActivityService)MySpringContextHandler.applicationContext.getBean("dataChangeActivityService");
            //审批拒绝
            if("reject_end".equals(activityName)){
                ResultVO res = dataChange.compressColInfoDataChangeActivity(delegateExecution.getProcessInstanceId(),AgStatus.Refuse.name());
                logger.info("=========BusinessTaskExecutionListener 流程{}eventName{}res{}",delegateExecution.getProcessInstanceId(),eventName,res.getResInfo());
            }
            //审批同意更新数据库
            if("finish_end".equals(activityName)){
                ResultVO res = dataChange.compressColInfoDataChangeActivity(delegateExecution.getProcessInstanceId(),AgStatus.Approved.name());
                logger.info("=========BusinessTaskExecutionListener 流程{}eventName{}res{}",delegateExecution.getProcessInstanceId(),eventName,res.getResInfo());
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