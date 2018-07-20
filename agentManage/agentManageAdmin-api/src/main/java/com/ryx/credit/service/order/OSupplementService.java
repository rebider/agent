package com.ryx.credit.service.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OSupplement;

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
    PageInfo selectAll(Page page, OSupplement oSupplement, String time);

    /**
     * 查询补款详情
     * @param id
     * @return
     */
    public OPaymentDetail selectById(String id);
}
