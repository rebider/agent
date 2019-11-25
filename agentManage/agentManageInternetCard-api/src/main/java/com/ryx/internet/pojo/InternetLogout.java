package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InternetLogout implements Serializable{
    private String id;

    private String agentId;

    private String agentName;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;

    private String agDocPro;

    private String busContactPerson;

    private BigDecimal logoutCardCount;

    private BigDecimal reviewStatus;

    private Date reviewPassTime;

    private String applyRemark;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private String iccidNumIds;

    public String getIccidNumIds() {
        return iccidNumIds;
    }

    public void setIccidNumIds(String iccidNumIds) {
        this.iccidNumIds = iccidNumIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public BigDecimal getLogoutCardCount() {
        return logoutCardCount;
    }

    public void setLogoutCardCount(BigDecimal logoutCardCount) {
        this.logoutCardCount = logoutCardCount;
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