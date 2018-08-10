package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class AgentoutVo extends AgentExtends implements Serializable {
    private String id;

    private String agUniqNum;

    private String agName;

    private String agHead;

    private String agentId;

    private String busNum;

    private String busPlatform;

    private String busType;
    private String busParentId;//直属上级id

    private String busParent;

    private String twoParentId;//二级上级id
    private String twoParentName;

    private String threeParentId;//三级上级id
    private String threeParentName;


    private String busRiskParent;

    private String busActivationParent;

    private String busRegion;

    private String busSentDirectly;

    private String busIndeAss;

    private String busScope;

    private BigDecimal cloType;

    private String cloRealname;

    private String cloBankAccount;

    private String cloBank;

    private String bankRegion;

    private String cloBankBranch;

    private String allLineNum;

    private String branchLineNum;

    private BigDecimal cloTaxPoint;

    private BigDecimal cloInvoice;

    private String cloPayCompany;

    private String yesOrNo;

    private String cloString;

    private String point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusParent() {
        return busParent;
    }

    public void setBusParent(String busParent) {
        this.busParent = busParent;
    }

    public String getBusRiskParent() {
        return busRiskParent;
    }

    public void setBusRiskParent(String busRiskParent) {
        this.busRiskParent = busRiskParent;
    }

    public String getBusActivationParent() {
        return busActivationParent;
    }

    public void setBusActivationParent(String busActivationParent) {
        this.busActivationParent = busActivationParent;
    }

    public String getBusRegion() {
        return busRegion;
    }

    public void setBusRegion(String busRegion) {
        this.busRegion = busRegion;
    }

    public String getBusSentDirectly() {
        return busSentDirectly;
    }

    public void setBusSentDirectly(String busSentDirectly) {
        this.busSentDirectly = busSentDirectly;
    }

    public String getBusIndeAss() {
        return busIndeAss;
    }

    public void setBusIndeAss(String busIndeAss) {
        this.busIndeAss = busIndeAss;
    }

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope;
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
        this.cloRealname = cloRealname;
    }

    public String getCloBankAccount() {
        return cloBankAccount;
    }

    public void setCloBankAccount(String cloBankAccount) {
        this.cloBankAccount = cloBankAccount;
    }

    public String getCloBank() {
        return cloBank;
    }

    public void setCloBank(String cloBank) {
        this.cloBank = cloBank;
    }

    public String getBankRegion() {
        return bankRegion;
    }

    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
    }

    public String getCloBankBranch() {
        return cloBankBranch;
    }

    public void setCloBankBranch(String cloBankBranch) {
        this.cloBankBranch = cloBankBranch;
    }

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum;
    }

    public String getBranchLineNum() {
        return branchLineNum;
    }

    public void setBranchLineNum(String branchLineNum) {
        this.branchLineNum = branchLineNum;
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

    public String getCloPayCompany() {
        return cloPayCompany;
    }

    public void setCloPayCompany(String cloPayCompany) {
        this.cloPayCompany = cloPayCompany;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(String yesOrNo) {

        this.yesOrNo = yesOrNo;
    }

    public String getCloString() {
        return cloString;
    }

    public void setCloString(String cloString) {
        this.cloString = cloString;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getBusParentId() {
        return busParentId;
    }

    public void setBusParentId(String busParentId) {
        this.busParentId = busParentId;
    }

    public String getTwoParentId() {
        return twoParentId;
    }

    public void setTwoParentId(String twoParentId) {
        this.twoParentId = twoParentId;
    }

    public String getTwoParentName() {
        return twoParentName;
    }

    public void setTwoParentName(String twoParentName) {
        this.twoParentName = twoParentName;
    }

    public String getThreeParentId() {
        return threeParentId;
    }

    public void setThreeParentId(String threeParentId) {
        this.threeParentId = threeParentId;
    }

    public String getThreeParentName() {
        return threeParentName;
    }

    public void setThreeParentName(String threeParentName) {
        this.threeParentName = threeParentName;
    }
}
