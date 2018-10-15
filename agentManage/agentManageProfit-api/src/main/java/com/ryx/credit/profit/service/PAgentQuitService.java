package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PAgentQuit;

import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/9/27
 * @Desc 代理商退出申请
 */
public interface PAgentQuitService {
    /**
     * 查询列表
     * @param pageInfo
     */
    PageInfo getAgentQuitList(Map<String, Object> param, PageInfo pageInfo);

    PAgentQuit getBusIdByAgentId(String agentId);

//    PAgentQuit getAgUniqNumAndId(String agUniqNum);

    PAgentQuit selectByPrimaryKey(String id);

    List<Map<String,Object>> queryBusPlat(Map<String, Object> param);

    /**
     * 代理商退出申请，进行审批流
     * @param pAgentQuit
     */
    ResultVO applyPAgentQuit(PAgentQuit pAgentQuit,String cuser)throws ProcessException;

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @throws ProcessException
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    public void completeTaskEnterActivity(String insid, String status);



    /**
     * 修改代理商退出申请
     * @param pAgentQuit
     */
    void updateAgentQuit(PAgentQuit pAgentQuit);

    /**
     * 新增代理商退出申请
     * @param pAgentQuit
     */
//    void applyPAgentQuit(PAgentQuit pAgentQuit);

    /**
     * 查询代理商退出申请
     */
    List<PAgentQuit> getAgentQuitList();

}
