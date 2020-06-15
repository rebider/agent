package com.ryx.credit.activity.task;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.agent.FreezeRequestDetailService;
import com.ryx.credit.service.agent.FreezeRequestService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AgentFreezeTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @author ssx
 * @version 1.0 2020-6-15 09:48:47
 * @see AgentFreezeTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class AgentFreezeTaskExecutionListener extends BaseTaskListener implements TaskListener, ExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(AgentFreezeTaskExecutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //代理商冻结申请服务类
            FreezeRequestService freezeRequestDetailService = (FreezeRequestService)MySpringContextHandler.applicationContext.getBean("FreezeRequestService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========AgentFreezeTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = freezeRequestDetailService.agentFreezeFinish(delegateExecution.getProcessInstanceId(), activityName);
                logger.info("=========AgentFreezeTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());

            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========AgentFreezeTaskExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = freezeRequestDetailService.agentFreezeFinish(delegateExecution.getProcessInstanceId(), activityName);
                logger.info("=========AgentFreezeTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());

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
