package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosKickBackReward implements Serializable {
    private String id;

    private String busName;

    private String agentName;

    private String agentId;

    private String rewardType;

    private String preteastCycle;

    private String checkMonth;

    private BigDecimal creditCardNewAmt;

    private BigDecimal oneMonthCount;

    private BigDecimal toolsCount;

    private String checkStatus;

    private BigDecimal pretestAmt;

    private BigDecimal shouldAmt;

    private BigDecimal chargeAmt;

    private BigDecimal lastMonthAmt;

    private String rev1;

    private String rev2;

    private BigDecimal pretestKAmt;

    private BigDecimal shouldKAmt;

    private BigDecimal notAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName == null ? null : busName.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType == null ? null : rewardType.trim();
    }

    public String getPreteastCycle() {
        return preteastCycle;
    }

    public void setPreteastCycle(String preteastCycle) {
        this.preteastCycle = preteastCycle == null ? null : preteastCycle.trim();
    }

    public String getCheckMonth() {
        return checkMonth;
    }

    public void setCheckMonth(String checkMonth) {
        this.checkMonth = checkMonth == null ? null : checkMonth.trim();
    }

    public BigDecimal getCreditCardNewAmt() {
        return creditCardNewAmt;
    }

    public void setCreditCardNewAmt(BigDecimal creditCardNewAmt) {
        this.creditCardNewAmt = creditCardNewAmt;
    }

    public BigDecimal getOneMonthCount() {
        return oneMonthCount;
    }

    public void setOneMonthCount(BigDecimal oneMonthCount) {
        this.oneMonthCount = oneMonthCount;
    }

    public BigDecimal getToolsCount() {
        return toolsCount;
    }

    public void setToolsCount(BigDecimal toolsCount) {
        this.toolsCount = toolsCount;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public BigDecimal getPretestAmt() {
        return pretestAmt;
    }

    public void setPretestAmt(BigDecimal pretestAmt) {
        this.pretestAmt = pretestAmt;
    }

    public BigDecimal getShouldAmt() {
        return shouldAmt;
    }

    public void setShouldAmt(BigDecimal shouldAmt) {
        this.shouldAmt = shouldAmt;
    }

    public BigDecimal getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(BigDecimal chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public BigDecimal getLastMonthAmt() {
        return lastMonthAmt;
    }

    public void setLastMonthAmt(BigDecimal lastMonthAmt) {
        this.lastMonthAmt = lastMonthAmt;
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

    public BigDecimal getPretestKAmt() {
        return pretestKAmt;
    }

    public void setPretestKAmt(BigDecimal pretestKAmt) {
        this.pretestKAmt = pretestKAmt;
    }

    public BigDecimal getShouldKAmt() {
        return shouldKAmt;
    }

    public void setShouldKAmt(BigDecimal shouldKAmt) {
        this.shouldKAmt = shouldKAmt;
    }

    public BigDecimal getNotAmt() {
        return notAmt;
    }

    public void setNotAmt(BigDecimal notAmt) {
        this.notAmt = notAmt;
    }
}