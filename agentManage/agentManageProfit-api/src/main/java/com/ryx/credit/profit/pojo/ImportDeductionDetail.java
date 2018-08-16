package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportDeductionDetail implements Serializable {

    private static final long serialVersionUID = 1694511729121734891L;
    private String id;

    private BigDecimal mustAmt;

    private String sourceId;

    private String deductionDate;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getMustAmt() {
        return mustAmt;
    }

    public void setMustAmt(BigDecimal mustAmt) {
        this.mustAmt = mustAmt;
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