package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * RefundTaskExecutionListener
 * Created by IntelliJ IDEA.
 * 退货审批
 * @author Wang Qi
 * @version 1.0 2018/7/19 9:45
 * @see RefundTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class RefundTaskExecutionListener  extends BaseTaskListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(RefundTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            IOrderReturnService orderReturnService = (IOrderReturnService) MySpringContextHandler.applicationContext.getBean("orderReturnService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========RefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
                orderReturnService.approvalReject(delegateExecution.getProcessInstanceId(),activityName);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========RefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName);
                orderReturnService.approvalFinish(delegateExecution.getProcessInstanceId(),activityName);
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
