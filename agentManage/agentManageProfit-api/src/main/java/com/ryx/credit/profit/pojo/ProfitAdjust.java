package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ProfitAdjust {
    private String id;

    private String adjustType;

    private BigDecimal adjustAmt;

    private String adjustUser;

    private String adjustInfo;

    private String adjustDate;

    private String agentId;

    private String agentPid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType == null ? null : adjustType.trim();
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public String getAdjustUser() {
        return adjustUser;
    }

    public void setAdjustUser(String adjustUser) {
        this.adjustUser = adjustUser == null ? null : adjustUser.trim();
    }

    public String getAdjustInfo() {
        return adjustInfo;
    }

    public void setAdjustInfo(String adjustInfo) {
        this.adjustInfo = adjustInfo == null ? null : adjustInfo.trim();
    }

    public String getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(String adjustDate) {
        this.adjustDate = adjustDate == null ? null : adjustDate.trim();
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
}