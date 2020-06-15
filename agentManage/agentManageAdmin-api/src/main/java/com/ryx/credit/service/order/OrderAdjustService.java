package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.order.OrderAdj;
import com.ryx.credit.pojo.admin.order.OrderAdjAccount;
import com.ryx.credit.pojo.admin.vo.OrderAdjAccountVo;
import com.ryx.credit.pojo.admin.vo.OrderAdjustVo;
import com.ryx.credit.pojo.admin.vo.OrderUpModelVo;

import java.util.List;
import java.util.Map;

public interface OrderAdjustService {
    /**
     * 查询欠款信息
     * @param orderId
     * @return
     */
    AgentResult refreshPaymentDetail(String orderId);


    AgentResult saveAdjInfo(OrderUpModelVo orderUpModelVo, Map map) throws Exception;

    PageInfo queryAgentUpModelList(Map par, Page page);

    AgentResult loadUpModelInfo(String adjId);

    /**
     * 启动订单调整审批
     * @return
     * @throws Exception
     */
    AgentResult startOrderAdjust(String id, String cuser) throws Exception;

    /**
     * 修改订单调整数据
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    AgentResult updateOrderAdjust(OrderUpModelVo orderUpModelVo, String userId) throws Exception;

    OrderAdj getByAdjIdStatus(String adjId);

    /**
     * 订单调整审批处理
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    AgentResult approvalTaskOrderAdjust(OrderUpModelVo orderUpModelVo, String userId) throws Exception;

    /**
     * 订单调整审批业务处理
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    Map<String, Object> approvalTaskOrderAdjustBusiness(OrderUpModelVo orderUpModelVo, String userId) throws Exception;

    /**
     * 订单调整审批通过
     * @param insid
     * @param actname
     * @return
     */
    AgentResult approveFinishOrderAdjust(String insid, String actname) throws Exception;

    AgentResult approvalTaskSettle(OrderAdj orderAdj) throws ProcessException;

    Map<String,Object> freshRefundAmo(String adjId,String proAmount,String refundAmo,String refundType,String takeAmt);

    Map<String,Object> saveProAmo(String adjId,String proAmount);

    /**
     * 订单数量调整导出
     */
    List<OrderAdjustVo> excelOrderAdjustAll(Map map);

    /**
     * 查看全部调整明细
     * @param par
     * @param page
     * @return
     */
    PageInfo queryUpModelListAll(Map par, Page page);

    /**
     * 执行订单调整计划
     */
    AgentResult adjustDoPayPlan(String adjId,OrderAdj orderAdj)throws Exception;

    AgentResult adjustCheckAmo(OrderUpModelVo orderUpModelVo) throws Exception;

    AgentResult saveAdjAccounts(OrderUpModelVo orderUpModelVo) throws Exception;


    AgentResult updateAdjAccountsByTk(OrderUpModelVo orderUpModelVo,String userId) throws Exception;

    /**
     * 启动订单调整退款出纳审批
     * @return
     * @throws Exception
     */
    AgentResult startOrderAdjustRefund(String id, String cuser) throws Exception;

    /**
     * 订单调整退款审批
     * @param insid
     * @param actname
     * @return
     * @throws Exception
     */
    AgentResult approveFinishOrderAdjustRefund(String insid, String actname) throws Exception;

    /**
     * 订单调整退款审批处理
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    AgentResult approvalTaskOrderAdjustRefund(OrderUpModelVo orderUpModelVo, String userId) throws Exception;

}
