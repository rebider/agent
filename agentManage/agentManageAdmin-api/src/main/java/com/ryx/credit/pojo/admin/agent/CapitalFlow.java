package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CapitalFlow implements Serializable{
    private String id;

    private String cType;

    private String capitalId;

    private BigDecimal srcType;

    private String srcId;

    private BigDecimal beforeAmount;

    private BigDecimal cAmount;

    private BigDecimal operationType;

    private String agentId;

    private String agentName;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public String getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(String capitalId) {
        this.capitalId = capitalId == null ? null : capitalId.trim();
    }

    public BigDecimal getSrcType() {
        return srcType;
    }

    public void setSrcType(BigDecimal srcType) {
        this.srcType = srcType;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getcAmount() {
        return cAmount;
    }

    public void setcAmount(BigDecimal cAmount) {
        this.cAmount = cAmount;
    }

    public BigDecimal getOperationType() {
        return operationType;
    }

    public void setOperationType(BigDecimal operationType) {
        this.operationType = operationType;
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