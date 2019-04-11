package com.ryx.credit.activity.task;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.order.OldOrderReturnService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：cx chenxiao 123123
 * 时间：2019/3/7
 * 描述：历史订单监听
 */
public class OldOrderReturnTaskExcutionListener  extends BaseTaskListener  implements TaskListener, ExecutionListener {

    private Logger logger = LoggerFactory.getLogger(OldOrderReturnTaskExcutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.info("======{}==={}==={}",delegateExecution.getProcessInstanceId(),delegateExecution.getEventName(),delegateExecution.getId());
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            String activityId = delegateExecution.getCurrentActivityId();
            //数据变更服务类
            OldOrderReturnService oldOrderReturnService = (OldOrderReturnService) MySpringContextHandler.applicationContext.getBean("oldOrderReturnService");
            //审批拒绝
            if ("reject_end".equals(activityName) || "reject_end".equals(activityId) ) {
                logger.info("=========MergeTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                oldOrderReturnService.approvalReject(delegateExecution.getProcessInstanceId(),activityName);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName) || "finish_end".equals(activityId)) {
                logger.info("=========MergeTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                oldOrderReturnService.approvalFinish(delegateExecution.getProcessInstanceId(),activityName);
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        notity(delegateTask);
    }
}
