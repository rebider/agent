package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.OrderStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OOrderExample;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private IdService idService;

    @Override
    public PageInfo orderList(OOrder product, Page page) {

        OOrderExample example = new OOrderExample();
        OOrderExample.Criteria criteria = example.createCriteria();

        List<OOrder> oOrders = orderMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oOrders);
        pageInfo.setTotal(orderMapper.countByExample(example));
        return pageInfo;
    }

    /**
     * 订单构建
     * @param orderFormVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult buildOrder(OrderFormVo orderFormVo,String userId) {
        orderFormVo.setUserId(userId);
        orderFormVo =  setOrderFormValue(orderFormVo,userId);

        return null;
    }

    private OrderFormVo setOrderFormValue(OrderFormVo orderFormVo,String userId){
        //订单基础数据
         Date d = Calendar.getInstance().getTime();
        orderFormVo.setId(idService.genId(TabId.o_order));
        orderFormVo.setoNum(orderFormVo.getId());
        orderFormVo.setoApytime(orderFormVo.getcTime());
        orderFormVo.setUserId(userId);
        //orderFormVo.setIncentiveAmo(new BigDecimal(0));
        orderFormVo.setPayAmo(orderFormVo.getoAmo());
        orderFormVo.setReviewStatus(AgStatus.Create.status);
        orderFormVo.setOrderStatus(OrderStatus.CREATE.status);
        orderFormVo.setClearStatus(Status.STATUS_0.status);
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(d);
        orderFormVo.setuUser(userId);
        orderFormVo.setuTime(d);
        orderFormVo.setVersion(Status.STATUS_0.status);


        //支付方式
        OPayment oPayment = orderFormVo.getoPayment();
        oPayment.setId(idService.genId(TabId.o_payment));
        oPayment.setUserId(userId);
        return orderFormVo;
    }
}
