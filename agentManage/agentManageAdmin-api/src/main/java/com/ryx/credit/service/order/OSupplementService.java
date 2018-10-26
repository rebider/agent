package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;

/**
 * 订单补款
 */
public interface OSupplementService {
    /**
     * 查询补款列表
     * @param page
     * @param oSupplement
     * @param time
     * @return
     */
    PageInfo selectAll(Page page, OSupplement oSupplement, String time,String userId);

    /**
     * 查询补款详情
     * @param id
     * @return
     */
    public OSupplement selectById(String id);

    /**
     * 添加补款
     */
    public ResultVO supplementSave(OsupplementVo osupplementVo) throws MessageException, Exception;

    /**
     * 提交审批
     */
    public ResultVO startSuppActivity(String id,String userId)throws ProcessException, Exception;

    /**
     * 查询基本信息
     */
    public OSupplement informationQuery(String id);

    /**
     * 详情数据
     */
    public OPaymentDetail selectByOPaymentId(String id);

    /**
     * 处理任务
     */
    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;


    /**
     * 修改业务流程状态
     */
    public ResultVO updateByActivId(String id,String activityName) throws MessageException;

    /**
     * 查询是否为待付款状态
     */

    public  ResultVO selectBySrcId(OsupplementVo osupplementVo);

    /**
     * 更新实际付款金额
     */
    public ResultVO updateAmount(AgentVo agentVo,Long userId) throws MessageException;
}
