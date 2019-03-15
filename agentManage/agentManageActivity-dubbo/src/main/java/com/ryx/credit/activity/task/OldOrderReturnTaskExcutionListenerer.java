package com.ryx.credit.activity.task;

import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.OldOrderReturnService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx
 * 时间：2019/3/13
 * 描述：
 */
public class OldOrderReturnTaskExcutionListenerer   implements TaskListener, ExecutionListener {
    private Logger logger = LoggerFactory.getLogger(OldOrderReturnTaskExcutionListenerer.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            OldOrderReturnService oldOrderReturnService = (OldOrderReturnService) MySpringContextHandler.applicationContext.getBean("oldOrderReturnService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========OldOrderReturnTaskExcutionListenerer 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========OldOrderReturnTaskExcutionListenerer 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
                oldOrderReturnService.approvalFinish(delegateExecution.getProcessInstanceId(),activityName);
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }   }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("======{}==={}==={}==={}",delegateTask.getProcessInstanceId(),delegateTask.getEventName(),delegateTask.getId(),delegateTask.getName());
    }
}
