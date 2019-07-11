package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InternetRenewOffset implements Serializable{
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

    private Date processTime;

    private Date cTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

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

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
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