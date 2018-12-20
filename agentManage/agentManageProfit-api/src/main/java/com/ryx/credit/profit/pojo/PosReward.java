package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *特殊奖励申请明细
 */
public class PosReward implements Serializable {
    private static final long serialVersionUID = -1379167402707558577L;
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String totalConsMonth;

    private BigDecimal growAmt;

    private String creditConsMonth;

    private BigDecimal rewardScale;

    private String applyStatus;

    private String userId;

    private String totalEndMonth;

    private Integer machineryNum;

    private String createTm;

    public String getTotalEndMonth() {
        return totalEndMonth;
    }

    public void setTotalEndMonth(String totalEndMonth) {
        this.totalEndMonth = totalEndMonth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

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

    public Integer getMachineryNum() {
        return machineryNum;
    }

    public void setMachineryNum(Integer machineryNum) {
        this.machineryNum = machineryNum;
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm;
    }
}