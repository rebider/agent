package com.ryx.credit.activity.entity;

import java.math.BigDecimal;

public class ProfitMonth {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String profitDate;

    private BigDecimal transProfitPos;

    private BigDecimal transSupplyProfitPos;

    private Long posReward;

    private BigDecimal transSupplyProfitMpos;

    private BigDecimal transProfitMpos;

    private BigDecimal profitDeduction;

    private BigDecimal profitSupply;

    private BigDecimal taxDeduction;

    private String status;

    private BigDecimal payProfit;

    private String remark;

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

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate == null ? null : profitDate.trim();
    }

    public BigDecimal getTransProfitPos() {
        return transProfitPos;
    }

    public void setTransProfitPos(BigDecimal transProfitPos) {
        this.transProfitPos = transProfitPos;
    }

    public BigDecimal getTransSupplyProfitPos() {
        return transSupplyProfitPos;
    }

    public void setTransSupplyProfitPos(BigDecimal transSupplyProfitPos) {
        this.transSupplyProfitPos = transSupplyProfitPos;
    }

    public Long getPosReward() {
        return posReward;
    }

    public void setPosReward(Long posReward) {
        this.posReward = posReward;
    }

    public BigDecimal getTransSupplyProfitMpos() {
        return transSupplyProfitMpos;
    }

    public void setTransSupplyProfitMpos(BigDecimal transSupplyProfitMpos) {
        this.transSupplyProfitMpos = transSupplyProfitMpos;
    }

    public BigDecimal getTransProfitMpos() {
        return transProfitMpos;
    }

    public void setTransProfitMpos(BigDecimal transProfitMpos) {
        this.transProfitMpos = transProfitMpos;
    }

    public BigDecimal getProfitDeduction() {
        return profitDeduction;
    }

    public void setProfitDeduction(BigDecimal profitDeduction) {
        this.profitDeduction = profitDeduction;
    }

    public BigDecimal getProfitSupply() {
        return profitSupply;
    }

    public void setProfitSupply(BigDecimal profitSupply) {
        this.profitSupply = profitSupply;
    }

    public BigDecimal getTaxDeduction() {
        return taxDeduction;
    }

    public void setTaxDeduction(BigDecimal taxDeduction) {
        this.taxDeduction = taxDeduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getPayProfit() {
        return payProfit;
    }

    public void setPayProfit(BigDecimal payProfit) {
        this.payProfit = payProfit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}