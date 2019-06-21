package com.ryx.credit.service.agent.netInPort;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 16:17
 * @Param
 * @return
 **/
public interface AgentNetInNotityService {

    PageInfo queryList(Page page, AgentPlatFormSyn agentPlatFormSyn);

    void asynNotifyPlatform(String busId,String notifyType);

    List<String> getParent(String busRegion);

    void netIn(String busId,String notifyType);

    void netInApplyEdit(String busId,String notifyType);

    void upgrade(String busId) throws Exception;
}
