package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitDirect implements Serializable {
    private String id;

    private String agentName;

    private String agentId;

    private String fristAgentPid;

    //新增
    private String fristAgentId;

    public String getFristAgentId() {
        return fristAgentId;
    }

    public void setFristAgentId(String fristAgentId) {
        this.fristAgentId = fristAgentId;
    }

    private String fristAgentName;

    private String parentAgentId;

    private String parentAgentName;

    private BigDecimal transAmt;

    private BigDecimal transFee;

    private BigDecimal profitAmt;

    private BigDecimal supplyAmt;

    private BigDecimal buckleAmt;

    private BigDecimal shouldProfit;

    private BigDecimal actualProfit;

    private String transMonth;

    private String agentEmail;

    private String accountCode;

    private String accountName;

    private String bankOpen;

    private String bankCode;

    private String bossCode;

    private BigDecimal parentBuckle;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getFristAgentPid() {
        return fristAgentPid;
    }

    public void setFristAgentPid(String fristAgentPid) {
        this.fristAgentPid = fristAgentPid == null ? null : fristAgentPid.trim();
    }

    public String getFristAgentName() {
        return fristAgentName;
    }

    public void setFristAgentName(String fristAgentName) {
        this.fristAgentName = fristAgentName == null ? null : fristAgentName.trim();
    }

    public String getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(String parentAgentId) {
        this.parentAgentId = parentAgentId == null ? null : parentAgentId.trim();
    }

    public String getParentAgentName() {
        return parentAgentName;
    }

    public void setParentAgentName(String parentAgentName) {
        this.parentAgentName = parentAgentName == null ? null : parentAgentName.trim();
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public BigDecimal getSupplyAmt() {
        return supplyAmt;
    }

    public void setSupplyAmt(BigDecimal supplyAmt) {
        this.supplyAmt = supplyAmt;
    }

    public BigDecimal getBuckleAmt() {
        return buckleAmt;
    }

    public void setBuckleAmt(BigDecimal buckleAmt) {
        this.buckleAmt = buckleAmt;
    }

    public BigDecimal getShouldProfit() {
        return shouldProfit;
    }

    public void setShouldProfit(BigDecimal shouldProfit) {
        this.shouldProfit = shouldProfit;
    }

    public BigDecimal getActualProfit() {
        return actualProfit;
    }

    public void setActualProfit(BigDecimal actualProfit) {
        this.actualProfit = actualProfit;
    }

    public String getTransMonth() {
        return transMonth;
    }

    public void setTransMonth(String transMonth) {
        this.transMonth = transMonth == null ? null : transMonth.trim();
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail == null ? null : agentEmail.trim();
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getBankOpen() {
        return bankOpen;
    }

    public void setBankOpen(String bankOpen) {
        this.bankOpen = bankOpen == null ? null : bankOpen.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBossCode() {
        return bossCode;
    }

    public void setBossCode(String bossCode) {
        this.bossCode = bossCode == null ? null : bossCode.trim();
    }

    public BigDecimal getParentBuckle() {
        return parentBuckle;
    }

    public void setParentBuckle(BigDecimal parentBuckle) {
        this.parentBuckle = parentBuckle;
    }
}