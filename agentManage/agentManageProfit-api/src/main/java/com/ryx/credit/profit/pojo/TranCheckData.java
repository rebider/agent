package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TranCheckData implements Serializable {
    private String id;

    private String profitMonth;

    private String platformType;

    private String platformNum;

    private BigDecimal technologyAmt;

    private BigDecimal technologyFee;

    private BigDecimal clearAmt;

    private BigDecimal clearFee;

    private String searchTime;

    private BigDecimal platformId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth == null ? null : profitMonth.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getPlatformNum() {
        return platformNum;
    }

    public void setPlatformNum(String platformNum) {
        this.platformNum = platformNum == null ? null : platformNum.trim();
    }

    public BigDecimal getTechnologyAmt() {
        return technologyAmt;
    }

    public void setTechnologyAmt(BigDecimal technologyAmt) {
        this.technologyAmt = technologyAmt;
    }

    public BigDecimal getTechnologyFee() {
        return technologyFee;
    }

    public void setTechnologyFee(BigDecimal technologyFee) {
        this.technologyFee = technologyFee;
    }

    public BigDecimal getClearAmt() {
        return clearAmt;
    }

    public void setClearAmt(BigDecimal clearAmt) {
        this.clearAmt = clearAmt;
    }

    public BigDecimal getClearFee() {
        return clearFee;
    }

    public void setClearFee(BigDecimal clearFee) {
        this.clearFee = clearFee;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime == null ? null : searchTime.trim();
    }

    public BigDecimal getPlatformId() {
        return platformId;
    }

    public void setPlatformId(BigDecimal platformId) {
        this.platformId = platformId;
    }
}