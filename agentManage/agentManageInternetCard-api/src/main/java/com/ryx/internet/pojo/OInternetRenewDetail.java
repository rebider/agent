package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OInternetRenewDetail implements Serializable {
    private String id;

    private String renewId;

    private String iccidNum;

    private String orderId;

    private String snNum;

    private String internetCardNum;

    private Date openAccountTime;

    private Date expireTime;

    private String merName;

    private String merId;

    private String agentId;

    private String agentName;

    private String renewWay;

    private BigDecimal offsetAmt;

    private BigDecimal renewAmt;

    private BigDecimal oughtAmt;

    private BigDecimal realityAmt;

    private String renewStatus;

    private BigDecimal status;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal version;

    private String renewWayName;

    public String getRenewWayName() {
        return renewWayName;
    }

    public void setRenewWayName(String renewWayName) {
        this.renewWayName = renewWayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRenewId() {
        return renewId;
    }

    public void setRenewId(String renewId) {
        this.renewId = renewId == null ? null : renewId.trim();
    }

    public String getIccidNum() {
        return iccidNum;
    }

    public void setIccidNum(String iccidNum) {
        this.iccidNum = iccidNum == null ? null : iccidNum.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum == null ? null : snNum.trim();
    }

    public String getInternetCardNum() {
        return internetCardNum;
    }

    public void setInternetCardNum(String internetCardNum) {
        this.internetCardNum = internetCardNum == null ? null : internetCardNum.trim();
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
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

    public String getRenewWay() {
        return renewWay;
    }

    public void setRenewWay(String renewWay) {
        this.renewWay = renewWay == null ? null : renewWay.trim();
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getRenewAmt() {
        return renewAmt;
    }

    public void setRenewAmt(BigDecimal renewAmt) {
        this.renewAmt = renewAmt;
    }

    public BigDecimal getOughtAmt() {
        return oughtAmt;
    }

    public void setOughtAmt(BigDecimal oughtAmt) {
        this.oughtAmt = oughtAmt;
    }

    public BigDecimal getRealityAmt() {
        return realityAmt;
    }

    public void setRealityAmt(BigDecimal realityAmt) {
        this.realityAmt = realityAmt;
    }

    public String getRenewStatus() {
        return renewStatus;
    }

    public void setRenewStatus(String renewStatus) {
        this.renewStatus = renewStatus == null ? null : renewStatus.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
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

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}