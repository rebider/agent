package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ODeductCapital implements Serializable{
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

    private Date cTime;

    private Date cUtime;

    private String cUser;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    private String cAgentId;

    private String sourceId;

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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }
}