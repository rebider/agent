package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ORefundPriceDiffDetail implements Serializable {
    private String id;

    private String subOrderId;

    private String activityFrontId;

    private String activityRealId;

    private String refundPriceDiffId;

    private String orderId;

    private Date sTime;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal changeCount;

    private String beginSn;

    private String endSn;

    private String agentId;

    private String vender;

    private String proModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public String getActivityFrontId() {
        return activityFrontId;
    }

    public void setActivityFrontId(String activityFrontId) {
        this.activityFrontId = activityFrontId == null ? null : activityFrontId.trim();
    }

    public String getActivityRealId() {
        return activityRealId;
    }

    public void setActivityRealId(String activityRealId) {
        this.activityRealId = activityRealId == null ? null : activityRealId.trim();
    }

    public String getRefundPriceDiffId() {
        return refundPriceDiffId;
    }

    public void setRefundPriceDiffId(String refundPriceDiffId) {
        this.refundPriceDiffId = refundPriceDiffId == null ? null : refundPriceDiffId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
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

    public BigDecimal getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(BigDecimal changeCount) {
        this.changeCount = changeCount;
    }

    public String getBeginSn() {
        return beginSn;
    }

    public void setBeginSn(String beginSn) {
        this.beginSn = beginSn == null ? null : beginSn.trim();
    }

    public String getEndSn() {
        return endSn;
    }

    public void setEndSn(String endSn) {
        this.endSn = endSn == null ? null : endSn.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender == null ? null : vender.trim();
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
    }
}