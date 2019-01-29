package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentQuitRefund;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.math.BigDecimal;

/**
 * 代理商退出--申请退款
 * Created by RYX on 2019/1/28.
 */
public interface AgentQuitRefundService {

    /**
     * 申请退款列表
     * @param agentQuitRefund
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    PageInfo queryQuitRefundList(AgentQuitRefund agentQuitRefund, Page page, String dataRole, Long userId);

    /**
     * 申请退款
     * @param agentQuitRefund
     * @param cUser
     * @return
     * @throws Exception
     */
    AgentResult startQuitRefundActivity(AgentQuitRefund agentQuitRefund, String cUser) throws Exception;

    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    AgentResult approvalQuitRefundTask(AgentVo agentVo, String userId, String busId) throws Exception;

    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    AgentResult compressQuitRefundActivity(String proIns, BigDecimal agStatus) throws Exception;

    /**
     * 根据ID查询合并数据
     * @param refundId
     * @return
     */
    AgentQuitRefund queryQuitRefundById(String refundId);
}
