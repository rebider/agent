package com.ryx.credit.activity.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OAccountAdjustDetail {
    private String id;

    private String adjustId;

    private String adjustType;

    private String srcId;

    private BigDecimal takeOutAmount;

    private String orderId;

    private String payType;

    private String paymentDetailId;

    private String batchCodeOld;

    private String batchCodeNew;

    private String agentId;

    private String cUser;

    private Date cDate;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(String adjustId) {
        this.adjustId = adjustId == null ? null : adjustId.trim();
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType == null ? null : adjustType.trim();
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public BigDecimal getTakeOutAmount() {
        return takeOutAmount;
    }

    public void setTakeOutAmount(BigDecimal takeOutAmount) {
        this.takeOutAmount = takeOutAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(String paymentDetailId) {
        this.paymentDetailId = paymentDetailId == null ? null : paymentDetailId.trim();
    }

    public String getBatchCodeOld() {
        return batchCodeOld;
    }

    public void setBatchCodeOld(String batchCodeOld) {
        this.batchCodeOld = batchCodeOld == null ? null : batchCodeOld.trim();
    }

    public String getBatchCodeNew() {
        return batchCodeNew;
    }

    public void setBatchCodeNew(String batchCodeNew) {
        this.batchCodeNew = batchCodeNew == null ? null : batchCodeNew.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}