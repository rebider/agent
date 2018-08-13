package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class PtaxHistory {
    private String id;

    private String taxMonth;

    private BigDecimal taxAmount;

    private String agentPid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaxMonth() {
        return taxMonth;
    }

    public void setTaxMonth(String taxMonth) {
        this.taxMonth = taxMonth == null ? null : taxMonth.trim();
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid == null ? null : agentPid.trim();
    }
}