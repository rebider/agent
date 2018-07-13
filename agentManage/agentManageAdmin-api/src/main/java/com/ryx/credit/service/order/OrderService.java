package com.ryx.credit.service.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OOrder;

/**
 * Created by RYX on 2018/7/13.
 * 订单服务类
 */
public interface OrderService {

    PageInfo orderList(OOrder product, Page page);
}
