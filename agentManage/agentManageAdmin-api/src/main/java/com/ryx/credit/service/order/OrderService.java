package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/13.
 * 订单服务类
 */
public interface OrderService {

    /**
     * 根据ID查询订单信息
     * @param orderId
     * @return
     */
    public OOrder getById(String orderId);

    /**
     * 获取订单列表
     * @param product
     * @param page
     * @return
     */
    PageInfo orderList(OOrder product, Page page);

    PageInfo orderList(Map par, Page page);


    /**
     * 根据审批状态来查询付款单信息
     * @param agentId
     * @param approveStatus
     * @return
     */
    public List<OPayment> queryApprovePayment(String agentId, BigDecimal approveStatus,List<BigDecimal> orderStatus);


    /**
     * 订单构建
     * @param orderFormVo
     * @return
     */
    public AgentResult buildOrder(OrderFormVo orderFormVo,String userId)throws Exception;


    /**
     * 付款单下单处理
     * @param oPayment
     * @return
     */
    public AgentResult paymentPlan(OPayment oPayment)throws Exception;


    /**
     * 加载订单信息
     * @param id
     * @return
     */
    public AgentResult loadAgentInfo(String id)throws Exception;


    /**
     * 启动订单审批
     * @return
     * @throws Exception
     */
    public AgentResult startOrderActiviy(String id,String cuser)throws Exception;

    /**
     * 订单审批处理
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    public AgentResult approvalTaskBussiData(AgentVo agentVo, String userId) throws Exception;

    /**
     * 订单审批
     * @param insid
     * @param actname
     * @return
     */
    public AgentResult approveFinish(String insid,String actname)throws Exception;

    /**
     * 处理订单抵扣类型扣款并返回明细
     * @param payment 付款单
     * @return
     */
    public AgentResult dealOrderDeduction(OPayment payment);
    /**
     * 查找订单id
     */
    public OPayment selectByOrderId(String orderId);

    /**
     * 导出订单信息
     */
    PageInfo getOrderList(Map<String, Object> param, PageInfo pageInfo);

    /**
     * 查询用户缴款相关信息
     * @param agentId
     * @param type
     * @return
     */
    public AgentResult queryAgentCapital(String agentId,String type);



}
