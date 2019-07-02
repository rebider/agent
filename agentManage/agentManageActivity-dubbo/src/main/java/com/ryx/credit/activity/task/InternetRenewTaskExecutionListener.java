package com.ryx.credit.activity.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FinanceTaskExecutionListener
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/21
 * @Time: 17:24
 * @description: FinanceTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class InternetRenewTaskExecutionListener extends BaseTaskListener implements TaskListener,ExecutionListener{

    private static final Logger logger = LoggerFactory.getLogger(InternetRenewTaskExecutionListener.class);



    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
        }else if ("end".equals(eventName)) {

            logger.info("=========FinanceTaskExecutionListener 流程结束 {}",eventName);

            String activityName = delegateExecution.getCurrentActivityName();

            //数据变更服务类
            DataChangeActivityService aes = (DataChangeActivityService)MySpringContextHandler.applicationContext.getBean("dataChangeActivityService");
            //审批拒绝
            if("reject_end".equals(activityName)){
               ResultVO res = aes.compressColInfoDataChangeActivity(delegateExecution.getProcessInstanceId(),AgStatus.Refuse.name());
               logger.info("=========FinanceTaskExecutionListener 流程{}eventName{}res{}",delegateExecution.getProcessInstanceId(),eventName,res.getResInfo());
            }
            //审批同意更新数据库
            if("finish_end".equals(activityName)){
                ResultVO res = aes.compressColInfoDataChangeActivity(delegateExecution.getProcessInstanceId(),AgStatus.Approved.name());
                logger.info("=========FinanceTaskExecutionListener 流程{}eventName{}res{}",delegateExecution.getProcessInstanceId(),eventName,res.getResInfo());
            }
        }else if ("take".equals(eventName)) {
            logger.info("take========="+"ActivityId:"+delegateExecution.getCurrentActivityId()+"  ProcessInstanceId:"+delegateExecution.getProcessInstanceId()+"  Execution:"+delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        notity(delegateTask);
    }
}
