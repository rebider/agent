package com.ryx.credit.profit.service;

import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;

import java.util.List;

/**
 * 业务平台对接接口类
 *
 * @Author Wang y
 * @Date 2018/10/15
 * @Time: 16:42
 */

public interface BusiPlatService {

    /**
     * 冻结
     * @param agentIds 代理商AG码list
     *                 return true：冻结失败
     */
    boolean mPos_Frozen(List<String> agentIds);

    /**
     * 解冻
     * @param agentIds 代理商AG码list
     *                 return true：解冻失败
     */
    boolean mPos_unFrozen(List<String> agentIds);

    /**
     * 代理商更名(手刷)
     * @param agentName 变更后代理商名称
     * @param platId 业务平台编号
     */
    void mPos_updateAgName(String agentName,List<String> platId);

    /**
     * 代理商更名(POS)
     * @param agentNotifyVo 代理商信息
     *                      uniqueId 代理商AB码
     *                      orgName 变更后名称
     *                      orgType 机构类型
     */
    void pos_updateAgName(AgentNotifyVo agentNotifyVo) throws Exception;

    /**
     * 代理商退出（手刷）
     */
    void mPos_quit();

    /**
     * 代理商退出（POS）
     */
    void pos_quit();
}
