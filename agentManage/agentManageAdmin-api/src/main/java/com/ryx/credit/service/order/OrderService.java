package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;

/**
 * Created by RYX on 2018/7/13.
 * 订单服务类
 */
public interface OrderService {

    PageInfo orderList(OOrder product, Page page);


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


    /**
     * 订单审批
     * @param insid
     * @param actname
     * @return
     */
    public AgentResult approveFinish(String insid,String actname)throws Exception;


}
