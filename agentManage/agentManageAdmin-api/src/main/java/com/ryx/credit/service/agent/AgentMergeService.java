package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfo;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2019/1/7.
 */
public interface AgentMergeService {

    /**
     * 合并列表
     * @param agentMerge
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    PageInfo selectAgentMergeList(AgentMerge agentMerge, Page page, String dataRole, Long userId);

    /**
     * 保存数据
     * @param agentMerge
     * @param cUser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    AgentResult saveAgentMerge(AgentMerge agentMerge, String[] busType, String cUser, String saveFlag,List<OCashReceivablesVo> oCashReceivables, String[] agentMergeFiles) throws Exception;

    /**
     * 提交数据并审批
     * @param id
     * @param cUser
     * @param isSave
     * @return
     * @throws Exception
     */
    AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception;

    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    AgentResult approvalAgentMergeTask(AgentVo agentVo, String userId, String busId) throws Exception;

    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus) throws Exception;

    /**
     * 根据ID查询合并数据
     * @param mergeId
     * @return
     */
    AgentMerge queryAgentMerge(String mergeId);

    /**
     * 根据合并ID查询合并业务信息
     * @param mergeId
     * @return
     */
    List<AgentMergeBusInfo> queryAgentMergeBusInfo(String mergeId);

    /**
     * 根据主代理商查询被合并代理商业务
     * @param mainAgentId
     * @return
     */
    List<AgentMergeBusInfo> queryMainAgentMergeBus(String mainAgentId);

    /**
     * 修改数据
     * @param agentMerge
     * @param busType
     * @param cUser
     * @return
     * @throws Exception
     */
    AgentResult editAgentMerge(AgentMerge agentMerge, String[] busType, String cUser,List<OCashReceivablesVo> oCashReceivables, String[] agentMergeFiles)throws Exception;


    AgentResult updateAgentName(String busId,List<AgentMergeBusInfo> agentMergeBusInfos) throws Exception;


    void manualAgentMergeNotify(String busId,String platformCode) throws Exception;

    /**
     * 删除合并业务数据
     * @param mergeId
     * @param cUser
     * @return
     * @throws Exception
     */
    AgentResult deleteAgentMerge(String mergeId, String cUser) throws Exception;

    /**
     * 合并业务明细列表
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    PageInfo selectMergeBusinfoList(AgentMergeBusInfo agentMergeBusInfo, Page page, String dataRole, Long userId);


    BigDecimal getSubAgentDebt(String agentId);


    BigDecimal getSubAgentOweTicket(String agentId);


}

