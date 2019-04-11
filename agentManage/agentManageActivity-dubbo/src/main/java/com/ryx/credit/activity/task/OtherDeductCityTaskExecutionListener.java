package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.profit.service.IProfitCityApplicationService;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.AgentMergeService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 省区其他扣款申请
 * OtherDeductCityTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @author chenqiutian
 * @version 1.0 2019/2/12 14：00
 * @see OtherDeductCityTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class OtherDeductCityTaskExecutionListener  extends BaseTaskListener  implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(OtherDeductCityTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();//MySpringContextHandler.applicationContext.getBean("profitCityApplicationService");
            //数据变更服务类
            IProfitCityApplicationService iProfitCityApplicationService = (IProfitCityApplicationService)MySpringContextHandler.applicationContext.getBean("profitCityApplicationService");
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("reject========");
                iProfitCityApplicationService.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(), "2");
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                iProfitCityApplicationService.completeTaskEnterActivity(delegateExecution.getProcessInstanceId(), "1");
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
