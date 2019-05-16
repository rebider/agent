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

    private String totalEndMonth;

    private BigDecimal machineryNum;

    private String createTm;

    private String busNum;

    private String busPlatform;

    private String appraisalCycle;

    private String rewardType;

    private String iscontainotherpos;

    private String assessWay;

    private String accountingCycle;

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

    public String getTotalEndMonth() {
        return totalEndMonth;
    }

    public void setTotalEndMonth(String totalEndMonth) {
        this.totalEndMonth = totalEndMonth == null ? null : totalEndMonth.trim();
    }

    public BigDecimal getMachineryNum() {
        return machineryNum;
    }

    public void setMachineryNum(BigDecimal machineryNum) {
        this.machineryNum = machineryNum;
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm == null ? null : createTm.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public String getAppraisalCycle() {
        return appraisalCycle;
    }

    public void setAppraisalCycle(String appraisalCycle) {
        this.appraisalCycle = appraisalCycle == null ? null : appraisalCycle.trim();
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType == null ? null : rewardType.trim();
    }

    public String getIscontainotherpos() {
        return iscontainotherpos;
    }

    public void setIscontainotherpos(String iscontainotherpos) {
        this.iscontainotherpos = iscontainotherpos == null ? null : iscontainotherpos.trim();
    }

    public String getAssessWay() {
        return assessWay;
    }

    public void setAssessWay(String assessWay) {
        this.assessWay = assessWay == null ? null : assessWay.trim();
    }

    public String getAccountingCycle() {
        return accountingCycle;
    }

    public void setAccountingCycle(String accountingCycle) {
        this.accountingCycle = accountingCycle == null ? null : accountingCycle.trim();
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
}