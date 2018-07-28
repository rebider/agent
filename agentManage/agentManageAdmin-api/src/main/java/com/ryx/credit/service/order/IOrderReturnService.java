package com.ryx.credit.service.order;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OReturnOrder;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:16 2018/7/25
 */
public interface IOrderReturnService {

    PageInfo orderList(OReturnOrder returnOrder, PageInfo page);
}
