package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentPlatFormSyn implements Serializable {
    private String id;

    private String agentId;

    private String busId;

    private String platformCode;

    private String notifyJson;

    private BigDecimal notifyStatus;

    private Date notifyTime;

    private String cUser;

    private Date cTime;

    private BigDecimal version;

    private Date succesTime;

    private BigDecimal notifyCount;

    private byte[] sendJson;

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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
    }

    public String getNotifyJson() {
        return notifyJson;
    }

    public void setNotifyJson(String notifyJson) {
        this.notifyJson = notifyJson == null ? null : notifyJson.trim();
    }

    public BigDecimal getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(BigDecimal notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
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

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public Date getSuccesTime() {
        return succesTime;
    }

    public void setSuccesTime(Date succesTime) {
        this.succesTime = succesTime;
    }

    public BigDecimal getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(BigDecimal notifyCount) {
        this.notifyCount = notifyCount;
    }

    public byte[] getSendJson() {
        return sendJson;
    }

    public void setSendJson(byte[] sendJson) {
        this.sendJson = sendJson;
    }
}