package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PTaxAdjust implements Serializable {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private BigDecimal taxOld;

    private BigDecimal taxIng;

    private String validDate;

    private String taxStatus;

    private String userId;

    private String profitMonth;

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth;
    }

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

    public BigDecimal getTaxOld() {
        return taxOld;
    }

    public void setTaxOld(BigDecimal taxOld) {
        this.taxOld = taxOld;
    }

    public BigDecimal getTaxIng() {
        return taxIng;
    }

    public void setTaxIng(BigDecimal taxIng) {
        this.taxIng = taxIng;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate == null ? null : validDate.trim();
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus == null ? null : taxStatus.trim();
    }
}