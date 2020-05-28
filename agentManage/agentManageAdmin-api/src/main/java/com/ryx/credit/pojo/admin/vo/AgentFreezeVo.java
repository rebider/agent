package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;

/**
 * Created by lhl on 2020/5/13.
 */
public class AgentFreezeVo implements Serializable {

    private String agId;
    private String agName;//代理商名称
    private String agNature;//公司性质
    private String agCapital;//注册资本(元)
    private String agBusLic;//营业执照
    private String agBusLicb;//营业执照起始时间
    private String agBusLice;//营业执照结束时间
    private String agLegal;//法人姓名
    private String agLegalCertype;//法人证件类型
    private String agLegalCernum;//法人证件号码
    private String agLegalMobile;//法人联系电话
    private String agHead;//负责人
    private String agHeadMobile;//负责人电话
    private String agRegArea;//注册区域
    private String agRegAdd;//注册地址
    private String agBusScope;//营业范围
    private String busContactEmail;//分润对接邮箱
    private String busRiskEmail;//投诉及风险风控对接邮箱
    private String agStatus;
    private String cTime;
    private String cIncomTime;
    private String acId;
    private String cloType;//收款账户类型
    private String cloRealname;//收款账户名
    private String cloBankAccount;//收款账号
    private String cloBank;//收款开户总行
    private String bankRegion;//开户行地区
    private String cloBankBranch;//收款开户行支行
    private String allLineNum;//总行联行号
    private String branchLineNum;//支行联行号
    private String cloTaxPoint;//税点
    private String cloInvoice;//是否开具分润发票
    private String acAgLegalCernum;//结算卡法人证件号
    private String cloReviewStatus;


    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAgNature() {
        return agNature;
    }

    public void setAgNature(String agNature) {
        this.agNature = agNature;
    }

    public String getAgCapital() {
        return agCapital;
    }

    public void setAgCapital(String agCapital) {
        this.agCapital = agCapital;
    }

    public String getAgBusLic() {
        return agBusLic;
    }

    public void setAgBusLic(String agBusLic) {
        this.agBusLic = agBusLic;
    }

    public String getAgBusLicb() {
        return agBusLicb;
    }

    public void setAgBusLicb(String agBusLicb) {
        this.agBusLicb = agBusLicb;
    }

    public String getAgBusLice() {
        return agBusLice;
    }

    public void setAgBusLice(String agBusLice) {
        this.agBusLice = agBusLice;
    }

    public String getAgLegal() {
        return agLegal;
    }

    public void setAgLegal(String agLegal) {
        this.agLegal = agLegal;
    }

    public String getAgLegalCertype() {
        return agLegalCertype;
    }

    public void setAgLegalCertype(String agLegalCertype) {
        this.agLegalCertype = agLegalCertype;
    }

    public String getAgLegalCernum() {
        return agLegalCernum;
    }

    public void setAgLegalCernum(String agLegalCernum) {
        this.agLegalCernum = agLegalCernum;
    }

    public String getAgLegalMobile() {
        return agLegalMobile;
    }

    public void setAgLegalMobile(String agLegalMobile) {
        this.agLegalMobile = agLegalMobile;
    }

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead;
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile;
    }

    public String getAgRegArea() {
        return agRegArea;
    }

    public void setAgRegArea(String agRegArea) {
        this.agRegArea = agRegArea;
    }

    public String getAgRegAdd() {
        return agRegAdd;
    }

    public void setAgRegAdd(String agRegAdd) {
        this.agRegAdd = agRegAdd;
    }

    public String getAgBusScope() {
        return agBusScope;
    }

    public void setAgBusScope(String agBusScope) {
        this.agBusScope = agBusScope;
    }

    public String getBusContactEmail() {
        return busContactEmail;
    }

    public void setBusContactEmail(String busContactEmail) {
        this.busContactEmail = busContactEmail;
    }

    public String getBusRiskEmail() {
        return busRiskEmail;
    }

    public void setBusRiskEmail(String busRiskEmail) {
        this.busRiskEmail = busRiskEmail;
    }

    public String getAgStatus() {
        return agStatus;
    }

    public void setAgStatus(String agStatus) {
        this.agStatus = agStatus;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getcIncomTime() {
        return cIncomTime;
    }

    public void setcIncomTime(String cIncomTime) {
        this.cIncomTime = cIncomTime;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public String getCloType() {
        return cloType;
    }

    public void setCloType(String cloType) {
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

    public String getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(String cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public String getCloInvoice() {
        return cloInvoice;
    }

    public void setCloInvoice(String cloInvoice) {
        this.cloInvoice = cloInvoice;
    }

    public String getAcAgLegalCernum() {
        return acAgLegalCernum;
    }

    public void setAcAgLegalCernum(String acAgLegalCernum) {
        this.acAgLegalCernum = acAgLegalCernum;
    }

    public String getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(String cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
    }

}
