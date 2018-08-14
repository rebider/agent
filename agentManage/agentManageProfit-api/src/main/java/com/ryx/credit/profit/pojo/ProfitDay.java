package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitDay implements Serializable {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String transDate;

    private String remitDate;

    private BigDecimal totalProfit;

    private BigDecimal frozenMoney;

    private BigDecimal successMoney;

    private BigDecimal failMoney;

    private BigDecimal realMoney;

    private BigDecimal redoMoney;

    private BigDecimal returnMoney;

    private BigDecimal totalProfit1;//新增

    public BigDecimal getTotalProfit1() {
        return totalProfit1;
    }

    public void setTotalProfit1(BigDecimal totalProfit1) {
        this.totalProfit1 = totalProfit1;
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

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public BigDecimal getSuccessMoney() {
        return successMoney;
    }

    public void setSuccessMoney(BigDecimal successMoney) {
        this.successMoney = successMoney;
    }

    public BigDecimal getFailMoney() {
        return failMoney;
    }

    public void setFailMoney(BigDecimal failMoney) {
        this.failMoney = failMoney;
    }

    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    public BigDecimal getRedoMoney() {
        return redoMoney;
    }

    public void setRedoMoney(BigDecimal redoMoney) {
        this.redoMoney = redoMoney;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }
}