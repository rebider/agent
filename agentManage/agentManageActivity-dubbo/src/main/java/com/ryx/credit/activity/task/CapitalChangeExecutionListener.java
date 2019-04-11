package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.agent.CapitalChangeApplyService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 保证金变更
 * CapitalChangeExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 1.0 2018/7/19 9:37
 * @see CapitalChangeExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class CapitalChangeExecutionListener  extends BaseTaskListener  implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CapitalChangeExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            CapitalChangeApplyService capitalChangeApplyService = (CapitalChangeApplyService) MySpringContextHandler.applicationContext.getBean("capitalChangeApplyService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========CapitalChangeExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = capitalChangeApplyService.compressCapitalChangeActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========CapitalChangeExecutionListener 流程{}eventName{}", delegateExecution.getProcessInstanceId(), eventName);
                AgentResult res = capitalChangeApplyService.compressCapitalChangeActivity(delegateExecution.getProcessInstanceId(), AgStatus.Approved.status);
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
