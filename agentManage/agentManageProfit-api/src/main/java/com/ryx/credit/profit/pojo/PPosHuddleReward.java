package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PPosHuddleReward implements Serializable {

    private String id;

    private String agentName;

    private String createTm;

    private String applyStatus;

    private String creditConsMonth;

    private String growAmt;

    private String machineryNum;

    private BigDecimal rewardScale;

    private String totalConsMonth;

    private String totalEndMonth;

    private String huddleCode;

    private String rev1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm == null ? null : createTm.trim();
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getCreditConsMonth() {
        return creditConsMonth;
    }

    public void setCreditConsMonth(String creditConsMonth) {
        this.creditConsMonth = creditConsMonth == null ? null : creditConsMonth.trim();
    }

    public String getGrowAmt() {
        return growAmt;
    }

    public void setGrowAmt(String growAmt) {
        this.growAmt = growAmt == null ? null : growAmt.trim();
    }

    public String getMachineryNum() {
        return machineryNum;
    }

    public void setMachineryNum(String machineryNum) {
        this.machineryNum = machineryNum == null ? null : machineryNum.trim();
    }

    public BigDecimal getRewardScale() {
        return rewardScale;
    }

    public void setRewardScale(BigDecimal rewardScale) {
        this.rewardScale = rewardScale;
    }

    public String getTotalConsMonth() {
        return totalConsMonth;
    }

    public void setTotalConsMonth(String totalConsMonth) {
        this.totalConsMonth = totalConsMonth == null ? null : totalConsMonth.trim();
    }

    public String getTotalEndMonth() {
        return totalEndMonth;
    }

    public void setTotalEndMonth(String totalEndMonth) {
        this.totalEndMonth = totalEndMonth == null ? null : totalEndMonth.trim();
    }

    public String getHuddleCode() {
        return huddleCode;
    }

    public void setHuddleCode(String huddleCode) {
        this.huddleCode = huddleCode == null ? null : huddleCode.trim();
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }
}