package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.vo.AgentVo;


/**
 * Created by cx on 2018/6/6.
 * 数据修改申请服务类
 */
public interface DataChangeActivityService {

    /**
     * 启动数据变更审批服务
     * @param dataChangeId
     * @return
     */
    ResultVO startDataChangeActivity(String dataChangeId,String userId)throws Exception;

    /**
     * 代理商启动结算卡变更审批服务
     * @param dataChangeId
     * @param userId
     * @return
     * @throws Exception
     */
    ResultVO agentStartDataChangeActivity(String dataChangeId, String userId) throws Exception;

    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    ResultVO compressColInfoDataChangeActivity(String proIns, String agStatus)throws Exception;


    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;
    AgentResult approvalTaskBusi(AgentVo agentVo, String userId) throws Exception;


    int updateByPrimaryKeySelective(DateChangeRequest dateChangeRequest);


    ResultVO deleteDataChange(String dataChangeId, String userId) throws Exception;


    /**
     * 根据代理商结算卡调整业务出款公司
     * @param agentId
     * @return
     * @throws Exception
     */
    ResultVO adjustFinanceOrgByAccount(String agentId)throws Exception;

    void agentUnFreezeResult(String agentId, String UnfreezeCause, String freezeCause, String freezePerson) throws Exception;

}
