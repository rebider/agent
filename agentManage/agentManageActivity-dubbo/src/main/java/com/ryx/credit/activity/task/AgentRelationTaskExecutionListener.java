package com.ryx.credit.activity.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx
 * 时间：2018/12/21
 * 描述：
 */
public class AgentRelationTaskExecutionListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(AgentRelationTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
             logger.info("{}={}={}",delegateExecution.getEventName(),delegateExecution.getCurrentActivityName(),delegateExecution.getCurrentActivityId());
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
