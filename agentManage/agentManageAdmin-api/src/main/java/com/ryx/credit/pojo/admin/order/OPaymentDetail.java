package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OPaymentDetail implements Serializable {
    private String id;

    private String paymentId;

    private String orderId;

    private String cOrderId;

    private String payMethod;

    private BigDecimal payAmount;

    private BigDecimal realPayAmount;

    private Date payTime;

    private String srcId;

    private String srcType;

    private Date planPayTime;

    private BigDecimal planNum;

    private String accountUser;

    private String accountpayType;

    private String payAccount;

    private BigDecimal paymentStatus;

    private String deferredRid;

    private String agentId;

    private String collectCompany;

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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getcOrderId() {
        return cOrderId;
    }

    public void setcOrderId(String cOrderId) {
        this.cOrderId = cOrderId == null ? null : cOrderId.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType == null ? null : srcType.trim();
    }

    public Date getPlanPayTime() {
        return planPayTime;
    }

    public void setPlanPayTime(Date planPayTime) {
        this.planPayTime = planPayTime;
    }

    public BigDecimal getPlanNum() {
        return planNum;
    }

    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }

    public String getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser == null ? null : accountUser.trim();
    }

    public String getAccountpayType() {
        return accountpayType;
    }

    public void setAccountpayType(String accountpayType) {
        this.accountpayType = accountpayType == null ? null : accountpayType.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public BigDecimal getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(BigDecimal paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDeferredRid() {
        return deferredRid;
    }

    public void setDeferredRid(String deferredRid) {
        this.deferredRid = deferredRid == null ? null : deferredRid.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getCollectCompany() {
        return collectCompany;
    }

    public void setCollectCompany(String collectCompany) {
        this.collectCompany = collectCompany == null ? null : collectCompany.trim();
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