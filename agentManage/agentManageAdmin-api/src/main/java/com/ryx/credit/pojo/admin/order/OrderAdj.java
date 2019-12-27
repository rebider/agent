package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderAdj implements Serializable {
    private String id;

    private String orderId;

    private String agentId;

    private String adjUserId;

    private BigDecimal orgOAmo;

    private BigDecimal orgIncentiveAmo;

    private BigDecimal orgPayAmo;

    private BigDecimal oAmo;

    private BigDecimal incentiveAmo;

    private BigDecimal payAmo;

    private String reson;

    private BigDecimal refundAmount;

    private BigDecimal refundType;

    private BigDecimal refundStat;

    private String orgPaymentId;

    private String newPaymentId;

    private BigDecimal reviewsStat;

    private Date reviewsDate;

    private Date adjTm;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal orgPlanNum;

    private BigDecimal stagesAmount;

    private Date refundTm;

    private BigDecimal settleAmount;

    private BigDecimal realRefundAmo;

    private BigDecimal refundMethod;

    private BigDecimal proRefundAmount;

    private BigDecimal offsetAmount;

    private BigDecimal difAmount;

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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAdjUserId() {
        return adjUserId;
    }

    public void setAdjUserId(String adjUserId) {
        this.adjUserId = adjUserId == null ? null : adjUserId.trim();
    }

    public BigDecimal getOrgOAmo() {
        return orgOAmo;
    }

    public void setOrgOAmo(BigDecimal orgOAmo) {
        this.orgOAmo = orgOAmo;
    }

    public BigDecimal getOrgIncentiveAmo() {
        return orgIncentiveAmo;
    }

    public void setOrgIncentiveAmo(BigDecimal orgIncentiveAmo) {
        this.orgIncentiveAmo = orgIncentiveAmo;
    }

    public BigDecimal getOrgPayAmo() {
        return orgPayAmo;
    }

    public void setOrgPayAmo(BigDecimal orgPayAmo) {
        this.orgPayAmo = orgPayAmo;
    }

    public BigDecimal getoAmo() {
        return oAmo;
    }

    public void setoAmo(BigDecimal oAmo) {
        this.oAmo = oAmo;
    }

    public BigDecimal getIncentiveAmo() {
        return incentiveAmo;
    }

    public void setIncentiveAmo(BigDecimal incentiveAmo) {
        this.incentiveAmo = incentiveAmo;
    }

    public BigDecimal getPayAmo() {
        return payAmo;
    }

    public void setPayAmo(BigDecimal payAmo) {
        this.payAmo = payAmo;
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

    public Date getAdjTm() {
        return adjTm;
    }

    public void setAdjTm(Date adjTm) {
        this.adjTm = adjTm;
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

    public BigDecimal getOrgPlanNum() {
        return orgPlanNum;
    }

    public void setOrgPlanNum(BigDecimal orgPlanNum) {
        this.orgPlanNum = orgPlanNum;
    }

    public BigDecimal getStagesAmount() {
        return stagesAmount;
    }

    public void setStagesAmount(BigDecimal stagesAmount) {
        this.stagesAmount = stagesAmount;
    }

    public Date getRefundTm() {
        return refundTm;
    }

    public void setRefundTm(Date refundTm) {
        this.refundTm = refundTm;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getRealRefundAmo() {
        return realRefundAmo;
    }

    public void setRealRefundAmo(BigDecimal realRefundAmo) {
        this.realRefundAmo = realRefundAmo;
    }

    public BigDecimal getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(BigDecimal refundMethod) {
        this.refundMethod = refundMethod;
    }

    public BigDecimal getProRefundAmount() {
        return proRefundAmount;
    }

    public void setProRefundAmount(BigDecimal proRefundAmount) {
        this.proRefundAmount = proRefundAmount;
    }

    public BigDecimal getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(BigDecimal offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public BigDecimal getDifAmount() {
        return difAmount;
    }

    public void setDifAmount(BigDecimal difAmount) {
        this.difAmount = difAmount;
    }
}