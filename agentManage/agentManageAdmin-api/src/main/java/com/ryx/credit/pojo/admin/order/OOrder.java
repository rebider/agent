package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OOrder implements Serializable {
    private String id;

    private String oNum;

    private Date oApytime;

    private Date oInuretime;

    private String userId;

    private String paymentMethod;

    private BigDecimal oAmo;

    private BigDecimal incentiveAmo;

    private BigDecimal payAmo;

    private String remark;

    private String ruleid;

    private String agentId;

    private String orderPlatform;

    private BigDecimal reviewStatus;

    private BigDecimal orderStatus;

    private BigDecimal clearStatus;

    private BigDecimal status;

    private Date cTime;

    private String uUser;

    private Date uTime;

    private BigDecimal version;

    private String busId;

    private String oxOrder;

    public String getOxOrder() {
        return oxOrder;
    }

    public void setOxOrder(String oxOrder) {
        this.oxOrder = oxOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getoNum() {
        return oNum;
    }

    public void setoNum(String oNum) {
        this.oNum = oNum == null ? null : oNum.trim();
    }

    public Date getoApytime() {
        return oApytime;
    }

    public void setoApytime(Date oApytime) {
        this.oApytime = oApytime;
    }

    public Date getoInuretime() {
        return oInuretime;
    }

    public void setoInuretime(Date oInuretime) {
        this.oInuretime = oInuretime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRuleid() {
        return ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid == null ? null : ruleid.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getOrderPlatform() {
        return orderPlatform;
    }

    public void setOrderPlatform(String orderPlatform) {
        this.orderPlatform = orderPlatform == null ? null : orderPlatform.trim();
    }

    public BigDecimal getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(BigDecimal reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public BigDecimal getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(BigDecimal orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(BigDecimal clearStatus) {
        this.clearStatus = clearStatus;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }
}