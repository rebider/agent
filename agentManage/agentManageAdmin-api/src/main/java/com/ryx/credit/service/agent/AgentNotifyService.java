package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;


/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 11:36
 */
public interface AgentNotifyService {

    void asynNotifyPlatform(String busId);


    /**
     * 手动调用 升级并联动开户信息修改
     * @param id
     * @param userid
     * @throws Exception
     */
    void userNotifyPlatformLevelAndUpdateAsynById(String id, String userid)throws Exception;

    /**
     * 根据业务编号进行开户信息修改
     * @param busId
     * @param userId
     * @throws Exception
     */
    void notifyPlatformUpadteByBusId(String busId, String userId)throws Exception;

    /**
     * 升级入网并修改
     * @param busId
     * @param impId
     * @throws Exception
     */
    void notifyPlatformNewLevelAndUpdate(String busId, String impId)throws Exception;

    /**
     * 入网修改
     * @param busId
     * @param impId
     * @throws Exception
     */
    void notifyPlatform(String busId,String impId)throws Exception;

    PageInfo queryList(Page page,AgentPlatFormSyn agentPlatFormSyn);

//    AgentResult httpRequestForPos(AgentNotifyVo agentNotifyVo)throws Exception;

    AgentPlatFormSyn findByBusId(String busId);

    void asynNotifyPlatform();

}
