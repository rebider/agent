package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImsMachineActivity implements Serializable {
    private String activityId;

    private String activityName;

    private String brandCode;

    private String brandName;

    private String activityStartTime;

    private String activityEndTime;

    private String activityType;

    private String activateType;

    private BigDecimal deposit;

    private String checkType;

    private BigDecimal standTime;

    private BigDecimal standAmt;

    private String settlementType;

    private String activeReturnType;

    private String standReturnType;

    private BigDecimal activeReturnDeposit;

    private String activeBackType;

    private String activeReturnOrg;

    private BigDecimal standReturnDeposit;

    private String standBackType;

    private String standReturnOrg;

    private String extraBonusType;

    private BigDecimal bonusNum;

    private BigDecimal daysPerPeriod;

    private BigDecimal transactionLimit;

    private BigDecimal bonusReturnDeposit;

    private String bonusBackType;

    private String bonusReturnOrg;

    private String activityEndType;

    private BigDecimal activityEndLimit;

    private String backWay;

    private String backType;

    private String noStandFineTypeOne;

    private String noStandFineTypeTwo;

    private String noStandCheckType;

    private BigDecimal noStandCheckDays;

    private BigDecimal noStandFineDeposit;

    private String status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime == null ? null : activityStartTime.trim();
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime == null ? null : activityEndTime.trim();
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType == null ? null : activityType.trim();
    }

    public String getActivateType() {
        return activateType;
    }

    public void setActivateType(String activateType) {
        this.activateType = activateType == null ? null : activateType.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    public BigDecimal getStandTime() {
        return standTime;
    }

    public void setStandTime(BigDecimal standTime) {
        this.standTime = standTime;
    }

    public BigDecimal getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(BigDecimal standAmt) {
        this.standAmt = standAmt;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType == null ? null : settlementType.trim();
    }

    public String getActiveReturnType() {
        return activeReturnType;
    }

    public void setActiveReturnType(String activeReturnType) {
        this.activeReturnType = activeReturnType == null ? null : activeReturnType.trim();
    }

    public String getStandReturnType() {
        return standReturnType;
    }

    public void setStandReturnType(String standReturnType) {
        this.standReturnType = standReturnType == null ? null : standReturnType.trim();
    }

    public BigDecimal getActiveReturnDeposit() {
        return activeReturnDeposit;
    }

    public void setActiveReturnDeposit(BigDecimal activeReturnDeposit) {
        this.activeReturnDeposit = activeReturnDeposit;
    }

    public String getActiveBackType() {
        return activeBackType;
    }

    public void setActiveBackType(String activeBackType) {
        this.activeBackType = activeBackType == null ? null : activeBackType.trim();
    }

    public String getActiveReturnOrg() {
        return activeReturnOrg;
    }

    public void setActiveReturnOrg(String activeReturnOrg) {
        this.activeReturnOrg = activeReturnOrg == null ? null : activeReturnOrg.trim();
    }

    public BigDecimal getStandReturnDeposit() {
        return standReturnDeposit;
    }

    public void setStandReturnDeposit(BigDecimal standReturnDeposit) {
        this.standReturnDeposit = standReturnDeposit;
    }

    public String getStandBackType() {
        return standBackType;
    }

    public void setStandBackType(String standBackType) {
        this.standBackType = standBackType == null ? null : standBackType.trim();
    }

    public String getStandReturnOrg() {
        return standReturnOrg;
    }

    public void setStandReturnOrg(String standReturnOrg) {
        this.standReturnOrg = standReturnOrg == null ? null : standReturnOrg.trim();
    }

    public String getExtraBonusType() {
        return extraBonusType;
    }

    public void setExtraBonusType(String extraBonusType) {
        this.extraBonusType = extraBonusType == null ? null : extraBonusType.trim();
    }

    public BigDecimal getBonusNum() {
        return bonusNum;
    }

    public void setBonusNum(BigDecimal bonusNum) {
        this.bonusNum = bonusNum;
    }

    public BigDecimal getDaysPerPeriod() {
        return daysPerPeriod;
    }

    public void setDaysPerPeriod(BigDecimal daysPerPeriod) {
        this.daysPerPeriod = daysPerPeriod;
    }

    public BigDecimal getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(BigDecimal transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public BigDecimal getBonusReturnDeposit() {
        return bonusReturnDeposit;
    }

    public void setBonusReturnDeposit(BigDecimal bonusReturnDeposit) {
        this.bonusReturnDeposit = bonusReturnDeposit;
    }

    public String getBonusBackType() {
        return bonusBackType;
    }

    public void setBonusBackType(String bonusBackType) {
        this.bonusBackType = bonusBackType == null ? null : bonusBackType.trim();
    }

    public String getBonusReturnOrg() {
        return bonusReturnOrg;
    }

    public void setBonusReturnOrg(String bonusReturnOrg) {
        this.bonusReturnOrg = bonusReturnOrg == null ? null : bonusReturnOrg.trim();
    }

    public String getActivityEndType() {
        return activityEndType;
    }

    public void setActivityEndType(String activityEndType) {
        this.activityEndType = activityEndType == null ? null : activityEndType.trim();
    }

    public BigDecimal getActivityEndLimit() {
        return activityEndLimit;
    }

    public void setActivityEndLimit(BigDecimal activityEndLimit) {
        this.activityEndLimit = activityEndLimit;
    }

    public String getBackWay() {
        return backWay;
    }

    public void setBackWay(String backWay) {
        this.backWay = backWay == null ? null : backWay.trim();
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType == null ? null : backType.trim();
    }

    public String getNoStandFineTypeOne() {
        return noStandFineTypeOne;
    }

    public void setNoStandFineTypeOne(String noStandFineTypeOne) {
        this.noStandFineTypeOne = noStandFineTypeOne == null ? null : noStandFineTypeOne.trim();
    }

    public String getNoStandFineTypeTwo() {
        return noStandFineTypeTwo;
    }

    public void setNoStandFineTypeTwo(String noStandFineTypeTwo) {
        this.noStandFineTypeTwo = noStandFineTypeTwo == null ? null : noStandFineTypeTwo.trim();
    }

    public String getNoStandCheckType() {
        return noStandCheckType;
    }

    public void setNoStandCheckType(String noStandCheckType) {
        this.noStandCheckType = noStandCheckType == null ? null : noStandCheckType.trim();
    }

    public BigDecimal getNoStandCheckDays() {
        return noStandCheckDays;
    }

    public void setNoStandCheckDays(BigDecimal noStandCheckDays) {
        this.noStandCheckDays = noStandCheckDays;
    }

    public BigDecimal getNoStandFineDeposit() {
        return noStandFineDeposit;
    }

    public void setNoStandFineDeposit(BigDecimal noStandFineDeposit) {
        this.noStandFineDeposit = noStandFineDeposit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}