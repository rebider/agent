package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Attachment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/6/26 15:34
 * @Description:
 */
public class OrganizationSerchVo implements Serializable{
    private String orgId;

    private String orgNick;

    private String orgName;

    private String orgLevel;

    private String orgType;

    private String orgParent;

    private String agentId;

    private String platId;

    private String bankCard;

    private String cloRealname;

    private BigDecimal cloType;

    private String cloBank;

    private String cloBankBranch;

    private String branchLineNum;

    private String allLineNum;

    private String bankRegion;

    private String cUser;

    private String uUser;

    private Date cTime;

    private Date uTime;

    private BigDecimal result;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    private String accountName;

    private String accountNum;

    private String accountBank;

    private String businessNum;

    private List<Attachment> attachmentList;

    private String platNum;

    private String platCode;

    public String getPlatNum() {
        return platNum;
    }

    public void setPlatNum(String platNum) {
        this.platNum = platNum;
    }

    public String getPlatCode() {
        return platCode;
    }

    public void setPlatCode(String platCode) {
        this.platCode = platCode;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgNick() {
        return orgNick;
    }

    public void setOrgNick(String orgNick) {
        this.orgNick = orgNick == null ? null : orgNick.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel == null ? null : orgLevel.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getOrgParent() {
        return orgParent;
    }

    public void setOrgParent(String orgParent) {
        this.orgParent = orgParent == null ? null : orgParent.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId == null ? null : platId.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getCloRealname() {
        return cloRealname;
    }

    public void setCloRealname(String cloRealname) {
        this.cloRealname = cloRealname == null ? null : cloRealname.trim();
    }

    public BigDecimal getCloType() {
        return cloType;
    }

    public void setCloType(BigDecimal cloType) {
        this.cloType = cloType;
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

    public String getBranchLineNum() {
        return branchLineNum;
    }

    public void setBranchLineNum(String branchLineNum) {
        this.branchLineNum = branchLineNum == null ? null : branchLineNum.trim();
    }

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum == null ? null : allLineNum.trim();
    }

    public String getBankRegion() {
        return bankRegion;
    }

    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion == null ? null : bankRegion.trim();
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

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum == null ? null : accountNum.trim();
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank == null ? null : accountBank.trim();
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum == null ? null : businessNum.trim();
    }
}