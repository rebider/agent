package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderAdjDetail implements Serializable {
    private String id;

    private String adjId;

    private String subOrderId;

    private BigDecimal orgProNum;

    private BigDecimal adjNum;

    private BigDecimal proNum;

    private BigDecimal difAmount;

    private Date cTm;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal adjustCount;

    public BigDecimal getAdjustCount() {
        return adjustCount;
    }

    public void setAdjustCount(BigDecimal adjustCount) {
        this.adjustCount = adjustCount;
    }
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

    public BigDecimal getOrgProNum() {
        return orgProNum;
    }

    public void setOrgProNum(BigDecimal orgProNum) {
        this.orgProNum = orgProNum;
    }

    public BigDecimal getAdjNum() {
        return adjNum;
    }

    public void setAdjNum(BigDecimal adjNum) {
        this.adjNum = adjNum;
    }

    public BigDecimal getProNum() {
        return proNum;
    }

    public void setProNum(BigDecimal proNum) {
        this.proNum = proNum;
    }

    public BigDecimal getDifAmount() {
        return difAmount;
    }

    public void setDifAmount(BigDecimal difAmount) {
        this.difAmount = difAmount;
    }

    public Date getcTm() {
        return cTm;
    }

    public void setcTm(Date cTm) {
        this.cTm = cTm;
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