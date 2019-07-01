package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OInternetRenew implements Serializable{
    private String id;

    private String renewWay;

    private BigDecimal renewCardCount;

    private BigDecimal sumOffsetAmt;

    private BigDecimal suppAmt;

    private BigDecimal reviewStatus;

    private Date reviewPassTime;

    private String applyRemark;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRenewWay() {
        return renewWay;
    }

    public void setRenewWay(String renewWay) {
        this.renewWay = renewWay == null ? null : renewWay.trim();
    }

    public BigDecimal getRenewCardCount() {
        return renewCardCount;
    }

    public void setRenewCardCount(BigDecimal renewCardCount) {
        this.renewCardCount = renewCardCount;
    }

    public BigDecimal getSumOffsetAmt() {
        return sumOffsetAmt;
    }

    public void setSumOffsetAmt(BigDecimal sumOffsetAmt) {
        this.sumOffsetAmt = sumOffsetAmt;
    }

    public BigDecimal getSuppAmt() {
        return suppAmt;
    }

    public void setSuppAmt(BigDecimal suppAmt) {
        this.suppAmt = suppAmt;
    }

    public BigDecimal getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(BigDecimal reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getReviewPassTime() {
        return reviewPassTime;
    }

    public void setReviewPassTime(Date reviewPassTime) {
        this.reviewPassTime = reviewPassTime;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
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

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
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
}