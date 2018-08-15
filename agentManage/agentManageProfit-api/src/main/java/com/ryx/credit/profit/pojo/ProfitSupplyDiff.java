package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ProfitSupplyDiff {
    private String id;

    private String parentAgentpid;

    private String parentAgentid;

    private String parentAgentname;

    private String agentId;

    private String agentPid;

    private String agentName;

    private BigDecimal diffAmt;

    private String diffDate;

    private String diffType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentAgentpid() {
        return parentAgentpid;
    }

    public void setParentAgentpid(String parentAgentpid) {
        this.parentAgentpid = parentAgentpid == null ? null : parentAgentpid.trim();
    }

    public String getParentAgentid() {
        return parentAgentid;
    }

    public void setParentAgentid(String parentAgentid) {
        this.parentAgentid = parentAgentid == null ? null : parentAgentid.trim();
    }

    public String getParentAgentname() {
        return parentAgentname;
    }

    public void setParentAgentname(String parentAgentname) {
        this.parentAgentname = parentAgentname == null ? null : parentAgentname.trim();
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

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public String getDiffDate() {
        return diffDate;
    }

    public void setDiffDate(String diffDate) {
        this.diffDate = diffDate == null ? null : diffDate.trim();
    }

    public String getDiffType() {
        return diffType;
    }

    public void setDiffType(String diffType) {
        this.diffType = diffType == null ? null : diffType.trim();
    }
}