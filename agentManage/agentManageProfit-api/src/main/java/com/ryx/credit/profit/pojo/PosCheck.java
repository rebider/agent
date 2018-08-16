package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosCheck implements Serializable {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String checkDateS;

    private String checkDateE;

    private BigDecimal totalAmt;

    private BigDecimal posOrders;

    private BigDecimal profitScale;

    private String appDate;

    private String checkStatus;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid == null ? null : agentPid.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getCheckDateS() {
        return checkDateS;
    }

    public void setCheckDateS(String checkDateS) {
        this.checkDateS = checkDateS == null ? null : checkDateS.trim();
    }

    public String getCheckDateE() {
        return checkDateE;
    }

    public void setCheckDateE(String checkDateE) {
        this.checkDateE = checkDateE == null ? null : checkDateE.trim();
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getPosOrders() {
        return posOrders;
    }

    public void setPosOrders(BigDecimal posOrders) {
        this.posOrders = posOrders;
    }

    public BigDecimal getProfitScale() {
        return profitScale;
    }

    public void setProfitScale(BigDecimal profitScale) {
        this.profitScale = profitScale;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate == null ? null : appDate.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }
}