package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ProfitSupplyTax {
    private String id;

    private String supplyTaxDate;

    private String supplyTaxAgentId;

    private String supplyTaxAgentName;

    private String supplyTaxSubId;

    private String supplyTaxSubName;

    public String getSupplyTaxType() {
        return supplyTaxType;
    }

    public void setSupplyTaxType(String supplyTaxType) {
        this.supplyTaxType = supplyTaxType;
    }

    private BigDecimal supplyTaxAmt;

    private String supplyTaxType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplyTaxDate() {
        return supplyTaxDate;
    }

    public void setSupplyTaxDate(String supplyTaxDate) {
        this.supplyTaxDate = supplyTaxDate == null ? null : supplyTaxDate.trim();
    }

    public String getSupplyTaxAgentId() {
        return supplyTaxAgentId;
    }

    public void setSupplyTaxAgentId(String supplyTaxAgentId) {
        this.supplyTaxAgentId = supplyTaxAgentId == null ? null : supplyTaxAgentId.trim();
    }

    public String getSupplyTaxAgentName() {
        return supplyTaxAgentName;
    }

    public void setSupplyTaxAgentName(String supplyTaxAgentName) {
        this.supplyTaxAgentName = supplyTaxAgentName == null ? null : supplyTaxAgentName.trim();
    }

    public String getSupplyTaxSubId() {
        return supplyTaxSubId;
    }

    public void setSupplyTaxSubId(String supplyTaxSubId) {
        this.supplyTaxSubId = supplyTaxSubId == null ? null : supplyTaxSubId.trim();
    }

    public String getSupplyTaxSubName() {
        return supplyTaxSubName;
    }

    public void setSupplyTaxSubName(String supplyTaxSubName) {
        this.supplyTaxSubName = supplyTaxSubName == null ? null : supplyTaxSubName.trim();
    }

    public BigDecimal getSupplyTaxAmt() {
        return supplyTaxAmt;
    }

    public void setSupplyTaxAmt(BigDecimal supplyTaxAmt) {
        this.supplyTaxAmt = supplyTaxAmt;
    }
}