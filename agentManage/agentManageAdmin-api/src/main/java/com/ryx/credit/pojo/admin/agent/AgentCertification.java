package com.ryx.credit.pojo.admin.agent;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentCertification  implements Serializable {
    private String id;

    private String agentId;

    private String reqRegNo;

    private String reqEntName;

    private String isCache;

    private String enterpriseName;

    private String frName;

    private String regNo;

    private BigDecimal regCap;

    private String regCapCur;

    private String esDate;

    private String agBusLic;

    private Date openFrom;

    private Date openTo;

    private String enterpriseType;

    private String enterpriseStatus;

    private Date cancelDate;

    private Date revokeDate;

    private String address;

    private String abuItem;

    private String cbuItem;

    private String operateScope;

    private String operateScopeAndForm;

    private String regOrg;

    private String ancheYear;

    private Date ancheDate;

    private String industryPhyCode;

    private String industryPhyName;

    private String industryCode;

    private String industryName;

    private BigDecimal recCap;

    private String oriRegNo;

    private String creditCode;

    private Date apprDate;

    private String orgNo;

    private String usci;

    private Date reqCerTm;

    private String reqCerUser;

    private Date cerSuccessTm;

    private BigDecimal cerNum;

    private BigDecimal cerProStat;

    private BigDecimal cerRes;

    private String orgAgName;

    private BigDecimal orgAgNature;

    private BigDecimal orgAgCapital;

    private String orgAgBusLic;

    private Date orgAgBusLicb;

    private Date orgAgBusLice;

    private String orgAgLegal;

    private String orgAgRegAdd;

    private String orgAgBusScope;

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

    public String getReqRegNo() {
        return reqRegNo;
    }

    public void setReqRegNo(String reqRegNo) {
        this.reqRegNo = reqRegNo == null ? null : reqRegNo.trim();
    }

    public String getReqEntName() {
        return reqEntName;
    }

    public void setReqEntName(String reqEntName) {
        this.reqEntName = reqEntName == null ? null : reqEntName.trim();
    }

    public String getIsCache() {
        return isCache;
    }

    public void setIsCache(String isCache) {
        this.isCache = isCache == null ? null : isCache.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName == null ? null : frName.trim();
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo == null ? null : regNo.trim();
    }

    public BigDecimal getRegCap() {
        return regCap;
    }

    public void setRegCap(BigDecimal regCap) {
        this.regCap = regCap;
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur == null ? null : regCapCur.trim();
    }

    public String getEsDate() {
        return esDate;
    }

    public void setEsDate(String esDate) {
        this.esDate = esDate == null ? null : esDate.trim();
    }

    public String getAgBusLic() {
        return agBusLic;
    }

    public void setAgBusLic(String agBusLic) {
        this.agBusLic = agBusLic == null ? null : agBusLic.trim();
    }

    public Date getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(Date openFrom) {
        this.openFrom = openFrom;
    }

    public Date getOpenTo() {
        return openTo;
    }

    public void setOpenTo(Date openTo) {
        this.openTo = openTo;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType == null ? null : enterpriseType.trim();
    }

    public String getEnterpriseStatus() {
        return enterpriseStatus;
    }

    public void setEnterpriseStatus(String enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus == null ? null : enterpriseStatus.trim();
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Date getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAbuItem() {
        return abuItem;
    }

    public void setAbuItem(String abuItem) {
        this.abuItem = abuItem == null ? null : abuItem.trim();
    }

    public String getCbuItem() {
        return cbuItem;
    }

    public void setCbuItem(String cbuItem) {
        this.cbuItem = cbuItem == null ? null : cbuItem.trim();
    }

    public String getOperateScope() {
        return operateScope;
    }

    public void setOperateScope(String operateScope) {
        this.operateScope = operateScope == null ? null : operateScope.trim();
    }

    public String getOperateScopeAndForm() {
        return operateScopeAndForm;
    }

    public void setOperateScopeAndForm(String operateScopeAndForm) {
        this.operateScopeAndForm = operateScopeAndForm == null ? null : operateScopeAndForm.trim();
    }

    public String getRegOrg() {
        return regOrg;
    }

    public void setRegOrg(String regOrg) {
        this.regOrg = regOrg == null ? null : regOrg.trim();
    }

    public String getAncheYear() {
        return ancheYear;
    }

    public void setAncheYear(String ancheYear) {
        this.ancheYear = ancheYear == null ? null : ancheYear.trim();
    }

    public Date getAncheDate() {
        return ancheDate;
    }

    public void setAncheDate(Date ancheDate) {
        this.ancheDate = ancheDate;
    }

    public String getIndustryPhyCode() {
        return industryPhyCode;
    }

    public void setIndustryPhyCode(String industryPhyCode) {
        this.industryPhyCode = industryPhyCode == null ? null : industryPhyCode.trim();
    }

    public String getIndustryPhyName() {
        return industryPhyName;
    }

    public void setIndustryPhyName(String industryPhyName) {
        this.industryPhyName = industryPhyName == null ? null : industryPhyName.trim();
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode == null ? null : industryCode.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public BigDecimal getRecCap() {
        return recCap;
    }

    public void setRecCap(BigDecimal recCap) {
        this.recCap = recCap;
    }

    public String getOriRegNo() {
        return oriRegNo;
    }

    public void setOriRegNo(String oriRegNo) {
        this.oriRegNo = oriRegNo == null ? null : oriRegNo.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getUsci() {
        return usci;
    }

    public void setUsci(String usci) {
        this.usci = usci == null ? null : usci.trim();
    }

    public Date getReqCerTm() {
        return reqCerTm;
    }

    public void setReqCerTm(Date reqCerTm) {
        this.reqCerTm = reqCerTm;
    }

    public String getReqCerUser() {
        return reqCerUser;
    }

    public void setReqCerUser(String reqCerUser) {
        this.reqCerUser = reqCerUser == null ? null : reqCerUser.trim();
    }

    public Date getCerSuccessTm() {
        return cerSuccessTm;
    }

    public void setCerSuccessTm(Date cerSuccessTm) {
        this.cerSuccessTm = cerSuccessTm;
    }

    public BigDecimal getCerNum() {
        return cerNum;
    }

    public void setCerNum(BigDecimal cerNum) {
        this.cerNum = cerNum;
    }

    public BigDecimal getCerProStat() {
        return cerProStat;
    }

    public void setCerProStat(BigDecimal cerProStat) {
        this.cerProStat = cerProStat;
    }

    public BigDecimal getCerRes() {
        return cerRes;
    }

    public void setCerRes(BigDecimal cerRes) {
        this.cerRes = cerRes;
    }

    public String getOrgAgName() {
        return orgAgName;
    }

    public void setOrgAgName(String orgAgName) {
        this.orgAgName = orgAgName == null ? null : orgAgName.trim();
    }

    public BigDecimal getOrgAgNature() {
        return orgAgNature;
    }

    public void setOrgAgNature(BigDecimal orgAgNature) {
        this.orgAgNature = orgAgNature;
    }

    public BigDecimal getOrgAgCapital() {
        return orgAgCapital;
    }

    public void setOrgAgCapital(BigDecimal orgAgCapital) {
        this.orgAgCapital = orgAgCapital;
    }

    public String getOrgAgBusLic() {
        return orgAgBusLic;
    }

    public void setOrgAgBusLic(String orgAgBusLic) {
        this.orgAgBusLic = orgAgBusLic == null ? null : orgAgBusLic.trim();
    }

    public Date getOrgAgBusLicb() {
        return orgAgBusLicb;
    }

    public void setOrgAgBusLicb(Date orgAgBusLicb) {
        this.orgAgBusLicb = orgAgBusLicb;
    }

    public Date getOrgAgBusLice() {
        return orgAgBusLice;
    }

    public void setOrgAgBusLice(Date orgAgBusLice) {
        this.orgAgBusLice = orgAgBusLice;
    }

    public String getOrgAgLegal() {
        return orgAgLegal;
    }

    public void setOrgAgLegal(String orgAgLegal) {
        this.orgAgLegal = orgAgLegal == null ? null : orgAgLegal.trim();
    }

    public String getOrgAgRegAdd() {
        return orgAgRegAdd;
    }

    public void setOrgAgRegAdd(String orgAgRegAdd) {
        this.orgAgRegAdd = orgAgRegAdd == null ? null : orgAgRegAdd.trim();
    }

    public String getOrgAgBusScope() {
        return orgAgBusScope;
    }

    public void setOrgAgBusScope(String orgAgBusScope) {
        this.orgAgBusScope = orgAgBusScope == null ? null : orgAgBusScope.trim();
    }
}