package com.ryx.credit.activity.task;

import com.ryx.credit.profit.service.IAgentPayService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分润模板线上审批
 * @Author cqt
 */

public class ProfitAgentPayExecutionListener extends BaseTaskListener  implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ProfitAgentPayExecutionListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            IAgentPayService agentPayService = (IAgentPayService) MySpringContextHandler.applicationContext.getBean("agentPayService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                agentPayService.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(),"1");
                logger.info("reject========");
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                agentPayService.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(),"0");
                logger.info("finish===审批同意");
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
