package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentMergeBusInfo implements Serializable {
    private String id;

    private String agentMargeId;

    private String busId;

    private String mainAgentId;

    private String subAgentId;

    private String busNum;

    private String busPlatform;

    private String busType;

    private String busParent;

    private String busRiskParent;

    private String busActivationParent;

    private String busRegion;

    private BigDecimal busSentDirectly;

    private BigDecimal busDirectCashback;

    private BigDecimal busIndeAss;

    private String busContact;

    private String busContactMobile;

    private String busContactEmail;

    private String busContactPerson;

    private String busRiskEmail;

    private BigDecimal cloTaxPoint;

    private BigDecimal cloInvoice;

    private BigDecimal cloReceipt;

    private String cloPayCompany;

    private String cloAgentColinfo;

    private String agZbh;

    private BigDecimal busStatus;

    private String busUseOrgan;

    private BigDecimal cloReviewStatus;

    private Date cTime;

    private Date cUtime;

    private String cUser;

    private BigDecimal mergeStatus;

    private BigDecimal status;

    private BigDecimal version;

    private String busScope;

    private BigDecimal dredgeS0;

    private String busLoginNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentMargeId() {
        return agentMargeId;
    }

    public void setAgentMargeId(String agentMargeId) {
        this.agentMargeId = agentMargeId == null ? null : agentMargeId.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getMainAgentId() {
        return mainAgentId;
    }

    public void setMainAgentId(String mainAgentId) {
        this.mainAgentId = mainAgentId == null ? null : mainAgentId.trim();
    }

    public String getSubAgentId() {
        return subAgentId;
    }

    public void setSubAgentId(String subAgentId) {
        this.subAgentId = subAgentId == null ? null : subAgentId.trim();
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

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }

    public String getBusParent() {
        return busParent;
    }

    public void setBusParent(String busParent) {
        this.busParent = busParent == null ? null : busParent.trim();
    }

    public String getBusRiskParent() {
        return busRiskParent;
    }

    public void setBusRiskParent(String busRiskParent) {
        this.busRiskParent = busRiskParent == null ? null : busRiskParent.trim();
    }

    public String getBusActivationParent() {
        return busActivationParent;
    }

    public void setBusActivationParent(String busActivationParent) {
        this.busActivationParent = busActivationParent == null ? null : busActivationParent.trim();
    }

    public String getBusRegion() {
        return busRegion;
    }

    public void setBusRegion(String busRegion) {
        this.busRegion = busRegion == null ? null : busRegion.trim();
    }

    public BigDecimal getBusSentDirectly() {
        return busSentDirectly;
    }

    public void setBusSentDirectly(BigDecimal busSentDirectly) {
        this.busSentDirectly = busSentDirectly;
    }

    public BigDecimal getBusDirectCashback() {
        return busDirectCashback;
    }

    public void setBusDirectCashback(BigDecimal busDirectCashback) {
        this.busDirectCashback = busDirectCashback;
    }

    public BigDecimal getBusIndeAss() {
        return busIndeAss;
    }

    public void setBusIndeAss(BigDecimal busIndeAss) {
        this.busIndeAss = busIndeAss;
    }

    public String getBusContact() {
        return busContact;
    }

    public void setBusContact(String busContact) {
        this.busContact = busContact == null ? null : busContact.trim();
    }

    public String getBusContactMobile() {
        return busContactMobile;
    }

    public void setBusContactMobile(String busContactMobile) {
        this.busContactMobile = busContactMobile == null ? null : busContactMobile.trim();
    }

    public String getBusContactEmail() {
        return busContactEmail;
    }

    public void setBusContactEmail(String busContactEmail) {
        this.busContactEmail = busContactEmail == null ? null : busContactEmail.trim();
    }

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson == null ? null : busContactPerson.trim();
    }

    public String getBusRiskEmail() {
        return busRiskEmail;
    }

    public void setBusRiskEmail(String busRiskEmail) {
        this.busRiskEmail = busRiskEmail == null ? null : busRiskEmail.trim();
    }

    public BigDecimal getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(BigDecimal cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public BigDecimal getCloInvoice() {
        return cloInvoice;
    }

    public void setCloInvoice(BigDecimal cloInvoice) {
        this.cloInvoice = cloInvoice;
    }

    public BigDecimal getCloReceipt() {
        return cloReceipt;
    }

    public void setCloReceipt(BigDecimal cloReceipt) {
        this.cloReceipt = cloReceipt;
    }

    public String getCloPayCompany() {
        return cloPayCompany;
    }

    public void setCloPayCompany(String cloPayCompany) {
        this.cloPayCompany = cloPayCompany == null ? null : cloPayCompany.trim();
    }

    public String getCloAgentColinfo() {
        return cloAgentColinfo;
    }

    public void setCloAgentColinfo(String cloAgentColinfo) {
        this.cloAgentColinfo = cloAgentColinfo == null ? null : cloAgentColinfo.trim();
    }

    public String getAgZbh() {
        return agZbh;
    }

    public void setAgZbh(String agZbh) {
        this.agZbh = agZbh == null ? null : agZbh.trim();
    }

    public BigDecimal getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(BigDecimal busStatus) {
        this.busStatus = busStatus;
    }

    public String getBusUseOrgan() {
        return busUseOrgan;
    }

    public void setBusUseOrgan(String busUseOrgan) {
        this.busUseOrgan = busUseOrgan == null ? null : busUseOrgan.trim();
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
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

    public BigDecimal getMergeStatus() {
        return mergeStatus;
    }

    public void setMergeStatus(BigDecimal mergeStatus) {
        this.mergeStatus = mergeStatus;
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

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope == null ? null : busScope.trim();
    }

    public BigDecimal getDredgeS0() {
        return dredgeS0;
    }

    public void setDredgeS0(BigDecimal dredgeS0) {
        this.dredgeS0 = dredgeS0;
    }

    public String getBusLoginNum() {
        return busLoginNum;
    }

    public void setBusLoginNum(String busLoginNum) {
        this.busLoginNum = busLoginNum == null ? null : busLoginNum.trim();
    }
}