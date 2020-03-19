package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OPayDetail;

public class OPayDetailVo extends OPayDetail {
    private  String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
