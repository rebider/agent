package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PProfitFactor {
    private String id;

    private String agentId;

    private String agentPid;

    private String agentName;

    private String factorMonth;

    private BigDecimal tatolAmt;

    private BigDecimal buckleAmt;

    private BigDecimal surplusAmt;

    private String remark;

    private Date factorDate;
    //新增  20180821
    private String parentAgentId;

    public String getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(String parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getFactorDate() {
        return factorDate;
    }

    public void setFactorDate(Date factorDate) {
        this.factorDate = factorDate;
    }
}