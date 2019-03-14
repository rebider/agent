package com.ryx.credit.activity.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx
 * 时间：2019/3/7
 * 描述：历史订单监听
 */
public class OldOrderReturnTaskExcutionListener  implements TaskListener, ExecutionListener {

    private Logger logger = LoggerFactory.getLogger(OldOrderReturnTaskExcutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.info("======{}==={}==={}",delegateExecution.getProcessInstanceId(),delegateExecution.getEventName(),delegateExecution.getId());
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("======{}==={}==={}==={}",delegateTask.getProcessInstanceId(),delegateTask.getEventName(),delegateTask.getId(),delegateTask.getName());
    }
}
