package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.pojo.admin.vo.OrderoutVo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
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

    PageInfo allOderList(Map par, Page page);

    public PageInfo agentOderList(Map par, Page page);
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
     * 更新订单
     * @param orderFormVo
     * @param userId
     * @return
     * @throws Exception
     */
    public AgentResult updateOrder(OrderFormVo orderFormVo, String userId) throws Exception;

    /**
     * 根据付款单属相初始化参数
     * @param payment
     * @return
     */
    public OPayment initPayment(OrderFormVo agentVo) throws MessageException;


    public AgentResult checkDownPaymentDate(Date date);


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
    public AgentResult dealOrderDeduction(OPayment payment)throws Exception;
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


    /**
     * 查询订单的补款信息
     * @param orderId
     * @param agentId
     * @return
     */
    public AgentResult queryOrderForOSupplementPaymentdetail(String orderId,String agentId);

    /**
     * 待配货商品
     * @param agentId
     * @param orderId
     * @return
     */
    public List<Map<String,Object>> querySubOrderInfoList(String agentId,String orderId);

    /**
     * 已配货商品
     * @param agentId
     * @param orderId
     * @return
     */
    public List<Map<String,Object>> queryHavePeiHuoProduct(String agentId,String orderId);


    /**
     * 订单商品配货
     * @param oReceiptOrder
     * @param oReceiptPro
     * @return
     * @throws Exception
     */
    public AgentResult subOrderPeiHuo(OReceiptOrder oReceiptOrder, OReceiptPro oReceiptPro,int sendNum)throws Exception;

    /**
     * 修改配货明细
     * @param oReceiptPro
     * @return
     * @throws Exception
     */
    public AgentResult subOrderPeiHuoUpdate(OReceiptPro oReceiptPro)throws Exception;


    /**
     * 更新配货及配货明细为待排单状态
     * @param orderId
     * @return
     * @throws Exception
     */
    public AgentResult subOrderPeiHuoUpdateStatus(String orderId,String agentId)throws Exception;


    /**
     * 同步发货订单数据
     * @param receiptOrder
     * @return
     * @throws Exception
     */
    public AgentResult sysnReceiptOrderPorNum(String receiptOrder)throws Exception;


    AgentResult queryPaymentXXDK(String agentId);


    AgentResult updateProfitTaxAmt(List<OPayment> taxAmtList)throws Exception;

    /**
     * 订单发货的导出
     */
    public List<OrderoutVo> exportOrder(Map map)  ;

    public BigDecimal queryAgentDebt(String agentId);

    /**
     * 刪除订单
     */
    AgentResult updateStatus(String id, String user);


    /**
     * 代理商月度打款金额开票不开票信息统计数据入库
     * @param cashSummaryMouth
     * @return
     */
    AgentResult insertSelectiveCashSummaryMouth(CashSummaryMouth cashSummaryMouth);
}
