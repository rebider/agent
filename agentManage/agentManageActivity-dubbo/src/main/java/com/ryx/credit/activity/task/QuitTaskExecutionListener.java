package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * QuitTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 1.0 2018/7/19 9:37
 * @see QuitTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class QuitTaskExecutionListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(QuitTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            AgentQuitService agentQuitService = (AgentQuitService)MySpringContextHandler.applicationContext.getBean("agentQuitService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========QuitTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = agentQuitService.compressAgentQuitActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========QuitTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = agentQuitService.compressAgentQuitActivity(delegateExecution.getProcessInstanceId(), AgStatus.Approved.status);
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
