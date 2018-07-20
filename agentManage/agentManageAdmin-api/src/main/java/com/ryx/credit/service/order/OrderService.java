package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
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
    public AgentResult buildOrder(OrderFormVo orderFormVo,String userId);


    /**
     * 付款单下单处理
     * @param oPayment
     * @return
     */
    public AgentResult paymentPlan(OPayment oPayment);



}
