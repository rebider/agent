package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PProfitFactor {
    private String id;

    private String agentId;

    private String agentPid;

    private String agentName;

    private String parentAgentId;

    private String parentAgentName;

    private String factorMonth;

    private BigDecimal tatolAmt;

    private BigDecimal buckleAmt;

    private BigDecimal surplusAmt;

    private BigDecimal sumDeductionAmt;

    private BigDecimal addDeductionAmt;

    private BigDecimal mustDeductionAmt;

    private BigDecimal actualDeductionAmt;

    private BigDecimal notDeductionAmt;

    private BigDecimal upperNotDeductionAmt;

    private String deductionStatus;

    private String remark;

    private Date updateDate;

    private Date factorDate;

    private String stagingStatus;

    private String deductionDesc;

    private String nextId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid == null ? null : agentPid.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
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

    public String getFactorMonth() {
        return factorMonth;
    }

    public void setFactorMonth(String factorMonth) {
        this.factorMonth = factorMonth == null ? null : factorMonth.trim();
    }

    public BigDecimal getTatolAmt() {
        return tatolAmt;
    }

    public void setTatolAmt(BigDecimal tatolAmt) {
        this.tatolAmt = tatolAmt;
    }

    public BigDecimal getBuckleAmt() {
        return buckleAmt;
    }

    public void setBuckleAmt(BigDecimal buckleAmt) {
        this.buckleAmt = buckleAmt;
    }

    public BigDecimal getSurplusAmt() {
        return surplusAmt;
    }

    public void setSurplusAmt(BigDecimal surplusAmt) {
        this.surplusAmt = surplusAmt;
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

    public BigDecimal getUpperNotDeductionAmt() {
        return upperNotDeductionAmt;
    }

    public void setUpperNotDeductionAmt(BigDecimal upperNotDeductionAmt) {
        this.upperNotDeductionAmt = upperNotDeductionAmt;
    }

    public String getDeductionStatus() {
        return deductionStatus;
    }

    public void setDeductionStatus(String deductionStatus) {
        this.deductionStatus = deductionStatus == null ? null : deductionStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getFactorDate() {
        return factorDate;
    }

    public void setFactorDate(Date factorDate) {
        this.factorDate = factorDate;
    }

    public String getStagingStatus() {
        return stagingStatus;
    }

    public void setStagingStatus(String stagingStatus) {
        this.stagingStatus = stagingStatus == null ? null : stagingStatus.trim();
    }

    public String getDeductionDesc() {
        return deductionDesc;
    }

    public void setDeductionDesc(String deductionDesc) {
        this.deductionDesc = deductionDesc == null ? null : deductionDesc.trim();
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId == null ? null : nextId.trim();
    }
}