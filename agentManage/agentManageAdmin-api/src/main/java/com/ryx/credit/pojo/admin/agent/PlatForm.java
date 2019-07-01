package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class  PlatForm implements Serializable{
    private String id;

    private String platformNum;

    private String platformName;

    private Date cTime;

    private Date cUtime;

    private String cUser;

    private BigDecimal platformStatus;

    private BigDecimal status;

    private BigDecimal version;

    private String platformType;

    private String posanameprefix;

    private String posbusitype;

    private String busplatform;

    private String platformUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlatformNum() {
        return platformNum;
    }

    public void setPlatformNum(String platformNum) {
        this.platformNum = platformNum == null ? null : platformNum.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUtime() {
        return cUtime;
    }

    public void setcUtime(Date cUtime) {
        this.cUtime = cUtime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public BigDecimal getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(BigDecimal platformStatus) {
        this.platformStatus = platformStatus;
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

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getPosanameprefix() {
        return posanameprefix;
    }

    public void setPosanameprefix(String posanameprefix) {
        this.posanameprefix = posanameprefix == null ? null : posanameprefix.trim();
    }

    public String getPosbusitype() {
        return posbusitype;
    }

    public void setPosbusitype(String posbusitype) {
        this.posbusitype = posbusitype == null ? null : posbusitype.trim();
    }

    public String getBusplatform() {
        return busplatform;
    }

    public void setBusplatform(String busplatform) {
        this.busplatform = busplatform;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }
}