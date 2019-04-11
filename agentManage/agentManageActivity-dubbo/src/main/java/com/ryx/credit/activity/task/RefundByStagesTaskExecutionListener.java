package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * RefundByStagesTaskExecutionListener
 * Created by IntelliJ IDEA.
 * 退货分期
 * @author Wang Qi
 * @version 1.0 2018/7/19 9:45
 * @see RefundByStagesTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class RefundByStagesTaskExecutionListener  extends BaseTaskListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(RefundByStagesTaskExecutionListener.class);
    private static final long serialVersionUID = -6029887212511952141L;


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            StagingService stagingServiceImpl = (StagingService)MySpringContextHandler.applicationContext.getBean("stagingServiceImpl");
            String remark = null;
            if (delegateExecution.getVariableInstances() !=null && delegateExecution.getVariableInstances().get("approvalOpinion") !=null){
                remark = delegateExecution.getVariableInstances().get("approvalOpinion").getTextValue();
            }
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                System.out.println();
               logger.info("=========RefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
            }
            //审批同意更新数据库
            else if ("finish_end".equals(activityName)) {
               logger.info("=========RefundTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
            }
            logger.info("根据流程结果完善数据状态");
            stagingServiceImpl.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(),activityName, remark);
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        notity(delegateTask);
    }
}
