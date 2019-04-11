package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

/**
 * TaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/21
 * @Time: 17:24
 * @description: TaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class TaskExecutionListener  extends BaseTaskListener implements TaskListener,ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        try {
            String eventName = delegateExecution.getEventName();
            if ("start".equals(eventName)) {
                logger.info("start========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
            }else if ("end".equals(eventName)) {
                String activityName = delegateExecution.getCurrentActivityName();
                AgentEnterService aes = (AgentEnterService)MySpringContextHandler.applicationContext.getBean("agentEnterService");
                if("reject_end".equals(activityName)){
                    aes.completeProcessing(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.name());
                }
                if("finish_end".equals(activityName)){
                    aes.completeProcessing(delegateExecution.getProcessInstanceId(),AgStatus.Approved.name());
                }
            }else if ("take".equals(eventName)) {
                logger.info("take========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
            }
        } catch (BeansException e) {
            e.printStackTrace();
            throw e;
        } catch (ProcessException e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        notity(delegateTask);
    }
}
