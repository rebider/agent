package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentColinfoRel implements Serializable {
    private String id;

    private String agentid;

    private String agentbusid;

    private String agentColinfoid;

    private String busPlatform;

    private Date cTime;

    private String cUse;

    private BigDecimal cSort;

    private BigDecimal isdefault;

    private BigDecimal status;

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

    public String getAgentbusid() {
        return agentbusid;
    }

    public void setAgentbusid(String agentbusid) {
        this.agentbusid = agentbusid == null ? null : agentbusid.trim();
    }

    public String getAgentColinfoid() {
        return agentColinfoid;
    }

    public void setAgentColinfoid(String agentColinfoid) {
        this.agentColinfoid = agentColinfoid == null ? null : agentColinfoid.trim();
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getcUse() {
        return cUse;
    }

    public void setcUse(String cUse) {
        this.cUse = cUse == null ? null : cUse.trim();
    }

    public BigDecimal getcSort() {
        return cSort;
    }

    public void setcSort(BigDecimal cSort) {
        this.cSort = cSort;
    }

    public BigDecimal getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(BigDecimal isdefault) {
        this.isdefault = isdefault;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}