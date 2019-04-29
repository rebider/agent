package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class FreezeOperationRecord implements Serializable {
    private String id;

    private String agentId;

    private String agentName;

    private String parentAgentId;

    private String parentAgentName;

    private String freezeType;

    private String status;

    private BigDecimal freezeAmt;

    private String operationTime;

    private String thawTime;

    private String freezeReason;

    private String thawReason;

    private String freezeOperator;

    private String thawOperator;

    private String freezeBatch;

    private String thawBatch;

    private String rev1;

    private String rev2;

    private String rev3;

    private String rev4;

    private String rev5;

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

    public String getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(String freezeType) {
        this.freezeType = freezeType == null ? null : freezeType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getFreezeAmt() {
        return freezeAmt;
    }

    public void setFreezeAmt(BigDecimal freezeAmt) {
        this.freezeAmt = freezeAmt;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime == null ? null : operationTime.trim();
    }

    public String getThawTime() {
        return thawTime;
    }

    public void setThawTime(String thawTime) {
        this.thawTime = thawTime == null ? null : thawTime.trim();
    }

    public String getFreezeReason() {
        return freezeReason;
    }

    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason == null ? null : freezeReason.trim();
    }

    public String getThawReason() {
        return thawReason;
    }

    public void setThawReason(String thawReason) {
        this.thawReason = thawReason == null ? null : thawReason.trim();
    }

    public String getFreezeOperator() {
        return freezeOperator;
    }

    public void setFreezeOperator(String freezeOperator) {
        this.freezeOperator = freezeOperator == null ? null : freezeOperator.trim();
    }

    public String getThawOperator() {
        return thawOperator;
    }

    public void setThawOperator(String thawOperator) {
        this.thawOperator = thawOperator == null ? null : thawOperator.trim();
    }

    public String getFreezeBatch() {
        return freezeBatch;
    }

    public void setFreezeBatch(String freezeBatch) {
        this.freezeBatch = freezeBatch == null ? null : freezeBatch.trim();
    }

    public String getThawBatch() {
        return thawBatch;
    }

    public void setThawBatch(String thawBatch) {
        this.thawBatch = thawBatch == null ? null : thawBatch.trim();
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }

    public String getRev2() {
        return rev2;
    }

    public void setRev2(String rev2) {
        this.rev2 = rev2 == null ? null : rev2.trim();
    }

    public String getRev3() {
        return rev3;
    }

    public void setRev3(String rev3) {
        this.rev3 = rev3 == null ? null : rev3.trim();
    }

    public String getRev4() {
        return rev4;
    }

    public void setRev4(String rev4) {
        this.rev4 = rev4 == null ? null : rev4.trim();
    }

    public String getRev5() {
        return rev5;
    }

    public void setRev5(String rev5) {
        this.rev5 = rev5 == null ? null : rev5.trim();
    }

    public String toString() {
        return "FreezeOperationRecord{" +
                "id='" + id + '\'' +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", parentAgentId='" + parentAgentId + '\'' +
                ", parentAgentName='" + parentAgentName + '\'' +
                ", freezeType='" + freezeType + '\'' +
                ", status='" + status + '\'' +
                ", freezeAmt=" + freezeAmt +
                ", operationTime='" + operationTime + '\'' +
                ", thawTime='" + thawTime + '\'' +
                ", freezeReason='" + freezeReason + '\'' +
                ", thawReason='" + thawReason + '\'' +
                ", freezeOperator='" + freezeOperator + '\'' +
                ", thawOperator='" + thawOperator + '\'' +
                ", freezeBatch='" + freezeBatch + '\'' +
                ", thawBatch='" + thawBatch + '\'' +
                ", rev1='" + rev1 + '\'' +
                ", rev2='" + rev2 + '\'' +
                ", rev3='" + rev3 + '\'' +
                ", rev4='" + rev4 + '\'' +
                ", rev5='" + rev5 + '\'' +
                '}';
    }
}