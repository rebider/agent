package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuckleRun implements Serializable{
    private String id;

    private String bearAgentId;

    private String bearAgentPid;

    private String agentId;

    private String runLevel;

    private BigDecimal runAmt;

    private BigDecimal supplyAmt;

    private String runDate;

    private String runStatus;

    private String createTime;

    private String supplyDate;

    private Short supplyTimes;

    private String souceId;

    private BigDecimal runType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBearAgentId() {
        return bearAgentId;
    }

    public void setBearAgentId(String bearAgentId) {
        this.bearAgentId = bearAgentId == null ? null : bearAgentId.trim();
    }

    public String getBearAgentPid() {
        return bearAgentPid;
    }

    public void setBearAgentPid(String bearAgentPid) {
        this.bearAgentPid = bearAgentPid == null ? null : bearAgentPid.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getRunLevel() {
        return runLevel;
    }

    public void setRunLevel(String runLevel) {
        this.runLevel = runLevel == null ? null : runLevel.trim();
    }

    public BigDecimal getRunAmt() {
        return runAmt;
    }

    public void setRunAmt(BigDecimal runAmt) {
        this.runAmt = runAmt;
    }

    public BigDecimal getSupplyAmt() {
        return supplyAmt;
    }

    public void setSupplyAmt(BigDecimal supplyAmt) {
        this.supplyAmt = supplyAmt;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate == null ? null : runDate.trim();
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus == null ? null : runStatus.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate == null ? null : supplyDate.trim();
    }

    public Short getSupplyTimes() {
        return supplyTimes;
    }

    public void setSupplyTimes(Short supplyTimes) {
        this.supplyTimes = supplyTimes;
    }

    public String getSouceId() {
        return souceId;
    }

    public void setSouceId(String souceId) {
        this.souceId = souceId == null ? null : souceId.trim();
    }

    public BigDecimal getRunType() {
        return runType;
    }

    public void setRunType(BigDecimal runType) {
        this.runType = runType;
    }
}