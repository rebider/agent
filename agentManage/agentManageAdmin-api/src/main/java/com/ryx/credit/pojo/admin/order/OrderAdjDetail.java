package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderAdjDetail implements Serializable {
    private String id;

    private String adjId;

    private String subOrderId;

    private BigDecimal adjNum;

    private BigDecimal difAmount;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdjId() {
        return adjId;
    }

    public void setAdjId(String adjId) {
        this.adjId = adjId == null ? null : adjId.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public BigDecimal getAdjNum() {
        return adjNum;
    }

    public void setAdjNum(BigDecimal adjNum) {
        this.adjNum = adjNum;
    }

    public BigDecimal getDifAmount() {
        return difAmount;
    }

    public void setDifAmount(BigDecimal difAmount) {
        this.difAmount = difAmount;
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
}