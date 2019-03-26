package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BusActRel extends BusActRelKey implements Serializable{
    private String busType;

    private Date cTime;

    private String cUser;

    private String activStatus;

    private BigDecimal status;

    private String agentId;

    private String agentName;

    private String netInBusType;

    private String dataShiro;

    public String getDataShiro() {
        return dataShiro;
    }

    public void setDataShiro(String dataShiro) {
        this.dataShiro = dataShiro;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getActivStatus() {
        return activStatus;
    }

    public void setActivStatus(String activStatus) {
        this.activStatus = activStatus == null ? null : activStatus.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
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

    public String getNetInBusType() {
        return netInBusType;
    }

    public void setNetInBusType(String netInBusType) {
        this.netInBusType = netInBusType == null ? null : netInBusType.trim();
    }
}