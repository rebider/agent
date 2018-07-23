package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ProfitDeduction {
    private String id;

    private String parentAgentPid;

    private String parentAgentId;

    private String parentAgentName;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String deductionDate;

    private String deductionType;

    private String deductionDesc;

    private BigDecimal sumDeductionAmt;

    private BigDecimal addDeductionAmt;

    private BigDecimal mustDeductionAmt;

    private BigDecimal actualDeductionAmt;

    private BigDecimal notDeductionAmt;

    private String remark;

    private String sourceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentAgentPid() {
        return parentAgentPid;
    }

    public void setParentAgentPid(String parentAgentPid) {
        this.parentAgentPid = parentAgentPid == null ? null : parentAgentPid.trim();
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

    public String getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(String deductionDate) {
        this.deductionDate = deductionDate == null ? null : deductionDate.trim();
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType == null ? null : deductionType.trim();
    }

    public String getDeductionDesc() {
        return deductionDesc;
    }

    public void setDeductionDesc(String deductionDesc) {
        this.deductionDesc = deductionDesc == null ? null : deductionDesc.trim();
    }

    public BigDecimal getSumDeductionAmt() {
        return sumDeductionAmt;
    }

    public void setSumDeductionAmt(BigDecimal sumDeductionAmt) {
        this.sumDeductionAmt = sumDeductionAmt;
    }

    public BigDecimal getAddDeductionAmt() {
        return addDeductionAmt;
    }

    public void setAddDeductionAmt(BigDecimal addDeductionAmt) {
        this.addDeductionAmt = addDeductionAmt;
    }

    public BigDecimal getMustDeductionAmt() {
        return mustDeductionAmt;
    }

    public void setMustDeductionAmt(BigDecimal mustDeductionAmt) {
        this.mustDeductionAmt = mustDeductionAmt;
    }

    public BigDecimal getActualDeductionAmt() {
        return actualDeductionAmt;
    }

    public void setActualDeductionAmt(BigDecimal actualDeductionAmt) {
        this.actualDeductionAmt = actualDeductionAmt;
    }

    public BigDecimal getNotDeductionAmt() {
        return notDeductionAmt;
    }

    public void setNotDeductionAmt(BigDecimal notDeductionAmt) {
        this.notDeductionAmt = notDeductionAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }
}