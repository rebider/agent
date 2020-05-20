package com.ryx.credit.activity.task;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.order.OrderAdjustService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OrderAdjustRefundTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @author ssx
 * @version 1.0 2018/7/19 9:37
 * @see OrderAdjustRefundTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class OrderAdjustRefundTaskExecutionListener extends BaseTaskListener implements TaskListener, ExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderAdjustRefundTaskExecutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            OrderAdjustService orderService = (OrderAdjustService)MySpringContextHandler.applicationContext.getBean("orderAdjustService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========OrderAdjustRefundTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = orderService.approveFinishOrderAdjustRefund(delegateExecution.getProcessInstanceId(), activityName);
                logger.info("=========OrderAdjustRefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());

            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========OrderAdjustRefundTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = orderService.approveFinishOrderAdjustRefund(delegateExecution.getProcessInstanceId(), activityName);
//                logger.info("=========OrderAdjustRefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());

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
