package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 作者:renshenghao
 * 时间：2019年4月25日10点03分
 * 描述:代理商分润解冻审批
 */
public class ThawAgentTaskExecutionListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ThawAgentTaskExecutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            IFreezeAgentSercice freezeAgentSercice = (IFreezeAgentSercice) MySpringContextHandler.applicationContext.getBean("freezeAgentSercice");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                freezeAgentSercice.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(), "2");
                logger.info("=========ThawAgentTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
            }
            //审批同意更新数据库
            if ("end_finish".equals(activityName)) {
                freezeAgentSercice.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(), "1");
                logger.info("=========ThawAgentTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
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
