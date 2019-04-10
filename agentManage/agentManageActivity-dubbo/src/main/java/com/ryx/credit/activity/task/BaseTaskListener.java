package com.ryx.credit.activity.task;

import com.ryx.credit.service.NotifyEmailService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;

import java.util.Iterator;
import java.util.Set;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/4/10 17:19
 * @Param
 * @return
 **/
public class BaseTaskListener {

    /**
     * 邮件通知
     * @param delegateTask
     */
    void notity(DelegateTask delegateTask){
        NotifyEmailService notifyEmailService = (NotifyEmailService) MySpringContextHandler.applicationContext.getBean("notifyEmailService");
        Set<IdentityLink> candidates = delegateTask.getCandidates();
        Iterator<IdentityLink> iterator = candidates.iterator();
        String groupId = "";
        while (iterator.hasNext()) {
            groupId = iterator.next().getGroupId();
        }
        String executionId = delegateTask.getExecutionId();
        String eventName = delegateTask.getEventName();
        notifyEmailService.notifyEmail(groupId,executionId,eventName);
    }
}
