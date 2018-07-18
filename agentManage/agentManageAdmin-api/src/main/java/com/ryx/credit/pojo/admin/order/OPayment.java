package com.ryx.credit.pojo.admin.order;

import java.math.BigDecimal;
import java.util.Date;

public class OPayment {
    private String id;

    private String userId;

    private String orderId;

    private String agentId;

    private String payMethod;

    private Date cTime;

    private BigDecimal payAmount;

    private BigDecimal realAmount;

    private Date payCompletTime;

    private BigDecimal payStatus;

    private Date planSucTime;

    private String payRule;

    private String guaranteeAgent;

    private BigDecimal settlementPrice;

    private String shareTemplet;

    private BigDecimal isCloInvoice;

    private String deductionType;

    private BigDecimal deductionAmount;

    private BigDecimal downPayment;

    private BigDecimal downPaymentCount;

    private Date downPaymentDate;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public Date getPayCompletTime() {
        return payCompletTime;
    }

    public void setPayCompletTime(Date payCompletTime) {
        this.payCompletTime = payCompletTime;
    }

    public BigDecimal getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(BigDecimal payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPlanSucTime() {
        return planSucTime;
    }

    public void setPlanSucTime(Date planSucTime) {
        this.planSucTime = planSucTime;
    }

    public String getPayRule() {
        return payRule;
    }

    public void setPayRule(String payRule) {
        this.payRule = payRule == null ? null : payRule.trim();
    }

    public String getGuaranteeAgent() {
        return guaranteeAgent;
    }

    public void setGuaranteeAgent(String guaranteeAgent) {
        this.guaranteeAgent = guaranteeAgent == null ? null : guaranteeAgent.trim();
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getShareTemplet() {
        return shareTemplet;
    }

    public void setShareTemplet(String shareTemplet) {
        this.shareTemplet = shareTemplet == null ? null : shareTemplet.trim();
    }

    public BigDecimal getIsCloInvoice() {
        return isCloInvoice;
    }

    public void setIsCloInvoice(BigDecimal isCloInvoice) {
        this.isCloInvoice = isCloInvoice;
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType == null ? null : deductionType.trim();
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getDownPaymentCount() {
        return downPaymentCount;
    }

    public void setDownPaymentCount(BigDecimal downPaymentCount) {
        this.downPaymentCount = downPaymentCount;
    }

    public Date getDownPaymentDate() {
        return downPaymentDate;
    }

    public void setDownPaymentDate(Date downPaymentDate) {
        this.downPaymentDate = downPaymentDate;
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