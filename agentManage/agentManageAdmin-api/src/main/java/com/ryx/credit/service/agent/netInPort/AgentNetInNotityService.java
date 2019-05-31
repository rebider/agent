package com.ryx.credit.service.agent.netInPort;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 16:17
 * @Param
 * @return
 **/
public interface AgentNetInNotityService {

    void asynNotifyPlatform(String busId,String notifyType);

    List<String> getParent(String busRegion);

    void netIn(String busId,String notifyType);

    void netInApplyEdit(String busId,String notifyType);
}
