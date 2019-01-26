package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentQuit implements Serializable {
    private String id;

    private String agentId;

    private String agentName;

    private String quitPlatform;

    private BigDecimal capitalDebt;

    private BigDecimal orderDebt;

    private BigDecimal profitDebt;

    private BigDecimal agentDept;

    private String suppType;

    private BigDecimal suppDept;

    private BigDecimal realitySuppDept;

    private BigDecimal agentOweTicket;

    private BigDecimal suppTicket;

    private BigDecimal subtractAmt;

    private String migrationPlatform;

    private BigDecimal contractStatus;

    private BigDecimal refundAmtStatus;

    private BigDecimal refundAmtDeadline;

    private Date approveTime;

    private BigDecimal cloReviewStatus;

    private String remark;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private String quitBusId;

    private BigDecimal appRefund;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getQuitPlatform() {
        return quitPlatform;
    }

    public void setQuitPlatform(String quitPlatform) {
        this.quitPlatform = quitPlatform == null ? null : quitPlatform.trim();
    }

    public BigDecimal getCapitalDebt() {
        return capitalDebt;
    }

    public void setCapitalDebt(BigDecimal capitalDebt) {
        this.capitalDebt = capitalDebt;
    }

    public BigDecimal getOrderDebt() {
        return orderDebt;
    }

    public void setOrderDebt(BigDecimal orderDebt) {
        this.orderDebt = orderDebt;
    }

    public BigDecimal getProfitDebt() {
        return profitDebt;
    }

    public void setProfitDebt(BigDecimal profitDebt) {
        this.profitDebt = profitDebt;
    }

    public BigDecimal getAgentDept() {
        return agentDept;
    }

    public void setAgentDept(BigDecimal agentDept) {
        this.agentDept = agentDept;
    }

    public String getSuppType() {
        return suppType;
    }

    public void setSuppType(String suppType) {
        this.suppType = suppType == null ? null : suppType.trim();
    }

    public BigDecimal getSuppDept() {
        return suppDept;
    }

    public void setSuppDept(BigDecimal suppDept) {
        this.suppDept = suppDept;
    }

    public BigDecimal getRealitySuppDept() {
        return realitySuppDept;
    }

    public void setRealitySuppDept(BigDecimal realitySuppDept) {
        this.realitySuppDept = realitySuppDept;
    }

    public BigDecimal getAgentOweTicket() {
        return agentOweTicket;
    }

    public void setAgentOweTicket(BigDecimal agentOweTicket) {
        this.agentOweTicket = agentOweTicket;
    }

    public BigDecimal getSuppTicket() {
        return suppTicket;
    }

    public void setSuppTicket(BigDecimal suppTicket) {
        this.suppTicket = suppTicket;
    }

    public BigDecimal getSubtractAmt() {
        return subtractAmt;
    }

    public void setSubtractAmt(BigDecimal subtractAmt) {
        this.subtractAmt = subtractAmt;
    }

    public String getMigrationPlatform() {
        return migrationPlatform;
    }

    public void setMigrationPlatform(String migrationPlatform) {
        this.migrationPlatform = migrationPlatform == null ? null : migrationPlatform.trim();
    }

    public BigDecimal getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(BigDecimal contractStatus) {
        this.contractStatus = contractStatus;
    }

    public BigDecimal getRefundAmtStatus() {
        return refundAmtStatus;
    }

    public void setRefundAmtStatus(BigDecimal refundAmtStatus) {
        this.refundAmtStatus = refundAmtStatus;
    }

    public BigDecimal getRefundAmtDeadline() {
        return refundAmtDeadline;
    }

    public void setRefundAmtDeadline(BigDecimal refundAmtDeadline) {
        this.refundAmtDeadline = refundAmtDeadline;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getQuitBusId() {
        return quitBusId;
    }

    public void setQuitBusId(String quitBusId) {
        this.quitBusId = quitBusId == null ? null : quitBusId.trim();
    }

    public BigDecimal getAppRefund() {
        return appRefund;
    }

    public void setAppRefund(BigDecimal appRefund) {
        this.appRefund = appRefund;
    }
}