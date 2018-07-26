package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitStagingDetail implements Serializable {
    private static final long serialVersionUID = -1490060185777401444L;
    private String id;

    private String stagId;

    private Integer currentStagCount;

    private BigDecimal mustAmt;

    private BigDecimal realAmt;

    private String status;

    private String sourceId;

    private String deductionDate;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStagId() {
        return stagId;
    }

    public void setStagId(String stagId) {
        this.stagId = stagId == null ? null : stagId.trim();
    }

    public Integer getCurrentStagCount() {
        return currentStagCount;
    }

    public void setCurrentStagCount(Integer currentStagCount) {
        this.currentStagCount = currentStagCount;
    }

    public BigDecimal getMustAmt() {
        return mustAmt;
    }

    public void setMustAmt(BigDecimal mustAmt) {
        this.mustAmt = mustAmt;
    }

    public BigDecimal getRealAmt() {
        return realAmt;
    }

    public void setRealAmt(BigDecimal realAmt) {
        this.realAmt = realAmt;
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

    public String getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(String deductionDate) {
        this.deductionDate = deductionDate == null ? null : deductionDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}