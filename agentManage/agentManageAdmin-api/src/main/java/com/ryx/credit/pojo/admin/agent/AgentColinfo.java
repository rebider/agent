package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AgentColinfo implements Serializable {
    private String id;

    private String agentId;

    private BigDecimal cloType;

    private String cloRealname;//收款账户名

    private String cloBank;//收款银行

    private String cloBankBranch;//收款银行支行

    private String cloBankAccount;//账户号

    private Date cTime;

    private Date cUtime;

    private String remark;

    private String cUser;

    private BigDecimal cloReviewStatus;

    private BigDecimal status;

    private BigDecimal varsion;

    private String branchLineNum;//收款银行支行联号

    private String allLineNum;//收款银行联号

    private String bankRegion;

    private BigDecimal cloTaxPoint;

    private BigDecimal cloInvoice;

    private String cloBankCode;//开户行行号

    private BigDecimal payStatus;

    private List<Attachment> attachmentList;

    private String accountId;//打款公司代码

    private String accountName; //打款公司名称

    private boolean isImport = false;

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVarsion() {
        return varsion;
    }

    public void setVarsion(BigDecimal varsion) {
        this.varsion = varsion;
    }

    public String getBranchLineNum() {
        return branchLineNum;
    }

    public void setBranchLineNum(String branchLineNum) {
        this.branchLineNum = branchLineNum;
    }

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum;
    }

    public String getBankRegion() {
        return bankRegion;
    }

    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
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

    public String getCloBankCode() {
        return cloBankCode;
    }

    public void setCloBankCode(String cloBankCode) {
        this.cloBankCode = cloBankCode;
    }

    public BigDecimal getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(BigDecimal payStatus) {
        this.payStatus = payStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public boolean isImport() {
        return isImport;
    }

    public void setImport(boolean anImport) {
        isImport = anImport;
    }
}