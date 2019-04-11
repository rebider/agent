package com.ryx.credit.service;

/**
 * Created by RYX on 2019/4/10.
 */
public interface NotifyEmailService {

    void notifyEmail(String groupId,String executionId,String eventName);

}
