package com.ryx.internet.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class InternetLogoutDetail {
    private String id;

    private String renewId;

    private String iccidNum;

    private BigDecimal internetCardStatus;

    private String orderId;

    private String snNum;

    private String internetCardNum;

    private Date openAccountTime;

    private Date expireTime;

    private String merName;

    private String merId;

    private String agentId;

    private String agentName;

    private String logoutStatus;

    private BigDecimal status;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;

    private String agDocPro;

    private String busContactPerson;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal version;

    private String issuer;

    private String failCause;

    private String mobileOrderNo;

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

    public BigDecimal getInternetCardStatus() {
        return internetCardStatus;
    }

    public void setInternetCardStatus(BigDecimal internetCardStatus) {
        this.internetCardStatus = internetCardStatus;
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

    public String getLogoutStatus() {
        return logoutStatus;
    }

    public void setLogoutStatus(String logoutStatus) {
        this.logoutStatus = logoutStatus == null ? null : logoutStatus.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict == null ? null : agDocDistrict.trim();
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro == null ? null : agDocPro.trim();
    }

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson == null ? null : busContactPerson.trim();
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer == null ? null : issuer.trim();
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause == null ? null : failCause.trim();
    }

    public String getMobileOrderNo() {
        return mobileOrderNo;
    }

    public void setMobileOrderNo(String mobileOrderNo) {
        this.mobileOrderNo = mobileOrderNo == null ? null : mobileOrderNo.trim();
    }

    @Override
    public String toString() {
        return "InternetLogoutDetail{" +
                "id='" + id + '\'' +
                ", renewId='" + renewId + '\'' +
                ", iccidNum='" + iccidNum + '\'' +
                ", internetCardStatus=" + internetCardStatus +
                ", orderId='" + orderId + '\'' +
                ", snNum='" + snNum + '\'' +
                ", internetCardNum='" + internetCardNum + '\'' +
                ", openAccountTime=" + openAccountTime +
                ", expireTime=" + expireTime +
                ", merName='" + merName + '\'' +
                ", merId='" + merId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", logoutStatus='" + logoutStatus + '\'' +
                ", status=" + status +
                ", busNum='" + busNum + '\'' +
                ", busPlatform='" + busPlatform + '\'' +
                ", agDocDistrict='" + agDocDistrict + '\'' +
                ", agDocPro='" + agDocPro + '\'' +
                ", busContactPerson='" + busContactPerson + '\'' +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", cUser='" + cUser + '\'' +
                ", uUser='" + uUser + '\'' +
                ", version=" + version +
                ", issuer='" + issuer + '\'' +
                ", failCause='" + failCause + '\'' +
                ", mobileOrderNo='" + mobileOrderNo + '\'' +
                '}';
    }
}