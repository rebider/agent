package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentQuitRefund implements Serializable{
    private String id;

    private String quitId;

    private BigDecimal realitySuppDept;

    private BigDecimal refundAmt;

    private Date approveTime;

    private BigDecimal cloReviewStatus;

    private String remark;

    private String agentId;

    private String agentName;

    private BigDecimal cloType;

    private String cloRealname;

    private String cloBank;

    private String cloBankBranch;

    private String cloBankAccount;

    private String brachLineNum;

    private String allLineNum;

    private BigDecimal cloInvoice;

    private BigDecimal cloTaxPoint;

    private String bankRegion;

    private String cloBankCode;

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

    public String getQuitId() {
        return quitId;
    }

    public void setQuitId(String quitId) {
        this.quitId = quitId == null ? null : quitId.trim();
    }

    public BigDecimal getRealitySuppDept() {
        return realitySuppDept;
    }

    public void setRealitySuppDept(BigDecimal realitySuppDept) {
        this.realitySuppDept = realitySuppDept;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
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

    public BigDecimal getCloType() {
        return cloType;
    }

    public void setCloType(BigDecimal cloType) {
        this.cloType = cloType;
    }

    public String getCloRealname() {
        return cloRealname;
    }

    public void setCloRealname(String cloRealname) {
        this.cloRealname = cloRealname == null ? null : cloRealname.trim();
    }

    public String getCloBank() {
        return cloBank;
    }

    public void setCloBank(String cloBank) {
        this.cloBank = cloBank == null ? null : cloBank.trim();
    }

    public String getCloBankBranch() {
        return cloBankBranch;
    }

    public void setCloBankBranch(String cloBankBranch) {
        this.cloBankBranch = cloBankBranch == null ? null : cloBankBranch.trim();
    }

    public String getCloBankAccount() {
        return cloBankAccount;
    }

    public void setCloBankAccount(String cloBankAccount) {
        this.cloBankAccount = cloBankAccount == null ? null : cloBankAccount.trim();
    }

    public String getBrachLineNum() {
        return brachLineNum;
    }

    public void setBrachLineNum(String brachLineNum) {
        this.brachLineNum = brachLineNum == null ? null : brachLineNum.trim();
    }

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum == null ? null : allLineNum.trim();
    }

    public BigDecimal getCloInvoice() {
        return cloInvoice;
    }

    public void setCloInvoice(BigDecimal cloInvoice) {
        this.cloInvoice = cloInvoice;
    }

    public BigDecimal getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(BigDecimal cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public String getBankRegion() {
        return bankRegion;
    }

    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion == null ? null : bankRegion.trim();
    }

    public String getCloBankCode() {
        return cloBankCode;
    }

    public void setCloBankCode(String cloBankCode) {
        this.cloBankCode = cloBankCode == null ? null : cloBankCode.trim();
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