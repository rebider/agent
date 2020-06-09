package com.ryx.credit.activity.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

/**
 * 作者：cx
 * 时间：2020/6/8
 * 描述：
 */
public class AppyChangeCardExecutionListener extends BaseTaskListener  implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
