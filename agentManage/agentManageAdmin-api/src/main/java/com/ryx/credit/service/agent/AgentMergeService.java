package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;

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
    AgentResult saveAgentMerge(AgentMerge agentMerge, String[] busType, String cUser, String saveFlag) throws Exception;

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
     * 根据ID查询数据
     * @param mergeId
     * @return
     */
    AgentMerge queryAgentMerge(String mergeId);

    /**
     * 修改数据
     * @param agentMerge
     * @param busType
     * @param cUser
     * @return
     * @throws Exception
     */
    AgentResult editAgentMerge(AgentMerge agentMerge, String[] busType, String cUser)throws Exception;


    AgentResult updateAgentName(String busId,List<AgentMergeBusInfo> agentMergeBusInfos) throws Exception;
}

