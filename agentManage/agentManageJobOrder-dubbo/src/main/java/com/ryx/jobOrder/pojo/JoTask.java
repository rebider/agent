package com.ryx.jobOrder.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class JoTask {
    private String id;

    private String joId;

    private String joTaskStatus;

    private Date joTaskTime;

    private Date joTaskAcceptTime;

    private Date joTaskNextTime;

    private String dealDeptId;

    private String dealDept;

    private String dealPersonId;

    private String dealPersonName;

    private String secondDealGroup;

    private String backDealDept;

    private String backDealPerson;

    private String joTaskContent;

    private String joTaskAnnexId;

    private BigDecimal joTaskTimeLength;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJoId() {
        return joId;
    }

    public void setJoId(String joId) {
        this.joId = joId == null ? null : joId.trim();
    }

    public String getJoTaskStatus() {
        return joTaskStatus;
    }

    public void setJoTaskStatus(String joTaskStatus) {
        this.joTaskStatus = joTaskStatus == null ? null : joTaskStatus.trim();
    }

    public Date getJoTaskTime() {
        return joTaskTime;
    }

    public void setJoTaskTime(Date joTaskTime) {
        this.joTaskTime = joTaskTime;
    }

    public Date getJoTaskAcceptTime() {
        return joTaskAcceptTime;
    }

    public void setJoTaskAcceptTime(Date joTaskAcceptTime) {
        this.joTaskAcceptTime = joTaskAcceptTime;
    }

    public Date getJoTaskNextTime() {
        return joTaskNextTime;
    }

    public void setJoTaskNextTime(Date joTaskNextTime) {
        this.joTaskNextTime = joTaskNextTime;
    }

    public String getDealDeptId() {
        return dealDeptId;
    }

    public void setDealDeptId(String dealDeptId) {
        this.dealDeptId = dealDeptId == null ? null : dealDeptId.trim();
    }

    public String getDealDept() {
        return dealDept;
    }

    public void setDealDept(String dealDept) {
        this.dealDept = dealDept == null ? null : dealDept.trim();
    }

    public String getDealPersonId() {
        return dealPersonId;
    }

    public void setDealPersonId(String dealPersonId) {
        this.dealPersonId = dealPersonId == null ? null : dealPersonId.trim();
    }

    public String getDealPersonName() {
        return dealPersonName;
    }

    public void setDealPersonName(String dealPersonName) {
        this.dealPersonName = dealPersonName == null ? null : dealPersonName.trim();
    }

    public String getSecondDealGroup() {
        return secondDealGroup;
    }

    public void setSecondDealGroup(String secondDealGroup) {
        this.secondDealGroup = secondDealGroup == null ? null : secondDealGroup.trim();
    }

    public String getBackDealDept() {
        return backDealDept;
    }

    public void setBackDealDept(String backDealDept) {
        this.backDealDept = backDealDept == null ? null : backDealDept.trim();
    }

    public String getBackDealPerson() {
        return backDealPerson;
    }

    public void setBackDealPerson(String backDealPerson) {
        this.backDealPerson = backDealPerson == null ? null : backDealPerson.trim();
    }

    public String getJoTaskContent() {
        return joTaskContent;
    }

    public void setJoTaskContent(String joTaskContent) {
        this.joTaskContent = joTaskContent == null ? null : joTaskContent.trim();
    }

    public String getJoTaskAnnexId() {
        return joTaskAnnexId;
    }

    public void setJoTaskAnnexId(String joTaskAnnexId) {
        this.joTaskAnnexId = joTaskAnnexId == null ? null : joTaskAnnexId.trim();
    }

    public BigDecimal getJoTaskTimeLength() {
        return joTaskTimeLength;
    }

    public void setJoTaskTimeLength(BigDecimal joTaskTimeLength) {
        this.joTaskTimeLength = joTaskTimeLength;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}