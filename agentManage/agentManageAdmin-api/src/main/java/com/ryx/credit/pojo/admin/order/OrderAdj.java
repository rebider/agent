package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderAdj implements Serializable {
    private String id;

    private String orderId;

    private String adjUserId;

    private Date adjTm;

    private BigDecimal curArrAmount;

    private BigDecimal orgStagesAmount;

    private BigDecimal stagesAmount;

    private String reson;

    private BigDecimal refundAmount;

    private BigDecimal refundType;

    private BigDecimal refundStat;

    private Date refundTm;

    private String orgPaymentId;

    private String newPaymentId;

    private BigDecimal reviewsStat;

    private Date reviewsDate;

    private BigDecimal status;

    private BigDecimal version;

    private String agentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getAdjUserId() {
        return adjUserId;
    }

    public void setAdjUserId(String adjUserId) {
        this.adjUserId = adjUserId == null ? null : adjUserId.trim();
    }

    public Date getAdjTm() {
        return adjTm;
    }

    public void setAdjTm(Date adjTm) {
        this.adjTm = adjTm;
    }

    public BigDecimal getCurArrAmount() {
        return curArrAmount;
    }

    public void setCurArrAmount(BigDecimal curArrAmount) {
        this.curArrAmount = curArrAmount;
    }

    public BigDecimal getOrgStagesAmount() {
        return orgStagesAmount;
    }

    public void setOrgStagesAmount(BigDecimal orgStagesAmount) {
        this.orgStagesAmount = orgStagesAmount;
    }

    public BigDecimal getStagesAmount() {
        return stagesAmount;
    }

    public void setStagesAmount(BigDecimal stagesAmount) {
        this.stagesAmount = stagesAmount;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson == null ? null : reson.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundType() {
        return refundType;
    }

    public void setRefundType(BigDecimal refundType) {
        this.refundType = refundType;
    }

    public BigDecimal getRefundStat() {
        return refundStat;
    }

    public void setRefundStat(BigDecimal refundStat) {
        this.refundStat = refundStat;
    }

    public Date getRefundTm() {
        return refundTm;
    }

    public void setRefundTm(Date refundTm) {
        this.refundTm = refundTm;
    }

    public String getOrgPaymentId() {
        return orgPaymentId;
    }

    public void setOrgPaymentId(String orgPaymentId) {
        this.orgPaymentId = orgPaymentId == null ? null : orgPaymentId.trim();
    }

    public String getNewPaymentId() {
        return newPaymentId;
    }

    public void setNewPaymentId(String newPaymentId) {
        this.newPaymentId = newPaymentId == null ? null : newPaymentId.trim();
    }

    public BigDecimal getReviewsStat() {
        return reviewsStat;
    }

    public void setReviewsStat(BigDecimal reviewsStat) {
        this.reviewsStat = reviewsStat;
    }

    public Date getReviewsDate() {
        return reviewsDate;
    }

    public void setReviewsDate(Date reviewsDate) {
        this.reviewsDate = reviewsDate;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }
}