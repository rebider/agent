package com.ryx.credit.activity.task.taskListenterCommon;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.activity.task.AgentMergerTaskExecutionListener;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;

import java.util.List;
/**
 * 监听类的公共部分
 * @Author chen Liang
 * @Date 2019/04/10
 */
public class TaskListenterCommon {

    public void notify(DelegateTask delegateTask, Logger logger) {
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            ThreadPool.putThreadPool(() -> {
                ThreadLocal<String> threadLocal = new ThreadLocal<>();
                threadLocal.set(delegateTask.getId());
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    logger.error("Thread error");
                }
                ActIdUserService actIdUserService = (ActIdUserService) MySpringContextHandler.applicationContext.getBean("actIdUserService");
                List<ActIdUser> actIdUserList = actIdUserService.selectByTaskId(threadLocal.get());
                String[] emails = new String[actIdUserList.size()];
                int i = 0;
                for (ActIdUser actIdUser : actIdUserList) {
                    emails[i++] = (String) actIdUser.getEmail();
                }
                AppConfig.sendEmail(emails, "name:" + delegateTask.getName() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "审批任务通知" + eventName);

            });
            logger.info("create=========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("assignment".endsWith(eventName)) {
            AppConfig.sendEmails("ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "task工作流通知" + eventName);
            logger.info("assignment========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("complete".endsWith(eventName)) {
            AppConfig.sendEmails("name:" + delegateTask.getName() + "  ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "task工作流通知" + eventName);
            logger.info("complete===========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("delete".endsWith(eventName)) {
            logger.info("delete=============");
        }
    }

}
