package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TranCheckPlatForm implements Serializable {
    private BigDecimal id;

    private String platformType;

    private String platformNum;

    private String technologyAddress;

    private String technologyAmt;

    private String technologyFee;

    private String clearAddress;

    private String clearAmt;

    private String clearFee;

    private BigDecimal orderNum;

    private Date createTime;

    private String createPerson;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public String getTechnologyAddress() {
        return technologyAddress;
    }

    public void setTechnologyAddress(String technologyAddress) {
        this.technologyAddress = technologyAddress == null ? null : technologyAddress.trim();
    }

    public String getTechnologyAmt() {
        return technologyAmt;
    }

    public void setTechnologyAmt(String technologyAmt) {
        this.technologyAmt = technologyAmt == null ? null : technologyAmt.trim();
    }

    public String getTechnologyFee() {
        return technologyFee;
    }

    public void setTechnologyFee(String technologyFee) {
        this.technologyFee = technologyFee == null ? null : technologyFee.trim();
    }

    public String getClearAddress() {
        return clearAddress;
    }

    public void setClearAddress(String clearAddress) {
        this.clearAddress = clearAddress == null ? null : clearAddress.trim();
    }

    public String getClearAmt() {
        return clearAmt;
    }

    public void setClearAmt(String clearAmt) {
        this.clearAmt = clearAmt == null ? null : clearAmt.trim();
    }

    public String getClearFee() {
        return clearFee;
    }

    public void setClearFee(String clearFee) {
        this.clearFee = clearFee == null ? null : clearFee.trim();
    }

    public BigDecimal getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }
}