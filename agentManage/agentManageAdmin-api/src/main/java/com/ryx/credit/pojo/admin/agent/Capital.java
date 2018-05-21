package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;
import java.util.Date;

public class Capital {
    private String id;

    private String cType;

    private BigDecimal cAmount;

    private BigDecimal cIsin;

    private BigDecimal cInAmount;

    private BigDecimal cBusStatus;

    private String cSrc;

    private Date cPlanintime;

    private Date cIntime;

    private Date cPaytime;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    private String cAgentId;

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

    public BigDecimal getcAmount() {
        return cAmount;
    }

    public void setcAmount(BigDecimal cAmount) {
        this.cAmount = cAmount;
    }

    public BigDecimal getcIsin() {
        return cIsin;
    }

    public void setcIsin(BigDecimal cIsin) {
        this.cIsin = cIsin;
    }

    public BigDecimal getcInAmount() {
        return cInAmount;
    }

    public void setcInAmount(BigDecimal cInAmount) {
        this.cInAmount = cInAmount;
    }

    public BigDecimal getcBusStatus() {
        return cBusStatus;
    }

    public void setcBusStatus(BigDecimal cBusStatus) {
        this.cBusStatus = cBusStatus;
    }

    public String getcSrc() {
        return cSrc;
    }

    public void setcSrc(String cSrc) {
        this.cSrc = cSrc == null ? null : cSrc.trim();
    }

    public Date getcPlanintime() {
        return cPlanintime;
    }

    public void setcPlanintime(Date cPlanintime) {
        this.cPlanintime = cPlanintime;
    }

    public Date getcIntime() {
        return cIntime;
    }

    public void setcIntime(Date cIntime) {
        this.cIntime = cIntime;
    }

    public Date getcPaytime() {
        return cPaytime;
    }

    public void setcPaytime(Date cPaytime) {
        this.cPaytime = cPaytime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getcAgentId() {
        return cAgentId;
    }

    public void setcAgentId(String cAgentId) {
        this.cAgentId = cAgentId == null ? null : cAgentId.trim();
    }
}