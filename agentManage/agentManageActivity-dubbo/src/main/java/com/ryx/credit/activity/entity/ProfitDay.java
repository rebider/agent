package com.ryx.credit.activity.entity;

import java.math.BigDecimal;

public class ProfitDay {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String transDate;

    private String remitDate;

    private BigDecimal rhbProfit;

    private BigDecimal zfProfit;

    private BigDecimal totalProfit;

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

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate == null ? null : transDate.trim();
    }

    public String getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(String remitDate) {
        this.remitDate = remitDate == null ? null : remitDate.trim();
    }

    public BigDecimal getRhbProfit() {
        return rhbProfit;
    }

    public void setRhbProfit(BigDecimal rhbProfit) {
        this.rhbProfit = rhbProfit;
    }

    public BigDecimal getZfProfit() {
        return zfProfit;
    }

    public void setZfProfit(BigDecimal zfProfit) {
        this.zfProfit = zfProfit;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
}