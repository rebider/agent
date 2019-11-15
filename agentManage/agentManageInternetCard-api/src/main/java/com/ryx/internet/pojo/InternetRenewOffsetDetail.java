package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class InternetRenewOffsetDetail implements Serializable{
    private String id;

    private String flowId;

    private String renewId;

    private String renewDetailId;

    private String iccidNum;

    private String agentId;

    private String agentName;

    private String merId;

    private String merName;

    private BigDecimal offsetAmt;

    private BigDecimal alreadyOffsetAmt;

    private BigDecimal todayOffsetAmt;

    private String cTime;

    private String processDate;

    private String processTime;

    private String cUser;

    private String uUser;

    private String cleanStatus;

    private BigDecimal status;

    private BigDecimal version;

    private String processDateBegin;

    private String processDateEnd;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;

    private String agDocPro;

    private String busContactPerson;

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson;
    }

    public String getProcessDateBegin() {
        return processDateBegin;
    }

    public void setProcessDateBegin(String processDateBegin) {
        this.processDateBegin = processDateBegin;
    }

    public String getProcessDateEnd() {
        return processDateEnd;
    }

    public void setProcessDateEnd(String processDateEnd) {
        this.processDateEnd = processDateEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public String getRenewId() {
        return renewId;
    }

    public void setRenewId(String renewId) {
        this.renewId = renewId == null ? null : renewId.trim();
    }

    public String getRenewDetailId() {
        return renewDetailId;
    }

    public void setRenewDetailId(String renewDetailId) {
        this.renewDetailId = renewDetailId == null ? null : renewDetailId.trim();
    }

    public String getIccidNum() {
        return iccidNum;
    }

    public void setIccidNum(String iccidNum) {
        this.iccidNum = iccidNum == null ? null : iccidNum.trim();
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

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getAlreadyOffsetAmt() {
        return alreadyOffsetAmt;
    }

    public void setAlreadyOffsetAmt(BigDecimal alreadyOffsetAmt) {
        this.alreadyOffsetAmt = alreadyOffsetAmt;
    }

    public BigDecimal getTodayOffsetAmt() {
        return todayOffsetAmt;
    }

    public void setTodayOffsetAmt(BigDecimal todayOffsetAmt) {
        this.todayOffsetAmt = todayOffsetAmt;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime == null ? null : cTime.trim();
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate == null ? null : processDate.trim();
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime == null ? null : processTime.trim();
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

    public String getCleanStatus() {
        return cleanStatus;
    }

    public void setCleanStatus(String cleanStatus) {
        this.cleanStatus = cleanStatus == null ? null : cleanStatus.trim();
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