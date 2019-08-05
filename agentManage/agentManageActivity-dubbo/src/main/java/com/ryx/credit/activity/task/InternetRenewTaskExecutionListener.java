package com.ryx.credit.activity.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.spring.MySpringContextHandler;
import com.ryx.internet.service.OInternetRenewService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CompensationTaskExecutionListener
 * Created by IntelliJ IDEA.
 *  物联网卡续费
 * @author liudh
 * @version 1.0 2019/7/3 11:15
 * @see CompensationTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class InternetRenewTaskExecutionListener extends BaseTaskListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CompensationTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            OInternetRenewService internetRenewService = (OInternetRenewService) MySpringContextHandler.applicationContext.getBean("internetRenewService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                AgentResult res = internetRenewService.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
                logger.info("=========InternetRenewTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                AgentResult res = internetRenewService.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Approved.status);
                logger.info("=========InternetRenewTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());
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
