package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;

public class AgentBusinfoFreeze {
    private String id;

    private String busId;

    private BigDecimal freezeType;

    private String busNum;

    private String platId;

    private String platType;

    private BigDecimal profitFreezr;

    private BigDecimal reflowFreeze;

    private BigDecimal monthlyFreeze;

    private BigDecimal dailyFreeze;

    private BigDecimal stopProfitFreeze;

    private BigDecimal cashFreeze;

    private BigDecimal stopCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public BigDecimal getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(BigDecimal freezeType) {
        this.freezeType = freezeType;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId == null ? null : platId.trim();
    }

    public String getPlatType() {
        return platType;
    }

    public void setPlatType(String platType) {
        this.platType = platType == null ? null : platType.trim();
    }

    public BigDecimal getProfitFreezr() {
        return profitFreezr;
    }

    public void setProfitFreezr(BigDecimal profitFreezr) {
        this.profitFreezr = profitFreezr;
    }

    public BigDecimal getReflowFreeze() {
        return reflowFreeze;
    }

    public void setReflowFreeze(BigDecimal reflowFreeze) {
        this.reflowFreeze = reflowFreeze;
    }

    public BigDecimal getMonthlyFreeze() {
        return monthlyFreeze;
    }

    public void setMonthlyFreeze(BigDecimal monthlyFreeze) {
        this.monthlyFreeze = monthlyFreeze;
    }

    public BigDecimal getDailyFreeze() {
        return dailyFreeze;
    }

    public void setDailyFreeze(BigDecimal dailyFreeze) {
        this.dailyFreeze = dailyFreeze;
    }

    public BigDecimal getStopProfitFreeze() {
        return stopProfitFreeze;
    }

    public void setStopProfitFreeze(BigDecimal stopProfitFreeze) {
        this.stopProfitFreeze = stopProfitFreeze;
    }

    public BigDecimal getCashFreeze() {
        return cashFreeze;
    }

    public void setCashFreeze(BigDecimal cashFreeze) {
        this.cashFreeze = cashFreeze;
    }

    public BigDecimal getStopCount() {
        return stopCount;
    }

    public void setStopCount(BigDecimal stopCount) {
        this.stopCount = stopCount;
    }
}