package com.ryx.credit.pojo.admin.order;

import java.math.BigDecimal;
import java.util.Date;

public class ReceiptPlan {
    private String id;

    private String planNum;

    private String orderId;

    private String receiptId;

    private String userId;

    private String proId;

    private String proType;

    private String proCom;

    private BigDecimal planProNum;

    private BigDecimal sendProNum;

    private Date sendDate;

    private Date realSendDate;

    private String returnOrderDetailId;

    private String remark;

    private String cUser;

    private Date cDate;

    private BigDecimal planOrderStatus;

    private BigDecimal status;

    private BigDecimal version;

    private String model;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum == null ? null : planNum.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom == null ? null : proCom.trim();
    }

    public BigDecimal getPlanProNum() {
        return planProNum;
    }

    public void setPlanProNum(BigDecimal planProNum) {
        this.planProNum = planProNum;
    }

    public BigDecimal getSendProNum() {
        return sendProNum;
    }

    public void setSendProNum(BigDecimal sendProNum) {
        this.sendProNum = sendProNum;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getRealSendDate() {
        return realSendDate;
    }

    public void setRealSendDate(Date realSendDate) {
        this.realSendDate = realSendDate;
    }

    public String getReturnOrderDetailId() {
        return returnOrderDetailId;
    }

    public void setReturnOrderDetailId(String returnOrderDetailId) {
        this.returnOrderDetailId = returnOrderDetailId == null ? null : returnOrderDetailId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public BigDecimal getPlanOrderStatus() {
        return planOrderStatus;
    }

    public void setPlanOrderStatus(BigDecimal planOrderStatus) {
        this.planOrderStatus = planOrderStatus;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }
}