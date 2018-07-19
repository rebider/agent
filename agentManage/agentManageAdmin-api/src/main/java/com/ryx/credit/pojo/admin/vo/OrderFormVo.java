package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OSubOrder;

import java.util.List;

/**
 * Created by RYX on 2018/7/18.
 * 订单表单表
 */
public class OrderFormVo extends OOrder{

    private OPayment oPayment;

    private List<OSubOrder> oSubOrder;

    private List<OReceiptOrderVo> oReceiptOrderList;

    private List<Attachment> attachments;

    public OPayment getoPayment() {
        return oPayment;
    }

    public void setoPayment(OPayment oPayment) {
        this.oPayment = oPayment;
    }

    public List<OSubOrder> getoSubOrder() {
        return oSubOrder;
    }

    public void setoSubOrder(List<OSubOrder> oSubOrder) {
        this.oSubOrder = oSubOrder;
    }

    public List<OReceiptOrderVo> getoReceiptOrderList() {
        return oReceiptOrderList;
    }

    public void setoReceiptOrderList(List<OReceiptOrderVo> oReceiptOrderList) {
        this.oReceiptOrderList = oReceiptOrderList;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
