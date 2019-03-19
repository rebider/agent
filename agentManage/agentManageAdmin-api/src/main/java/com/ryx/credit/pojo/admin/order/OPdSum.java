package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OPdSum implements Serializable {
    private String id;

    private String agentid;

    private String platform;

    private String sumMouth;

    private BigDecimal realAmount;

    private Date realDatetime;

    private String realUser;

    private String paySrc;

    private String paySrcType;

    private String sumStatus;

    private BigDecimal status;

    private BigDecimal version;

    private String cUser;

    private Date cTime;

    private Date uTime;

    private BigDecimal sumAmount;

    private String rev;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid == null ? null : agentid.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getSumMouth() {
        return sumMouth;
    }

    public void setSumMouth(String sumMouth) {
        this.sumMouth = sumMouth == null ? null : sumMouth.trim();
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public Date getRealDatetime() {
        return realDatetime;
    }

    public void setRealDatetime(Date realDatetime) {
        this.realDatetime = realDatetime;
    }

    public String getRealUser() {
        return realUser;
    }

    public void setRealUser(String realUser) {
        this.realUser = realUser == null ? null : realUser.trim();
    }

    public String getPaySrc() {
        return paySrc;
    }

    public void setPaySrc(String paySrc) {
        this.paySrc = paySrc == null ? null : paySrc.trim();
    }

    public String getPaySrcType() {
        return paySrcType;
    }

    public void setPaySrcType(String paySrcType) {
        this.paySrcType = paySrcType == null ? null : paySrcType.trim();
    }

    public String getSumStatus() {
        return sumStatus;
    }

    public void setSumStatus(String sumStatus) {
        this.sumStatus = sumStatus == null ? null : sumStatus.trim();
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

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
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

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev == null ? null : rev.trim();
    }
}