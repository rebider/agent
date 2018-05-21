package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AgentPlatFormSyn {
    private String id;

    private String agentId;

    private String busId;

    private String platformCode;

    private String sendjson;

    private String notifyjson;

    private BigDecimal notifystatus;

    private Date notifytime;

    private Date cTime;

    private BigDecimal version;

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

    public String getSendjson() {
        return sendjson;
    }

    public void setSendjson(String sendjson) {
        this.sendjson = sendjson == null ? null : sendjson.trim();
    }

    public String getNotifyjson() {
        return notifyjson;
    }

    public void setNotifyjson(String notifyjson) {
        this.notifyjson = notifyjson == null ? null : notifyjson.trim();
    }

    public BigDecimal getNotifystatus() {
        return notifystatus;
    }

    public void setNotifystatus(BigDecimal notifystatus) {
        this.notifystatus = notifystatus;
    }

    public Date getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(Date notifytime) {
        this.notifytime = notifytime;
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
}