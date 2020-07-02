package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentBusinfoFreeze implements Serializable{
    private String id;

    private String agId;

    private String busId;

    private BigDecimal freezeType;

    private String busNum;

    private String platId;

    private String platType;

    private String busPlatform;

    private BigDecimal busFreeze;

    private BigDecimal profitFreeze;

    private BigDecimal reflowFreeze;

    private BigDecimal monthlyFreeze;

    private BigDecimal dailyFreeze;

    private BigDecimal stopProfitFreeze;

    private BigDecimal cashFreeze;

    private BigDecimal stopCount;

    private Date cTime;

    private Date uTime;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId == null ? null : agId.trim();
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

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public BigDecimal getBusFreeze() {
        return busFreeze;
    }

    public void setBusFreeze(BigDecimal busFreeze) {
        this.busFreeze = busFreeze;
    }

    public BigDecimal getProfitFreeze() {
        return profitFreeze;
    }

    public void setProfitFreeze(BigDecimal profitFreeze) {
        this.profitFreeze = profitFreeze;
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

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if(busId.equals(((AgentBusinfoFreeze)obj).busId) && freezeType.equals(((AgentBusinfoFreeze)obj).freezeType) ){
            return true;
        }else{
            return false;
        }
    }
}