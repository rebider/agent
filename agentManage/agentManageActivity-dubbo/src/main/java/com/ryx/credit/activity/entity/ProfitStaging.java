package com.ryx.credit.activity.entity;

import java.math.BigDecimal;

public class ProfitStaging {
    private String id;

    private String stagType;

    private Short stagCount;

    private BigDecimal sumAmt;

    private BigDecimal stagAmt;

    private String status;

    private String sourceId;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStagType() {
        return stagType;
    }

    public void setStagType(String stagType) {
        this.stagType = stagType == null ? null : stagType.trim();
    }

    public Short getStagCount() {
        return stagCount;
    }

    public void setStagCount(Short stagCount) {
        this.stagCount = stagCount;
    }

    public BigDecimal getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(BigDecimal sumAmt) {
        this.sumAmt = sumAmt;
    }

    public BigDecimal getStagAmt() {
        return stagAmt;
    }

    public void setStagAmt(BigDecimal stagAmt) {
        this.stagAmt = stagAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}