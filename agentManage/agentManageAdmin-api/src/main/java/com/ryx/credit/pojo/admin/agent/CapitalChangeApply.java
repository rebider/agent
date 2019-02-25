package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CapitalChangeApply implements Serializable {
    private String id;

    private String capitalType;

    private BigDecimal capitalAmt;

    private BigDecimal refundType;

    private BigDecimal machinesDeptAmt;

    private BigDecimal operationType;

    private BigDecimal operationAmt;

    private BigDecimal realOperationAmt;

    private BigDecimal serviceCharge;

    private String agentId;

    private String agentName;

    private BigDecimal cloReviewStatus;

    private BigDecimal cloType;

    private String cloRealname;

    private String cloBank;

    private String cloBankBranch;

    private String cloBankAccount;

    private String allLineNum;

    private String branchLineNum;

    private BigDecimal cloTaxPoint;

    private BigDecimal cloInvoice;

    private String bankRegion;

    private String cloBankCode;

    private String remark;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private Date remitTime;

    private String remitPerson;

    private BigDecimal remitAmt;

    private List<Attachment> attachments;

    private List<Attachment> financeAttachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCapitalType() {
        return capitalType;
    }

    public void setCapitalType(String capitalType) {
        this.capitalType = capitalType == null ? null : capitalType.trim();
    }

    public BigDecimal getCapitalAmt() {
        return capitalAmt;
    }

    public void setCapitalAmt(BigDecimal capitalAmt) {
        this.capitalAmt = capitalAmt;
    }

    public BigDecimal getRefundType() {
        return refundType;
    }

    public void setRefundType(BigDecimal refundType) {
        this.refundType = refundType;
    }

    public BigDecimal getMachinesDeptAmt() {
        return machinesDeptAmt;
    }

    public void setMachinesDeptAmt(BigDecimal machinesDeptAmt) {
        this.machinesDeptAmt = machinesDeptAmt;
    }

    public BigDecimal getOperationType() {
        return operationType;
    }

    public void setOperationType(BigDecimal operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getOperationAmt() {
        return operationAmt;
    }

    public void setOperationAmt(BigDecimal operationAmt) {
        this.operationAmt = operationAmt;
    }

    public BigDecimal getRealOperationAmt() {
        return realOperationAmt;
    }

    public void setRealOperationAmt(BigDecimal realOperationAmt) {
        this.realOperationAmt = realOperationAmt;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
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

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
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

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum == null ? null : allLineNum.trim();
    }

    public String getBranchLineNum() {
        return branchLineNum;
    }

    public void setBranchLineNum(String branchLineNum) {
        this.branchLineNum = branchLineNum == null ? null : branchLineNum.trim();
    }

    public BigDecimal getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(BigDecimal cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public BigDecimal getCloInvoice() {
        return cloInvoice;
    }

    public void setCloInvoice(BigDecimal cloInvoice) {
        this.cloInvoice = cloInvoice;
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

    public List<Attachment> getFinanceAttachments() {
        return financeAttachments;
    }

    public void setFinanceAttachments(List<Attachment> financeAttachments) {
        this.financeAttachments = financeAttachments;
    }

    public Date getRemitTime() {
        return remitTime;
    }

    public void setRemitTime(Date remitTime) {
        this.remitTime = remitTime;
    }

    public String getRemitPerson() {
        return remitPerson;
    }

    public void setRemitPerson(String remitPerson) {
        this.remitPerson = remitPerson;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }
}