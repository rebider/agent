package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosReward implements Serializable {
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String totalConsMonth;

    private BigDecimal growAmt;

    private String creditConsMonth;

    private BigDecimal rewardScale;

    private String applyStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getTotalConsMonth() {
        return totalConsMonth;
    }

    public void setTotalConsMonth(String totalConsMonth) {
        this.totalConsMonth = totalConsMonth == null ? null : totalConsMonth.trim();
    }

    public BigDecimal getGrowAmt() {
        return growAmt;
    }

    public void setGrowAmt(BigDecimal growAmt) {
        this.growAmt = growAmt;
    }

    public String getCreditConsMonth() {
        return creditConsMonth;
    }

    public void setCreditConsMonth(String creditConsMonth) {
        this.creditConsMonth = creditConsMonth == null ? null : creditConsMonth.trim();
    }

    public BigDecimal getRewardScale() {
        return rewardScale;
    }

    public void setRewardScale(BigDecimal rewardScale) {
        this.rewardScale = rewardScale;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }
}