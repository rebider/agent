package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PCityApplicationDetail  implements Serializable {
    private static final long serialVersionUID = 2347411248960514038L;

    private String id;

    private String applicationType;

    private String agentId;

    private String agentName;

    private String parentAgentId;

    private String parentAgentName;

    private String applicationMonth;

    private BigDecimal applicationAmt;

    private String deductionRemark;

    private String remark;

    private String applicationStatus;

    private String createName;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType == null ? null : applicationType.trim();
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

    public String getApplicationMonth() {
        return applicationMonth;
    }

    public void setApplicationMonth(String applicationMonth) {
        this.applicationMonth = applicationMonth == null ? null : applicationMonth.trim();
    }

    public BigDecimal getApplicationAmt() {
        return applicationAmt;
    }

    public void setApplicationAmt(BigDecimal applicationAmt) {
        this.applicationAmt = applicationAmt;
    }

    public String getDeductionRemark() {
        return deductionRemark;
    }

    public void setDeductionRemark(String deductionRemark) {
        this.deductionRemark = deductionRemark == null ? null : deductionRemark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus == null ? null : applicationStatus.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}