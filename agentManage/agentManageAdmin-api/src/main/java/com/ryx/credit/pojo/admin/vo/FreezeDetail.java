package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class FreezeDetail implements Serializable {
    private BigDecimal busFreeze;

    private BigDecimal profitFreeze;

    private BigDecimal reflowFreeze;

    private BigDecimal monthlyFreeze;

    private BigDecimal dailyFreeze;

    private BigDecimal stopProfitFreeze;

    private BigDecimal cashFreeze;

    private BigDecimal stopCount;

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
}

