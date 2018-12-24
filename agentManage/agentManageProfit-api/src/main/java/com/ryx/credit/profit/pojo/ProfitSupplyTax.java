package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitSupplyTax implements Serializable {
    private String id;

    private String supplyTaxDate;

    private String supplyTaxAgentId;

    private String supplyTaxAgentName;

    private String supplyTaxSubId;

    private String supplyTaxSubName;

    private BigDecimal supplyTaxAmt;

    private String supplyTaxType;

    private String supplyTaxPlatform;

    private String createTime;

    private String updateTime;

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

    public String getSupplyTaxType() {
        return supplyTaxType;
    }

    public void setSupplyTaxType(String supplyTaxType) {
        this.supplyTaxType = supplyTaxType == null ? null : supplyTaxType.trim();
    }

    public String getSupplyTaxPlatform() {
        return supplyTaxPlatform;
    }

    public void setSupplyTaxPlatform(String supplyTaxPlatform) {
        this.supplyTaxPlatform = supplyTaxPlatform == null ? null : supplyTaxPlatform.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}